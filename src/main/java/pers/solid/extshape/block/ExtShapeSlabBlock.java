package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPSlabBlock;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.item.HoneycombItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeSlabBlock extends BRRPSlabBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_slab", this.getNamePrefix());
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier id = getBlockModelId();
    // 对于上蜡的铜，其自身的方块模型以及对应完整方块的模型均为未上蜡的方块模型，故在此处做出调整。
    final Block baseBlock = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().getOrDefault(this.baseBlock, this.baseBlock);
    return JBlockStates.simpleSlab(baseBlock != null ? ResourceGeneratorHelper.getBlockModelId(baseBlock) : id.brrp_append("_double"), id, id.brrp_append("_top"));
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return baseBlock == null ? null : simpleStoneCuttingRecipe(2);
  }

  @Override
  public String getRecipeGroup() {
    Block baseBlock = this.baseBlock;
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_slab";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_slab";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_slab";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_slab";
    return "";
  }
}
