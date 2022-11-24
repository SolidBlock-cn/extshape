package pers.solid.extshape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("extshape:config").executes(context -> {
      final MinecraftClient client = context.getSource().getClient();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    }));
  }
}
