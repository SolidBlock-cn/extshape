package pers.solid.extshape;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.references.BlockItemId;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.BlockTransformerMappings;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.ResolvableNumber;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mixin.BlockTransformerMixin;
import pers.solid.extshape.number.ProductNumberProvider;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>欢迎使用扩展方块形状模组。本模组为许多方块提供了各个形状的变种，包括原版不存在的形状。
 * <p>本模组中的所有方块是在 {@link ExtShapeBlocks} 中创建的，创建的同时将其注册，并创建和注册对应的方块物品。本模组还提供了一定的配置功能，参见 {@link ExtShapeConfig}。
 * <p>本模组还有一个内置的方块映射管理系统，由 {@link BlockBiMaps} 提供。方块映射是指的方块与方块之间的关系。本模组的方块被创建时，就会自动加入映射中。此外，原版的方块映射也会加入。可以利用 {@link BlockBiMaps#getBlockOf} 来获取特定方块的特定形状的变种。
 * <hr>
 * <p>Welcome to use Extended Block Shapes mod, which provides various variants in different shapes of many blocks, including shapes that do not exist in vanilla Minecraft.
 * <p>Blocks of this mod are created in {@link ExtShapeBlocks}; while created, they are also registered, and so as their corresponding block items. This mod also provides a simple configuration. See {@link ExtShapeConfig}.
 * <p>This mod contains an internal block mapping management, provided by {@link BlockBiMaps}。Block mapping means the relations between blocks. Blocks in this mod are added instantly to the mappings upon created. Besides, vanilla block mappings are also added. You may get the specified variant of a specified block by {@link BlockBiMaps#getBlockOf}.
 *
 * @author SolidBlock
 */
public class ExtShape implements ModInitializer {
  /**
   * 本模组的 id，同时也是所有物品的命名空间。
   */
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShape.class);

  private static final Identifier defaultId = Identifier.fromNamespaceAndPath(MOD_ID, "default");

  /**
   * 该字段仅在开发环境中使用，在加载 DataFixer 时赋值，并在完成注册表后检查。
   */
  public static @Nullable Map<String, String> idMapToVerify = null;

  /**
   * 创建一个以模型命名 id 为命名空间的 id。
   */
  public static Identifier id(String path) {
    // 使用 withPath 是为了避免不必要地对 namespace 进行 validate。
    return defaultId.withPath(path);
  }


  @Override
  public void onInitialize() {
    ExtShapeConfig.init();
    Registry.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, id("product"), ProductNumberProvider.MAP_CODEC);
    ExtShapeBlocks.init();
    ExtShapeTags.init();

    // registerFlammableBlocks(); 关于注册可燃方块的部分，请直接参见 ExtShapeBlocks 中的有关代码。
    VanillaItemGroup.registerForMod();
    ResourceLoader.registerBuiltinPack(id("recipe_tweak"), FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(), Component.translatable("resourcePack.extshape.recipe_tweak.name"), PackActivationType.DEFAULT_ENABLED);

    EXTENDED_STRIPPABLE_BLOCKS.add(createEnhancedBlockTransformData());
    registerRegistryAliases();

    CommandRegistrationCallback.EVENT.register(RecipeConflict::registerCommand);

    FabricLoader.getInstance().getEntrypoints("extshape:post_initialize", ModInitializer.class).forEach(ModInitializer::onInitialize);

    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      validateIdMap();
      ServerLifecycleEvents.SERVER_STARTED.register(server -> {
        LOGGER.info("Validating Extended Block Shapes mod content");
        validateTagsForBlocks(server, ExtShapeBlocks.getBlocks());
        LOGGER.info("Extended Block Shapes mod content is successfully validated");

        LOGGER.info("Validating Extended Block Shapes recipes");
        validateStonecuttingForBlocks(server, ExtShapeBlocks.getBaseBlocks());
        LOGGER.info("Extended Block Shapes recipes are successfully validated");
      });
    }
  }

  /**
   * <p>检测各方块所属 mineable 标签与其基础方块是否一致，如果不一致，则会抛出错误。例如，如果基础方块有 mineable/axe 标签，其墙却没有，或者基础方块没有 mineable/pickaxe 标签，但其栅栏方块有，这些情况都会抛出错误。
   * <p>此方法只能在运行后执行，因为需要已加载数据包。
   */
  @ApiStatus.AvailableSince("3.1.5")
  public static void validateTagsForBlocks(MinecraftServer server, Collection<Block> blocks) {
    int errors = 0;

    final Registry<Block> blockRegistry = server.registryAccess().lookupOrThrow(Registries.BLOCK);
    final Registry<Item> itemRegistry = server.registryAccess().lookupOrThrow(Registries.ITEM);
    for (Block block : blocks) {
      if (!block.isEnabled(server.overworld().enabledFeatures())) {
        continue;
      }
      if (block instanceof ExtShapeBlockInterface i) {
        final Block baseBlock = i.getBaseBlock();
        final Holder<Block> blockEntry = blockRegistry.wrapAsHolder(block);
        final Holder<Block> baseBlockEntry = blockRegistry.wrapAsHolder(baseBlock);

        for (TagKey<Block> tag : ImmutableSet.of(BlockTags.MINEABLE_WITH_AXE, BlockTags.MINEABLE_WITH_HOE, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.NEEDS_IRON_TOOL, BlockTags.NEEDS_STONE_TOOL, BlockTags.SWORD_EFFICIENT, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_COPPER_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.SHEARS_MAJOR_BREAKING_SPEED, BlockTags.SHEARS_MINOR_BREAKING_SPEED, BlockTags.SHEARS_EXTREME_BREAKING_SPEED)) {
          final boolean blockInTag = blockEntry.is(tag);
          final boolean baseBlockInTag = baseBlockEntry.is(tag);
          if (blockInTag != baseBlockInTag) {
            LOGGER.error("Tag check for {} does not match! The block {} in the tag: {}, but the base block {} in the tag: {}", tag.location(), BuiltInRegistries.BLOCK.getKey(block), blockInTag, BuiltInRegistries.BLOCK.getKey(baseBlock), baseBlockInTag);
            errors++;
          }
        }
        for (Map.Entry<BlockShape, TagKey<Block>> entry : ExtShapeTags.SHAPE_TO_TAG.entrySet()) {
          BlockShape blockShape = entry.getKey();
          TagKey<Block> blockTagKey = entry.getValue();
          final boolean blockInShape = blockShape.test(block);
          final boolean blockInTag = blockEntry.is(blockTagKey);
          if (blockInShape != blockInTag) {
            LOGGER.error("Tag check for {} does not match! The block {} in the shape: {}, but the block in the shape tag: {}", blockTagKey.location(), BuiltInRegistries.BLOCK.getKey(block), blockInShape, blockInTag);
            errors++;
          }

          final Item item = block.asItem();
          TagKey<Item> itemTagKey = TagKey.create(Registries.ITEM, blockTagKey.location());
          final boolean itemInTag = itemRegistry.wrapAsHolder(item).is(itemTagKey);
          if (blockInShape != itemInTag) {
            LOGGER.error("Tag check for {} does not match! The block item {} in the shape: {}, but the item in the shape tag: {}", itemTagKey.location(), BuiltInRegistries.ITEM.getKey(item), blockInShape, itemInTag);
            errors++;
          }
        }
      }
    }

    if (errors > 0) {
      throw new IllegalStateException("Failed to validate tags for blocks with " + errors + " errors!");
    }
  }

  /**
   * 验证模组的切石配方，避免出现基础方块 A 能切成 A 的 X 形状但不能切成 Y 形状，或 A 能切成 B 的 X 形状但不能切成 Y 形状的问题。注意：仅限切石为建筑方块形状以及墙，如果切石为其他形状，则会抛出错误。
   */
  @ApiStatus.AvailableSince("3.1.5")
  public static void validateStonecuttingForBlocks(MinecraftServer server, Collection<Block> baseBlocks) {
    final SelectableRecipe.SingleInputSet<StonecutterRecipe> stonecutter = server.getRecipeManager().stonecutterRecipes();
    int errors = 0;

    final Object2IntMap<BlockShape> stoneCuttableShapes = Object2IntMap.ofEntries(
        Object2IntMap.entry(BlockShape.STAIRS, 1),
        Object2IntMap.entry(BlockShape.SLAB, 2),
        Object2IntMap.entry(BlockShape.VERTICAL_QUARTER_PIECE, 4),
        Object2IntMap.entry(BlockShape.VERTICAL_SLAB, 2),
        Object2IntMap.entry(BlockShape.VERTICAL_STAIRS, 1),
        Object2IntMap.entry(BlockShape.QUARTER_PIECE, 4),
        Object2IntMap.entry(BlockShape.WALL, 1)
    );
    for (Block baseBlock : baseBlocks) {
      final SelectableRecipe.SingleInputSet<StonecutterRecipe> set = stonecutter.selectByInput(new ItemStack(baseBlock));
      if (set.isEmpty()) {
        continue;
      }

      // 映射，键为对应合成产物的基础方块（可能和 baseBlock 变量的值相同或不同），值为形状到布尔值的映射（方块存在且可合成的为 true，方块存在且不可合成的为 false，方块不存在的为 null）
      final Map<Block, Set<BlockShape>> map = new HashMap<>();
      for (SelectableRecipe.SingleInputEntry<StonecutterRecipe> entry : set.entries()) {
        final Optional<RecipeHolder<StonecutterRecipe>> optional = entry.recipe().recipe();
        if (optional.isEmpty()) {
          continue;
        }

        final RecipeHolder<StonecutterRecipe> recipeHolder = optional.get();
        final StonecutterRecipe recipe = recipeHolder.value();
        final Item resultItem;
        final int resultCount;

        if (recipe.resultDisplay() instanceof SlotDisplay.ItemSlotDisplay(Holder<Item> resultItemHolder)) {
          resultItem = resultItemHolder.value();
          resultCount = 1;
        } else if (recipe.resultDisplay() instanceof SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate stack)) {
          resultItem = stack.item().value();
          resultCount = stack.count();
        } else {
          continue;
        }

        if (!recipeHolder.id().identifier().getPath().startsWith(BuiltInRegistries.ITEM.getKey(resultItem).getPath())) {
          LOGGER.error("Stonecutting recipe name mismatches! Recipe name {} does not start with item name of {}.", recipeHolder.id().identifier(), BuiltInRegistries.ITEM.getKey(resultItem));
          errors++;
        }

        if (!(resultItem instanceof BlockItem blockItem)) {
          continue;
        }

        final Block resultBlock = blockItem.getBlock();
        final BlockShape resultShape = BlockShape.getShapeOf(resultBlock);
        if (resultShape != null) {
          final Block resultBase = BlockBiMaps.of(resultShape).inverse().get(resultBlock);
          if (!stoneCuttableShapes.containsKey(resultShape)) {
            LOGGER.error("The shape {} should not be stone-cut, but {} can be cut into these shapes of {}!", resultShape.getSerializedName(), BuiltInRegistries.BLOCK.getKey(baseBlock), BuiltInRegistries.BLOCK.getKey(resultBase));
            errors++;
          }
          /* 检测切石产生的方块数量是否符合要求。
          由于一个铜能切成 4 个切制铜块，因此切成各种形状也是按照 4 倍（原版也是如此）。目前，模组不对切石配方进行此检测。

          if (stoneCuttableShapes.getInt(resultShape) != resultCount) {
            LOGGER.error("Result count mismatches! The shape {} is expected to have {} results, but {} can be cut into {} items of {}!", resultShape.getSerializedName(), stoneCuttableShapes.getInt(resultShape), BuiltInRegistries.BLOCK.getKey(baseBlock), resultCount, BuiltInRegistries.ITEM.getKey(resultItem));
          }*/

          map.computeIfAbsent(resultBase, _ -> new HashSet<>()).add(resultShape);
        }
      }

      for (Map.Entry<Block, Set<BlockShape>> e : map.entrySet()) {
        final Block resultBase = e.getKey();
        final Set<BlockShape> craftableShapes = e.getValue();
        final Set<BlockShape> uncraftableShapes = stoneCuttableShapes.keySet().stream().filter(blockShape -> BlockBiMaps.getBlockOf(blockShape, resultBase) != null).filter(blockShape -> !craftableShapes.contains(blockShape)).collect(Collectors.toSet());


        if (!uncraftableShapes.isEmpty()) {
          LOGGER.error("{} can be stone-cut into {} of {}, but cannot be cut into {} of that block!", BuiltInRegistries.BLOCK.getKey(baseBlock), craftableShapes.stream().map(BlockShape::getSerializedName).collect(Collectors.joining(", ")), BuiltInRegistries.BLOCK.getKey(resultBase), uncraftableShapes.stream().map(BlockShape::getSerializedName).collect(Collectors.joining(", ")));
          errors++;
        }
      }
    }

    if (errors > 0) {
      throw new IllegalStateException("Failed to validate stonecutting recipes with " + errors + " errors!");
    }
  }

  /**
   * 模组中的通过斧去皮的方块转换数据。
   */
  public static final List<BlockTransformer.BlockTransformData> EXTENDED_STRIPPABLE_BLOCKS = new ArrayList<>();

  /**
   * 创建用于本模组中的去皮方块的 {@link BlockTransformer.BlockTransformData}。本模组不修改原版的 {@link BlockTransformerMappings#AXE_STRIPPABLES}，而是通过 {@link BlockTransformerMixin} 让斧在给树去皮时，识别本模组中的可去皮方块。
   *
   * @see BlockTransformerMixin
   * @see #EXTENDED_STRIPPABLE_BLOCKS
   */
  private static BlockTransformer.BlockTransformData createEnhancedBlockTransformData() {
    final RuleBasedStateProvider.Builder ruleBasedStateProviderBuilder = RuleBasedStateProvider.builder();
    Streams.concat(
        IntStream.range(0, BlockCollections.LOGS.size()).mapToObj(i -> Pair.of(BlockCollections.LOGS.get(i), BlockCollections.STRIPPED_LOGS.get(i))),
        IntStream.range(0, BlockCollections.WOODS.size()).mapToObj(i -> Pair.of(BlockCollections.WOODS.get(i), BlockCollections.STRIPPED_WOODS.get(i))),
        IntStream.range(0, BlockCollections.HYPHAES.size()).mapToObj(i -> Pair.of(BlockCollections.HYPHAES.get(i), BlockCollections.STRIPPED_HYPHAES.get(i))),
        IntStream.range(0, BlockCollections.STEMS.size()).mapToObj(i -> Pair.of(BlockCollections.STEMS.get(i), BlockCollections.STRIPPED_STEMS.get(i))),
        Stream.of(Pair.of(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK))
    ).forEach(pair -> {
      final Block inputBase = pair.getFirst();
      final Block outputBase = pair.getSecond();
      for (BlockShape shape : BlockShape.values()) {
        final Block input = BlockBiMaps.getBlockOf(shape, inputBase);
        final Block output = BlockBiMaps.getBlockOf(shape, outputBase);
        if (input != null && output != null) {
          ruleBasedStateProviderBuilder.ifTrueThenProvide(BlockPredicate.matchesBlocks(input), new CopyPropertiesProvider(output));
        }
      }
    });

    return BlockTransformer.BlockTransformData.builder(ruleBasedStateProviderBuilder.build()).sound(SoundEvents.AXE_STRIP).build();
  }


  /**
   * 检查模组中的方块在熔炉中的燃烧情况是否与基础方块相符。
   */
  public static void verifyFuelTimes(String name, Collection<Block> baseBlocks, Predicate<Block> filter) {
    LOGGER.info("Verifying {} Fuel Times", name);
    int errors = 0;

    for (Block baseBlock : baseBlocks) {
      final ItemStack baseStack = new ItemStack(baseBlock);
      final CookingFuel baseCookingFuel = baseStack.get(DataComponents.COOKING_FUEL);
      final boolean baseIsFuel = baseCookingFuel != null;
      for (BlockShape blockShape : BlockShape.values()) {
        final Block shaped = BlockBiMaps.getBlockOf(blockShape, baseBlock);
        if (shaped == null || !filter.test(shaped)) {
          continue;
        }

        final ItemStack shapedStack = new ItemStack(shaped);
        final CookingFuel shapeCookingFuel = shapedStack.get(DataComponents.COOKING_FUEL);
        final boolean shapeIsFuel = shapeCookingFuel != null;

        if (baseIsFuel != shapeIsFuel) {
          LOGGER.error("Fuel check failed! The base block {} is fuel: {}, but its {} shape {} is fuel: {}",
              BuiltInRegistries.BLOCK.getKey(baseBlock),
              baseIsFuel,
              blockShape.getSerializedName(),
              BuiltInRegistries.BLOCK.getKey(shaped),
              shapeIsFuel);
          errors++;
        }
        if (shapeIsFuel && baseIsFuel
            && shapeCookingFuel.burnTime() instanceof ResolvableNumber.Reference(ResourceKey<NumberProvider> shapeKey)
            && baseCookingFuel.burnTime() instanceof ResolvableNumber.Reference(ResourceKey<NumberProvider> baseKey)
            && !(shapeKey.identifier().getPath().contains(baseKey.identifier().getPath()))) {
          LOGGER.error("Fuel check failed! The base block {} has burn time id: {}, but its {} shape {} has burn time id: {}",
              BuiltInRegistries.BLOCK.getKey(baseBlock),
              baseKey.identifier(),
              blockShape.getSerializedName(),
              BuiltInRegistries.BLOCK.getKey(shaped),
              shapeKey.identifier());
          errors++;
        }
      }
    }

    if (errors > 0) {
      throw new IllegalStateException(String.format("Fuel check of %s failed with %s errors!", name, errors));
    } else {
      LOGGER.info("Fuel check of {} completed", name);
    }
  }

  private static void registerRegistryAliases() {
    for (BlockItemId blockItemId : Iterables.concat(BlockItemIds.WOOL_STAIRS.asList(), BlockItemIds.WOOL_SLAB.asList(), BlockItemIds.CONCRETE_STAIRS.asList(), BlockItemIds.CONCRETE_SLAB.asList())) {
      BuiltInRegistries.BLOCK.addAlias(id(blockItemId.block().identifier().getPath()), blockItemId.block().identifier());
      BuiltInRegistries.ITEM.addAlias(id(blockItemId.item().identifier().getPath()), blockItemId.item().identifier());
    }
  }

  private static void validateIdMap() {
    if (idMapToVerify == null) return;
    final List<RuntimeException> exceptions = new ArrayList<>();
    idMapToVerify.forEach((k, v) -> {
      final Identifier key = Identifier.parse(k);
      try {
        Validate.validState(!BuiltInRegistries.BLOCK.containsKey(key), "The id %s is to be replaced, but still exists in the block registry!", key);
        Validate.validState(!BuiltInRegistries.ITEM.containsKey(key), "The id %s is to be replaced, but still exists in the item registry!", key);
      } catch (RuntimeException e) {
        exceptions.add(e);
      }
      final Identifier value = Identifier.parse(v);
      try {
        Validate.validState(BuiltInRegistries.BLOCK.containsKey(value), "The id %s is to be replace with, but does not exist in the block registry!", value);
        Validate.validState(BuiltInRegistries.ITEM.containsKey(value), "The id %s is to be replace with, but does not exist in the item registry!", value);
      } catch (RuntimeException e) {
        exceptions.add(e);
      }
    });
    if (!exceptions.isEmpty()) {
      final IllegalStateException exception = new IllegalStateException("Found invalid data fixers in Extended Block Shapes mod!");
      exceptions.forEach(exception::addSuppressed);
      throw exception;
    }
  }
}
