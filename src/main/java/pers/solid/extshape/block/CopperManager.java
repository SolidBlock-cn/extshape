package pers.solid.extshape.block;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.ExtShapeBlockTypes;
import pers.solid.extshape.util.FenceSettings;

import java.util.List;
import java.util.function.Predicate;

/**
 * 处理铜的生锈、除蜡、涂蜡的一些类。
 * @param unwaxed 未涂蜡的铜块，氧化程度从低到高的列表
 * @param waxed 涂蜡的铜块，氧化程度从低到高的列表，需要与未涂蜡的铜块对应
 */
public record CopperManager(List<Block> unwaxed, List<Block> waxed) {
  /**
   * 不同氧化等级的铜方块。
   */
  public static final ImmutableList<Block> COPPER_BLOCKS = ImmutableList.of(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
  /**
   * 不同氧化等级的切制铜方块。
   */
  public static final ImmutableList<Block> CUT_COPPER_BLOCKS = ImmutableList.of(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
  /**
   * 不同氧化等级的涂蜡铜方块。
   */
  public static final ImmutableList<Block> WAXED_COPPER_BLOCKS = ImmutableList.of(Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
  /**
   * 不同氧化等级的涂蜡切制铜方块。
   */
  public static final ImmutableList<Block> WAXED_CUT_COPPER_BLOCKS = ImmutableList.of(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER);
  /**
   * 依次排列的不同的氧化等级。
   */
  public static final ImmutableList<Oxidizable.OxidationLevel> OXIDATION_LEVELS = ImmutableList.of(Oxidizable.OxidationLevel.UNAFFECTED, Oxidizable.OxidationLevel.EXPOSED, Oxidizable.OxidationLevel.WEATHERED, Oxidizable.OxidationLevel.OXIDIZED);

  public static final CopperManager COPPER = new CopperManager(COPPER_BLOCKS, WAXED_COPPER_BLOCKS);
  public static final CopperManager CUT_COPPER = new CopperManager(CUT_COPPER_BLOCKS, WAXED_CUT_COPPER_BLOCKS);

  /**
   * 为一个特定氧化等级以及涂蜡情况的铜方块注册 {@code BlocksBuilder}。
   */
  public static BlocksBuilder registerCopperBlock(BlocksBuilderFactory blocksBuilderFactory, Block copperBase, @NotNull Oxidizable.OxidationLevel oxidationLevel, boolean waxed) {
    final BlocksBuilder builder = blocksBuilderFactory.createAllShapes(copperBase).setActivationSettings(ActivationSettings.COPPER.get(oxidationLevel));

    if (!waxed) {
      builder.addPreBuildConsumer((blockShape, oneBuilder) -> {
        if (oneBuilder instanceof StairsBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeStairsBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof SlabBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeSlabBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof QuarterPieceBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeQuarterPieceBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof VerticalStairsBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeVerticalStairsBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof VerticalSlabBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeVerticalSlabBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof VerticalQuarterPieceBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeVerticalQuarterPieceBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof FenceBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeFenceBlock.WithOxidation(x.baseBlock, s.secondIngredient, x.blockSettings, oxidationLevel));
        } else if (oneBuilder instanceof FenceGateBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeFenceGateBlock.WithOxidation(x.baseBlock, x.blockSettings, s.fenceSettings, oxidationLevel));
        } else if (oneBuilder instanceof PressurePlateBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapePressurePlateBlock.WithOxidation(x.baseBlock, x.blockSettings, s.activationSettings, oxidationLevel));
        } else if (oneBuilder instanceof ButtonBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeButtonBlock.WithOxidation(x.baseBlock, x.blockSettings, s.activationSettings, oxidationLevel));
        } else if (oneBuilder instanceof WallBuilder s) {
          s.setInstanceSupplier(x -> new ExtShapeWallBlock.WithOxidation(x.baseBlock, x.blockSettings, oxidationLevel));
        }
      });
    }

    builder.markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.COPPER_INGOT, ExtShapeBlockTypes.COPPER_WOOD_TYPE))
        .build();
    return builder;
  }

  public static void registerWithMultipleOxidizationLevel(BlocksBuilderFactory blocksBuilderFactory, List<Block> coppers, boolean waxed) {
    final BlocksBuilder[] blocksBuilders = new BlocksBuilder[coppers.size()];
    for (int i = 0; i < coppers.size(); i++) {
      final BlocksBuilder blocksBuilder = registerCopperBlock(blocksBuilderFactory, coppers.get(i), OXIDATION_LEVELS.get(i), waxed);
      blocksBuilders[i] = blocksBuilder;
      if (i > 0 && !waxed) {
        final BlocksBuilder previous = blocksBuilders[i - 1];
        for (var entry : blocksBuilder.entrySet()) {
          final BlockShape key = entry.getKey();
          final var value = entry.getValue();
          final var previousValue = previous.get(key);
          if (value != null && previousValue != null) {
            OxidizableBlocksRegistry.registerOxidizableBlockPair(previousValue.instance, value.instance);
          }
        }
      }
    }
  }

  public static void registerExtendedWax(BlocksBuilderFactory blocksBuilderFactory, List<Block> unwaxedBases, List<Block> waxedBases) {
    Preconditions.checkArgument(unwaxedBases.size() == waxedBases.size(), "unwaxedBases and waxedBases should be of same size!");
    for (int i = 0; i < unwaxedBases.size(); i++) {
      final Block unwaxedBase = unwaxedBases.get(i);
      final Block waxedBase = waxedBases.get(i);
      for (BlockShape shape : BlockShape.values()) {
        final Block unwaxed = BlockBiMaps.getBlockOf(shape, unwaxedBase);
        final Block waxed = BlockBiMaps.getBlockOf(shape, waxedBase);
        if (unwaxed != null && waxed != null && blocksBuilderFactory.instanceCollection != null && blocksBuilderFactory.instanceCollection.contains(unwaxed) && blocksBuilderFactory.instanceCollection.contains(waxed)) {
          OxidizableBlocksRegistry.registerWaxableBlockPair(unwaxed, waxed);
        }
      }
    }
  }

  public void registerBlocks(BlocksBuilderFactory blocksBuilderFactory) {
    registerWithMultipleOxidizationLevel(blocksBuilderFactory, unwaxed, false);
    registerWithMultipleOxidizationLevel(blocksBuilderFactory, waxed, true);
    registerExtendedWax(blocksBuilderFactory, unwaxed, waxed);
  }

  public static <B extends Block & ExtShapeBlockInterface & Oxidizable> MapCodec<B> createCodec(RecordCodecBuilder<B, AbstractBlock.Settings> settingsCodec, Function3<Block, AbstractBlock.Settings, Oxidizable.OxidationLevel, B> function) {
    return RecordCodecBuilder.mapCodec(instance -> instance.group(
        Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock),
        settingsCodec,
        weatheringStateField()
    ).apply(instance, function));
  }

  @NotNull
  public static <B extends Oxidizable> RecordCodecBuilder<B, Oxidizable.OxidationLevel> weatheringStateField() {
    return Oxidizable.OxidationLevel.CODEC.fieldOf("weathering_state").forGetter(Degradable::getDegradationLevel);
  }

  /**
   * 不同氧化等级的铜的激活时长（用于按钮及压力板）。
   *
   * @param oxidationLevel 氧化等级。
   * @return 方块激活持续的刻数。
   */
  public static int getActivationRate(@NotNull Oxidizable.OxidationLevel oxidationLevel) {
    return switch (oxidationLevel) {
      case UNAFFECTED -> 10;
      case EXPOSED -> 40;
      case WEATHERED -> 70;
      case OXIDIZED -> 100;
    };
  }

  public void generateWaxRecipes(RecipeExporter exporter, Predicate<Block> blockPredicate) {
    Preconditions.checkArgument(unwaxed.size() == waxed.size(), "unwaxedBlocks and waxedBlocks must be of same size!");

    for (int i = 0; i < unwaxed.size(); i++) {
      final Block unwaxedBaseBlock = unwaxed.get(i);
      final Block waxedBaseBlock = waxed.get(i);
      generateWaxRecipesForShapes(exporter, unwaxedBaseBlock, waxedBaseBlock, blockPredicate);
    }
  }

  private static void generateWaxRecipesForShapes(RecipeExporter exporter, Block unwaxedBaseBlock, Block waxedBaseBlock, Predicate<Block> blockPredicate) {
    for (BlockShape shape : BlockShape.values()) {
      final Block unwaxed = BlockBiMaps.getBlockOf(shape, unwaxedBaseBlock);
      final Block waxed = BlockBiMaps.getBlockOf(shape, waxedBaseBlock);
      if (unwaxed != null && waxed != null && blockPredicate.test(waxed)) {
        final ShapelessRecipeJsonBuilder recipe = ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, waxed)
            .input(unwaxed)
            .input(Items.HONEYCOMB)
            .group(RecipeProvider.getItemPath(waxed))
            .criterion(RecipeProvider.hasItem(unwaxed), RecipeProvider.conditionsFromItem(unwaxed));
        recipe.offerTo(exporter, Identifier.of(CraftingRecipeJsonBuilder.getItemId(waxed).getNamespace(), RecipeProvider.convertBetween(waxed, Items.HONEYCOMB)));
      }
    }
  }
}
