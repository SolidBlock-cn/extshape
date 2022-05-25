package pers.solid.extshape.mixin;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import pers.solid.extshape.ExtShape;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends MiningToolItem {
  protected PickaxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks, Settings settings) {
    super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
  }

  /**
   * @see pers.solid.extshape.tag.ExtShapeBlockTags#PICKAXE_UNMINEABLE
   */
  private static final Tag<Block> PICKAXE_UNMINEABLE = TagRegistry.block(new Identifier(ExtShape.MOD_ID, "pickaxe_unmineable"));

  /**
   * 如果方块属于 {@link #PICKAXE_UNMINEABLE} 这个标签中，那么即使属于 {@link net.minecraft.tag.BlockTags#PICKAXE_MINEABLE}，也不能认为可以开采。这是考虑到 Minecraft 将整个 {@link net.minecraft.tag.BlockTags#WALLS}（{@code minecraft:walls}）都加入了镐的可开采行列，但本模组添加的方块并非全都可以用镐开采，因此特地做出修改。
   */
  @Override
  public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
    if (state.isIn(PICKAXE_UNMINEABLE)) return 1;
    return super.getMiningSpeedMultiplier(stack, state);
  }
}
