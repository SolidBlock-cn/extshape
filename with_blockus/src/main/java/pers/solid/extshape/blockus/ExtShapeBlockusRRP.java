package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.data.providers.BlockusRecipeProvider;
import com.brand.blockus.utils.tags.BlockusBlockTags;
import com.brand.blockus.utils.tags.BlockusItemTags;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.RRPEventHelper;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BlockResourceGenerator;
import pers.solid.brrp.v1.generator.TextureRegistry;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rrp.UnusualLootTables;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @see ExtShapeRRP
 */
public final class ExtShapeBlockusRRP {
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShapeBlockusRRP.class);
  public static final RuntimeResourcePack PACK = RuntimeResourcePack.create(new Identifier(ExtShapeBlockus.NAMESPACE, "pack"));

  private ExtShapeBlockusRRP() {
  }

  public static void registerRRP() {
    generateServerData(PACK);
    PACK.setSidedRegenerationCallback(ResourceType.SERVER_DATA, () -> {
      PACK.clearResources(ResourceType.SERVER_DATA);
      generateServerData(PACK);
    });
    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
      generateClientResources(PACK);
      PACK.setSidedRegenerationCallback(ResourceType.CLIENT_RESOURCES, () -> {
        PACK.clearResources(ResourceType.CLIENT_RESOURCES);
        generateClientResources(PACK);
      });
    }
    RRPEventHelper.BEFORE_VANILLA.registerPack(PACK);
  }

  /**
   * 为运行时资源包添加客户端资源。该方法执行时不会清除已生成的资源（如有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时资源包。
   */
  @Environment(EnvType.CLIENT)
  public static void generateClientResources(RuntimeResourcePack pack) {
    LOGGER.info("Generating client resources for Extended Block Shapes mod!");

    // 注册纹理变量。
    registerTextures();

    // 为方块添加资源。
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeAssets(pack);
    }
  }

  @Environment(EnvType.CLIENT)
  private static void registerTextures() {
    TextureRegistry.register(BlockusBlocks.ROUGH_BASALT.block, new Identifier("block/basalt_top"));
    TextureRegistry.register(BlockusBlocks.ROUGH_SANDSTONE.block, new Identifier("block/sandstone_bottom"));
    TextureRegistry.register(BlockusBlocks.ROUGH_RED_SANDSTONE.block, new Identifier("block/red_sandstone_bottom"));
    TextureRegistry.register(BlockusBlocks.ROUGH_SOUL_SANDSTONE.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_bottom"));

    TextureRegistry.registerSuffixed(BlockusBlocks.STRIPPED_WHITE_OAK_LOG, TextureKey.END, "_top");
    TextureRegistry.register(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, new Identifier(Blockus.MOD_ID, "block/stripped_white_oak_log"));
    TextureRegistry.registerSuffixed(BlockusBlocks.WHITE_OAK_LOG, TextureKey.END, "_top");
    TextureRegistry.register(BlockusBlocks.WHITE_OAK_WOOD, new Identifier(Blockus.MOD_ID, "block/white_oak_log"));

    TextureRegistry.registerSuffixed(BlockusBlocks.SOUL_SANDSTONE.block, TextureKey.TOP, "_top");
    TextureRegistry.register(BlockusBlocks.SMOOTH_SOUL_SANDSTONE.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_top"));

    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      TextureRegistry.registerSuffixed(block, TextureKey.END, "_top");
    }

    for (final Supplier<Block> supplier : ImmutableList.<Supplier<Block>>of(
        () -> BlockusBlocks.OAK_SMALL_LOGS,
        () -> BlockusBlocks.SPRUCE_SMALL_LOGS,
        () -> BlockusBlocks.BIRCH_SMALL_LOGS,
        () -> BlockusBlocks.JUNGLE_SMALL_LOGS,
        () -> BlockusBlocks.ACACIA_SMALL_LOGS,
        () -> BlockusBlocks.DARK_OAK_SMALL_LOGS,
        () -> BlockusBlocks.MANGROVE_SMALL_LOGS,
        () -> BlockusBlocks.WARPED_SMALL_STEMS,
        () -> BlockusBlocks.CRIMSON_SMALL_STEMS,
        () -> BlockusBlocks.WHITE_OAK_SMALL_LOGS
    )) {
      Block block = supplier.get();
      TextureRegistry.registerSuffixed(block, TextureKey.END, "_top");
    }
  }

  /**
   * 为运行时资源包添加服务器数据。该方法执行时不会清除已经生成的数据（若有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时数据包。
   */
  public static void generateServerData(RuntimeResourcePack pack) {
    LOGGER.info("Generating server data for Extended Block Shapes - Blockus mod!");

    generateBlockusBlockData(pack);
    generateBlockusCrossShapeData(pack);
    generateTags(pack);
    generateStainedStoneBricksRecipe(pack);
    generateShingleDyeingRecipes(pack);
    generatePaperCookingRecipes(pack);
  }

  private static void generatePaperCookingRecipes(RuntimeResourcePack pack) {
    for (BlockShape blockShape : BlockShape.values()) {
      final Block burntPaperBlock = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.BURNT_PAPER_BLOCK);
      final Block paperBlock = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.PAPER_BLOCK);
      if (burntPaperBlock != null && paperBlock != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(burntPaperBlock)) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(paperBlock), blockShape.getRecipeCategory(), burntPaperBlock, 0.1F * blockShape.logicalCompleteness, (int) (200 * blockShape.logicalCompleteness)).criterion("has_paper_block", RecipeProvider.conditionsFromItem(paperBlock)).offerTo(pack.getRecipeExporter(), BRRPUtils.getRecipeId(burntPaperBlock).brrp_suffixed("_from_smelting"));
      }
    }
  }

  private static void generateShingleDyeingRecipes(RuntimeResourcePack pack) {
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      Item dyeItem = Registries.ITEM.get(new Identifier(Identifier.DEFAULT_NAMESPACE, StringUtils.substringBefore(Registries.BLOCK.getId(bssTypes.block).getPath(), "_shingle") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bssTypes.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final CraftingRecipeJsonBuilder recipe = BlockusRecipeProvider
            .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("shingles_" + blockShape.asString() + "_from_dyeing")
            .criterion(RecipeProvider.hasItem(BlockusBlocks.SHINGLES.block), RecipeProvider.conditionsFromItem(BlockusBlocks.SHINGLES.block));
        recipe.offerTo(pack.getRecipeExporter(), new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
      }
    }
  }

  private static void generateStainedStoneBricksRecipe(RuntimeResourcePack pack) {
    for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      Item dyeItem = Registries.ITEM.get(new Identifier(Identifier.DEFAULT_NAMESPACE, StringUtils.substringBefore(Registries.BLOCK.getId(bsswTypes.block).getPath(), "_stone_brick") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswTypes.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final CraftingRecipeJsonBuilder recipe = BlockusRecipeProvider
            .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("stained_stone_brick_" + blockShape.asString() + "_from_dyeing")
            .criterion(RecipeProvider.hasItem(Blocks.STONE_BRICKS), RecipeProvider.conditionsFromItem(Blocks.STONE_BRICKS));
        recipe.offerTo(pack.getRecipeExporter(), new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
      }
    }
  }

  /**
   * 为一系列木质方块的各个形状生成由这些方块烧炼成烧焦的木板的各个形状的配方。由于在 Blockus 模组中只支持对木板、木马赛克烧焦，故模组亦不再允许对木头和木马赛克的各个形状烧焦。
   */
  private static void generateWoodCharringRecipes(RuntimeResourcePack pack, Collection<Block> charrablePlanks, Block charredBaseBlock) {
    for (BlockShape blockShape : BlockShape.values()) {
      final Block charredOutput = BlockBiMaps.getBlockOf(blockShape, charredBaseBlock);
      if (charredOutput != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(charredOutput)) {
        final ItemConvertible[] ingredients = charrablePlanks.stream().map(block -> BlockBiMaps.getBlockOf(blockShape, block)).filter(Predicates.notNull()).toArray(ItemConvertible[]::new);
        final CookingRecipeJsonBuilder cookingRecipe = CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ingredients), blockShape.getRecipeCategory(), charredOutput, 0.1F * blockShape.logicalCompleteness, (int) (200 * blockShape.logicalCompleteness)).criterion("has_planks", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create().items(ingredients).build()));
        cookingRecipe.offerTo(pack.getRecipeExporter(), new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(charredOutput) + "_from_smelting"));
      }
    }
  }

  private static void generateBlockusCrossShapeData(RuntimeResourcePack pack) {
    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      // 由于糖方块是已弃用的方块，故忽略。
      if (baseBlock == BlockusBlocks.SUGAR_BLOCK) continue;
      new BlockusCrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, pack).generateCrossShapeData();
    }
  }

  private static void generateTags(RuntimeResourcePack pack) {
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.STAIRS, ItemTags.STAIRS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.SLABS, ItemTags.SLABS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.FENCES, ItemTags.FENCES);
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.WALLS, ItemTags.WALLS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_SLABS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.QUARTER_PIECES);
    ExtShapeBlockusTags.TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_STAIRS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_QUARTER_PIECES);

    ExtShapeTags.SHAPE_TO_LOG_TAG.values().forEach(ExtShapeBlockusTags.TAG_PREPARATIONS::forceSetBlockTagWithItem);
    ExtShapeTags.SHAPE_TO_WOODEN_TAG.values().forEach(ExtShapeBlockusTags.TAG_PREPARATIONS::forceSetBlockTagWithItem);

    ExtShapeBlockusTags.TAG_PREPARATIONS.setBlockTagWithItem(BlockusBlockTags.ALL_PATTERNED_WOOLS, BlockusItemTags.ALL_PATTERNED_WOOLS);
    ExtShapeBlockusTags.TAG_PREPARATIONS.write(pack);
  }

  private static void generateBlockusBlockData(RuntimeResourcePack pack) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      final Block baseBlock = generator.getBaseBlock();
      if (baseBlock != BlockusBlocks.SUGAR_BLOCK) generator.writeRecipes(pack);
      final UnusualLootTables.LootTableFunction lootTableFunction = BlockusUnusualLootTables.INSTANCE.get(baseBlock);
      if (lootTableFunction != null) {
        pack.addLootTable(generator.getLootTableId(), lootTableFunction.apply(baseBlock, BlockShape.getShapeOf(block), block));
      } else {
        generator.writeLootTable(pack);
      }
    }
  }
}
