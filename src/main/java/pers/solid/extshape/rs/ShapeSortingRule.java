package pers.solid.extshape.rs;

import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mappings.BlockMappings;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public record ShapeSortingRule(Predicate<Block> blockPredicate, Collection<BlockShape> shapes)
    /*implements SortingRule<Block>*/ {
  //  @Override
  public @Nullable Iterable<Block> getFollowers(Block leadingObj) {
    if (blockPredicate.test(leadingObj)) {
      return shapes.stream()
          .map(shape -> BlockMappings.getBlockOf(shape, leadingObj))
          .filter(Objects::nonNull)
          ::iterator;
    }
    return null;
  }
}
