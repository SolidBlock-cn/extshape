package pers.solid.extshape.builder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.extshape.block.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 方块形状。以前是枚举，现在不是了。每个形状对象创建时，都会自动加入 {@link #NAME_TO_SHAPE} 和 {@link #SHAPES} 中，相当于一个“注册表”。部分形状有对应的原版的方块变种类型（{@link #vanillaVariant}）。
 */
public class BlockShape implements StringIdentifiable, Comparable<BlockShape> {

  // 方块形状注册表部分。

  /**
   * 由形状名称到形状对象的映射，由 {@link #byName(String)} 使用。
   */
  private static final BiMap<String, BlockShape> NAME_TO_SHAPE = HashBiMap.create();
  /**
   * 由所有形状对象组成的列表，其列表索引应该与 {@link #id} 一致。
   */
  private static final List<BlockShape> SHAPES = new ArrayList<>();
  /**
   * 由 {@link #values()} 使用，不可修改。
   */
  private static final @UnmodifiableView List<BlockShape> VALUES = Collections.unmodifiableList(SHAPES);

  // 扩展方块形状模组内置的方块形状部分。

  public static final BlockShape STAIRS = new BlockShape(StairsBlock.class, BlockFamily.Variant.STAIRS, "stairs", 1f, true);
  public static final BlockShape SLAB = new BlockShape(SlabBlock.class, BlockFamily.Variant.SLAB, "slab", 0.5f, true);
  public static final BlockShape VERTICAL_SLAB = new BlockShape(VerticalSlabBlock.class, null, "vertical_slab", 0.5f, true);
  public static final BlockShape VERTICAL_STAIRS = new BlockShape(VerticalStairsBlock.class, null, "vertical_stairs", 1f, true);
  public static final BlockShape QUARTER_PIECE = new BlockShape(QuarterPieceBlock.class, null, "quarter_piece", 0.25f, true);
  public static final BlockShape VERTICAL_QUARTER_PIECE = new BlockShape(VerticalQuarterPieceBlock.class, null, "vertical_quarter_piece", 0.25f, true);
  public static final BlockShape FENCE = new BlockShape(FenceBlock.class, BlockFamily.Variant.FENCE, "fence", 1f, false);
  public static final BlockShape FENCE_GATE = new BlockShape(FenceGateBlock.class, BlockFamily.Variant.FENCE_GATE, "fence_gate", 1f, false);
  public static final BlockShape WALL = new BlockShape(WallBlock.class, BlockFamily.Variant.WALL, "wall", 1f, false);
  public static final BlockShape BUTTON = new BlockShape(AbstractButtonBlock.class, BlockFamily.Variant.BUTTON, "button", 1 / 3f, false);

  public static final BlockShape PRESSURE_PLATE = new BlockShape(PressurePlateBlock.class, BlockFamily.Variant.PRESSURE_PLATE, "pressure_plate", 1 / 3f, false);

  // 非静态部分

  /**
   * 该形状对应的方块类，用于判断某个方块对象属于哪个形状。
   *
   * @see #getShapeOf(Block)
   */
  public final Class<? extends Block> withClass;
  /**
   * 该形状对应的原版的 {@link BlockFamily.Variant}。
   */
  public final @Nullable BlockFamily.Variant vanillaVariant;
  /**
   * 该形状的名称，由 {@link #asString()} 使用。
   */
  private final @NotNull String name;
  /**
   * 该方块在占整个方块的大致比例，通常会与游戏内的行为相仿。楼梯方块显然是 3/4，但是其各个方面的数据都与原版方块相同，因此其逻辑上的完整度为 1。
   */
  public final float logicalCompleteness;
  /**
   * 该方块是否为建筑方块。
   */
  public final boolean isConstruction;
  public final int id;

  public BlockShape(Class<? extends Block> withClass, @Nullable BlockFamily.Variant vanillaVariant, @NotNull String name, float logicalCompleteness, boolean isConstruction) {
    this.withClass = withClass;
    this.vanillaVariant = vanillaVariant;
    this.name = name;
    this.logicalCompleteness = logicalCompleteness;
    this.isConstruction = isConstruction;
    this.id = SHAPES.size();
    SHAPES.add(this);
    NAME_TO_SHAPE.put(name, this);
  }

  @Nullable
  public static BlockShape getShapeOf(Block block) {
    if (block instanceof ExtShapeBlockInterface e) {
      return e.getBlockShape();
    }
    for (BlockShape shape : BlockShape.values()) {
      if (shape.withClass.isInstance(block)) return shape;
    }
    return null;
  }

  public static List<BlockShape> values() {
    return VALUES;
  }

  @Override
  @NotNull
  public String asString() {
    return name;
  }

  public static BlockShape byName(String name) {
    return NAME_TO_SHAPE.get(name);
  }

  @Override
  public int compareTo(@NotNull BlockShape o) {
    return id - o.id;
  }
}
