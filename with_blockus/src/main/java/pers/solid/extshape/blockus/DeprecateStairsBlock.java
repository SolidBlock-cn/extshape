package pers.solid.extshape.blockus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.extshape.block.ExtShapeStairsBlock;

import java.util.List;

public class DeprecateStairsBlock extends ExtShapeStairsBlock {
  private final Block recommended;

  public DeprecateStairsBlock(Block baseBlock, Settings settings, Block recommended) {
    super(baseBlock, settings.dropsLike(recommended));
    this.recommended = recommended;
    setDefaultState(recommended.getDefaultState());
  }

  @Override
  public MutableText getName() {
    return super.getName().formatted(Formatting.STRIKETHROUGH);
  }

  @Override
  public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
    super.appendTooltip(stack, world, tooltip, options);
    tooltip.add(Text.translatable("block.extshape_blockus.deprecated").formatted(Formatting.YELLOW));
  }

  @Override
  public void writeRecipes(RuntimeResourcePack pack) {
    pack.addRecipeAndAdvancement(getRecipeId().withSuffixedPath("_deprecation"), ShapelessRecipeJsonBuilder.create(getRecipeCategory(), recommended).input(this).criterionFromItem(this));
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    world.setBlockState(pos, recommended.getStateWithProperties(state), Block.NOTIFY_LISTENERS);
    return super.onUse(state, world, pos, player, hand, hit);
  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    return recommended.getStateWithProperties(state).getStateForNeighborUpdate(direction, neighborState, world, pos, neighborPos);
  }
}
