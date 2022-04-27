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
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeVerticalSlabBlock(Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_slab", this.getNamePrefix());
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JBlockStates getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return JBlockStates.ofVariants(new JVariants()
        .addVariant("facing", "south", new JBlockModel(identifier).uvlock())
        .addVariant("facing", "west", new JBlockModel(identifier).uvlock().y(90))
        .addVariant("facing", "north", new JBlockModel(identifier).uvlock().y(180))
        .addVariant("facing", "east", new JBlockModel(identifier).uvlock().y(270))
    );
  }


  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JModel getBlockModel() {
    return simpleModel("extshape:block/vertical_slab");
  }

  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(2);
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_slab";
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_slab";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_slab";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_vertical_slab";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_vertical_slab";
    return "";
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }
}
