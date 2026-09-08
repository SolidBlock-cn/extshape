package pers.solid.extshape.config;

import com.google.common.base.Suppliers;
import com.google.gson.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

public enum WoodenAndBambooBlockSorting implements StringRepresentable {
  SAME_SPECIES_DIFFERENT_FORMS_TOGETHER("same_species_different_forms_together", Suppliers.memoize(() -> List.of(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD))),
  SAME_FORM_DIFFERENT_SPECIES_TOGETHER("same_form_different_species_together", Suppliers.memoize(() -> List.of(Blocks.OAK_WOOD, Blocks.BIRCH_WOOD, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)));

  public static final EnumCodec<WoodenAndBambooBlockSorting> CODEC = StringRepresentable.fromEnum(WoodenAndBambooBlockSorting::values);

  private final String name;
  private final Component displayName;
  public final Supplier<@Unmodifiable List<Block>> examples;

  WoodenAndBambooBlockSorting(String name, Supplier<@Unmodifiable List<Block>> examples) {
    this.name = name;
    this.displayName = Component.translatable("options.extshape.wooden_and_bamboo_block_sorting." + name);
    this.examples = examples;
  }

  @Override
  public String getSerializedName() {
    return name;
  }

  public Component displayName() {
    return this.displayName;
  }

  public @Unmodifiable List<Block> examples() {
    return examples.get();
  }

  public enum Serializer implements JsonSerializer<WoodenAndBambooBlockSorting>, JsonDeserializer<WoodenAndBambooBlockSorting> {
    INSTANCE;

    @Override
    public @Nullable WoodenAndBambooBlockSorting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return CODEC.byName(json.getAsString());
    }

    @Override
    public JsonElement serialize(WoodenAndBambooBlockSorting src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getSerializedName());
    }
  }
}
