package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPStairsBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShapeStairsBlock extends BRRPStairsBlock implements ExtShapeVariantBlockInterface {

  public ExtShapeStairsBlock(Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_stairs", this.getNamePrefix());
  }

  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null || NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock) ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public String getRecipeGroup() {
    Block baseBlock = this.baseBlock;
    if ((ExtShapeBlockTags.WOOLS).contains(baseBlock)) return "wool_stairs";
    if ((ExtShapeBlockTags.CONCRETES).contains(baseBlock)) return "concrete_stairs";
    if ((ExtShapeBlockTags.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_stairs";
    if ((ExtShapeBlockTags.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_stairs";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.STAIRS;
  }
}
