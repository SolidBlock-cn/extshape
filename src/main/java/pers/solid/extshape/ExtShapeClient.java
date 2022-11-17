package pers.solid.extshape;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import pers.solid.extshape.config.ExtShapeOptionsScreen;
import pers.solid.extshape.mixin.ClientCommandSourceAccessor;

@OnlyIn(Dist.CLIENT)
@Mod("extshape")
public class ExtShapeClient {
  public static void registerCommand(RegisterClientCommandsEvent event) {
    final CommandDispatcher<ClientCommandSource> dispatcher = (CommandDispatcher<ClientCommandSource>) (CommandDispatcher) event.getDispatcher();
    dispatcher.register(LiteralArgumentBuilder.<ClientCommandSource>literal("extshape:config").executes(context -> {
      final MinecraftClient client = ((ClientCommandSourceAccessor) context.getSource()).getClient();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    }));
  }
}
