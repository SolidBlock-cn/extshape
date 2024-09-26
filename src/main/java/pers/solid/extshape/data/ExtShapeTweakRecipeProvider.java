package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.*;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 一些特殊的资源包，用于修复本模组中的配方与原版配方冲突的问题。
 */
public class ExtShapeTweakRecipeProvider extends FabricRecipeProvider {
  public ExtShapeTweakRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate(RecipeExporter exporter) {
    // 羊毛的特殊合成配方：羊毛压力板 ↔ 3个地毯
    for (Block baseBlock : BlockCollections.WOOLS) {
      final ExtShapePressurePlateBlock pressurePlate = (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, baseBlock);
      Preconditions.checkNotNull(pressurePlate, "pressure plate of %s", baseBlock);
      final Identifier woolId = Registries.BLOCK.getId(baseBlock);
      final Identifier carpetId = Identifier.of(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final Item carpet = Registries.ITEM.get(carpetId);
      ShapedRecipeJsonBuilder.create(pressurePlate.getRecipeCategory(), pressurePlate)
          .pattern("###")
          .input('#', carpet)
          .criterion(RecipeProvider.hasItem(carpet), RecipeProvider.conditionsFromItem(carpet))
          .group(pressurePlate.getRecipeGroup())
          .offerTo(exporter);
      final Identifier reverseRecipeId = ExtShape.id(carpetId.getPath() + "_from_pressure_plate");
      ShapelessRecipeJsonBuilder.create(pressurePlate.getRecipeCategory(), carpet, 3)
          .input(Ingredient.ofItems(pressurePlate))
          .criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(pressurePlate))
          .group("wool_from_pressure_plate")
          .offerTo(exporter, reverseRecipeId);
    }

    // 苔藓的特殊合成配方：覆地苔藓 ↔ 苔藓压力板
    {
      final ExtShapePressurePlateBlock mossPressurePlate = (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, Blocks.MOSS_BLOCK);
      Preconditions.checkNotNull(mossPressurePlate, "moss pressure plate block");
      ShapedRecipeJsonBuilder.create(mossPressurePlate.getRecipeCategory(), mossPressurePlate)
          .pattern("###")
          .input('#', Items.MOSS_CARPET)
          .criterion(RecipeProvider.hasItem(Items.MOSS_CARPET), RecipeProvider.conditionsFromItem(Items.MOSS_CARPET))
          .group(mossPressurePlate.getRecipeGroup())
          .offerTo(exporter);
      final Identifier reverseRecipeId = ExtShape.id("moss_carpet_from_pressure_plate");
      ShapelessRecipeJsonBuilder.create(mossPressurePlate.getRecipeCategory(), Blocks.MOSS_CARPET)
          .input(Ingredient.ofItems(mossPressurePlate))
          .criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(mossPressurePlate))
          .offerTo(exporter, reverseRecipeId);
    }

    // 特殊的雪台阶配方
    final ExtShapeSlabBlock snowSlab = (ExtShapeSlabBlock) BlockBiMaps.getBlockOf(BlockShape.SLAB, Blocks.SNOW_BLOCK);
    Preconditions.checkNotNull(snowSlab, "snow slab");
    ShapelessRecipeJsonBuilder.create(snowSlab.getRecipeCategory(), snowSlab)
        .input(Ingredient.ofItems(Blocks.SNOW))
        .criterion("has_snow", RecipeProvider.conditionsFromItem(Blocks.SNOW))
        .group(snowSlab.getRecipeGroup())
        .offerTo(exporter);

    // 覆盖原版的砂岩、红砂岩和石英的楼梯、台阶配方，使之不再支持属于该基础方块的方块
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.RED_SANDSTONE_SLAB, Ingredient.ofItems(Blocks.RED_SANDSTONE))
        .criterion("has_red_sandstone", conditionsFromItem(Blocks.RED_SANDSTONE))
        .offerTo(exporter);
    createStairsRecipe(Blocks.RED_SANDSTONE_STAIRS, Ingredient.ofItems(Blocks.RED_SANDSTONE))
        .criterion("has_red_sandstone", conditionsFromItem(Blocks.RED_SANDSTONE))
        .offerTo(exporter);
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.SANDSTONE_SLAB, Ingredient.ofItems(Blocks.SANDSTONE))
        .criterion("has_sandstone", conditionsFromItem(Blocks.SANDSTONE))
        .offerTo(exporter);
    createStairsRecipe(Blocks.SANDSTONE_STAIRS, Ingredient.ofItems(Blocks.SANDSTONE))
        .criterion("has_sandstone", conditionsFromItem(Blocks.SANDSTONE))
        .offerTo(exporter);
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.QUARTZ_SLAB, Ingredient.ofItems(Blocks.QUARTZ_BLOCK))
        .criterion("has_quartz_block", conditionsFromItem(Blocks.QUARTZ_BLOCK))
        .offerTo(exporter);
    createStairsRecipe(Blocks.QUARTZ_STAIRS, Ingredient.ofItems(Blocks.QUARTZ_BLOCK))
        .criterion("has_quartz_block", conditionsFromItem(Blocks.QUARTZ_BLOCK))
        .offerTo(exporter);

    // 特殊的按钮合成配方
    Iterable<Pair<Block, Ingredient>> baseAndResource = List.of(
        Pair.of(Blocks.EMERALD_BLOCK, Ingredient.fromTag(ConventionalItemTags.EMERALD_GEMS)),
        Pair.of(Blocks.IRON_BLOCK, Ingredient.fromTag(ConventionalItemTags.IRON_INGOTS)),
        Pair.of(Blocks.GOLD_BLOCK, Ingredient.fromTag(ConventionalItemTags.GOLD_INGOTS)),
        Pair.of(Blocks.DIAMOND_BLOCK, Ingredient.fromTag(ConventionalItemTags.DIAMOND_GEMS)),
        Pair.of(Blocks.COAL_BLOCK, Ingredient.ofItems(Items.COAL)),
        Pair.of(Blocks.LAPIS_BLOCK, Ingredient.fromTag(ConventionalItemTags.LAPIS_GEMS)),
        Pair.of(Blocks.PUMPKIN, Ingredient.ofItems(Items.PUMPKIN_SEEDS)),
        Pair.of(Blocks.NETHERITE_BLOCK, Ingredient.fromTag(ConventionalItemTags.NETHERITE_INGOTS)),
        Pair.of(Blocks.RAW_GOLD_BLOCK, Ingredient.ofItems(Items.RAW_GOLD)),
        Pair.of(Blocks.RAW_COPPER_BLOCK, Ingredient.ofItems(Items.RAW_COPPER)),
        Pair.of(Blocks.RAW_IRON_BLOCK, Ingredient.ofItems(Items.RAW_IRON)),
        Pair.of(Blocks.BAMBOO_BLOCK, Ingredient.ofItems(Items.REDSTONE)),
        Pair.of(Blocks.STRIPPED_BAMBOO_BLOCK, Ingredient.ofItems(Items.REDSTONE))
    );
    baseAndResource = Iterables.concat(baseAndResource, Iterables.transform(Iterables.concat(CopperManager.COPPER_BLOCKS,
        CopperManager.WAXED_COPPER_BLOCKS,
        BlockCollections.LOGS,
        BlockCollections.WOODS,
        BlockCollections.HYPHAES,
        BlockCollections.STEMS,
        BlockCollections.STRIPPED_LOGS,
        BlockCollections.STRIPPED_WOODS,
        BlockCollections.STRIPPED_HYPHAES,
        BlockCollections.STRIPPED_STEMS), baseBlock -> Pair.of(baseBlock, Ingredient.ofItems(Items.REDSTONE))));
    for (Pair<Block, Ingredient> pair : baseAndResource) {
      final Block baseBlock = pair.getFirst();
      final ExtShapeButtonBlock button = (ExtShapeButtonBlock) BlockBiMaps.getBlockOf(BlockShape.BUTTON, baseBlock);
      if (button == null) continue;

      ShapelessRecipeJsonBuilder.create(button.getRecipeCategory(), button)
          .input(baseBlock)
          .input(pair.getSecond())
          .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
          .group(button.getRecipeGroup())
          .offerTo(exporter);
    }

    // 墙的合成配方
    for (Block baseBlock : BlockCollections.PLANKS) {
      final ExtShapeWallBlock wall = (ExtShapeWallBlock) BlockBiMaps.getBlockOf(BlockShape.WALL, baseBlock);
      Preconditions.checkNotNull(wall, "wall of %s", baseBlock);

      ShapedRecipeJsonBuilder.create(wall.getRecipeCategory(), wall, 6)
          .pattern(" * ")
          .pattern("###")
          .pattern("###")
          .input('*', Items.STICK)
          .input('#', baseBlock)
          .group(wall.getRecipeGroup())
          .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
          .offerTo(exporter);
    }
    for (Block baseBlock : Iterables.concat(CopperManager.COPPER_BLOCKS, CopperManager.WAXED_COPPER_BLOCKS)) {
      final ExtShapeWallBlock wall = (ExtShapeWallBlock) BlockBiMaps.getBlockOf(BlockShape.WALL, baseBlock);
      Preconditions.checkNotNull(wall, "wall of %s", baseBlock);

      ShapedRecipeJsonBuilder.create(wall.getRecipeCategory(), wall, 6)
          .pattern(" * ")
          .pattern("###")
          .pattern("###")
          .input('*', Items.COPPER_INGOT)
          .input('#', baseBlock)
          .group(wall.getRecipeGroup())
          .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
          .offerTo(exporter);
    }
  }

  @Override
  protected Identifier getRecipeIdentifier(Identifier identifier) {
    return identifier;
  }
}
