package pers.solid.extshape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("extshape:config").executes(context -> {
      final MinecraftClient client = context.getSource().getClient();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    })));
  }
}
