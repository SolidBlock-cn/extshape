package pers.solid.extshape.block;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTags;

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
    return Text.translatable("block.extshape.?_vertical_quarter_piece", this.getNamePrefix());
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
    if ((ExtShapeBlockTags.PLANKS).contains(baseBlock)) return "wooden_vertical_quarter_piece";
    if ((ExtShapeBlockTags.WOOLS).contains(baseBlock)) return "wool_vertical_quarter_piece";
    if ((ExtShapeBlockTags.CONCRETES).contains(baseBlock)) return "concrete_vertical_quarter_piece";
    if ((ExtShapeBlockTags.STAINED_TERRACOTTA).contains(baseBlock)) return
        "stained_terracotta_vertical_quarter_piece";
    if ((ExtShapeBlockTags.GLAZED_TERRACOTTA).contains(baseBlock)) return
        "glazed_terracotta_vertical_quarter_piece";
    return "";
  }
}
