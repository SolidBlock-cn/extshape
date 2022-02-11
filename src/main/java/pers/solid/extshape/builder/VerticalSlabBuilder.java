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
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
  protected VerticalSlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalSlabBlock(builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.VERTICAL_SLABS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_SLAB);
  }

  @Override
  protected String getSuffix() {
    return "_vertical_slab";
  }

  /**
   * 垂直台阶的方块状态定义。参考格式：<pre>
   * {
   *   "variants": {
   *     "facing=south": { "model": "%1$s" , "uvlock":true },
   *     "facing=west":  { "model": "%1$s", "y":  90 , "uvlock":true},
   *     "facing=north": { "model": "%1$s", "y": 180 , "uvlock":true },
   *     "facing=east":  { "model": "%1$s", "y": 270 , "uvlock":true }
   *   }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JState getBlockStates() {
    final Identifier identifier = blockIdentifier(getIdentifier());
    return JState.state(new JVariant()
        .put("facing", "south", new JBlockModel(identifier).uvlock())
        .put("facing", "west", new JBlockModel(identifier).uvlock().y(90))
        .put("facing", "north", new JBlockModel(identifier).uvlock().y(180))
        .put("facing", "east", new JBlockModel(identifier).uvlock().y(270))
    );
  }

  /**
   * 台阶的方块模型。参考格式：<pre>
   * {
   *     "parent": "extshape:block/vertical_slab",
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
    return simpleModel("extshape:block/vertical_slab");
  }

  /**
   * 垂直台阶方块没有直接的合成配方，跨方块类型的合成由 {@link BlocksBuilder} 定义。
   */
  @Override
  @Deprecated
  public @Nullable JRecipe getCraftingRecipe() {
    return null;
  }

  /**
   * 垂直台阶的切石配方。参考格式：
   * <pre>
   * {
   *   "type": "minecraft:stonecutting",
   *   "ingredient": {
   *     "item": "%s"
   *   },
   *   "result": "%s",
   *   "count": 2
   * }</pre>
   */
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
}
