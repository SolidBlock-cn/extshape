package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import java.util.Collections;

public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

  public final Block baseBlock;

  public ExtShapePressurePlateBlock(Block baseBlock, ActivationRule type, Settings settings) {
    super(type, settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_pressure_plate", this.getNamePrefix());
  }


  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return JBlockStates.delegate(BlockStateModelGenerator.createPressurePlateBlockState(
        this,
        blockModelId,
        blockModelId.brrp_append("_down")
    ));
  }

  @Environment(EnvType.CLIENT)
  @Override
  @NotNull
  public JModel getBlockModel() {
    return new JModel("block/pressure_plate_up")
        .addTexture("texture", getTextureId(TextureKey.TEXTURE));
  }


  @Override
  @Environment(EnvType.CLIENT)
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final JModel blockModel = getBlockModel();
    pack.addModel(blockModel, blockModelId);
    pack.addModel(blockModel.parent("block/pressure_plate_down"), blockModelId.brrp_append("_down"));
  }


  @Override
  public @NotNull JRecipe getCraftingRecipe() {
    if (ExtShapeBlockTags.WOOLS.contains(baseBlock)) {
      return new JShapelessRecipe(this.getItemId().toString(), Collections.singleton(getBaseBlockId().toString().replaceAll("_wool$", "_carpet")));
    } else {
      return new JShapedRecipe(this)
          .pattern("##")
          .addKey("#", baseBlock)
          .addInventoryChangedCriterion("has_ingredient", baseBlock)
          .group(getRecipeGroup());
    }
  }

  @Override
  public String getRecipeGroup() {
    if (ExtShapeBlockTags.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
    if (ExtShapeBlockTags.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
    if (ExtShapeBlockTags.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_pressure_plate";
    if (ExtShapeBlockTags.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
    if (ExtShapeBlockTags.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
    return "";
  }
}
