package pers.solid.extshape.config;

import com.google.common.base.Suppliers;
import com.google.common.collect.Streams;
import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum ColorfulBlockSorting implements StringIdentifiable {
  SAME_COLOR_DIFFERENT_SHAPES_TOGETHER("same_color_different_shapes_together", Suppliers.memoize(() -> Stream.of(Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL).flatMap(base -> Stream.of(base, BlockBiMaps.getBlockOfOrThrow(BlockShape.STAIRS, base), BlockBiMaps.getBlockOfOrThrow(BlockShape.SLAB, base))).toList())),
  SAME_SHAPE_DIFFERENT_COLORS_TOGETHER("same_shape_different_colors_together", Suppliers.memoize(() -> Stream.of(List.of(Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL)).flatMap(blocks -> Streams.concat(blocks.stream(), Stream.of(BlockShape.STAIRS, BlockShape.SLAB).flatMap(shape -> blocks.stream().map(base -> BlockBiMaps.getBlockOfOrThrow(shape, base))))).toList()));

  private final String name;
  private final Text displayName;
  public final Supplier<@Unmodifiable List<Block>> examples;
  @SuppressWarnings("deprecation")
  public static final Codec<ColorfulBlockSorting> CODEC = StringIdentifiable.createCodec(ColorfulBlockSorting::values);

  ColorfulBlockSorting(String name, Supplier<@Unmodifiable List<Block>> examples) {
    this.name = name;
    this.displayName = Text.translatable("options.extshape.colorful_block_sorting." + name);
    this.examples = examples;
  }

  @Override
  public String asString() {
    return this.name;
  }

  public Text displayName() {
    return this.displayName;
  }

  public @Unmodifiable List<Block> examples() {
    return examples.get();
  }

  public enum Serializer implements JsonSerializer<ColorfulBlockSorting>, JsonDeserializer<ColorfulBlockSorting> {
    INSTANCE;

    @Override
    public ColorfulBlockSorting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return CODEC.byId(json.getAsString());
    }

    @Override
    public JsonElement serialize(ColorfulBlockSorting src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.asString());
    }
  }
}
