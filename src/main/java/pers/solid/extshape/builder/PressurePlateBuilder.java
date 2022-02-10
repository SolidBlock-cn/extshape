package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.HardnessAccessor;
import pers.solid.extshape.mixin.SettingsAccessor;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {
    public final @NotNull PressurePlateBlock.ActivationRule type;

    protected PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock) {
        super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(((HardnessAccessor) ((SettingsAccessor) baseBlock).getSettings()).getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(type, builder.blockSettings));
        this.type = type;
        this.defaultTag = ExtShapeBlockTag.PRESSURE_PLATES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.PRESSURE_PLATE);
    }

    @Override
    protected String getSuffix() {
        return "_pressure_plate";
    }

    /**
     * 压力板的方块状态定义。参考格式：
     * <pre>
     * {
     *   "variants": {
     *     "powered=false": {
     *       "model": "%1$s"
     *     },
     *     "powered=true": {
     *       "model": "%1$s_down"
     *     }
     *   }
     * }
     * </pre>
     */
    @Environment(EnvType.CLIENT)
    @Override
    public @Nullable JState getBlockStates() {
        final Identifier identifier = getIdentifier();
        return JState.state(new JVariant()
                .put("powered", false, new JBlockModel(blockIdentifier(identifier)))
                .put("powered", true, new JBlockModel(blockIdentifier(identifier, "_down"))));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public JModel getBlockModel() {
        return simpleTextureModel("block/pressure_plate_up");
    }

    /**
     * 压力板被按下时的模型。
     */
    @Environment(EnvType.CLIENT)
    public JModel getDownModel() {
        return simpleTextureModel("block/pressure_plate_down");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void writeBlockModel(RuntimeResourcePack pack) {
        super.writeBlockModel(pack);
        pack.addModel(getDownModel(), blockIdentifier(getIdentifier(), "_down"));
    }

    /**
     * 合成配方。<br>
     * 普通压力板的参考格式：<pre>
     * {
     *   "type": "minecraft:crafting_shaped",
     *   "group": "%s",
     *   "pattern": ["##"],
     *   "key": {"#": {"item": "%s"} },
     *   "result": {"item": "%s"}
     * }
     * </pre>
     * 羊毛压力板的参考格式：<pre>
     * {
     *   "type": "minecraft:crafting_shapeless",
     *   "group": "%s",
     *   "ingredients": [{"item": "%s"}],
     *   "result": {"item": "%s"}
     * }
     * </pre>
     */
    @Override
    public @Nullable JRecipe getCraftingRecipe() {
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) {
            return JRecipe.shapeless(JIngredients.ingredients().add(JIngredient.ingredient().item(getBaseIdentifier().toString().replaceAll("_wool$", "_carpet"))), JResult.result(getIdentifier().toString()));
        } else {
            return JRecipe.shaped(JPattern.pattern("##"), JKeys.keys().key("#", JIngredient.ingredient().item(getBaseIdentifier().toString())), JResult.result(getIdentifier().toString()));
        }
    }

    @Override
    public String getRecipeGroup() {
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_pressure_plate";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
        return "";
    }
}
