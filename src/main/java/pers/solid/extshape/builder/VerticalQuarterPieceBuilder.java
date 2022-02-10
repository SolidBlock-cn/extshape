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
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
    protected VerticalQuarterPieceBuilder(Block baseBlock) {
        super(baseBlock, builder -> new ExtShapeVerticalQuarterPieceBlock(builder.blockSettings));
        this.defaultTag = ExtShapeBlockTag.VERTICAL_QUARTER_PIECES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_QUARTER_PIECE);
    }

    @Override
    protected String getSuffix() {
        return "_vertical_quarter_piece";
    }

    /**
     * 参考格式：<pre>
     * {
     *   "variants": {
     *     "facing=south_east": { "model": "%1$s" , "uvlock":true },
     *     "facing=south_west":  { "model": "%1$s", "y":  90 , "uvlock":true},
     *     "facing=north_west": { "model": "%1$s", "y": 180 , "uvlock":true },
     *     "facing=north_east":  { "model": "%1$s", "y": 270 , "uvlock":true }
     *   }
     * }
     * </pre>
     */
    @Environment(EnvType.CLIENT)
    @Override
    public @Nullable JState getBlockStates() {
        final Identifier identifier = blockIdentifier(getIdentifier());
        return JState.state(new JVariant()
                .put("facing", "south_east", new JBlockModel(identifier).uvlock())
                .put("facing", "south_west", new JBlockModel(identifier).uvlock().y(90))
                .put("facing", "north_west", new JBlockModel(identifier).uvlock().y(180))
                .put("facing", "north_east", new JBlockModel(identifier).uvlock().y(270))
        );
    }

    /**
     * 参考格式：<pre>
     * {
     *     "parent": "extshape:block/vertical_quarter_piece",
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
        return simpleModel("extshape:block/vertical_quarter_piece");
    }

    /**
     * 参考格式：<pre>
     * {
     *   "type": "minecraft:stonecutting",
     *   "ingredient": {
     *     "item": "%s"
     *   },
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
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_quarter_piece";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_quarter_piece";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_quarter_piece";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_vertical_quarter_piece";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_vertical_quarter_piece";
        return "";
    }
}
