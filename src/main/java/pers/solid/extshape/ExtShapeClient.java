package pers.solid.extshape;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@OnlyIn(Dist.CLIENT)
public final class ExtShapeClient {

  private ExtShapeClient() {
    throw new UnsupportedOperationException("You are not allowed to instantiate this class");
  }

  public static void registerCommand(RegisterClientCommandsEvent event) {
    event.getDispatcher().register(CommandManager.literal("extshape:config").executes(context -> {
      final MinecraftClient client = MinecraftClient.getInstance();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    }));
  }
}
