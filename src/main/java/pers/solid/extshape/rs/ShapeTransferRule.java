package pers.solid.extshape.rs;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.mod.TransferRule;

public record ShapeTransferRule(Multimap<BlockShape, ItemGroup> multimap)
    implements TransferRule {
  @Override
  public @Nullable Iterable<ItemGroup> getTransferredGroups(Item item) {
    if (item instanceof BlockItem blockItem) {
      final Block block = blockItem.getBlock();
      final BlockShape shapeOf = BlockShape.getShapeOf(block);
      return shapeOf == null ? null : multimap.get(shapeOf);
    }
    return null;
  }
}
