package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.data.client.TextureKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTags;

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
            getTextureId(TextureKey.BOTTOM),
            getTextureId(TextureKey.SIDE)));
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
    return simpleStoneCuttingRecipe(4);
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTags.PLANKS).contains(baseBlock)) return "wooden_quarter_piece";
    if ((ExtShapeBlockTags.WOOLS).contains(baseBlock)) return "wool_quarter_piece";
    if ((ExtShapeBlockTags.CONCRETES).contains(baseBlock)) return "concrete_quarter_piece";
    if ((ExtShapeBlockTags.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_quarter_piece";
    if ((ExtShapeBlockTags.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_quarter_piece";
    return "";
  }
}
