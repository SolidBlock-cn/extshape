package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPStairsBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeStairsBlock extends BRRPStairsBlock implements ExtShapeVariantBlockInterface {

  public ExtShapeStairsBlock(Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_stairs", this.getNamePrefix());
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public String getRecipeGroup() {
    Block baseBlock = this.baseBlock;
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_stairs";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_stairs";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_stairs";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_stairs";
    return "";
  }
}
