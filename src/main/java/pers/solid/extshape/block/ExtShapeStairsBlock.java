package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPStairsBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
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

  public static class WithExtension extends ExtShapeStairsBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.onStacksDropped(state, world, pos, stack, dropExperience);
      extension.stacksDroppedCallback.onStackDropped(state, world, pos, stack, dropExperience);
    }

  }
}
