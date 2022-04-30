package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import org.jetbrains.annotations.Contract;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.block.VerticalStairsBlock;

public class ExtShapeBlockItemBuilder extends AbstractItemBuilder<ExtShapeBlockItem> {
  public final Block block;

  protected ExtShapeBlockItemBuilder(Block block, FabricItemSettings settings) {
    super(settings);
    this.block = block;
  }

  @Contract(value = "_ -> this", mutates = "this")
  @CanIgnoreReturnValue
  public ExtShapeBlockItemBuilder group(ItemGroup group) {
    settings.group(group);
    return this;
  }

  @Contract(value = "-> this", mutates = "this")
  @CanIgnoreReturnValue
  public ExtShapeBlockItemBuilder group() {
    Block block = this.block;
    if (block instanceof StairsBlock || block instanceof SlabBlock || block instanceof VerticalSlabBlock || block instanceof QuarterPieceBlock || block instanceof VerticalStairsBlock || block instanceof VerticalQuarterPieceBlock)
      settings.group(ItemGroup.BUILDING_BLOCKS);
    else if (block instanceof FenceBlock || block instanceof WallBlock) settings.group(ItemGroup.DECORATIONS);
    else if (block instanceof AbstractButtonBlock || block instanceof PressurePlateBlock || block instanceof FenceGateBlock)
      settings.group(ItemGroup.REDSTONE);
    return this;
  }

  @Override
  public void createInstance() {
    this.item = new ExtShapeBlockItem(block, settings);
  }
}
