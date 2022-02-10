package pers.solid.extshape.builder;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.GlazedTerracottaSlabBlock;

/**
 * 用于带釉陶瓦。
 */
public class GlazedTerracottaSlabBuilder extends SlabBuilder {
    public GlazedTerracottaSlabBuilder(Block baseBlock) {
        super(baseBlock);
        this.setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(FabricBlockSettings.copyOf(builder.baseBlock)));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public @Nullable JModel getTopBlockModel() {
        return simpleModel("extshape:block/glazed_terracotta_slab_top");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public @Nullable JModel getBlockModel() {
        return simpleModel("extshape:block/glazed_terracotta_slab");
    }

    /**
     * 带釉陶瓦台阶的方块状态定义。参考格式：
     * <pre style="font-size:small">
     * {
     *   "variants": {
     *     "type=bottom,facing=south": {
     *       "model": "%1$s"
     *     },
     *     "type=double,facing=south": {
     *       "model": "%2$s"
     *     },
     *     "type=top,facing=south": {
     *       "model": "%1$s_top"
     *     },
     *     "type=bottom,facing=west": {
     *       "model": "%1$s",
     *       "y": 90
     *     },
     *     "type=double,facing=west": {
     *       "model": "%2$s",
     *       "y": 90
     *     },
     *     "type=top,facing=west": {
     *       "model": "%1$s_top",
     *       "y": 90
     *     },
     *     "type=bottom,facing=north": {
     *       "model": "%1$s",
     *       "y": 180
     *     },
     *     "type=double,facing=north": {
     *       "model": "%2$s",
     *       "y": 180
     *     },
     *     "type=top,facing=north": {
     *       "model": "%1$s_top",
     *       "y": 180
     *     },
     *     "type=bottom,facing=east": {
     *       "model": "%1$s",
     *       "y": 270
     *     },
     *     "type=double,facing=east": {
     *       "model": "%2$s",
     *       "y": 270
     *     },
     *     "type=top,facing=east": {
     *       "model": "%1$s_top",
     *       "y": 270
     *     }
     *   }
     * }
     * </pre>
     */
    @Environment(EnvType.CLIENT)
    @Override
    public @Nullable JState getBlockStates() {
        final JVariant variant = new JVariant();
        final Identifier identifier = getIdentifier();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            final int rotation = (int) direction.asRotation();
            variant.put("type=bottom,facing", direction, new JBlockModel(blockIdentifier(identifier)).y(rotation));
            variant.put("type=top,facing", direction, new JBlockModel(blockIdentifier(identifier, "_top")).y(rotation));
            variant.put("type=double,facing", direction, new JBlockModel(blockIdentifier(getBaseIdentifier())).y(rotation));
        }
        return JState.state(variant);
    }
}
