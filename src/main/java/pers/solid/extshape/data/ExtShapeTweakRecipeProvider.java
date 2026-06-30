package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.block.ExtShapeWallBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.List;

/**
 * 一些特殊的资源包，用于修复本模组中的配方与原版配方冲突的问题。
 */
public class ExtShapeTweakRecipeProvider extends RecipeProvider {

  protected ExtShapeTweakRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
    super(registries, exporter);
  }

  @Override
  public void buildRecipes() {
    // 羊毛的特殊合成配方：羊毛压力板 ↔ 3个地毯
    for (Block baseBlock : BlockCollections.WOOLS) {
      final ExtShapePressurePlateBlock pressurePlate = (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, baseBlock);
      Preconditions.checkNotNull(pressurePlate, "pressure plate of %s", baseBlock);
      final Identifier woolId = BuiltInRegistries.BLOCK.getKey(baseBlock);
      final Identifier carpetId = Identifier.fromNamespaceAndPath(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final Item carpet = BuiltInRegistries.ITEM.getValue(carpetId);
      shaped(pressurePlate.getRecipeCategory(), pressurePlate)
          .pattern("###")
          .define('#', carpet)
          .unlockedBy(getHasName(carpet), has(carpet))
          .group(pressurePlate.getRecipeGroup())
          .save(output);
      final Identifier reverseRecipeId = ExtShape.id(carpetId.getPath() + "_from_pressure_plate");
      shapeless(pressurePlate.getRecipeCategory(), carpet, 3)
          .requires(Ingredient.of(pressurePlate))
          .unlockedBy("has_pressure_plate", has(pressurePlate))
          .group("wool_from_pressure_plate")
          .save(output, ResourceKey.create(Registries.RECIPE, reverseRecipeId));
    }

    // 苔藓的特殊合成配方：覆地苔藓 ↔ 苔藓压力板
    final var carpetAndPlate = List.of(
        Pair.of(Blocks.MOSS_CARPET, (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, Blocks.MOSS_BLOCK)),
        Pair.of(Blocks.PALE_MOSS_CARPET, (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, Blocks.PALE_MOSS_BLOCK))
    );
    for (var pair : carpetAndPlate) {
      var carpet = pair.getFirst();
      var pressurePlate = Preconditions.checkNotNull(pair.getSecond(), "pressure plate of %s", carpet);
      shaped(pressurePlate.getRecipeCategory(), pressurePlate)
          .pattern("###")
          .define('#', carpet)
          .unlockedBy(getHasName(carpet), has(carpet))
          .group(pressurePlate.getRecipeGroup())
          .save(output);
      final Identifier reverseRecipeId = ExtShape.id(BuiltInRegistries.ITEM.getKey(carpet.asItem()).getPath() + "_from_pressure_plate");
      shapeless(pressurePlate.getRecipeCategory(), carpet, 3)
          .requires(Ingredient.of(pressurePlate))
          .unlockedBy("has_pressure_plate", has(pressurePlate))
          .save(output, ResourceKey.create(Registries.RECIPE, reverseRecipeId));
    }

    // 特殊的雪台阶配方
    final ExtShapeSlabBlock snowSlab = (ExtShapeSlabBlock) BlockBiMaps.getBlockOf(BlockShape.SLAB, Blocks.SNOW_BLOCK);
    Preconditions.checkNotNull(snowSlab, "snow slab");
    shapeless(snowSlab.getRecipeCategory(), snowSlab)
        .requires(Ingredient.of(Blocks.SNOW))
        .unlockedBy("has_snow", has(Blocks.SNOW))
        .group(snowSlab.getRecipeGroup())
        .save(output);

    // 覆盖原版的砂岩、红砂岩和石英的楼梯、台阶配方，使之不再支持属于该基础方块的方块
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, Blocks.RED_SANDSTONE_SLAB, Ingredient.of(Blocks.RED_SANDSTONE))
        .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
        .save(output);
    stairBuilder(Blocks.RED_SANDSTONE_STAIRS, Ingredient.of(Blocks.RED_SANDSTONE))
        .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
        .save(output);
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, Blocks.SANDSTONE_SLAB, Ingredient.of(Blocks.SANDSTONE))
        .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
        .save(output);
    stairBuilder(Blocks.SANDSTONE_STAIRS, Ingredient.of(Blocks.SANDSTONE))
        .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
        .save(output);
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, Blocks.QUARTZ_SLAB, Ingredient.of(Blocks.QUARTZ_BLOCK))
        .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
        .save(output);
    stairBuilder(Blocks.QUARTZ_STAIRS, Ingredient.of(Blocks.QUARTZ_BLOCK))
        .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
        .save(output);

    // 特殊的按钮合成配方
    Iterable<Pair<Block, Ingredient>> baseAndResource = List.of(
        Pair.of(Blocks.EMERALD_BLOCK, tag(ConventionalItemTags.EMERALD_GEMS)),
        Pair.of(Blocks.IRON_BLOCK, tag(ConventionalItemTags.IRON_INGOTS)),
        Pair.of(Blocks.GOLD_BLOCK, tag(ConventionalItemTags.GOLD_INGOTS)),
        Pair.of(Blocks.DIAMOND_BLOCK, tag(ConventionalItemTags.DIAMOND_GEMS)),
        Pair.of(Blocks.COAL_BLOCK, Ingredient.of(Items.COAL)),
        Pair.of(Blocks.LAPIS_BLOCK, tag(ConventionalItemTags.LAPIS_GEMS)),
        Pair.of(Blocks.PUMPKIN, Ingredient.of(Items.PUMPKIN_SEEDS)),
        Pair.of(Blocks.NETHERITE_BLOCK, tag(ConventionalItemTags.NETHERITE_INGOTS)),
        Pair.of(Blocks.RAW_GOLD_BLOCK, Ingredient.of(Items.RAW_GOLD)),
        Pair.of(Blocks.RAW_COPPER_BLOCK, Ingredient.of(Items.RAW_COPPER)),
        Pair.of(Blocks.RAW_IRON_BLOCK, Ingredient.of(Items.RAW_IRON)),
        Pair.of(Blocks.BAMBOO_BLOCK, Ingredient.of(Items.REDSTONE)),
        Pair.of(Blocks.STRIPPED_BAMBOO_BLOCK, Ingredient.of(Items.REDSTONE)),
        Pair.of(Blocks.RESIN_BLOCK, tag(ConventionalItemTags.RESIN_CLUMPS))
    );
    baseAndResource = Iterables.concat(baseAndResource, Iterables.transform(Iterables.concat(Blocks.COPPER_BLOCK.asList(),
        BlockCollections.LOGS,
        BlockCollections.WOODS,
        BlockCollections.HYPHAES,
        BlockCollections.STEMS,
        BlockCollections.STRIPPED_LOGS,
        BlockCollections.STRIPPED_WOODS,
        BlockCollections.STRIPPED_HYPHAES,
        BlockCollections.STRIPPED_STEMS), baseBlock -> Pair.of(baseBlock, Ingredient.of(Items.REDSTONE))));
    for (Pair<Block, Ingredient> pair : baseAndResource) {
      final Block baseBlock = pair.getFirst();
      final ExtShapeButtonBlock button = (ExtShapeButtonBlock) BlockBiMaps.getBlockOf(BlockShape.BUTTON, baseBlock);
      if (button == null) continue;

      shapeless(button.getRecipeCategory(), button)
          .requires(baseBlock)
          .requires(pair.getSecond())
          .unlockedBy(getHasName(baseBlock), has(baseBlock))
          .group(button.getRecipeGroup())
          .save(output);
    }

    // 墙的合成配方
    for (Block baseBlock : BlockCollections.PLANKS) {
      final ExtShapeWallBlock wall = (ExtShapeWallBlock) BlockBiMaps.getBlockOf(BlockShape.WALL, baseBlock);
      Preconditions.checkNotNull(wall, "wall of %s", baseBlock);

      shaped(wall.getRecipeCategory(), wall, 6)
          .pattern(" * ")
          .pattern("###")
          .pattern("###")
          .define('*', Items.STICK)
          .define('#', baseBlock)
          .group(wall.getRecipeGroup())
          .unlockedBy(getHasName(baseBlock), has(baseBlock))
          .save(output);
    }
    for (Block baseBlock : Blocks.COPPER_BLOCK.asList()) {
      final ExtShapeWallBlock wall = (ExtShapeWallBlock) BlockBiMaps.getBlockOf(BlockShape.WALL, baseBlock);
      Preconditions.checkNotNull(wall, "wall of %s", baseBlock);

      shaped(wall.getRecipeCategory(), wall, 6)
          .pattern(" * ")
          .pattern("###")
          .pattern("###")
          .define('*', Items.COPPER_INGOT)
          .define('#', baseBlock)
          .group(wall.getRecipeGroup())
          .unlockedBy(getHasName(baseBlock), has(baseBlock))
          .save(output);
    }
  }
}
