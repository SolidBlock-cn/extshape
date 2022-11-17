package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.TextureKey;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeQuarterPieceBlock extends QuarterPieceBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeQuarterPieceBlock(@NotNull Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_quarter_piece", this.getNamePrefix());
  }


  @Override
  @OnlyIn(Dist.CLIENT)
  public @NotNull JBlockStates getBlockStates() {
    final JVariants variant = new JVariants();
    final Identifier blockModelId = getBlockModelId();
    for (Direction direction : Direction.Type.HORIZONTAL) {
      variant
          .addVariant("half=top,facing", direction, new JBlockModel(blockModelId.brrp_append("_top")).uvlock().y(((int) direction.asRotation())))
          .addVariant("half=bottom,facing", direction, new JBlockModel(blockModelId).uvlock().y((int) direction.asRotation()));
    }
    return JBlockStates.ofVariants(variant);
  }


  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JModel getBlockModel() {
    return new JModel("extshape:block/quarter_piece")
        .textures(JTextures.ofSides(
            getTextureId(TextureKey.TOP),
            getTextureId(TextureKey.SIDE),
            getTextureId(TextureKey.BOTTOM)));
  }

  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final JModel blockModel = getBlockModel();
    pack.addModel(blockModel, blockModelId);
    pack.addModel(blockModel.parent("extshape:block/quarter_piece_top"), blockModelId.brrp_append("_top"));
  }


  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(4).recipeCategory(getRecipeCategory());
  }

  @Override
  public String getRecipeGroup() {
    if ((BlockCollections.PLANKS).contains(baseBlock)) return "wooden_quarter_piece";
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_quarter_piece";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_quarter_piece";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_quarter_piece";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_quarter_piece";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.QUARTER_PIECE;
  }


  public static class WithExtension extends ExtShapeQuarterPieceBlock {
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
