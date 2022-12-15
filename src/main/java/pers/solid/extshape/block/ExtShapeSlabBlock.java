package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPSlabBlock;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeSlabBlock extends BRRPSlabBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_slab", this.getNamePrefix());
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier id = getBlockModelId();
    // 对于上蜡的铜，其自身的方块模型以及对应完整方块的模型均为未上蜡的方块模型，故在此处做出调整。
    Identifier blockModelId = baseBlock == null ? null : ResourceGeneratorHelper.getBlockModelId(baseBlock);
    if (blockModelId != null && blockModelId.getPath().contains("waxed_") && blockModelId.getPath().contains("copper")) {
      blockModelId = new Identifier(blockModelId.getNamespace(), blockModelId.getPath().replace("waxed_", ""));
    }
    return JBlockStates.simpleSlab(baseBlock != null ? blockModelId : id.brrp_append("_double"), id, id.brrp_append("_top"));
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = (baseBlock == Blocks.SNOW_BLOCK && ExtShapeConfig.CURRENT_CONFIG.specialSnowSlabCrafting) ? new JShapelessRecipe(this, Blocks.SNOW).addInventoryChangedCriterion("has_snow", Blocks.SNOW) : super.getCraftingRecipe();
    return craftingRecipe != null && !NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock) ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return baseBlock == null ? null : simpleStoneCuttingRecipe(2);
  }

  @Override
  public String getRecipeGroup() {
    Block baseBlock = this.baseBlock;
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_slab";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_slab";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_slab";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_slab";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.SLAB;
  }


  public static class WithExtension extends ExtShapeSlabBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      extension.stacksDroppedCallback.onStackDropped(state, world, pos, stack);
    }

  }
}
