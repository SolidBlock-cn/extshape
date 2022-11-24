package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPStairsBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeStairsBlock extends BRRPStairsBlock implements ExtShapeVariantBlockInterface {

  public ExtShapeStairsBlock(Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_stairs", this.getNamePrefix());
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
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_stairs";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_stairs";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_stairs";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_stairs";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.STAIRS;
  }

  public static class WithExtension extends ExtShapeStairsBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      extension.stacksDroppedCallback.onStackDropped(state, world, pos, stack);
    }

  }
}
