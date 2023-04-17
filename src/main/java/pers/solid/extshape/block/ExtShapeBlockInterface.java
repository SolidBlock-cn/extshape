package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.data.client.Model;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BlockResourceGenerator;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.AbstractBlockAccessor;
import pers.solid.extshape.rrp.RecipeGroupRegistry;

/**
 * 该模组中的绝大多数方块共用的接口。
 */
public interface ExtShapeBlockInterface extends BlockResourceGenerator, IForgeBlock {
  /**
   * 方块所在的合成配方的组。
   *
   * @return 方块合成配方中的 {@code group} 字段。
   * @see net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder#group(String)
   */
  default String getRecipeGroup() {
    return RecipeGroupRegistry.getRecipeGroup((ItemConvertible) this);
  }

  /**
   * 方块的切石配方。该方法在生成数据时，只有在确认了 {@link #isStoneCut(Block)} 的情况下才会被使用，因此该方法内部无需判断方块是否可以切石。
   *
   * @return 方块的切石配方。用于切石机。
   */
  @Override
  default @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return null;
  }

  /**
   * 通过判断方块的材料是否为石头，来确定方块是否可以被切石。用于生成运行时数据。
   *
   * @param baseBlock 基础方块。
   * @return 方块能否被切石机切石。
   */
  static boolean isStoneCut(Block baseBlock) {
    return baseBlock != null && (((AbstractBlockAccessor) baseBlock).getMaterial() == Material.STONE || ((AbstractBlockAccessor) baseBlock).getMaterial() == Material.METAL);
  }

  @Override
  default boolean shouldWriteStonecuttingRecipe() {
    return isStoneCut(getBaseBlock());
  }

  @OnlyIn(Dist.CLIENT)
  default ModelJsonBuilder simpleModel(Model parent) {
    return ModelUtils.createModelWithVariants(this, parent);
  }

  /**
   * 考虑到上蜡的方块使用的模型均为未上蜡的方块的模型，故在此做出调整。
   */
  @OnlyIn(Dist.CLIENT)
  @Override
  default Identifier getBlockModelId() {
    final Identifier blockModelId = BlockResourceGenerator.super.getBlockModelId();
    final String path = blockModelId.getPath();
    if (path.contains("waxed_") && path.contains("copper")) {
      return new Identifier(blockModelId.getNamespace(), path.replace("waxed_", ""));
    } else {
      return blockModelId;
    }
  }

  default SingleItemRecipeJsonBuilder simpleStoneCuttingRecipe(int resultCount) {
    final Block baseBlock = getBaseBlock();
    if (baseBlock == null) {
      return null;
    } else {
      return SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(baseBlock), getRecipeCategory(), (ItemConvertible) this, resultCount).criterion("has_base_block", RecipeProvider.conditionsFromItem(baseBlock));
    }
  }

  /**
   * <p>为该方块写入合成配方。</p>
   *
   * @param pack 运行时资源包。
   * @see pers.solid.brrp.v1.generator.ItemResourceGenerator#writeRecipes(RuntimeResourcePack)
   * @since 1.5.1
   */
  @ApiStatus.AvailableSince("1.5.1")
  default void writeCraftingRecipe(RuntimeResourcePack pack) {
    final @Nullable CraftingRecipeJsonBuilder craftingRecipe = getCraftingRecipe();
    if (craftingRecipe != null) {
      final Identifier recipeId = getRecipeId();
      pack.addRecipeAndAdvancement(recipeId, craftingRecipe);
    }
  }

  /**
   * 为该方块写入切石配方。该方法并不会检查方块是否可切石，因此你可能需要先调用 {@link #shouldWriteStonecuttingRecipe()}。
   *
   * @param pack 运行时资源包。
   * @since 1.5.1
   */
  @ApiStatus.AvailableSince("1.5.1")
  default void writeStonecuttingRecipe(RuntimeResourcePack pack) {
    final @Nullable SingleItemRecipeJsonBuilder stonecuttingRecipe = getStonecuttingRecipe();
    if (stonecuttingRecipe != null) {
      final Identifier stonecuttingRecipeId = getStonecuttingRecipeId();
      pack.addRecipeAndAdvancement(stonecuttingRecipeId, stonecuttingRecipe);
    }
  }

  /**
   * @since 1.5.1 覆盖了原先的方法，以便于更好地控制流程。
   */
  @ApiStatus.AvailableSince("1.5.1")
  @Override
  default void writeRecipes(RuntimeResourcePack pack) {
    writeCraftingRecipe(pack);
    if (shouldWriteStonecuttingRecipe()) {
      writeStonecuttingRecipe(pack);
    }
  }

  @Override
  default int getFlammability(BlockState state, BlockView level, BlockPos pos, Direction direction) {
    final Block block = getBaseBlock();
    if (block != null) {
      if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) {
        return 0;
      }
      return block.getFlammability(block.getDefaultState(), level, pos, direction);
    } else {
      return IForgeBlock.super.getFlammability(state, level, pos, direction);
    }
  }

  @Override
  default int getFireSpreadSpeed(BlockState state, BlockView level, BlockPos pos, Direction direction) {
    final Block block = getBaseBlock();
    if (block != null) {
      if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) {
        return 0;
      }
      return block.getFireSpreadSpeed(block.getDefaultState(), level, pos, direction);
    } else {
      return IForgeBlock.super.getFireSpreadSpeed(state, level, pos, direction);
    }
  }

  @Override
  default boolean canEntityDestroy(BlockState state, BlockView level, BlockPos pos, Entity entity) {
    final Block block = getBaseBlock();
    if (block != null) {
      return block.canEntityDestroy(block.getDefaultState(), level, pos, entity);
    } else {
      return IForgeBlock.super.canEntityDestroy(state, level, pos, entity);
    }
  }

  /**
   * 获取当前方块所属的形状。{@link BlockShape#getShapeOf(Block)} 方法会尝试先调用此方法，如果方块不在此接口，则使用其内在的方法判断。
   *
   * @return 方块所属的形状。
   * @implNote 实现了此类的必须适当返回。如果返回 {@code null}，那么即使 {@link BlockShape#getShapeOf} 找得到对应类的形状对象，也不会再去查找了。
   */
  @Contract(pure = true)
  default BlockShape getBlockShape() {
    return null;
  }

  @Override
  default @Nullable RecipeCategory getRecipeCategory() {
    final BlockShape blockShape = getBlockShape();
    if (blockShape.isConstruction) {
      return RecipeCategory.BUILDING_BLOCKS;
    } else if (blockShape == BlockShape.FENCE || blockShape == BlockShape.WALL) {
      return RecipeCategory.DECORATIONS;
    } else if (blockShape == BlockShape.FENCE_GATE || blockShape == BlockShape.PRESSURE_PLATE || blockShape == BlockShape.BUTTON) {
      return RecipeCategory.REDSTONE;
    } else {
      return null;
    }
  }

  @Override
  @Nullable
  default BlockState getToolModifiedState(BlockState state, ItemUsageContext context, ToolAction toolAction, boolean simulate) {
    state = ObjectUtils.defaultIfNull(IForgeBlock.super.getToolModifiedState(state, context, toolAction, simulate), state);
    if (toolAction == ToolActions.AXE_STRIP && ExtShape.EXTENDED_STRIPPABLE_BLOCKS.containsKey(state.getBlock())) {
      return ExtShape.EXTENDED_STRIPPABLE_BLOCKS.get(state.getBlock()).getStateWithProperties(state);
    }
    return null;
  }
}
