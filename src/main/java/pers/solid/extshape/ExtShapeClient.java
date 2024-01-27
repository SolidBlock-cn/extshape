package pers.solid.extshape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

/**
 * 扩展方块形状模组的客户端部分。
 */
@Environment(EnvType.CLIENT)
public class ExtShapeClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    // 用于打开配置界面的 extshape:config 命令。
    // 当你没有安装 Mod Menu 时，仍可通过此命令来打开配置界面。
    ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("extshape:config").executes(context -> {
      final MinecraftClient client = context.getSource().getClient();
      client.send(() -> client.setScreen(new ExtShapeOptionsScreen(null)));
      return 1;
    })));
  }
}
