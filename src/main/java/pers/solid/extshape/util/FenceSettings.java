package pers.solid.extshape.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static final FenceSettings DEFAULT = common(Items.STICK);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, WoodTypeRegistry.register(new Identifier(ExtShape.MOD_ID, "amethyst"), BlockSetTypeRegistry.registerWood(new Identifier(ExtShape.MOD_ID, "amethyst")), BlockSoundGroup.AMETHYST_BLOCK, BlockSoundGroup.AMETHYST_BLOCK, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT));

  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, WoodType.OAK);
  }
}
