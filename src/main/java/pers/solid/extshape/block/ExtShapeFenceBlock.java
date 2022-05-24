package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPFenceBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShapeFenceBlock extends BRRPFenceBlock implements ExtShapeVariantBlockInterface {

  /**
   * 合成栅栏方块需要使用的第二个材料。
   */
  private final Item secondIngredient;

  public ExtShapeFenceBlock(Block baseBlock, Item secondIngredient, Settings settings) {
    super(baseBlock, settings);
    this.secondIngredient = secondIngredient;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_fence", this.getNamePrefix());
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    return new JShapedRecipe(this).resultCount(3)
        .pattern("W#W", "W#W")
        .group(getRecipeGroup())
        .addKey("W", baseBlock)
        .addKey("#", getSecondIngredient())
        .addInventoryChangedCriterion("has_ingredient", baseBlock);
  }

  @Nullable
  @Override
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTags.WOOLS).contains(baseBlock)) return "wool_fence";
    if ((ExtShapeBlockTags.CONCRETES).contains(baseBlock)) return "concrete_fence";
    if ((ExtShapeBlockTags.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_fence";
    if ((ExtShapeBlockTags.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_fence";
    return "";
  }
}
