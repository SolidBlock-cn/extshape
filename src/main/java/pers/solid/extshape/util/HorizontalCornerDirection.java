package pers.solid.extshape.util;

import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 水平角落方向。类似于 {@link Direction}，但是指向水平方向的四个角落。
 *
 * @see Direction
 */
public enum HorizontalCornerDirection implements StringIdentifiable {
  SOUTH_WEST(0, 2, "south_west", new Vec3i(-1, 0, 1)),
  NORTH_WEST(1, 3, "north_west", new Vec3i(-1, 0, -1)),
  NORTH_EAST(2, 0, "north_east", new Vec3i(1, 0, -1)),
  SOUTH_EAST(3, 1, "south_east", new Vec3i(1, 0, 1));

  private static final HorizontalCornerDirection[] ALL = values();
  private static final Map<String, HorizontalCornerDirection> NAME_MAP = Arrays.stream(ALL).collect(Collectors.toMap(HorizontalCornerDirection::getName, (direction) -> direction));
  private static final HorizontalCornerDirection[] VALUES = Arrays.stream(ALL).sorted(Comparator.comparingInt((direction) -> direction.id)).toArray(HorizontalCornerDirection[]::new);
  private final String name;
  private final int idOpposite;
  private final int id;
  private final Vec3i vector;

  /**
   * @param id         方向的内部数字id。
   * @param idOpposite 方向对应相反方向的内部数字id。
   * @param name       方向名称。
   * @param vector     方向对应的向量。非单位向量。
   */
  HorizontalCornerDirection(int id, int idOpposite, String name, Vec3i vector) {
    this.id = id;
    this.idOpposite = idOpposite;
    this.name = name;
    this.vector = vector;
  }

  @Nullable
  public static HorizontalCornerDirection byName(@Nullable String name) {
    return name == null ? null : NAME_MAP.get(name.toLowerCase(Locale.ROOT));
  }

  public static HorizontalCornerDirection byId(int id) {
    return VALUES[MathHelper.abs(id % VALUES.length)];
  }

  public static HorizontalCornerDirection fromHorizontal(int value) {
    return VALUES[MathHelper.abs(value % VALUES.length)];
  }

  /**
   * 从旋转角度算出距离该旋转角度最近的方向。用于方块放置。
   *
   * @param rotation 旋转角度。
   * @return 离该旋转角度最近的方向。
   */
  public static HorizontalCornerDirection fromRotation(double rotation) {
    return fromHorizontal(MathHelper.floor(rotation / 90.0D) & 3);
  }

  public static HorizontalCornerDirection random(Random random) {
    return Util.getRandom(ALL, random);
  }

  @Override
  public String asString() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public HorizontalCornerDirection getOpposite() {
    return byId(this.idOpposite);
  }

  public HorizontalCornerDirection rotateYClockwise() {
    return byId(Math.floorMod(this.id + 1, VALUES.length));
  }

  public HorizontalCornerDirection rotateYCounterclockwise() {
    return byId(Math.floorMod(this.id - 1, VALUES.length));
  }

  public String getName() {
    return this.name;
  }

  public float asRotation() {
    return (float) (((this.id & 3) + 0.5) * 90);
  }

  @Override
  public String toString() {
    return this.name;
  }

  public Vec3i getVector() {
    return this.vector;
  }

  public HorizontalCornerDirection rotate(BlockRotation rotation) {
    return switch (rotation) {
      case CLOCKWISE_90 -> this.rotateYClockwise();
      case CLOCKWISE_180 -> this.getOpposite();
      case COUNTERCLOCKWISE_90 -> this.rotateYCounterclockwise();
      default -> this;
    };
  }

  public HorizontalCornerDirection mirror(BlockMirror mirror) {
    return switch (mirror) {
      case FRONT_BACK -> switch (this) {
        case NORTH_EAST -> NORTH_WEST;
        case NORTH_WEST -> NORTH_EAST;
        case SOUTH_EAST -> SOUTH_WEST;
        case SOUTH_WEST -> SOUTH_EAST;
      };
      case LEFT_RIGHT -> switch (this) {
        case NORTH_EAST -> SOUTH_EAST;
        case NORTH_WEST -> SOUTH_WEST;
        case SOUTH_EAST -> NORTH_EAST;
        case SOUTH_WEST -> NORTH_WEST;
      };
      default -> this;
    };
  }
}
