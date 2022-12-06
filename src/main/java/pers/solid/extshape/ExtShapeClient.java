package pers.solid.extshape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("extshape:config").executes(context -> {
      context.getSource().getClient().setScreen(new ExtShapeOptionsScreen(null));
      return 1;
    })));
  }
}
