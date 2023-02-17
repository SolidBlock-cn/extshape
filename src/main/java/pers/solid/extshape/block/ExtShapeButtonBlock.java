package pers.solid.extshape.block;

import com.google.common.collect.ImmutableSet;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.ButtonSettings;

import java.util.Collection;

public class ExtShapeButtonBlock extends ButtonBlock implements ExtShapeVariantBlockInterface {
  /**
   * 该集合内的方块将不会生成按钮配方，以解决合成配方的冲突。
   *
   * @see pers.solid.extshape.config.ExtShapeConfig#avoidSomeButtonRecipes
   */
  public static final @Unmodifiable Collection<Block> REFUSE_RECIPES = ImmutableSet.<Block>builder().add(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK, Blocks.NETHERITE_BLOCK, Blocks.PUMPKIN, Blocks.COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.WAXED_COPPER_BLOCK, Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK).addAll(BlockCollections.LOGS).addAll(BlockCollections.WOODS).addAll(BlockCollections.HYPHAES).addAll(BlockCollections.STEMS).addAll(BlockCollections.STRIPPED_LOGS).addAll(BlockCollections.STRIPPED_WOODS).addAll(BlockCollections.STRIPPED_HYPHAES).addAll(BlockCollections.STRIPPED_STEMS).build();
  public final Block baseBlock;

  public ExtShapeButtonBlock(Block baseBlock, Settings settings, BlockSetType blockSetType, int i, boolean bl) {
    super(settings, blockSetType, i, bl);
    this.baseBlock = baseBlock;
  }

  public ExtShapeButtonBlock(Block baseBlock, ButtonSettings buttonSettings, AbstractBlock.Settings blockSettings) {
    super(blockSettings, buttonSettings.blockSetType(), buttonSettings.time(), buttonSettings.wooden());
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_button", this.getNamePrefix());
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
    if (REFUSE_RECIPES.contains(baseBlock) && ExtShapeConfig.CURRENT_CONFIG.avoidSomeButtonRecipes) {
      return null;
    }
    return new JShapelessRecipe(this, baseBlock)
        .addInventoryChangedCriterion("has_ingredient", baseBlock)
        .recipeCategory(getRecipeCategory())
        .group(getRecipeGroup());
  }

  @Override
  public String getRecipeGroup() {
    final Block baseBlock = getBaseBlock();
    if (BlockCollections.WOOLS.contains(baseBlock)) return "wool_button";
    if (BlockCollections.CONCRETES.contains(baseBlock)) return "concrete_button";
    if (BlockCollections.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_button";
    if (BlockCollections.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_button";
    if (BlockCollections.PLANKS.contains(baseBlock)) return "wooden_button";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }
}
