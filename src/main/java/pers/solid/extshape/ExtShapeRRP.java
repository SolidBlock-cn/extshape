package pers.solid.extshape;

import net.devtech.arrp.api.RRPCallbackConditional;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.generator.ItemResourceGenerator;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.devtech.arrp.json.recipe.JStonecuttingRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.TextureMappings;
import pers.solid.extshape.mixin.AbstractBlockMixin;
import pers.solid.extshape.tag.TagGenerator;

import static net.minecraft.item.Item.BLOCK_ITEMS;

public final class ExtShapeRRP {
  /**
   * 仅适用于客户端的资源包。
   *
   * @see ExtShapeRRP#STANDARD_PACK
   */
  @Environment(EnvType.CLIENT)
  public static final RuntimeResourcePack CLIENT_PACK = RuntimeResourcePack.create(new Identifier("extshape", "client"));
  /**
   * 适用于整个模组的运行时资源包，服务端和客户端都会运行。之所以称为“standard”而非“server”，是因为即使在客户端启动时，该资源包也会存在。
   *
   * @see ExtShapeRRP#CLIENT_PACK
   */
  public static final RuntimeResourcePack STANDARD_PACK = RuntimeResourcePack.create(new Identifier("extshape", "standard"));

  private static final Logger LOGGER = LoggerFactory.getLogger("EXTSHAPE-Runtime resource pack");

  public static void registerRRP() {
    RRPCallbackConditional.BEFORE_VANILLA.register((resourceType, builder) -> {
      if (resourceType == ResourceType.SERVER_DATA || resourceType == null) {
        STANDARD_PACK.clearResources(ResourceType.SERVER_DATA);
        generateServerData(STANDARD_PACK);
        builder.add(STANDARD_PACK);
      }
      if (resourceType == ResourceType.CLIENT_RESOURCES || resourceType == null) {
        try {
          CLIENT_PACK.clearResources(ResourceType.CLIENT_RESOURCES);
          generateClientResources(CLIENT_PACK);
          builder.add(CLIENT_PACK);
        } catch (NoSuchFieldError throwable) {
          throw new AssertionError(throwable);
        }
      }
    });
  }

  /**
   * 为运行时资源包添加客户端资源。该方法执行时不会清除已生成的资源（如有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时资源包。
   */
  @Environment(EnvType.CLIENT)
  public static void generateClientResources(RuntimeResourcePack pack) {
    LOGGER.info("Generating client resources for Extended Block Shapes mod!");

    // 注册纹理变量。
    TextureMappings.registerTextures();

    // 为方块添加资源。
    for (Block block : ExtShapeBlocks.BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeAssets(pack);
    }
  }

  public static void generateServerData(RuntimeResourcePack pack) {
    LOGGER.info("Generating server data for Extended Block Shapes mod!");

    // 为方块添加数据。
    for (Block block : ExtShapeBlocks.BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeData(pack);
    }

    for (Block baseBlock : BlockMappings.BASE_BLOCKS) {
      generateCrossShapeDataFor(baseBlock, pack);
    }

    // 添加方块标签。
    TagGenerator.writeAllBlockTagFiles(pack);
  }

  /**
   * 获取方块所对应的配方的id。
   */
  private static Identifier recipeIdOf(Block block) {
    if (block instanceof ItemResourceGenerator generator) {
      return generator.getRecipeId();
    } else {
      final Identifier itemId = Registry.ITEM.getId(block.asItem());
      final ItemGroup group = block.asItem().getGroup();
      if (group != null) {
        return itemId.brrp_prepend(group.getName() + "/");
      } else {
        return itemId;
      }
    }
  }

  /**
   * 获取方块所对应的特定配方的id，会加上后缀并<b>强制使用 {@link ExtShape#MOD_ID} 作为命名空间</b>。例如：
   * <pre>{@code recipeIdOf(Blocks.OAK_SLAB, "from_vertical_slab")
   * -> new Identifier("extshape", "oak_slab_from_vertical_slab")}</pre>
   */
  private static Identifier recipeIdOf(Block block, String prefix) {
    final Identifier recipeId = recipeIdOf(block);
    return new Identifier(ExtShape.MOD_ID, recipeId.getPath() + prefix);
  }

  /**
   * 为方块生成跨形状的数据。例如，台阶与垂直台阶之间的合成配方，或者台阶到横条的切石配方。
   *
   * @param baseBlock 基础方块。
   * @param pack      运行时资源包。
   */
  public static void generateCrossShapeDataFor(Block baseBlock, RuntimeResourcePack pack) {
    // 台阶与垂直台阶之间的配方。
    final Block slab = BlockMappings.getBlockOf(Shape.SLAB, baseBlock);
    final Block verticalSlab = BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, baseBlock);
    if (slab != null && verticalSlab != null
        && BLOCK_ITEMS.containsKey(slab) && BLOCK_ITEMS.containsKey(verticalSlab)) {
      Identifier toVerticalId = recipeIdOf(verticalSlab);
      Identifier toSlabId = recipeIdOf(slab, "_from_vertical_slab");
      final JShapelessRecipe toVertical = new JShapelessRecipe(verticalSlab, slab);
      toVertical.addInventoryChangedCriterion("has_slab", slab).prepareAdvancement(toVerticalId);
      pack.addRecipe(toVerticalId, toVertical);
      pack.addAdvancement(toVerticalId.brrp_prepend("recipes/"), toVertical.advancementBuilder);
      final JShapelessRecipe toSlab = new JShapelessRecipe(slab, verticalSlab);
      toSlab.addInventoryChangedCriterion("has_vertical_slab", verticalSlab).prepareAdvancement(toSlabId);
      pack.addRecipe(toSlabId, toSlab);
      pack.addAdvancement(toSlabId.brrp_prepend("recipes/"), toSlab.advancementBuilder);
    }

    // 楼梯与垂直楼梯之间的配方。
    final Block stairs = BlockMappings.getBlockOf(Shape.STAIRS, baseBlock);
    final Block verticalStairs = BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS, baseBlock);
    if (stairs != null && verticalStairs != null
        && BLOCK_ITEMS.containsKey(stairs) && BLOCK_ITEMS.containsKey(verticalStairs)) {
      Identifier toVerticalId = recipeIdOf(verticalStairs);
      Identifier toStairsId = recipeIdOf(stairs, "_from_vertical_stairs");
      final JShapelessRecipe toVertical = new JShapelessRecipe(verticalStairs, stairs);
      toVertical.addInventoryChangedCriterion("has_stairs", stairs).prepareAdvancement(toVerticalId);
      pack.addRecipe(toVerticalId, toVertical);
      pack.addAdvancement(toVerticalId.brrp_prepend("recipes/"), toVertical.advancementBuilder);
      final JShapelessRecipe toStairs = new JShapelessRecipe(stairs, verticalStairs);
      toStairs.addInventoryChangedCriterion("has_vertical_stairs", verticalStairs).prepareAdvancement(toStairsId);
      pack.addRecipe(toStairsId, toStairs);
      pack.addAdvancement(toStairsId.brrp_prepend("recipes/"), toStairs.advancementBuilder);
    }

    // 横条与纵条之间的配方。
    final Block quarterPiece = BlockMappings.getBlockOf(Shape.QUARTER_PIECE, baseBlock);
    final Block verticalQuarterPiece = BlockMappings.getBlockOf(Shape.VERTICAL_QUARTER_PIECE, baseBlock);
    if (quarterPiece != null && verticalQuarterPiece != null
        && BLOCK_ITEMS.containsKey(quarterPiece) && BLOCK_ITEMS.containsKey(verticalQuarterPiece)) {
      Identifier toVerticalId = recipeIdOf(verticalQuarterPiece);
      Identifier toQuarterPieceId = recipeIdOf(quarterPiece, "_from_vertical_quarter_piece");
      final JShapelessRecipe toVertical = new JShapelessRecipe(verticalQuarterPiece, quarterPiece);
      toVertical.addInventoryChangedCriterion("has_stairs", quarterPiece).prepareAdvancement(toVerticalId);
      pack.addRecipe(toVerticalId, toVertical);
      pack.addAdvancement(toVerticalId.brrp_prepend("recipes/"), toVertical.advancementBuilder);
      final JShapelessRecipe toQuarterPiece = new JShapelessRecipe(quarterPiece, verticalQuarterPiece);
      toQuarterPiece.addInventoryChangedCriterion("has_vertical_stairs", verticalQuarterPiece).prepareAdvancement(toQuarterPieceId);
      pack.addRecipe(toQuarterPieceId, toQuarterPiece);
      pack.addAdvancement(toQuarterPieceId.brrp_prepend("recipes/"), toQuarterPiece.advancementBuilder);
    }

    // 该方块是否允许被切石
    final boolean stoneCut = ((AbstractBlockMixin) baseBlock).getMaterial() == Material.STONE;

    if (quarterPiece != null && BLOCK_ITEMS.containsKey(quarterPiece)) {
      // 1x楼梯 -> 3x横条
      if (stairs != null && BLOCK_ITEMS.containsKey(stairs) && stoneCut) {
        final Identifier fromStairsId = recipeIdOf(quarterPiece).brrp_append("_from_stairs_stonecutting");
        final JStonecuttingRecipe fromStairsRecipe = new JStonecuttingRecipe(stairs, quarterPiece, 3);
        fromStairsRecipe.addInventoryChangedCriterion("has_stairs", stairs).prepareAdvancement(fromStairsId);
        pack.addRecipe(fromStairsId, fromStairsRecipe);
        pack.addAdvancement(fromStairsId.brrp_prepend("recipes/"), fromStairsRecipe.advancementBuilder);
      }

      // 1x台阶 -> 2x横条
      if (slab != null && BLOCK_ITEMS.containsKey(slab)) {
        final Identifier craftingId = recipeIdOf(quarterPiece).brrp_append("_from_slab");
        final JShapedRecipe craftingRecipe = new JShapedRecipe(quarterPiece)
            .pattern("###")
            .addKey("#", slab)
            .resultCount(6);
        pack.addRecipe(craftingId, craftingRecipe);
        craftingRecipe.addInventoryChangedCriterion("has_slab", slab).prepareAdvancement(craftingId);
        pack.addAdvancement(craftingId.brrp_prepend("recipes/"), craftingRecipe.advancementBuilder);
        if (stoneCut) {
          final Identifier stonecuttingId = recipeIdOf(quarterPiece).brrp_append("_from_slab_stonecutting");
          final JStonecuttingRecipe stonecuttingRecipe = new JStonecuttingRecipe(slab, quarterPiece, 2);
          pack.addRecipe(stonecuttingId, stonecuttingRecipe);
          stonecuttingRecipe.addInventoryChangedCriterion("has_slab", slab).prepareAdvancement(stonecuttingId);
          pack.addAdvancement(stonecuttingId.brrp_prepend("recipes/"), stonecuttingRecipe.advancementBuilder);
        }
      }

      // 1x纵台阶 -> 2x横条
      if (verticalSlab != null && BLOCK_ITEMS.containsKey(verticalSlab)) {
        final Identifier craftingId = recipeIdOf(quarterPiece).brrp_append("_from_vertical_slab");
        final JShapedRecipe craftingRecipe = new JShapedRecipe(quarterPiece)
            .pattern("###")
            .addKey("#", verticalSlab)
            .resultCount(6);
        pack.addRecipe(craftingId, craftingRecipe);
        craftingRecipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab).prepareAdvancement(craftingId);
        pack.addAdvancement(craftingId.brrp_prepend("recipes/"), craftingRecipe.advancementBuilder);
        if (stoneCut) {
          final Identifier stonecuttingId = recipeIdOf(quarterPiece).brrp_append("_from_vertical_slab_stonecutting");
          final JStonecuttingRecipe stonecuttingRecipe = new JStonecuttingRecipe(verticalSlab, quarterPiece, 2);
          pack.addRecipe(stonecuttingId, stonecuttingRecipe);
          stonecuttingRecipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab).prepareAdvancement(stonecuttingId);
          pack.addAdvancement(stonecuttingId.brrp_prepend("recipes/"), stonecuttingRecipe.advancementBuilder);
        }
      }
    }

    if (verticalQuarterPiece != null && BLOCK_ITEMS.containsKey(verticalQuarterPiece)) {
      // 1x纵台阶 -> 2x纵条
      if (verticalSlab != null && BLOCK_ITEMS.containsKey(verticalSlab)) {
        final Identifier craftingId = recipeIdOf(verticalQuarterPiece).brrp_append("_from_vertical_slab");
        final JShapedRecipe craftingRecipe = new JShapedRecipe(verticalQuarterPiece)
            .pattern("#", "#", "#")
            .addKey("#", verticalSlab)
            .resultCount(2);
        pack.addRecipe(craftingId, craftingRecipe);
        craftingRecipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab).prepareAdvancement(craftingId);
        pack.addAdvancement(craftingId.brrp_prepend("recipes/"), craftingRecipe.advancementBuilder);
        if (stoneCut) {
          final Identifier stonecuttingId = recipeIdOf(verticalQuarterPiece).brrp_append("_from_vertical_slab_stonecutting");
          final JStonecuttingRecipe stonecuttingRecipe = new JStonecuttingRecipe(verticalSlab, verticalQuarterPiece, 2);
          pack.addRecipe(stonecuttingId, stonecuttingRecipe);
          stonecuttingRecipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab).prepareAdvancement(stonecuttingId);
          pack.addAdvancement(stonecuttingId.brrp_prepend("recipes/"), stonecuttingRecipe.advancementBuilder);
        }
      }

      // 1x纵楼梯 -> 3x纵条
      if (verticalStairs != null && BLOCK_ITEMS.containsKey(verticalStairs) && stoneCut) {
        final Identifier stonecuttingId = recipeIdOf(verticalQuarterPiece).brrp_append("_from_vertical_stairs_stonecutting");
        final JStonecuttingRecipe stonecuttingRecipe = new JStonecuttingRecipe(verticalStairs, verticalQuarterPiece, 3);
        pack.addRecipe(stonecuttingId, stonecuttingRecipe);
        stonecuttingRecipe.addInventoryChangedCriterion("has_vertical_stairs", verticalStairs).prepareAdvancement(stonecuttingId);
        pack.addAdvancement(stonecuttingId.brrp_prepend("recipes/"), stonecuttingRecipe.advancementBuilder);
      }
    }
  }
}
