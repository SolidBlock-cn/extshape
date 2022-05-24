package pers.solid.extshape.block;

import com.google.common.collect.BiMap;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JStonecuttingRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.mappings.VanillaStonecutting;
import pers.solid.extshape.mixin.AbstractBlockAccessor;

/**
 * 该模组中的绝大多数方块共用的接口。
 */
public interface ExtShapeBlockInterface extends BlockResourceGenerator {
  /**
   * 方块所在的合成配方的组。
   *
   * @return 方块合成配方中的 {@code group} 字段。
   * @see JRecipe#group(String)
   */
  default String getRecipeGroup() {
    return "";
  }

  /**
   * 方块的切石配方。该方法在生成数据时，只有在确认了 {@link #isStoneCut(Block)} 的情况下才会被使用，因此该方法内部无需判断方块是否可以切石。
   *
   * @return 方块的切石配方。用于切石机。
   */
  @Override
  default @Nullable JRecipe getStonecuttingRecipe() {
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

  @Environment(EnvType.CLIENT)
  default JModel simpleModel(String parent) {
    return new JModel(parent).textures(JTextures.ofSides(getTextureId(TextureKey.TOP), getTextureId(TextureKey.SIDE), getTextureId(TextureKey.BOTTOM)));
  }

  /**
   * 考虑到上蜡的方块使用的模型均为未上蜡的方块的模型，故在此做出调整。
   */
  @Environment(EnvType.CLIENT)
  @Override
  default Identifier getBlockModelId() {
    final BiMap<Block, Block> map = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get();
    if (this instanceof Block && map.containsKey(this)) {
      return ResourceGeneratorHelper.getBlockModelId(map.get(this));
    } else {
      return BlockResourceGenerator.super.getBlockModelId();
    }
  }

  default JStonecuttingRecipe simpleStoneCuttingRecipe(int resultCount) {
    final Block baseBlock = getBaseBlock();
    if (baseBlock == null) {
      return null;
    } else {
      final JStonecuttingRecipe stonecuttingRecipe = new JStonecuttingRecipe(baseBlock, (ItemConvertible) this, resultCount);
      stonecuttingRecipe.addInventoryChangedCriterion("has_base_block", baseBlock);
      return stonecuttingRecipe;
    }
  }

  /**
   * <p>为该方块写入合成配方。</p>
   *
   * @param pack 运行时资源包。
   * @see net.devtech.arrp.generator.ItemResourceGenerator#writeRecipes(RuntimeResourcePack)
   * @since 1.5.1
   */
  @ApiStatus.AvailableSince("1.5.1")
  default void writeCraftingRecipe(RuntimeResourcePack pack) {
    final JRecipe craftingRecipe = getCraftingRecipe();
    if (craftingRecipe != null) {
      final Identifier recipeId = getRecipeId();
      pack.addRecipe(recipeId, craftingRecipe);
      pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(recipeId), craftingRecipe);
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
    final JRecipe stonecuttingRecipe = getStonecuttingRecipe();
    if (stonecuttingRecipe != null) {
      final Identifier stonecuttingRecipeId = getStonecuttingRecipeId();
      pack.addRecipe(stonecuttingRecipeId, stonecuttingRecipe);
      pack.addRecipeAdvancement(stonecuttingRecipeId, getAdvancementIdForRecipe(stonecuttingRecipeId), stonecuttingRecipe);

      // 处理二次切石一步到位的情况。
      if (stonecuttingRecipe instanceof JStonecuttingRecipe jStonecuttingRecipe) {
        // block 是切石前的基础方块。
        for (Block block : VanillaStonecutting.INSTANCE.get(getBaseBlock())) {
          final String path = Registry.BLOCK.getId(block).getPath();
          final Identifier secondaryId = getRecipeId().brrp_append("_from_" + path + "_stonecutting");
          final JStonecuttingRecipe secondaryRecipe = new JStonecuttingRecipe(
              JIngredient.ofItem(block),
              jStonecuttingRecipe.result,
              jStonecuttingRecipe.count
          );
          secondaryRecipe.addInventoryChangedCriterion("has_" + path, block);
          pack.addRecipe(secondaryId, secondaryRecipe);
          pack.addRecipeAdvancement(secondaryId, getAdvancementIdForRecipe(secondaryId), secondaryRecipe);
        }
      }
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
}
