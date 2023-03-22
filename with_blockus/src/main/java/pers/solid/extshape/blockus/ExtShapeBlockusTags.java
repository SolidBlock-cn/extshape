package pers.solid.extshape.blockus;

import com.brand.blockus.tags.BlockusBlockTags;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.tag.BlockItemTagPreparation;
import pers.solid.extshape.tag.BlockTagPreparation;
import pers.solid.extshape.tag.ItemTagPreparation;
import pers.solid.extshape.tag.TagPreparation;

import java.util.Collection;
import java.util.LinkedHashSet;

public final class ExtShapeBlockusTags {

  public static final Collection<TagPreparation<?>> TAGS_TO_WRITE = new LinkedHashSet<>();

  public static final BlockTagPreparation STAIRS = ofBlockAndItem(BlockTags.STAIRS, ItemTags.STAIRS);
  public static final BlockTagPreparation SLAB = ofBlockAndItem(BlockTags.SLABS, ItemTags.SLABS);
  public static final BlockTagPreparation FENCES = ofBlockAndItem(BlockTags.FENCES, ItemTags.FENCES);
  public static final BlockTagPreparation FENCE_GATES = ofBlockAndItem(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
  public static final BlockTagPreparation WALLS = ofBlockAndItem(BlockTags.WALLS, ItemTags.WALLS);
  public static final BlockTagPreparation BUTTONS = ofBlockAndItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
  public static final BlockTagPreparation PRESSURE_PLATES = ofBlockOnly(BlockTags.PRESSURE_PLATES);
  public static final BlockTagPreparation VERTICAL_SLABS = ofBlockAndItem(new Identifier(ExtShape.MOD_ID, "vertical_slabs"));
  public static final BlockTagPreparation QUARTER_PIECES = ofBlockAndItem(new Identifier(ExtShape.MOD_ID, "quarter_pieces"));
  public static final BlockTagPreparation VERTICAL_STAIRS = ofBlockAndItem(new Identifier(ExtShape.MOD_ID, "vertical_stairs"));
  public static final BlockTagPreparation VERTICAL_QUARTER_PIECES = ofBlockAndItem(new Identifier(ExtShape.MOD_ID, "vertical_quarter_pieces"));


  public static final BlockTagPreparation AXE_MINEABLE = ofBlockOnly(BlockTags.AXE_MINEABLE);
  public static final BlockTagPreparation HOE_MINEABLE = ofBlockOnly(BlockTags.HOE_MINEABLE);
  public static final BlockTagPreparation PICKAXE_MINEABLE = ofBlockOnly(BlockTags.PICKAXE_MINEABLE);
  public static final BlockTagPreparation SHOVEL_MINEABLE = ofBlockOnly(BlockTags.SHOVEL_MINEABLE);
  public static final BlockTagPreparation SHEARS_MINEABLE = ofBlockOnly(FabricMineableTags.SHEARS_MINEABLE);

  public static final BlockTagPreparation AMETHYST_BLOCKS = ofBlockOnly(BlockusBlockTags.AMETHYST_BLOCKS);

  /**
   * 写入所有的方块标签文件。
   */
  public static void writeAllTags() {
    for (var tag : TAGS_TO_WRITE) {
      tag.writeIntoPack();
    }
  }


  private static BlockTagPreparation ofBlockOnly(@NotNull String path) {
    return ofBlockOnly(new Identifier(ExtShapeBlockus.NAMESPACE, path));
  }

  static BlockTagPreparation ofBlockOnly(@NotNull Identifier identifier) {
    final BlockTagPreparation empty = (BlockTagPreparation) TagPreparation.INTERNER.intern(new BlockTagPreparation(ExtShapeBlockusRRP.EXTSHAPE_STANDARD_PACK, identifier));
    TAGS_TO_WRITE.add(empty);
    return empty;
  }

  private static BlockTagPreparation ofBlockAndItem(@NotNull Identifier identifier) {
    final BlockTagPreparation blockTag = ofBlockOnly(identifier);
    TAGS_TO_WRITE.add(new BlockItemTagPreparation(blockTag));
    return blockTag;
  }

  static BlockTagPreparation ofBlockOnly(@NotNull TagKey<Block> tag) {
    return ofBlockOnly(tag.id());
  }

  private static BlockTagPreparation ofBlockAndItem(@NotNull TagKey<Block> blockTag, @NotNull TagKey<Item> itemTag) {
    Preconditions.checkArgument(blockTag.id().equals(itemTag.id()));
    final BlockTagPreparation instance = ofBlockOnly(blockTag);
    TAGS_TO_WRITE.add(new BlockItemTagPreparation(instance));
    return instance;
  }

  private static ItemTagPreparation ofItemOnly(@NotNull TagKey<Item> tag) {
    final ItemTagPreparation empty = (ItemTagPreparation) TagPreparation.INTERNER.intern(new ItemTagPreparation(ExtShapeBlockusRRP.EXTSHAPE_STANDARD_PACK, tag.id()));
    TAGS_TO_WRITE.add(empty);
    return empty;
  }
}
