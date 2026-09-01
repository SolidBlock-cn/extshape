package pers.solid.extshape;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.display.CuttingRecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.*;
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

  private static final Identifier defaultId = Identifier.of(MOD_ID, "default");

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
    ExtShapeBlocks.init();
    ExtShapeTags.init();

    // registerFlammableBlocks(); 关于注册可燃方块的部分，请直接参见 ExtShapeBlocks 中的有关代码。
    VanillaItemGroup.registerForMod();
    ResourceManagerHelper.registerBuiltinResourcePack(id("recipe_tweak"), FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(), Text.translatable("resourcePack.extshape.recipe_tweak.name"), ResourcePackActivationType.DEFAULT_ENABLED);

    registerStrippableBlocks();
    registerFuels();

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

    final Registry<Block> blockRegistry = server.getRegistryManager().getOrThrow(RegistryKeys.BLOCK);
    final Registry<Item> itemRegistry = server.getRegistryManager().getOrThrow(RegistryKeys.ITEM);
    for (Block block : blocks) {
      if (!block.isEnabled(server.getOverworld().getEnabledFeatures())) {
        continue;
      }
      if (block instanceof ExtShapeBlockInterface i) {
        final Block baseBlock = i.getBaseBlock();
        final RegistryEntry<Block> blockEntry = blockRegistry.getEntry(block);
        final RegistryEntry<Block> baseBlockEntry = blockRegistry.getEntry(baseBlock);

        for (TagKey<Block> tag : ImmutableSet.of(BlockTags.AXE_MINEABLE, BlockTags.HOE_MINEABLE, BlockTags.PICKAXE_MINEABLE, BlockTags.SHOVEL_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.NEEDS_IRON_TOOL, BlockTags.NEEDS_STONE_TOOL, BlockTags.SWORD_EFFICIENT, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_COPPER_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
          final boolean blockInTag = blockEntry.isIn(tag);
          final boolean baseBlockInTag = baseBlockEntry.isIn(tag);

          // 在 1.21.11 及之前的版本，豁免栅栏门的 mineable/axe 和墙的 mineable/pickaxe 标签，会在 ToolComponentMixin 中予以特殊处理
          if (tag == BlockTags.AXE_MINEABLE && blockEntry.isIn(BlockTags.FENCE_GATES)) continue;
          if (tag == BlockTags.PICKAXE_MINEABLE && blockEntry.isIn(BlockTags.WALLS)) continue;
          if (blockInTag != baseBlockInTag) {
            LOGGER.error("Tag check for {} does not match! The block {} in the tag: {}, but the base block {} in the tag: {}", tag.id(), Registries.BLOCK.getId(block), blockInTag, Registries.BLOCK.getId(baseBlock), baseBlockInTag);
            errors++;
          }
        }
        for (Map.Entry<BlockShape, TagKey<Block>> entry : ExtShapeTags.SHAPE_TO_TAG.entrySet()) {
          BlockShape blockShape = entry.getKey();
          TagKey<Block> blockTagKey = entry.getValue();
          final boolean blockInShape = blockShape.test(block);
          final boolean blockInTag = blockEntry.isIn(blockTagKey);
          if (blockInShape != blockInTag) {
            LOGGER.error("Tag check for {} does not match! The block {} in the shape: {}, but the block in the shape tag: {}", blockTagKey.id(), Registries.BLOCK.getId(block), blockInShape, blockInTag);
            errors++;
          }

          final Item item = block.asItem();
          TagKey<Item> itemTagKey = TagKey.of(RegistryKeys.ITEM, blockTagKey.id());
          final boolean itemInTag = itemRegistry.getEntry(item).isIn(itemTagKey);
          if (blockInShape != itemInTag) {
            LOGGER.error("Tag check for {} does not match! The block item {} in the shape: {}, but the item in the shape tag: {}", itemTagKey.id(), Registries.ITEM.getId(item), blockInShape, itemInTag);
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
    final CuttingRecipeDisplay.Grouping<StonecuttingRecipe> stonecutter = server.getRecipeManager().getStonecutterRecipes();
    int errors = 0;

    final Object2IntMap<BlockShape> stoneCuttableShapes = Util.make(new Object2IntOpenHashMap<>(), map -> {
      map.put(BlockShape.STAIRS, 1);
      map.put(BlockShape.SLAB, 2);
      map.put(BlockShape.VERTICAL_QUARTER_PIECE, 4);
      map.put(BlockShape.VERTICAL_SLAB, 2);
      map.put(BlockShape.VERTICAL_STAIRS, 1);
      map.put(BlockShape.QUARTER_PIECE, 4);
      map.put(BlockShape.WALL, 1);
    });
    for (Block baseBlock : baseBlocks) {
      final CuttingRecipeDisplay.Grouping<StonecuttingRecipe> set = stonecutter.filter(new ItemStack(baseBlock));
      if (set.isEmpty()) {
        continue;
      }

      // 映射，键为对应合成产物的基础方块（可能和 baseBlock 变量的值相同或不同），值为形状到布尔值的映射（方块存在且可合成的为 true，方块存在且不可合成的为 false，方块不存在的为 null）
      final Map<Block, Set<BlockShape>> map = new HashMap<>();
      for (CuttingRecipeDisplay.GroupEntry<StonecuttingRecipe> entry : set.entries()) {
        final Optional<RecipeEntry<StonecuttingRecipe>> optional = entry.recipe().recipe();
        if (optional.isEmpty()) {
          continue;
        }

        final RecipeEntry<StonecuttingRecipe> recipeHolder = optional.get();
        final StonecuttingRecipe recipe = recipeHolder.value();
        final Item resultItem;
        final int resultCount;

        if (recipe.createResultDisplay() instanceof SlotDisplay.ItemSlotDisplay(RegistryEntry<Item> resultItemHolder)) {
          resultItem = resultItemHolder.value();
          resultCount = 1;
        } else if (recipe.createResultDisplay() instanceof SlotDisplay.StackSlotDisplay(ItemStack stack)) {
          resultItem = stack.getItem();
          resultCount = stack.getCount();
        } else {
          continue;
        }

        if (!recipeHolder.id().getValue().getPath().startsWith(Registries.ITEM.getId(resultItem).getPath())) {
          LOGGER.error("Stonecutting recipe name mismatches! Recipe name {} does not start with item name of {}.", recipeHolder.id().getValue(), Registries.ITEM.getId(resultItem));
          errors++;
        }

        if (!(resultItem instanceof BlockItem blockItem)) {
          continue;
        }

        final Block resultBlock = blockItem.getBlock();
        final BlockShape resultShape = BlockShape.getShapeOf(resultBlock);
        if (resultShape != null) {
          final Block resultBase = BlockBiMaps.of(resultShape).inverse().get(resultBlock);
          if (resultBase == null) {
            continue;
          }
          if (!stoneCuttableShapes.containsKey(resultShape)) {
            LOGGER.error("The shape {} should not be stone-cut, but {} can be cut into these shapes of {}!", resultShape.asString(), Registries.BLOCK.getId(baseBlock), Registries.BLOCK.getId(resultBase));
            errors++;
          }
          /* 检测切石产生的方块数量是否符合要求。
          由于一个铜能切成 4 个切制铜块，因此切成各种形状也是按照 4 倍（原版也是如此）。目前，模组不对切石配方进行此检测。

          if (stoneCuttableShapes.getInt(resultShape) != resultCount) {
            LOGGER.error("Result count mismatches! The shape {} is expected to have {} results, but {} can be cut into {} items of {}!", resultShape.getSerializedName(), stoneCuttableShapes.getInt(resultShape), Registries.BLOCK.getId(baseBlock), resultCount, Registries.ITEM.getId(resultItem));
          }*/

          map.computeIfAbsent(resultBase, block -> new HashSet<>()).add(resultShape);
        }
      }

      for (Map.Entry<Block, Set<BlockShape>> e : map.entrySet()) {
        final Block resultBase = e.getKey();
        final Set<BlockShape> craftableShapes = e.getValue();
        final Set<BlockShape> uncraftableShapes = stoneCuttableShapes.keySet().stream().filter(blockShape -> BlockBiMaps.getBlockOf(blockShape, resultBase) != null).filter(blockShape -> !craftableShapes.contains(blockShape)).collect(Collectors.toSet());


        if (!uncraftableShapes.isEmpty()) {
          LOGGER.error("{} can be stone-cut into {} of {}, but cannot be cut into {} of that block!", Registries.BLOCK.getId(baseBlock), craftableShapes.stream().map(BlockShape::asString).collect(Collectors.joining(", ")), Registries.BLOCK.getId(resultBase), uncraftableShapes.stream().map(BlockShape::asString).collect(Collectors.joining(", ")));
          errors++;
        }
      }
    }

    if (errors > 0) {
      throw new IllegalStateException("Failed to validate stonecutting recipes with " + errors + " errors!");
    }
  }

  /**
   * 可通过斧去皮的方块，包括模组中的。
   */
  public static final Map<Block, Block> EXTENDED_STRIPPABLE_BLOCKS = new HashMap<>();

  /**
   * 注册所有可去皮的方块。考虑到存在复杂的方块状态的情况，故不使用 {@link net.fabricmc.fabric.api.registry.StrippableBlockRegistry}，而使用 {@link pers.solid.extshape.mixin.AxeItemMixin}。
   */
  private static void registerStrippableBlocks() {
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
          EXTENDED_STRIPPABLE_BLOCKS.put(input, output);
        }
      }
    });
  }

  /**
   * 在初始化时，注册所有的燃料。注意：对于 Forge 版本，物品的燃烧由 {@code IForgeItem} 的相关接口决定。部分是直接由其标签决定的，例如木制、竹制的楼梯、台阶，原版的标签即定义了可作为燃料。
   *
   * @see ExtShapeBlocks
   * @see net.minecraft.item.FuelRegistry#createDefault(RegistryWrapper.WrapperLookup, FeatureSet)
   */
  @ApiStatus.AvailableSince("1.5.0")
  private static void registerFuels() {
    final Object2IntMap<TagKey<Block>> map = new Object2IntOpenHashMap<>();

    // 参照原版木制（含下界木）楼梯和台阶，楼梯燃烧时间为 300 刻，台阶燃烧时间为 150 刻。
    // 但是，non_flammable_wood 标签的仍然不会被熔炉接受。
    map.put(ExtShapeTags.WOODEN_VERTICAL_STAIRS, 300);
    map.put(ExtShapeTags.WOODEN_VERTICAL_SLABS, 150);
    map.put(ExtShapeTags.WOODEN_QUARTER_PIECES, 75);
    map.put(ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES, 75);
    map.put(ExtShapeTags.WOODEN_WALLS, 300);

    // 参照原版羊毛燃烧时间为 100 刻，楼梯燃烧时间和基础方块相同，台阶燃烧时间为一半。
    map.put(ExtShapeTags.WOOLEN_STAIRS, 100);
    map.put(ExtShapeTags.WOOLEN_SLABS, 50);
    map.put(ExtShapeTags.WOOLEN_QUARTER_PIECES, 25);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_STAIRS, 100);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_SLABS, 50);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES, 25);

    // 栅栏、栅栏门、压力板、燃烧时间和基础方块一致，门的燃烧时间为三分之二，按钮为三分之一。
    // 但考虑到羊毛压力板是与地毯相互合成的，故燃烧时间与地毯一致，为 67。
    map.put(ExtShapeTags.WOOLEN_FENCES, 100);
    map.put(ExtShapeTags.WOOLEN_FENCE_GATES, 100);
    map.put(ExtShapeTags.WOOLEN_PRESSURE_PLATES, 67);
    map.put(ExtShapeTags.WOOLEN_BUTTONS, 33);
    map.put(ExtShapeTags.WOOLEN_WALLS, 100);

    FuelRegistryEvents.BUILD.register((builder, context) -> map.forEach((blockTagKey, integer) -> builder.add(TagKey.of(RegistryKeys.ITEM, blockTagKey.id()), integer)));
  }

  private static void validateIdMap() {
    if (idMapToVerify == null) return;
    final List<RuntimeException> exceptions = new ArrayList<>();
    idMapToVerify.forEach((k, v) -> {
      final Identifier key = Identifier.of(k);
      try {
        Validate.validState(!Registries.BLOCK.containsId(key), "The id %s is to be replaced, but still exists in the block registry!", key);
        Validate.validState(!Registries.ITEM.containsId(key), "The id %s is to be replaced, but still exists in the item registry!", key);
      } catch (RuntimeException e) {
        exceptions.add(e);
      }
      final Identifier value = Identifier.of(v);
      try {
        Validate.validState(Registries.BLOCK.containsId(value), "The id %s is to be replace with, but does not exist in the block registry!", value);
        Validate.validState(Registries.ITEM.containsId(value), "The id %s is to be replace with, but does not exist in the item registry!", value);
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
