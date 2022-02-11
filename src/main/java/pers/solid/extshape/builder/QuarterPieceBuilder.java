package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeQuarterPieceBlock;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class QuarterPieceBuilder extends AbstractBlockBuilder<QuarterPieceBlock> {
  protected QuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeQuarterPieceBlock(builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.QUARTER_PIECES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.QUARTER_PIECE);
  }

  @Override
  protected String getSuffix() {
    return "_quarter_piece";
  }

  /**
   * 横条方块的方块状态定义文件。参考格式：
   * <pre>
   * { "variants": {
   *     "half=top,facing=south": { "model": "%1$s_top" , "uvlock":true },
   *     "half=top,facing=west":  { "model": "%1$s_top", "y":  90 , "uvlock":true},
   *     "half=top,facing=north": { "model": "%1$s_top", "y": 180 , "uvlock":true },
   *     "half=top,facing=east":  { "model": "%1$s_top", "y": 270 , "uvlock":true },
   *     "half=bottom,facing=south": { "model": "%1$s" , "uvlock":true },
   *     "half=bottom,facing=west":  { "model": "%1$s", "y":  90 , "uvlock":true},
   *     "half=bottom,facing=north": { "model": "%1$s", "y": 180 , "uvlock":true },
   *     "half=bottom,facing=east":  { "model": "%1$s", "y": 270 , "uvlock":true }
   * }}
   * </pre>
   */
  @Override
  @Environment(EnvType.CLIENT)
  public JState getBlockStates() {
    final JVariant variant = JState.variant();
    for (Direction direction : Direction.Type.HORIZONTAL) {
      variant
          .put("half=top,facing", direction, new JBlockModel(blockIdentifier(getIdentifier(), "_top")).uvlock().y(((int) direction.asRotation())))
          .put("half=bottom,facing", direction, new JBlockModel(blockIdentifier(getIdentifier())).uvlock().y((int) direction.asRotation()));
    }
    return JState.state(variant);
  }

  /**
   * 参考格式：
   * <pre>
   * {
   *     "parent": "extshape:block/quarter_piece",
   *     "textures": {
   *         "bottom": "%s",
   *         "top": "%s",
   *         "side": "%s"
   *     }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JModel getBlockModel() {
    return simpleModel("extshape:block/quarter_piece");
  }

  /**
   * 参考格式：
   * <pre>
   * {
   *     "parent": "extshape:block/quarter_piece_top",
   *     "textures": {
   *         "bottom": "%s",
   *         "top": "%s",
   *         "side": "%s"
   *     }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  public @Nullable JModel getTopBlockModel() {
    return simpleModel("extshape:block/quarter_piece_top");
  }

  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    pack.addModel(getTopBlockModel(), blockIdentifier(getIdentifier(), "_top"));
  }

  /**
   * 参考格式：<pre>
   * {
   *   "type": "minecraft:stonecutting",
   *   "ingredient": {"item": "%s"},
   *   "result": "%s",
   *   "count": 4
   * }
   * </pre>
   */
  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(4);
  }

  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_quarter_piece";
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_quarter_piece";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_quarter_piece";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_quarter_piece";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_quarter_piece";
    return "";
  }
}
