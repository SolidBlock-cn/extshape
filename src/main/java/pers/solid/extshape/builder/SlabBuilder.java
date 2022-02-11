package pers.solid.extshape.builder;

import com.google.gson.JsonObject;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.*;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.SLABS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB);
  }

  @Override
  protected String getSuffix() {
    return "_slab";
  }

  /**
   * 该台阶方块的<b>底部</b>方块模型。<br>
   * 参考格式：
   * <pre>
   * { "parent": "minecraft:block/slab",
   *   "textures": {
   *     "bottom": "%s",
   *     "top": "%s",
   *     "side": "%s"}}</pre>
   * <p>
   */
  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JModel getBlockModel() {
    return simpleModel("block/slab");
  }

  /**
   * 该台阶方块的<b>顶部</b>方块模型。<br>
   * 参考格式：
   * <pre>
   * { "parent": "minecraft:block/slab",
   *   "textures": {
   *     "bottom": "%s",
   *     "top": "%s",
   *     "side": "%s"}}</pre>
   * <p>
   */
  @Environment(EnvType.CLIENT)
  public @Nullable JModel getTopBlockModel() {
    return simpleModel("block/slab_top");
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final Identifier identifier = getIdentifier();
    pack.addModel(getTopBlockModel(), blockIdentifier(identifier, "_top"));
  }

  /**
   * 台阶的方块状态文件。
   * <br>
   * 参考格式：
   * <pre>
   *  { "variants": {
   *     "type=bottom": {"model": "%1$s:block/%2$s"},
   *     "type=double": {"model": "%3$s:block/%4$s"},
   *     "type=top": {"model": "%1$s:block/%2$s_top"}
   *   }}
   * </pre>
   */
  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JState getBlockStates() {
    return JState.state(new JVariant()
        .put("type", "bottom", new JBlockModel(blockIdentifier(getIdentifier())))
        .put("type", "double", new JBlockModel(blockIdentifier(getBaseIdentifier())))
        .put("type", "top", new JBlockModel(blockIdentifier(getIdentifier(), "_top")))
    );
  }

  /**
   * 台阶的合成配方。参考格式：<pre>
   * {
   *   "type": "minecraft:crafting_shaped",
   *   "group": "%3$s",
   *   "pattern": [
   *     "###"
   *   ],
   *   "key": {
   *     "#": {
   *       "item": "%s"
   *     }
   *   },
   *   "result": {
   *     "item": "%s",
   *     "count": 6
   *   }
   * }
   * </pre>
   */
  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    return JRecipe.shaped(
        JPattern.pattern("###"),
        JKeys.keys().key("#", JIngredient.ingredient().item(getBaseIdentifier().toString())),
        JResult.stackedResult(getIdentifier().toString(), 6)
    ).group(getRecipeGroup());
  }

  /**
   * 台阶的切石配方。参考格式：
   * <pre>
   * {
   *   "type": "minecraft:stonecutting",
   *   "ingredient": {
   *     "item": "%s"
   *   },
   *   "result": "%s",
   *   "count": 2
   * }
   * </pre>
   */
  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(2);
  }

  /**
   * 台阶的战利品表。双台阶破坏后返回两个台阶。参考格式：<pre>
   * {  "type": "minecraft:block",
   *   "pools": [{
   *       "rolls": 1.0,
   *       "bonus_rolls": 0.0,
   *       "entries": [{
   *           "type": "minecraft:item",
   *           "functions": [{
   *               "function": "minecraft:set_count",
   *               "conditions": [{
   *                   "condition": "minecraft:block_state_property",
   *                   "block": "%1$s",
   *                   "properties": {"type": "double"}
   *                 }],
   *               "count": 2.0,
   *               "add": false
   *             },{
   *               "function": "minecraft:explosion_decay"
   *             }],
   *           "name": "%1$s"
   *         }]}]}</pre>
   */
  @Override
  public @Nullable JLootTable getLootTable() {
    return new JLootTable("block").pool(new JPool().rolls(1).bonus(0).entry(
        new JEntry().type("item").function(
            new JFunction("set_count").condition(
                new JCondition().condition("block_state_property").parameter("block", getIdentifier().toString()).parameter("properties", Util.make(new JsonObject(), o -> o.addProperty("type", "double")))
            ).parameter("count", 2).parameter("add", false)
        ).function("explosion_decay").name(getIdentifier().toString())
    ));
  }

  @Override
  public String getRecipeGroup() {
    Block baseBlock = this.baseBlock;
    if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_slab";
    if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_slab";
    if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_slab";
    if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_slab";
    return "";
  }
}
