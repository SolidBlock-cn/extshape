package pers.solid.extshape.block;

import com.google.common.collect.ImmutableSet;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.Collection;

public class ExtShapeButtonBlock extends AbstractButtonBlock implements ExtShapeVariantBlockInterface {
  /**
   * 该集合内的方块将不会生成按钮配方，以解决合成配方的冲突。
   *
   * @see pers.solid.extshape.config.ExtShapeConfig#avoidSomeButtonRecipes
   */
  public static final @Unmodifiable Collection<Block> REFUSE_RECIPES = ImmutableSet.<Block>builder().add(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK, Blocks.NETHERITE_BLOCK, Blocks.PUMPKIN, Blocks.COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.WAXED_COPPER_BLOCK).addAll(ExtShapeTags.LOGS).addAll(ExtShapeTags.WOODS).addAll(ExtShapeTags.HYPHAES).addAll(ExtShapeTags.STEMS).addAll(ExtShapeTags.STRIPPED_LOGS).addAll(ExtShapeTags.STRIPPED_WOODS).addAll(ExtShapeTags.STRIPPED_HYPHAES).addAll(ExtShapeTags.STRIPPED_STEMS).build();
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
  @OnlyIn(Dist.CLIENT)
  public @NotNull JBlockStates getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return JBlockStates.delegate(BlockStateModelGenerator.createButtonBlockState(
        this,
        blockModelId,
        blockModelId.brrp_append("_pressed")
    ));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  @NotNull
  public JModel getBlockModel() {
    return new JModel("block/button").addTexture("texture", getTextureId(TextureKey.TEXTURE));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final JModel blockModel = getBlockModel();
    pack.addModel(blockModel, blockModelId);
    pack.addModel(blockModel.parent("block/button_inventory"), blockModelId.brrp_append("_inventory"));
    pack.addModel(blockModel.parent("block/button_pressed"), blockModelId.brrp_append("_pressed"));
  }


  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JModel getItemModel() {
    return new JModel(getBlockModelId().brrp_append("_inventory"));
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final Block baseBlock = getBaseBlock();
    if (REFUSE_RECIPES.contains(baseBlock) && ExtShapeConfig.CURRENT_CONFIG.avoidSomeButtonRecipes) {
      return null;
    }
    return new JShapelessRecipe(this, baseBlock)
        .addInventoryChangedCriterion("has_ingredient", baseBlock)
        .group(getRecipeGroup());
  }

  @Override
  public String getRecipeGroup() {
    final Block baseBlock = getBaseBlock();
    if (ExtShapeTags.WOOLS.contains(baseBlock)) return "wool_button";
    if (ExtShapeTags.CONCRETES.contains(baseBlock)) return "concrete_button";
    if (ExtShapeTags.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_button";
    if (ExtShapeTags.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_button";
    if (ExtShapeTags.PLANKS.contains(baseBlock)) return "wooden_button";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }

  public enum ButtonType {
    WOODEN,
    STONE,
    HARD,
    SOFT
  }
}
