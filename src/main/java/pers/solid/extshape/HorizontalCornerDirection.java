package pers.solid.extshape;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public enum HorizontalCornerDirection implements StringIdentifiable {
    SOUTH_WEST(0,2, "south_west",new Vec3i(-1,0,1)),
    NORTH_WEST(1,3,"north_west",new Vec3i(-1,0,-1)),
    NORTH_EAST(2,0,"north_east",new Vec3i(1,0,-1)),
    SOUTH_EAST(3,1,"south_east",new Vec3i(1,0,1));

    private final String name;
    private final int idOpposite;
    private final int id;
    private final Vec3i vector;
    private static final HorizontalCornerDirection[] ALL = values();
    private static final Map<String, HorizontalCornerDirection> NAME_MAP =
            Arrays.stream(ALL).collect(Collectors.toMap(HorizontalCornerDirection::getName, (direction) -> direction));
    private static final HorizontalCornerDirection[] VALUES = Arrays.stream(ALL).sorted(Comparator.comparingInt((direction) -> direction.id)).toArray(HorizontalCornerDirection[]::new);
    private static final Long2ObjectMap<HorizontalCornerDirection> VECTOR_TO_DIRECTION =
            Arrays.stream(ALL).collect(Collectors.toMap((direction) -> (new BlockPos(direction.getVector())).asLong(), (direction) -> direction, (direction1, direction2) -> {
        throw new IllegalArgumentException("Duplicate keys");
    }, Long2ObjectOpenHashMap::new));

    private HorizontalCornerDirection(int id, int idOpposite, String name, Vec3i vector) {
        this.id = id;
        this.idOpposite = idOpposite;
        this.name = name;
        this.vector = vector;
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


    public HorizontalCornerDirection rotateClockwise(Direction.Axis axis) {
        if (axis == Direction.Axis.Y) {
            return this.rotateYClockwise();
        } else {
            throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    public HorizontalCornerDirection rotateCounterclockwise(Direction.Axis axis) {
        if (axis == Direction.Axis.Y) {
            return this.rotateYCounterclockwise();
        }
        throw new IllegalStateException("Unable to get CW facing for axis " + axis);
    }

    public HorizontalCornerDirection rotateYClockwise() {
        return byId(Math.floorMod(this.id+1,VALUES.length));
    }

    public HorizontalCornerDirection rotateYCounterclockwise() {
        return byId(Math.floorMod(this.id-1, VALUES.length));
    }

    public int getOffsetX() {
        return this.vector.getX();
    }

    public int getOffsetY() {
        return this.vector.getY();
    }

    public int getOffsetZ() {
        return this.vector.getZ();
    }


    public Vec3f getUnitVector() {
        return new Vec3f((float)this.getOffsetX(), (float)this.getOffsetY(), (float)this.getOffsetZ());
    }

    public String getName() {
        return this.name;
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
    @Nullable
    public static HorizontalCornerDirection fromVector(BlockPos pos) {
        return VECTOR_TO_DIRECTION.get(pos.asLong());
    }

    @Nullable
    public static HorizontalCornerDirection fromVector(int x, int y, int z) {
        return VECTOR_TO_DIRECTION.get(BlockPos.asLong(x, y, z));
    }

    public static HorizontalCornerDirection fromRotation(double rotation) {
        return fromHorizontal(MathHelper.floor(rotation / 90.0D) & 3);
    }

    public float asRotation() {
        return (float)(((this.id&3) +0.5) * 90);
    }

    public static Direction random(Random random) {
        return (Direction) Util.getRandom((Object[])ALL, random);
    }

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
