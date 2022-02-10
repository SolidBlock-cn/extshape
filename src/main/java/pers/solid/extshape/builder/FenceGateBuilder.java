package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {
    protected final Item craftingIngredient;

    protected FenceGateBuilder(Block baseBlock, Item craftingIngredient) {
        super(baseBlock, builder -> new ExtShapeFenceGateBlock(builder.blockSettings));
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCE_GATES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE_GATE);
    }

    @Override
    protected String getSuffix() {
        return "_fence_gate";
    }

    @Override
    public FenceGateBlock build() {
        super.build();
        IngredientMappings.MAPPING_OF_FENCE_GATE_INGREDIENTS.put(this.instance, this.craftingIngredient);
        return this.instance;
    }

    /**
     * 栅栏门的方块状态定义。参考格式：<pre>
     * {
     *   "variants": {
     *     "facing=east,in_wall=false,open=false": {
     *       "uvlock": true,
     *       "y": 270,
     *       "model": "%1$s:block/%2$s"
     *     },
     *     "facing=east,in_wall=false,open=true": {
     *       "uvlock": true,
     *       "y": 270,
     *       "model": "%1$s:block/%2$s_open"
     *     },
     *     "facing=east,in_wall=true,open=false": {
     *       "uvlock": true,
     *       "y": 270,
     *       "model": "%1$s:block/%2$s_wall"
     *     },
     *     "facing=east,in_wall=true,open=true": {
     *       "uvlock": true,
     *       "y": 270,
     *       "model": "%1$s:block/%2$s_wall_open"
     *     },
     *     "facing=north,in_wall=false,open=false": {
     *       "uvlock": true,
     *       "y": 180,
     *       "model": "%1$s:block/%2$s"
     *     },
     *     "facing=north,in_wall=false,open=true": {
     *       "uvlock": true,
     *       "y": 180,
     *       "model": "%1$s:block/%2$s_open"
     *     },
     *     "facing=north,in_wall=true,open=false": {
     *       "uvlock": true,
     *       "y": 180,
     *       "model": "%1$s:block/%2$s_wall"
     *     },
     *     "facing=north,in_wall=true,open=true": {
     *       "uvlock": true,
     *       "y": 180,
     *       "model": "%1$s:block/%2$s_wall_open"
     *     },
     *     "facing=south,in_wall=false,open=false": {
     *       "uvlock": true,
     *       "model": "%1$s:block/%2$s"
     *     },
     *     "facing=south,in_wall=false,open=true": {
     *       "uvlock": true,
     *       "model": "%1$s:block/%2$s_open"
     *     },
     *     "facing=south,in_wall=true,open=false": {
     *       "uvlock": true,
     *       "model": "%1$s:block/%2$s_wall"
     *     },
     *     "facing=south,in_wall=true,open=true": {
     *       "uvlock": true,
     *       "model": "%1$s:block/%2$s_wall_open"
     *     },
     *     "facing=west,in_wall=false,open=false": {
     *       "uvlock": true,
     *       "y": 90,
     *       "model": "%1$s:block/%2$s"
     *     },
     *     "facing=west,in_wall=false,open=true": {
     *       "uvlock": true,
     *       "y": 90,
     *       "model": "%1$s:block/%2$s_open"
     *     },
     *     "facing=west,in_wall=true,open=false": {
     *       "uvlock": true,
     *       "y": 90,
     *       "model": "%1$s:block/%2$s_wall"
     *     },
     *     "facing=west,in_wall=true,open=true": {
     *       "uvlock": true,
     *       "y": 90,
     *       "model": "%1$s:block/%2$s_wall_open"
     *     }
     *   }
     * }
     * </pre>
     */
    @Override
    @Environment(EnvType.CLIENT)
    public JState getBlockStates() {
        final JVariant variant = new JVariant();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            final Identifier identifier = getIdentifier();
            final int rotation = (int) direction.asRotation();
            variant.put("in_wall=false,open=false,facing", direction, new JBlockModel(blockIdentifier(identifier)).uvlock().y(rotation));
            variant.put("in_wall=false,open=true,facing", direction, new JBlockModel(blockIdentifier(identifier, "_open")).uvlock().y(rotation));
            variant.put("in_wall=true,open=false,facing", direction, new JBlockModel(blockIdentifier(identifier, "_wall")).uvlock().y(rotation));
            variant.put("in_wall=true,open=true,facing", direction, new JBlockModel(blockIdentifier(identifier, "_wall_open")).uvlock().y(rotation));
        }
        return JState.state(variant);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getBlockModel() {
        return simpleTextureModel("block/template_fence_gate");
    }

    @Environment(EnvType.CLIENT)
    public @Nullable JModel getOpenBlockModel() {
        return simpleTextureModel("block/template_fence_gate_open");
    }

    @Environment(EnvType.CLIENT)
    public @Nullable JModel getWallBlockModel() {
        return simpleTextureModel("block/template_fence_gate_wall");
    }

    @Environment(EnvType.CLIENT)
    public @Nullable JModel getWallOpenBlockModel() {
        return simpleTextureModel("block/template_fence_gate_wall_open");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void writeBlockModel(RuntimeResourcePack pack) {
        super.writeBlockModel(pack);
        final Identifier identifier = getIdentifier();
        pack.addModel(getOpenBlockModel(), blockIdentifier(identifier, "_open"));
        pack.addModel(getWallBlockModel(), blockIdentifier(identifier, "_wall"));
        pack.addModel(getWallOpenBlockModel(), blockIdentifier(identifier, "_wall_open"));
    }

    /**
     * 栅栏门的合成配方。参考格式：<pre>
     * {
     *     "type": "minecraft:crafting_shaped",
     *     "group": "%s",
     *     "pattern": [
     *       "#W#",
     *       "#W#"
     *     ],
     *     "key": {
     *       "#": {"item": "%s"},
     *       "W": {"item": "%s"}
     *     },
     *     "result": {"item": "%s"}
     *   }
     * </pre>
     */
    @Override
    public @Nullable JRecipe getCraftingRecipe() {
        return JRecipe.shaped(JPattern.pattern("#W#", "#W#"), JKeys.keys()
                .key("#", JIngredient.ingredient().item(craftingIngredient))
                .key("W", JIngredient.ingredient().item(getBaseIdentifier().toString())), JResult.stackedResult(getIdentifier().toString(), 3));
    }

    @Override
    public String getRecipeGroup() {
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence_gate";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence_gate";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_fence_gate";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_fence_gate";
        return "";
    }
}
