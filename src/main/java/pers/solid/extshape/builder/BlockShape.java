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
import java.util.function.Function;

public class BlockShape implements StringIdentifiable {

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

  public static final BlockShape STAIRS = new BlockShape(StairsBlock.class, BlockFamily.Variant.STAIRS, "stairs", 1f, true, StairsBuilder::new);
  public static final BlockShape SLAB = new BlockShape(SlabBlock.class, BlockFamily.Variant.SLAB, "slab", 0.5f, true, SlabBuilder::new);
  public static final BlockShape VERTICAL_SLAB = new BlockShape(VerticalSlabBlock.class, null, "vertical_slab", 0.5f, true, VerticalSlabBuilder::new);
  public static final BlockShape VERTICAL_STAIRS = new BlockShape(VerticalStairsBlock.class, null, "vertical_stairs", 1f, true, VerticalStairsBuilder::new);
  public static final BlockShape QUARTER_PIECE = new BlockShape(QuarterPieceBlock.class, null, "quarter_piece", 0.25f, true, QuarterPieceBuilder::new);
  public static final BlockShape VERTICAL_QUARTER_PIECE = new BlockShape(VerticalQuarterPieceBlock.class, null, "vertical_quarter_piece", 0.25f, true, VerticalQuarterPieceBuilder::new);
  public static final BlockShape FENCE = new BlockShape(FenceBlock.class, BlockFamily.Variant.FENCE, "fence", 1f, false, baseBlock -> new FenceBuilder(baseBlock, null));
  public static final BlockShape FENCE_GATE = new BlockShape(FenceGateBlock.class, BlockFamily.Variant.FENCE_GATE, "fence_gate", 1f, false, baseBlock -> new FenceGateBuilder(baseBlock, null));
  public static final BlockShape WALL = new BlockShape(WallBlock.class, BlockFamily.Variant.WALL, "wall", 1f, false, WallBuilder::new);
  public static final BlockShape BUTTON = new BlockShape(AbstractButtonBlock.class, BlockFamily.Variant.BUTTON, "button", 1 / 3f, false, baseBlock -> new ButtonBuilder(ExtShapeButtonBlock.ButtonType.STONE, baseBlock));

  public static final BlockShape PRESSURE_PLATE = new BlockShape(PressurePlateBlock.class, BlockFamily.Variant.PRESSURE_PLATE, "pressure_plate", 1 / 3f, false, baseBlock -> new PressurePlateBuilder(PressurePlateBlock.ActivationRule.MOBS, baseBlock));

  // 非静态部分

  /**
   * 该形状对应的方块类。
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
  public final float logicalCompleteness;
  public final boolean isConstruction;
  private final Function<Block, AbstractBlockBuilder<? extends Block>> blockBuilderFunction;
  public final int id;

  public BlockShape(Class<? extends Block> withClass, @Nullable BlockFamily.Variant vanillaVariant, @NotNull String name, float logicalCompleteness, boolean isConstruction, Function<Block, AbstractBlockBuilder<? extends Block>> blockBuilderFunction) {
    this.withClass = withClass;
    this.vanillaVariant = vanillaVariant;
    this.name = name;
    this.logicalCompleteness = logicalCompleteness;
    this.isConstruction = isConstruction;
    this.blockBuilderFunction = blockBuilderFunction;
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

  public AbstractBlockBuilder<? extends Block> createBuilder(Block baseBlock) {
    if (blockBuilderFunction == null) {
      throw new IllegalArgumentException("The shape has no block builder function.");
    }
    return blockBuilderFunction.apply(baseBlock);
  }

  public static BlockShape byName(String name) {
    return NAME_TO_SHAPE.get(name);
  }
}
