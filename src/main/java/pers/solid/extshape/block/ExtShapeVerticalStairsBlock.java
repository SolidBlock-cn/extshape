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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShapeVerticalStairsBlock extends VerticalStairsBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeVerticalStairsBlock(Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_stairs", this.getNamePrefix());
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JBlockStates getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return JBlockStates.ofVariants(new JVariants()
        .addVariant("facing", "south_west", new JBlockModel(identifier).uvlock())
        .addVariant("facing", "north_west", new JBlockModel(identifier).uvlock().y(90))
        .addVariant("facing", "north_east", new JBlockModel(identifier).uvlock().y(180))
        .addVariant("facing", "south_east", new JBlockModel(identifier).uvlock().y(270))
    );
  }


  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JModel getBlockModel() {
    return simpleModel("extshape:block/vertical_stairs");
  }

  /**
   * 注意：跨方块类型的合成表由 {@link BlocksBuilder} 定义。
   */
  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    return null;
  }


  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }


  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTags.PLANKS).contains(baseBlock)) return "wooden_vertical_stairs";
    if ((ExtShapeBlockTags.WOOLS).contains(baseBlock)) return "wool_vertical_stairs";
    if ((ExtShapeBlockTags.CONCRETES).contains(baseBlock)) return "concrete_vertical_stairs";
    if ((ExtShapeBlockTags.STAINED_TERRACOTTA).contains(baseBlock)) return
        "stained_terracotta_vertical_stairs";
    if ((ExtShapeBlockTags.GLAZED_TERRACOTTA).contains(baseBlock)) return
        "glazed_terracotta_vertical_stairs";
    return "";
  }
}
