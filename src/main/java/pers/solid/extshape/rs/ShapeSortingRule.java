package pers.solid.extshape.rs;

import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.mod.SortingRule;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public record ShapeSortingRule(Predicate<Block> blockPredicate, Collection<Shape> shapes)
    implements SortingRule<Block> {
  @Override
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
