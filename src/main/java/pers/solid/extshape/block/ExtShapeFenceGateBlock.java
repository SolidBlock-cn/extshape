package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPFenceGateBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeFenceGateBlock extends BRRPFenceGateBlock implements ExtShapeVariantBlockInterface {

  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(Block baseBlock, Item secondIngredient, Settings settings) {
    super(baseBlock, settings);
    this.secondIngredient = secondIngredient;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_fence_gate", this.getNamePrefix());
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
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence_gate";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence_gate";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
        "stained_terracotta_fence_gate";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
        "glazed_terracotta_fence_gate";
    return "";
  }
}
