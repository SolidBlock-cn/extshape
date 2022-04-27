package pers.solid.extshape;

import net.devtech.arrp.api.RRPCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    RRPCallback.BEFORE_VANILLA.register(resources -> resources.add(ExtShapeRRP.CLIENT_PACK));
  }
}
