package pers.solid.extshape.builder;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeVerticalStairsBlock;
import pers.solid.extshape.block.VerticalStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalStairsBuilder extends AbstractBlockBuilder<VerticalStairsBlock> {
  protected VerticalStairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalStairsBlock(builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.VERTICAL_STAIRS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_STAIRS);
  }

  @Override
  protected String getSuffix() {
    return "_vertical_stairs";
  }

  /**
   * 垂直台阶方块的方块状态定义。参考格式：<pre>
   * { "variants": {
   *     "facing=south_west": { "model": "%1$s" , "uvlock":true },
   *     "facing=north_west":  { "model": "%1$s", "y":  90 , "uvlock":true},
   *     "facing=north_east": { "model": "%1$s", "y": 180 , "uvlock":true },
   *     "facing=south_east":  { "model": "%1$s", "y": 270 , "uvlock":true }
   * }}
   * </pre>
   */
  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JState getBlockStates() {
    final Identifier identifier = blockIdentifier(getIdentifier());
    return JState.state(new JVariant()
        .put("facing", "south_west", new JBlockModel(identifier).uvlock())
        .put("facing", "north_west", new JBlockModel(identifier).uvlock().y(90))
        .put("facing", "north_east", new JBlockModel(identifier).uvlock().y(180))
        .put("facing", "south_east", new JBlockModel(identifier).uvlock().y(270))
    );
  }

  /**
   * 垂直台阶方块的方块模型。参考格式：<pre>
   * {
   *     "parent": "extshape:block/vertical_stairs",
   *     "textures": {
   *         "bottom": "%s",
   *         "top": "%s",
   *         "side": "%s"
   *     }
   * }
   * </pre>
   */
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

  /**
   * 垂直台阶方块的切石合成表。参考格式：<pre>
   * {
   *   "type": "minecraft:stonecutting",
   *   "ingredient": {
   *     "item": "%s"
   *   },
   *   "result": "%s",
   *   "count": 1
   * }
   * </pre>
   */
  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }


  @Override
  public String getRecipeGroup() {
    if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_stairs";
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_stairs";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_stairs";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
        "stained_terracotta_vertical_stairs";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
        "glazed_terracotta_vertical_stairs";
    return "";
  }
}
