package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.ButtonBuilder;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShapeButtonBlock extends AbstractButtonBlock implements ExtShapeVariantBlockInterface {
  public final ButtonType type;
  public final Block baseBlock;

  public ExtShapeButtonBlock(Block baseBlock, @NotNull ButtonType type, Settings settings) {
    super(type == ButtonType.WOODEN, settings);
    this.baseBlock = baseBlock;
    this.type = type;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  protected SoundEvent getClickSound(boolean powered) {
    if (this.type == ButtonType.WOODEN)
      return powered ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
    else return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_button", this.getNamePrefix());
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @NotNull JBlockStates getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return JBlockStates.delegate(BlockStateModelGenerator.createButtonBlockState(
        this,
        blockModelId,
        blockModelId.brrp_append("_pressed")
    ));
  }

  @Environment(EnvType.CLIENT)
  @Override
  @NotNull
  public JModel getBlockModel() {
    return new JModel("block/button").addTexture("texture", getTextureId(TextureKey.TEXTURE));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final JModel blockModel = getBlockModel();
    pack.addModel(blockModel, blockModelId);
    pack.addModel(blockModel.parent("block/button_inventory"), blockModelId.brrp_append("_inventory"));
    pack.addModel(blockModel.parent("block/button_pressed"), blockModelId.brrp_append("_pressed"));
  }


  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JModel getItemModel() {
    return new JModel(getBlockModelId().brrp_append("_inventory"));
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final Block baseBlock = getBaseBlock();
    if (ButtonBuilder.REFUSE_RECIPES.contains(baseBlock)) {
      return null;
    }
    return new JShapelessRecipe(this, baseBlock)
        .addInventoryChangedCriterion("has_ingredient", baseBlock)
        .group(getRecipeGroup());
  }

  @Override
  public String getRecipeGroup() {
    final Block baseBlock = getBaseBlock();
    if (ExtShapeBlockTags.WOOLS.contains(baseBlock)) return "wool_button";
    if (ExtShapeBlockTags.CONCRETES.contains(baseBlock)) return "concrete_button";
    if (ExtShapeBlockTags.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_button";
    if (ExtShapeBlockTags.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_button";
    if (ExtShapeBlockTags.PLANKS.contains(baseBlock)) return "wooden_button";
    return "";
  }

  public enum ButtonType {
    WOODEN,
    STONE,
    HARD,
    SOFT
  }
}
