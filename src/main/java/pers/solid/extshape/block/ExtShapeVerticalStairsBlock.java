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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.util.BlockCollections;

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
    return Text.translatable("block.extshape.?_vertical_stairs", this.getNamePrefix());
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @NotNull JBlockStates getBlockStates() {
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
  public @NotNull JModel getBlockModel() {
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
    return simpleStoneCuttingRecipe(1).recipeCategory(getRecipeCategory());
  }


  @Override
  public String getRecipeGroup() {
    if ((BlockCollections.PLANKS).contains(baseBlock)) return "wooden_vertical_stairs";
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_vertical_stairs";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_vertical_stairs";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return
        "stained_terracotta_vertical_stairs";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return
        "glazed_terracotta_vertical_stairs";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.VERTICAL_STAIRS;
  }


  public static class WithExtension extends ExtShapeVerticalStairsBlock {
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
