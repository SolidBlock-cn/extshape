package pers.solid.extshape.config;

import com.mojang.datafixers.util.Either;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.Tooltip;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import pers.solid.extshape.ExtShapeRRP;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class ExtShapeRRPScreen extends Screen {
  public final Screen parent;
  protected ButtonWidget regenClientResourcesButton;
  protected transient Thread regenClientResourcesThread;
  protected ButtonWidget regenServerDataButton;
  protected transient Thread regenServerDataThread;
  protected ButtonWidget dumpClientResourcesButton;
  protected transient Thread dumpClientResourcesThread;
  protected ButtonWidget dumpServerDataButton;
  protected transient Thread dumpServerDataThread;
  protected TextFieldWidget dumpPathField;
  protected Either<String, Throwable> fullPath;
  protected Path dumpPath = RuntimeResourcePack.DEFAULT_OUTPUT;

  protected ButtonWidget finishButton;

  protected ExtShapeRRPScreen(Screen parent) {
    super(Text.translatable("options.extshape.rrp.title"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    super.init();
    // 里面的内容不需要被选中，所以只是drawable。
    addDrawable(new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25));
    addDrawableChild(regenClientResourcesButton = new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.regenClientResources"), button -> {
      if (regenClientResourcesThread != null && regenClientResourcesThread.getState() != Thread.State.TERMINATED) return;
      (
          regenClientResourcesThread = new Thread(() -> {
            regenClientResourcesButton.setMessage(Text.translatable("options.extshape.rrp.regenClientResources.process"));
            regenClientResourcesButton.active = false;
            try {
              ExtShapeRRP.CLIENT_PACK.clearResources(ResourceType.CLIENT_RESOURCES);
              ExtShapeRRP.generateClientResources(ExtShapeRRP.CLIENT_PACK);
            } catch (Throwable throwable) {
              ExtShapeConfig.LOGGER.error("Error when regenerating client resources.", throwable);
            } finally {
              regenClientResourcesButton.setMessage(Text.translatable("options.extshape.rrp.regenClientResources"));
              regenClientResourcesButton.active = true;
            }
          })
      ).start();
      regenClientResourcesThread.setName("RegenClientResources");
    })
        .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.regenClientResources.description")))
        .position(width / 2 - 205, 36).size(200, 20).build());
    addDrawableChild(regenServerDataButton = new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.regenServerData"), button -> {
      if (regenServerDataThread != null && regenServerDataThread.getState() != Thread.State.TERMINATED) return;
      (regenServerDataThread = new Thread(() -> {
        regenServerDataButton.setMessage(Text.translatable("options.extshape.rrp.regenServerData.process"));
        regenServerDataButton.active = false;
        try {
          ExtShapeRRP.STANDARD_PACK.clearResources(ResourceType.SERVER_DATA);
          ExtShapeRRP.generateServerData(ExtShapeRRP.STANDARD_PACK);
        } catch (Throwable throwable) {
          ExtShapeConfig.LOGGER.error("Error when regenerating server data.", throwable);
        } finally {
          regenServerDataButton.setMessage(Text.translatable("options.extshape.rrp.regenServerData"));
          regenServerDataButton.active = true;
        }
      })).start();
      regenServerDataThread.setName("RegenServerData");
    })
        .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.regenServerData.description")))
        .position(width / 2 + 5, 36).size(200, 20).build());
    addDrawableChild(dumpClientResourcesButton = new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.exportClientResources"), button -> {
      if (dumpClientResourcesThread != null && dumpClientResourcesThread.getState() != Thread.State.TERMINATED) return;
      (dumpClientResourcesThread = new Thread(() -> {
        dumpClientResourcesButton.setMessage(Text.translatable("options.extshape.rrp.exportClientResources.process"));
        dumpClientResourcesButton.active = false;
        try {
          ExtShapeRRP.CLIENT_PACK.dump(dumpPath);
          ExtShapeConfig.LOGGER.info("FINISH!");
        } catch (Throwable throwable) {
          ExtShapeConfig.LOGGER.error("Error when dumping client resource.", throwable);
        } finally {
          dumpClientResourcesButton.setMessage(Text.translatable("options.extshape.rrp.exportClientResources"));
          dumpClientResourcesButton.active = true;
        }
      })).start();
      dumpClientResourcesThread.setName("DumpClientResources");
    })
        .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.exportClientResources.description")))
        .position(width / 2 - 205, 61).size(200, 20).build());
    addDrawableChild(dumpServerDataButton = new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.exportServerData"), button -> {
      if (dumpServerDataThread != null && dumpServerDataThread.getState() != Thread.State.TERMINATED) return;
      (dumpServerDataThread = new Thread(() -> {
        dumpServerDataButton.setMessage(Text.translatable("options.extshape.rrp.exportServerData.process"));
        dumpServerDataButton.active = false;
        try {
          ExtShapeRRP.STANDARD_PACK.dump(dumpPath);
          ExtShapeConfig.LOGGER.info("FINISH!");
        } catch (Throwable throwable) {
          ExtShapeConfig.LOGGER.error("Error when dumping server data.", throwable);
        } finally {
          dumpServerDataButton.setMessage(Text.translatable("options.extshape.rrp.exportServerData"));
          dumpServerDataButton.active = true;
        }
      })).start();
      dumpServerDataThread.setName("DumpServerData");
    })
        .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.exportServerData.description")))
        .position(width / 2 + 5, 61).size(200, 20).build());
    if (dumpPathField == null) {
      addDrawableChild(dumpPathField = new TextFieldWidget(textRenderer, width / 2 - 140, 115, 250, 20, Text.translatable("options.extshape.rrp.exportFilePath")));
      dumpPathField.setMaxLength(Integer.MAX_VALUE);
      dumpPathField.setChangedListener(s -> {
        try {
          dumpPath = Path.of(s);
          dumpPathField.setEditableColor(0xe0e0e0);
          dumpClientResourcesButton.active = true;
          dumpServerDataButton.active = true;
          fullPath = Either.left(dumpPath.toAbsolutePath().toString());
        } catch (InvalidPathException e) {
          dumpPathField.setEditableColor(0xFF5555);
          dumpClientResourcesButton.active = false;
          dumpServerDataButton.active = false;
          fullPath = Either.right(e);
        }
      });
      dumpPathField.setText(dumpPath.toString());
    } else addDrawableChild(dumpPathField);
    dumpPathField.setX(width / 2 - 125);

    addDrawableChild(finishButton = new ButtonWidget.Builder(ScreenTexts.BACK, button -> {
      if (client != null) {
        client.setScreen(parent);
      }
    }).position(width / 2 - 100, height - 27).size(200, 20).build());
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredText(matrices, textRenderer, title, width / 2, 16, 0xffffff);
    drawCenteredText(matrices, textRenderer, Text.translatable("options.extshape.rrp.textFieldHeader"), width / 2, 100, 0xffffff);
    if (fullPath != null) {
      MultilineText.create(textRenderer, fullPath.map(s -> Text.translatable("options.extshape.rrp.fullPath", s).formatted(Formatting.GRAY), throwable -> Text.translatable("options.extshape.rrp.invalidPath", throwable.getMessage()).formatted(Formatting.RED)), width - 40).drawCenterWithShadow(matrices, width / 2, 140);
    }
  }

  @Override
  public void tick() {
    super.tick();
    boolean hasTask = regenClientResourcesThread != null && regenClientResourcesThread.isAlive()
        || regenServerDataThread != null && regenServerDataThread.isAlive()
        || dumpClientResourcesThread != null && dumpClientResourcesThread.isAlive()
        || dumpServerDataThread != null && dumpServerDataThread.isAlive();
    finishButton.active = !hasTask;
  }

  @Override
  public void close() {
    assert client != null;  // super 里面就忽略了 client==null 的情况
    client.setScreen(parent);
  }

  @Override
  public void removed() {
    super.removed();
    if (regenClientResourcesThread != null) {
      if (regenClientResourcesThread.getState() != Thread.State.TERMINATED) {
        ExtShapeConfig.LOGGER.warn("Screen closed. Interrupting {}!", regenClientResourcesThread);
      }
      regenClientResourcesThread.interrupt();
      regenClientResourcesThread = null;
    }
    if (regenServerDataThread != null) {
      if (regenServerDataThread.getState() != Thread.State.TERMINATED) {
        ExtShapeConfig.LOGGER.warn("Screen closed. Interrupting {}!", regenServerDataThread);
      }
      regenServerDataThread.interrupt();
      regenServerDataThread = null;
    }
    if (dumpClientResourcesThread != null) {
      if (dumpClientResourcesThread.getState() != Thread.State.TERMINATED) {
        ExtShapeConfig.LOGGER.warn("Screen closed. Interrupting {}!", dumpClientResourcesThread);
      }
      dumpClientResourcesThread.interrupt();
      dumpClientResourcesThread = null;
    }
    if (dumpServerDataThread != null) {
      if (dumpServerDataThread.getState() != Thread.State.TERMINATED) {
        ExtShapeConfig.LOGGER.warn("Screen closed. Interrupting {}!", dumpServerDataThread);
      }
      dumpServerDataThread.interrupt();
      dumpServerDataThread = null;
    }
  }
}
