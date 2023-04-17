package pers.solid.extshape.util;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static final FenceSettings DEFAULT = common(Items.STICK);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, WoodType.register(new WoodType("extshape:amethyst", BlockSetType.register(new BlockSetType("extshape:amethyst")), BlockSoundGroup.AMETHYST_BLOCK, BlockSoundGroup.AMETHYST_BLOCK, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT)));

  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, WoodType.OAK);
  }
}
