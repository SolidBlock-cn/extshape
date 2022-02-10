package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeFenceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceBuilder extends AbstractBlockBuilder<FenceBlock> {
    protected final Item craftingIngredient;

    protected FenceBuilder(Block baseBlock, Item craftingIngredient) {
        super(baseBlock, builder -> new ExtShapeFenceBlock(builder.blockSettings));
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE);
    }

    @Override
    protected String getSuffix() {
        return "_fence";
    }

    @Override
    public FenceBlock build() {
        super.build();
        IngredientMappings.MAPPING_OF_FENCE_INGREDIENTS.put(this.instance, this.craftingIngredient);
        return this.instance;
    }

    /**
     * 栅栏的方块状态定义。参考格式：<pre>
     * {
     *   "multipart": [
     *     {
     *       "apply": {
     *         "model": "%1$s:block/%2$s_post"
     *       }
     *     },
     *     {
     *       "when": {
     *         "north": "true"
     *       },
     *       "apply": {
     *         "model": "%1$s:block/%2$s_side",
     *         "uvlock": true
     *       }
     *     },
     *     {
     *       "when": {
     *         "east": "true"
     *       },
     *       "apply": {
     *         "model": "%1$s:block/%2$s_side",
     *         "y": 90,
     *         "uvlock": true
     *       }
     *     },
     *     {
     *       "when": {
     *         "south": "true"
     *       },
     *       "apply": {
     *         "model": "%1$s:block/%2$s_side",
     *         "y": 180,
     *         "uvlock": true
     *       }
     *     },
     *     {
     *       "when": {
     *         "west": "true"
     *       },
     *       "apply": {
     *         "model": "%1$s:block/%2$s_side",
     *         "y": 270,
     *         "uvlock": true
     *       }
     *     }
     *   ]
     * }
     * </pre>
     */
    @Override
    @Environment(EnvType.CLIENT)
    public @Nullable JState getBlockStates() {
        final Identifier identifier = getIdentifier();
        final JState state = JState.state(new JMultipart().addModel(new JBlockModel(blockIdentifier(identifier, "_post"))));
        for (Direction direction : Direction.Type.HORIZONTAL) {
            state.add(new JMultipart().addModel(new JBlockModel(blockIdentifier(identifier, "_side")).uvlock().y(((int) direction.getOpposite().asRotation()))).when(new JWhen().add(direction.asString(), "true")));
        }
        return state;
    }

    @Environment(EnvType.CLIENT)
    public JModel getInventoryModel() {
        return simpleTextureModel("block/fence_inventory");
    }

    @Environment(EnvType.CLIENT)
    public JModel getSideModel() {
        return simpleTextureModel("block/fence_side");
    }

    @Environment(EnvType.CLIENT)
    public JModel getPostModel() {
        return simpleTextureModel("block/fence_post");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void writeBlockModel(RuntimeResourcePack pack) {
        final Identifier identifier = getIdentifier();
        pack.addModel(getInventoryModel(), blockIdentifier(identifier, "_inventory"));
        pack.addModel(getSideModel(), blockIdentifier(identifier, "_side"));
        pack.addModel(getPostModel(), blockIdentifier(identifier, "_post"));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getItemModel() {
        return JModel.model(blockIdentifier(getIdentifier(), "_inventory"));
    }

    /**
     * 栅栏的合成配方。参考格式：<pre>
     * {
     *   "type": "minecraft:crafting_shaped",
     *   "group": "%s",
     *   "pattern": [
     *     "W#W",
     *     "W#W"
     *   ],
     *   "key": {
     *     "W": {"item": "%s"},
     *     "#": {"item": "%s"}
     *   },
     *   "result": {"item": "%s",n"count": 3}
     * }
     * </pre>
     */
    @Override
    public @Nullable JRecipe getCraftingRecipe() {
        return JRecipe.shaped(JPattern.pattern("W#W", "W#W"), JKeys.keys()
                .key("W", JIngredient.ingredient().item(getBaseIdentifier().toString()))
                .key("#", JIngredient.ingredient().item(craftingIngredient)), JResult.stackedResult(getIdentifier().toString(), 3));
    }

    @Override
    public String getRecipeGroup() {
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_fence";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_fence";
        return "";
    }

}
