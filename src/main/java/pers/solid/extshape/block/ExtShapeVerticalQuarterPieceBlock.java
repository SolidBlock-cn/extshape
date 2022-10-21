package pers.solid.extshape.block;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeTags;

public class ExtShapeVerticalQuarterPieceBlock extends VerticalQuarterPieceBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeVerticalQuarterPieceBlock(Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_quarter_piece", this.getNamePrefix());
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return JBlockStates.ofVariants(new JVariants()
        .addVariant("facing", "south_east", new JBlockModel(identifier).uvlock())
        .addVariant("facing", "south_west", new JBlockModel(identifier).uvlock().y(90))
        .addVariant("facing", "north_west", new JBlockModel(identifier).uvlock().y(180))
        .addVariant("facing", "north_east", new JBlockModel(identifier).uvlock().y(270))
    );
  }


  @Override
  @Environment(EnvType.CLIENT)
  public @NotNull JModel getBlockModel() {
    return simpleModel("extshape:block/vertical_quarter_piece");
  }


  @Override
  public @NotNull JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(4);
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeTags.PLANKS).contains(baseBlock)) return "wooden_vertical_quarter_piece";
    if ((ExtShapeTags.WOOLS).contains(baseBlock)) return "wool_vertical_quarter_piece";
    if ((ExtShapeTags.CONCRETES).contains(baseBlock)) return "concrete_vertical_quarter_piece";
    if ((ExtShapeTags.STAINED_TERRACOTTA).contains(baseBlock)) return
        "stained_terracotta_vertical_quarter_piece";
    if ((ExtShapeTags.GLAZED_TERRACOTTA).contains(baseBlock)) return
        "glazed_terracotta_vertical_quarter_piece";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.VERTICAL_QUARTER_PIECE;
  }


  public static class WithExtension extends ExtShapeVerticalQuarterPieceBlock {
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
