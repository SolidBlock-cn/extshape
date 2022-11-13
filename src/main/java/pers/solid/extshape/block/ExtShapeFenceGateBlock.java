package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPFenceGateBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeTags;

public class ExtShapeFenceGateBlock extends BRRPFenceGateBlock implements ExtShapeVariantBlockInterface {

  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(Block baseBlock, Item secondIngredient, Settings settings) {
    super(baseBlock, settings);
    this.secondIngredient = secondIngredient;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_fence_gate", this.getNamePrefix());
  }

  @NotNull
  @Override
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeTags.WOOLS).contains(baseBlock)) return "wool_fence_gate";
    if ((ExtShapeTags.CONCRETES).contains(baseBlock)) return "concrete_fence_gate";
    if ((ExtShapeTags.STAINED_TERRACOTTA).contains(baseBlock)) return
        "stained_terracotta_fence_gate";
    if ((ExtShapeTags.GLAZED_TERRACOTTA).contains(baseBlock)) return
        "glazed_terracotta_fence_gate";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE_GATE;
  }
}
