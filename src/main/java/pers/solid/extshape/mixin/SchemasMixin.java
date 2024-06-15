package pers.solid.extshape.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.ItemNameFix;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * 此 mixin 用于将之前版本的一些 id 同步更新为新版本的。
 */
@Mixin(Schemas.class)
public abstract class SchemasMixin {
  @Shadow
  @Final
  private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

  @Contract
  @Shadow
  private static UnaryOperator<String> replacing(Map<String, String> replacements) {
    throw new AssertionError();
  }

  @Inject(method = "build", at = @At("TAIL"))
  private static void postBuild(DataFixerBuilder builder, CallbackInfo ci) {
    // in 24w18a (dataVersion = 3940), some blocks are not experimental, and we should convert them into vanilla ones.
    final Schema schema = builder.addSchema(3939, EMPTY_IDENTIFIER_NORMALIZE);
    final UnaryOperator<String> unaryOperator = replacing(Map.of(
        "extshape:tuff_stairs", "minecraft:tuff_stairs",
        "extshape:tuff_slab", "minecraft:tuff_slab",
        "extshape:tuff_wall", "minecraft:tuff_wall"
    ));
    builder.addFixer(BlockNameFix.create(schema, "Rename tuff stairs and slab blocks from 'extshape' namespace to vanilla ones", unaryOperator));
    builder.addFixer(ItemNameFix.create(schema, "Rename tuff stairs and slab items from 'extshape' namespace to vanilla ones", unaryOperator));
  }
}
