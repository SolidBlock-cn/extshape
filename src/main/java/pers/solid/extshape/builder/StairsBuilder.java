package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
    /**
     * 楼梯方块的方块状态定义文件的格式。<br>
     * Java 8 不支持文本块，所以……
     */
    @Environment(EnvType.CLIENT)
    private static final String BLOCK_STATES_FORMAT = "{\"variants\":{\"facing=east,half=bottom,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":270,\"uvlock\":true},\"facing=east,half=bottom,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\"},\"facing=east,half=bottom,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":270,\"uvlock\":true},\"facing=east,half=bottom,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\"},\"facing=east,half=bottom,shape=straight\":{\"model\":\"%1$s:block/%2$s\"},\"facing=east,half=top,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"uvlock\":true},\"facing=east,half=top,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":90,\"uvlock\":true},\"facing=east,half=top,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"uvlock\":true},\"facing=east,half=top,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":90,\"uvlock\":true},\"facing=east,half=top,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"x\":180,\"uvlock\":true},\"facing=north,half=bottom,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":180,\"uvlock\":true},\"facing=north,half=bottom,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":270,\"uvlock\":true},\"facing=north,half=bottom,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":180,\"uvlock\":true},\"facing=north,half=bottom,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":270,\"uvlock\":true},\"facing=north,half=bottom,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"y\":270,\"uvlock\":true},\"facing=north,half=top,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":270,\"uvlock\":true},\"facing=north,half=top,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"uvlock\":true},\"facing=north,half=top,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":270,\"uvlock\":true},\"facing=north,half=top,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"uvlock\":true},\"facing=north,half=top,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"x\":180,\"y\":270,\"uvlock\":true},\"facing=south,half=bottom,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\"},\"facing=south,half=bottom,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":90,\"uvlock\":true},\"facing=south,half=bottom,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\"},\"facing=south,half=bottom,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":90,\"uvlock\":true},\"facing=south,half=bottom,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"y\":90,\"uvlock\":true},\"facing=south,half=top,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":90,\"uvlock\":true},\"facing=south,half=top,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":180,\"uvlock\":true},\"facing=south,half=top,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":90,\"uvlock\":true},\"facing=south,half=top,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":180,\"uvlock\":true},\"facing=south,half=top,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"x\":180,\"y\":90,\"uvlock\":true},\"facing=west,half=bottom,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":90,\"uvlock\":true},\"facing=west,half=bottom,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"y\":180,\"uvlock\":true},\"facing=west,half=bottom,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":90,\"uvlock\":true},\"facing=west,half=bottom,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"y\":180,\"uvlock\":true},\"facing=west,half=bottom,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"y\":180,\"uvlock\":true},\"facing=west,half=top,shape=inner_left\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":180,\"uvlock\":true},\"facing=west,half=top,shape=inner_right\":{\"model\":\"%1$s:block/%2$s_inner\",\"x\":180,\"y\":270,\"uvlock\":true},\"facing=west,half=top,shape=outer_left\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":180,\"uvlock\":true},\"facing=west,half=top,shape=outer_right\":{\"model\":\"%1$s:block/%2$s_outer\",\"x\":180,\"y\":270,\"uvlock\":true},\"facing=west,half=top,shape=straight\":{\"model\":\"%1$s:block/%2$s\",\"x\":180,\"y\":180,\"uvlock\":true}}}";

    protected StairsBuilder(Block baseBlock) {
        super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock.getDefaultState(), builder.blockSettings));
        this.defaultTag = ExtShapeBlockTag.STAIRS;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.STAIRS);
    }

    @Override
    protected String getSuffix() {
        return "_stairs";
    }

    /**
     * 楼梯的方块模型。参考格式：<pre>
     * { "parent": "minecraft:block/stairs",
     *   "textures": {
     *     "bottom": "%s",
     *     "top": "%s",
     *     "side": "%s" }}
     * </pre>
     */
    @Override
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getBlockModel() {
        return simpleModel("block/stairs");
    }

    /**
     * 内部形状的楼梯的方块模型。
     */
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getInnerBlockModel() {
        return simpleModel("block/inner_stairs");
    }

    /**
     * 外侧形状的楼梯方块模型。
     */
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getOuterBlockModel() {
        return simpleModel("block/outer_stairs");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void writeBlockModel(RuntimeResourcePack pack) {
        super.writeBlockModel(pack);
        pack.addModel(getInnerBlockModel(), blockIdentifier(getIdentifier(), "_inner"));
        pack.addModel(getOuterBlockModel(), blockIdentifier(getIdentifier(), "_outer"));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void writeBlockStates(RuntimeResourcePack pack) {
        final Identifier identifier = getIdentifier();
        pack.addAsset(new Identifier(identifier.getNamespace(), "blockstates/" + identifier.getPath() + ".json"), String.format(BLOCK_STATES_FORMAT, identifier.getNamespace(), identifier.getPath()).getBytes());
    }

    /**
     * 楼梯的合成配方。参考格式：<pre>
     * { "type": "minecraft:crafting_shaped",
     *   "group": "%3$s",
     *   "pattern": [
     *     "#  ",
     *     "## ",
     *     "###"
     *   ],
     *   "key": {"#": {"item": "%s"}},
     *   "result": {"item": "%s", "count": 4} }
     * </pre>
     */
    @Override
    public @Nullable JRecipe getCraftingRecipe() {
        return JRecipe.shaped(JPattern.pattern(
                "#  ",
                "## ",
                "###"
        ), JKeys.keys().key("#", JIngredient.ingredient().item(getBaseIdentifier().toString())), JResult.stackedResult(getIdentifier().toString(), 4)).group(getRecipeGroup());
    }
}
