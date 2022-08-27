package pers.solid.extshape;

import com.google.common.base.Suppliers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import pers.solid.extshape.config.ExtShapeOptionsScreen;

@OnlyIn(Dist.CLIENT)
@Mod(ExtShape.MOD_ID)
public class ExtShapeClient {
  public ExtShapeClient() {
    ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, Suppliers.ofInstance(new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> new ExtShapeOptionsScreen(screen))));
  }
}
