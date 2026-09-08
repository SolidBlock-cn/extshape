package pers.solid.extshape.config;

import com.google.common.base.Suppliers;
import com.google.common.collect.Streams;
import com.google.gson.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum ColoredBlockSorting implements StringRepresentable {
  SAME_COLOR_DIFFERENT_SHAPES_TOGETHER("same_color_different_shapes_together", Suppliers.memoize(() -> Stream.of(Blocks.WOOL.white(), Blocks.WOOL.lightGray(), Blocks.WOOL.gray(), Blocks.WOOL.black()).flatMap(base -> Stream.of(base, BlockBiMaps.getBlockOfOrThrow(BlockShape.STAIRS, base), BlockBiMaps.getBlockOfOrThrow(BlockShape.SLAB, base))).toList())),
  SAME_SHAPE_DIFFERENT_COLORS_TOGETHER("same_shape_different_colors_together", Suppliers.memoize(() -> Stream.of(List.of(Blocks.WOOL.white(), Blocks.WOOL.lightGray(), Blocks.WOOL.gray(), Blocks.WOOL.black())).flatMap(blocks -> Streams.concat(blocks.stream(), Stream.of(BlockShape.STAIRS, BlockShape.SLAB).flatMap(shape -> blocks.stream().map(base -> BlockBiMaps.getBlockOfOrThrow(shape, base))))).toList()));

  private final String name;
  private final Component displayName;
  public final Supplier<@Unmodifiable List<Block>> examples;
  public static final EnumCodec<ColoredBlockSorting> CODEC = StringRepresentable.fromEnum(ColoredBlockSorting::values);

  ColoredBlockSorting(String name, Supplier<@Unmodifiable List<Block>> examples) {
    this.name = name;
    this.displayName = Component.translatable("options.extshape.colored_block_sorting." + name);
    this.examples = examples;
  }

  @Override
  public String getSerializedName() {
    return this.name;
  }

  public Component displayName() {
    return this.displayName;
  }

  public @Unmodifiable List<Block> examples() {
    return examples.get();
  }

  public enum Serializer implements JsonSerializer<ColoredBlockSorting>, JsonDeserializer<ColoredBlockSorting> {
    INSTANCE;

    @Override
    public @Nullable ColoredBlockSorting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return CODEC.byName(json.getAsString());
    }

    @Override
    public JsonElement serialize(ColoredBlockSorting src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getSerializedName());
    }
  }
}
