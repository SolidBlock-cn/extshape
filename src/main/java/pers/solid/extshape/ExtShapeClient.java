package pers.solid.extshape;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@OnlyIn(Dist.CLIENT)
@Mod("extshape")
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ExtShapeClient {
  @SubscribeEvent
  public static void registerCommand(RegisterClientCommandsEvent event) {
    event.getDispatcher().register(CommandManager.literal("extshape:config").executes(context -> {
      final MinecraftClient client = MinecraftClient.getInstance();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    }));
  }
}
