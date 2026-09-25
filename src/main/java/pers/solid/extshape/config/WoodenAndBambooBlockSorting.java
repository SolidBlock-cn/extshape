package pers.solid.extshape.config;

import com.google.common.base.Suppliers;
import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

public enum WoodenAndBambooBlockSorting implements StringIdentifiable {
  SAME_SPECIES_DIFFERENT_FORMS_TOGETHER("same_species_different_forms_together", Suppliers.memoize(() -> List.of(Blocks.OAK_LOG, Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_OAK_WOOD, Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_BIRCH_WOOD, Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG))),
  SAME_FORM_DIFFERENT_SPECIES_TOGETHER("same_form_different_species_together", Suppliers.memoize(() -> List.of(Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.SPRUCE_LOG, Blocks.OAK_WOOD, Blocks.BIRCH_WOOD, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)));

  @SuppressWarnings("deprecation")
  public static final Codec<WoodenAndBambooBlockSorting> CODEC = StringIdentifiable.createCodec(WoodenAndBambooBlockSorting::values);

  private final String name;
  private final Text displayName;
  public final Supplier<@Unmodifiable List<Block>> examples;

  WoodenAndBambooBlockSorting(String name, Supplier<@Unmodifiable List<Block>> examples) {
    this.name = name;
    this.displayName = Text.translatable("options.extshape.wooden_and_bamboo_block_sorting." + name);
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

  public enum Serializer implements JsonSerializer<WoodenAndBambooBlockSorting>, JsonDeserializer<WoodenAndBambooBlockSorting> {
    INSTANCE;

    @Override
    public WoodenAndBambooBlockSorting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return CODEC.byId(json.getAsString());
    }

    @Override
    public JsonElement serialize(WoodenAndBambooBlockSorting src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.asString());
    }
  }
}
