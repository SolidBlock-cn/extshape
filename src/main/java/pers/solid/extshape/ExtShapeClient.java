package pers.solid.extshape;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {
    /**
     * 仅适用于客户端的资源包。
     *
     * @see ExtShape#EXTSHAPE_PACK
     */
    @Environment(EnvType.CLIENT)
    public static final RuntimeResourcePack EXTSHAPE_PACK_CLIENT = RuntimeResourcePack.create(new Identifier("extshape", "client"));

    @Override
    public void onInitializeClient() {
        RRPCallback.BEFORE_VANILLA.register(resources -> resources.add(EXTSHAPE_PACK_CLIENT));
    }
}
