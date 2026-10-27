package pers.solid.extshape.blockus.data;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusBlockCollections;
import pers.solid.extshape.blockus.BlockusCopperManager;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;

public class ExtShapeBlockusRecipeGenerator extends RecipeGenerator {
  protected ExtShapeBlockusRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
    super(registries, exporter);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(this, exporter);
      }
    }

    final Multimap<Block, ObjectIntPair<Block>> mappedInstance = BlockusStonecutting.INSTANCE;
    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, mappedInstance, this, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    for (Block baseBlock : BlockusStonecutting.INSTANCE.keys()) {
      if (!ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS.contains(baseBlock)) {
        final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, mappedInstance, this, exporter);
        crossShapeDataGeneration.enableConversionWithinBlock = false;
        crossShapeDataGeneration.generateCrossShapeData();
      }
    }

    registerShingleDyeingRecipes(exporter);
    registerStainedStoneBricksRecipe(exporter);

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlockusBlocks.BLOCKUS_BLOCKS);
    BlockusCopperManager.COPPER_BRICKS.generateWaxRecipes(this, exporter, predicate);
    BlockusCopperManager.COPPER_TUFF.generateWaxRecipes(this, exporter, predicate);
  }


  private void registerShingleDyeingRecipes(RecipeExporter exporter) {
    for (var bsswBundle : BlockusBlockCollections.TINTED_SHINGLES) {
      Item dyeItem = Registries.ITEM.get(Identifier.ofVanilla(StringUtils.substringBefore(Registries.BLOCK.getId(bsswBundle.block).getPath(), "_shingle") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final @Nullable Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block);
        final @Nullable Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("shingles_" + blockShape.asString() + "_from_dyeing")
            .criterion(hasItem(BlockusBlocks.SHINGLES.block), conditionsFromItem(BlockusBlocks.SHINGLES.block))
            .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, ExtShapeBlockus.id(getItemPath(dyed) + "_from_dyeing")));
      }
    }
  }

  private void registerStainedStoneBricksRecipe(RecipeExporter exporter) {
    for (var bsswBundle : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      Item dyeItem = Registries.ITEM.get(Identifier.ofVanilla(StringUtils.substringBefore(Registries.BLOCK.getId(bsswBundle.block).getPath(), "_stone_brick") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final @Nullable Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
        final @Nullable Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final CraftingRecipeJsonBuilder recipe = createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("stained_stone_brick_" + blockShape.asString() + "_from_dyeing")
            .criterion(hasItem(Blocks.STONE_BRICKS), conditionsFromItem(Blocks.STONE_BRICKS));
        recipe.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, ExtShapeBlockus.id(getItemPath(dyed) + "_from_dyeing")));
      }
    }
  }

  protected CraftingRecipeJsonBuilder createEnclosedRecipe(ItemConvertible output, Ingredient input, ItemConvertible center) {
    return this.createShaped(RecipeCategory.BUILDING_BLOCKS, output, 8)
        .input('X', input)
        .input('#', center)
        .pattern("XXX")
        .pattern("X#X")
        .pattern("XXX");
  }
}
