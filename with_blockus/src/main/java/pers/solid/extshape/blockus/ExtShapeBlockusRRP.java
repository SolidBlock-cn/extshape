package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.data.provider.BlockusRecipeProvider;
import com.brand.blockus.tags.BlockusBlockTags;
import com.brand.blockus.tags.BlockusItemTags;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.resource.ResourceType;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.RRPEventHelper;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BlockResourceGenerator;
import pers.solid.brrp.v1.generator.TextureRegistry;
import pers.solid.extshape.blockus.mixin.CookingRecipeJsonProviderAccessor;
import pers.solid.extshape.blockus.mixin.ShapedRecipeJsonProviderAccessor;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rrp.UnusualLootTables;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.List;
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
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_BASALT.block, block -> TextureRegistry.register(block, new Identifier("block/basalt_top")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier("block/sandstone_bottom")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_RED_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier("block/red_sandstone_bottom")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SOUL_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_bottom")));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_LOG, block -> TextureRegistry.registerSuffixed(block, TextureKey.END, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/stripped_white_oak_log")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_LOG, block -> TextureRegistry.registerSuffixed(block, TextureKey.END, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_WOOD, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/white_oak_log")));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE, bsswTypes -> TextureRegistry.registerSuffixed(bsswTypes.block, TextureKey.TOP, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMOOTH_SOUL_SANDSTONE, bssTypes -> TextureRegistry.register(bssTypes.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_top")));

    for (Supplier<Block> supplier : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      ExtShapeBlockus.tryConsume(supplier, block -> TextureRegistry.registerSuffixed(block, TextureKey.END, "_top"));
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
      ExtShapeBlockus.tryConsume(supplier, block -> TextureRegistry.registerSuffixed(block, TextureKey.END, "_top"));
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
    generatePlankCookingRecipe(pack);
    generateHerringBonePlanksCookingRecipe(pack);
    generateStainedStoneBricksRecipe(pack);
    generateShingleDyeingRecipes(pack);
    generatePaperCookingRecipes(pack);
  }

  private static void generatePaperCookingRecipes(RuntimeResourcePack pack) {
    for (BlockShape blockShape : BlockShape.values()) {
      final Block burntPaperBlock = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.BURNT_PAPER_BLOCK);
      final Block paperBlock = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.PAPER_BLOCK);
      if (burntPaperBlock != null && paperBlock != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(burntPaperBlock)) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(paperBlock), burntPaperBlock, 0.1F * blockShape.logicalCompleteness, (int) (200 * blockShape.logicalCompleteness)).criterion("has_paper_block", RecipeProvider.conditionsFromItem(paperBlock)).offerTo(recipeJsonProvider -> {
          pack.addRecipe(recipeJsonProvider.getRecipeId(), recipeJsonProvider);
          pack.addAdvancement(recipeJsonProvider.getAdvancementId(), ((CookingRecipeJsonProviderAccessor) recipeJsonProvider).getAdvancementBuilder());
        }, BRRPUtils.getRecipeId(burntPaperBlock).brrp_suffixed("_from_smelting"));
      }
    }
  }

  private static void generateShingleDyeingRecipes(RuntimeResourcePack pack) {
    for (Supplier<BSSTypes> supplier : BlockusBlockCollections.TINTED_SHRINGLES) {
      ExtShapeBlockus.tryConsume(supplier, bssTypes -> {
        Item dyeItem = Registry.ITEM.get(new Identifier("minecraft", StringUtils.substringBefore(Registry.BLOCK.getId(bssTypes.block).getPath(), "_shingle") + "_dye"));
        for (BlockShape blockShape : BlockShape.values()) {
          final Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block);
          final Block dyed = BlockBiMaps.getBlockOf(blockShape, bssTypes.block);
          if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
          final CraftingRecipeJsonBuilder recipe = BlockusRecipeProvider
              .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
              .group("shingles_" + blockShape.asString() + "_from_dyeing")
              .criterion(RecipeProvider.hasItem(BlockusBlocks.SHINGLES.block), RecipeProvider.conditionsFromItem(BlockusBlocks.SHINGLES.block));
          recipe.offerTo(recipeJsonProvider -> {
            pack.addRecipe(recipeJsonProvider.getRecipeId(), recipeJsonProvider);
            pack.addAdvancement(recipeJsonProvider.getAdvancementId(), ((ShapedRecipeJsonProviderAccessor) recipeJsonProvider).getAdvancementBuilder());
          }, new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
        }
      });
    }
  }

  private static void generateStainedStoneBricksRecipe(RuntimeResourcePack pack) {
    for (Supplier<BSSWTypes> supplier : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      ExtShapeBlockus.tryConsume(supplier, bsswTypes -> {
        Item dyeItem = Registry.ITEM.get(new Identifier("minecraft", StringUtils.substringBefore(Registry.BLOCK.getId(bsswTypes.block).getPath(), "_stone_brick") + "_dye"));
        for (BlockShape blockShape : BlockShape.values()) {
          final Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
          final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswTypes.block);
          if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
          final CraftingRecipeJsonBuilder recipe = BlockusRecipeProvider
              .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
              .group("stained_stone_brick_" + blockShape.asString() + "_from_dyeing")
              .criterion(RecipeProvider.hasItem(Blocks.STONE_BRICKS), RecipeProvider.conditionsFromItem(Blocks.STONE_BRICKS));
          recipe.offerTo(recipeJsonProvider -> {
            pack.addRecipe(recipeJsonProvider.getRecipeId(), recipeJsonProvider);
            pack.addAdvancement(recipeJsonProvider.getAdvancementId(), ((ShapedRecipeJsonProviderAccessor) recipeJsonProvider).getAdvancementBuilder());
          }, new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
        }
      });
    }
  }

  private static void generateHerringBonePlanksCookingRecipe(RuntimeResourcePack pack) {
    final List<Block> herringBonePlanksThatBurn = ImmutableList.of(BlockusBlocks.HERRINGBONE_OAK_PLANKS, BlockusBlocks.HERRINGBONE_BIRCH_PLANKS, BlockusBlocks.HERRINGBONE_SPRUCE_PLANKS, BlockusBlocks.HERRINGBONE_JUNGLE_PLANKS, BlockusBlocks.HERRINGBONE_ACACIA_PLANKS, BlockusBlocks.HERRINGBONE_DARK_OAK_PLANKS, BlockusBlocks.HERRINGBONE_MANGROVE_PLANKS, BlockusBlocks.HERRINGBONE_WHITE_OAK_PLANKS, BlockusBlocks.HERRINGBONE_BAMBOO_PLANKS);
    for (BlockShape blockShape : BlockShape.values()) {
      final Block charredOutput = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.HERRINGBONE_CHARRED_PLANKS);
      if (charredOutput != null) {
        final ItemConvertible[] ingredients = herringBonePlanksThatBurn.stream().map(block -> BlockBiMaps.getBlockOf(blockShape, block)).filter(Predicates.notNull()).toArray(ItemConvertible[]::new);
        final CookingRecipeJsonBuilder cookingRecipe = CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ingredients), charredOutput, 0.1F * blockShape.logicalCompleteness, (int) (200 * blockShape.logicalCompleteness)).criterion("has_planks", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create().items(ingredients).build()));
        cookingRecipe.offerTo(recipeJsonProvider -> {
          pack.addRecipe(recipeJsonProvider.getRecipeId(), recipeJsonProvider);
          pack.addAdvancement(recipeJsonProvider.getAdvancementId(), ((CookingRecipeJsonProviderAccessor) recipeJsonProvider).getAdvancementBuilder());
        }, new Identifier(ExtShapeBlockus.NAMESPACE, RecipeProvider.getItemPath(charredOutput) + "_from_smelting"));
      }
    }
  }

  private static void generatePlankCookingRecipe(RuntimeResourcePack pack) {
    final List<Block> planksThatBurn = ImmutableList.of(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, BlockusBlocks.BAMBOO.planks, BlockusBlocks.WHITE_OAK.planks, Blocks.MANGROVE_PLANKS, BlockusBlocks.LEGACY_PLANKS);
    for (BlockShape blockShape : BlockShape.values()) {
      final Block charredOutput = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.CHARRED.planks);
      if (charredOutput != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(charredOutput)) {
        final ItemConvertible[] ingredients = planksThatBurn.stream().map(block -> BlockBiMaps.getBlockOf(blockShape, block)).filter(Predicates.notNull()).toArray(ItemConvertible[]::new);
        final CookingRecipeJsonBuilder cookingRecipe = CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ingredients), charredOutput, 0.1F * blockShape.logicalCompleteness, ((int) (200 * blockShape.logicalCompleteness))).criterion("has_planks", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create().items(ingredients).build()));
        cookingRecipe.offerTo(recipeJsonProvider -> {
          pack.addRecipe(recipeJsonProvider.getRecipeId(), recipeJsonProvider);
          pack.addAdvancement(recipeJsonProvider.getAdvancementId(), ((CookingRecipeJsonProviderAccessor) recipeJsonProvider).getAdvancementBuilder());
        }, BRRPUtils.getRecipeId(charredOutput).brrp_suffixed("_from_smelting"));
      }
    }
  }

  private static void generateBlockusCrossShapeData(RuntimeResourcePack pack) {
    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      new BlockusCrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, pack).generateCrossShapeData();
    }
  }

  private static void generateTags(RuntimeResourcePack pack) {
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.STAIRS, ItemTags.STAIRS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.SLABS, ItemTags.SLABS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.FENCES, ItemTags.FENCES);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.WALLS, ItemTags.WALLS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_SLABS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.QUARTER_PIECES);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_STAIRS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_QUARTER_PIECES);

    ExtShapeTags.SHAPE_TO_LOG_TAG.values().forEach(ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS::forceSetBlockTagWithItem);
    ExtShapeTags.SHAPE_TO_WOODEN_TAG.values().forEach(ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS::forceSetBlockTagWithItem);

    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockusBlockTags.ALL_PATTERNED_WOOLS, BlockusItemTags.ALL_PATTERNED_WOOLS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.write(pack);
  }

  private static void generateBlockusBlockData(RuntimeResourcePack pack) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeRecipes(pack);
      final Block baseBlock = generator.getBaseBlock();
      final UnusualLootTables.LootTableFunction lootTableFunction = ExtShapeUnusualLootTables.INSTANCE.get(baseBlock);
      if (lootTableFunction != null) {
        pack.addLootTable(generator.getLootTableId(), lootTableFunction.apply(baseBlock, BlockShape.getShapeOf(block), block));
      } else {
        generator.writeLootTable(pack);
      }
    }
  }
}
