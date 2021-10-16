package pers.solid.extshape.tag;

import com.google.gson.JsonArray;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.Blocks.*;

/**
 * 本模组使用的方块标签。需要注意的是，这些标签是内置的会直接存储内容，不会从数据包中加载。不应与 {@link net.minecraft.tag.BlockTags} 混淆。
 */
public class ExtShapeBlockTag extends ExtShapeTag<Block> {
    /**
     * 包含所有已创建的标签。
     **/
    public static final List<ExtShapeBlockTag> ALL_EXTSHAPE_BLOCK_TAGS = new ArrayList<>();

    public static final ExtShapeBlockTag PLANKS = new ExtShapeBlockTag(
            OAK_PLANKS,
            SPRUCE_PLANKS,
            BIRCH_PLANKS,
            JUNGLE_PLANKS,
            ACACIA_PLANKS,
            DARK_OAK_PLANKS,
            CRIMSON_PLANKS,
            WARPED_PLANKS
    );
    public static final ExtShapeBlockTag OVERWORLD_PLANKS = new ExtShapeBlockTag(
            OAK_PLANKS,
            SPRUCE_PLANKS,
            BIRCH_PLANKS,
            JUNGLE_PLANKS,
            ACACIA_PLANKS,
            DARK_OAK_PLANKS
    );
    public static final ExtShapeBlockTag STONE_VARIANTS = new ExtShapeBlockTag(
            GRANITE,
            POLISHED_GRANITE,
            DIORITE,
            POLISHED_DIORITE,
            ANDESITE,
            POLISHED_ANDESITE
    );
    public static final ExtShapeBlockTag STONES = Util.make(new ExtShapeBlockTag(
            STONE), blockTag -> blockTag.addTag(STONE_VARIANTS));
    public static final ExtShapeBlockTag SANDSTONES = new ExtShapeBlockTag(
            SANDSTONE,
            CUT_SANDSTONE,
            CHISELED_SANDSTONE,
            SMOOTH_SANDSTONE,
            RED_SANDSTONE,
            CUT_RED_SANDSTONE,
            CHISELED_RED_SANDSTONE,
            SMOOTH_RED_SANDSTONE
    );
    public static final ExtShapeBlockTag WOOLS = new ExtShapeBlockTag(WHITE_WOOL,
            ORANGE_WOOL,
            MAGENTA_WOOL,
            LIGHT_BLUE_WOOL,
            YELLOW_WOOL,
            LIME_WOOL,
            PINK_WOOL,
            GRAY_WOOL,
            LIGHT_GRAY_WOOL,
            CYAN_WOOL,
            PURPLE_WOOL,
            BLUE_WOOL,
            BROWN_WOOL,
            GREEN_WOOL,
            RED_WOOL,
            BLACK_WOOL);
    public static final ExtShapeBlockTag GLAZED_TERRACOTTAS = new ExtShapeBlockTag(
            WHITE_GLAZED_TERRACOTTA,
            ORANGE_GLAZED_TERRACOTTA,
            MAGENTA_GLAZED_TERRACOTTA,
            LIGHT_BLUE_GLAZED_TERRACOTTA,
            YELLOW_GLAZED_TERRACOTTA,
            LIME_GLAZED_TERRACOTTA,
            PINK_GLAZED_TERRACOTTA,
            GRAY_GLAZED_TERRACOTTA,
            LIGHT_GRAY_GLAZED_TERRACOTTA,
            CYAN_GLAZED_TERRACOTTA,
            PURPLE_GLAZED_TERRACOTTA,
            BLUE_GLAZED_TERRACOTTA,
            BROWN_GLAZED_TERRACOTTA,
            GREEN_GLAZED_TERRACOTTA,
            RED_GLAZED_TERRACOTTA,
            BLACK_GLAZED_TERRACOTTA
    );
    public static final ExtShapeBlockTag CONCRETES = new ExtShapeBlockTag(WHITE_CONCRETE,
            ORANGE_CONCRETE,
            MAGENTA_CONCRETE,
            LIGHT_BLUE_CONCRETE,
            YELLOW_CONCRETE,
            LIME_CONCRETE,
            PINK_CONCRETE,
            GRAY_CONCRETE,
            LIGHT_GRAY_CONCRETE,
            CYAN_CONCRETE,
            PURPLE_CONCRETE,
            BLUE_CONCRETE,
            BROWN_CONCRETE,
            GREEN_CONCRETE,
            RED_CONCRETE,
            BLACK_CONCRETE);
    public static final ExtShapeBlockTag STAINED_TERRACOTTAS = new ExtShapeBlockTag(
            WHITE_TERRACOTTA,
            ORANGE_TERRACOTTA,
            MAGENTA_TERRACOTTA,
            LIGHT_BLUE_TERRACOTTA,
            YELLOW_TERRACOTTA,
            LIME_TERRACOTTA,
            PINK_TERRACOTTA,
            GRAY_TERRACOTTA,
            LIGHT_GRAY_TERRACOTTA,
            CYAN_TERRACOTTA,
            PURPLE_TERRACOTTA,
            BLUE_TERRACOTTA,
            BROWN_TERRACOTTA,
            GREEN_TERRACOTTA,
            RED_TERRACOTTA,
            BLACK_TERRACOTTA
    );
    public static final ExtShapeBlockTag ORE_BLOCKS = new ExtShapeBlockTag(COAL_BLOCK,
            GOLD_BLOCK,
            IRON_BLOCK,
            DIAMOND_BLOCK,
            LAPIS_BLOCK,
            EMERALD_BLOCK,
            NETHERITE_BLOCK);
    // 所有方块。
    public static final ExtShapeBlockTag EXTSHAPE_BLOCKS = new ExtShapeBlockTag();
    public static final ExtShapeBlockTag WOOLEN_BLOCKS = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_blocks"));
    public static final ExtShapeBlockTag WOODEN_BLOCKS = new ExtShapeBlockTag(new Identifier("extshape",
            "wooden_blocks"));
    public static final ExtShapeBlockTag OVERWORLD_WOODEN_BLOCKS = new ExtShapeBlockTag(); // 仅包含由模组添加的方块，用于模组内部使用
    // 完整方块（主要用于双石台阶方块）
    public static final ExtShapeBlockTag FULL_BLOCKS = new ExtShapeBlockTag().addToTag(EXTSHAPE_BLOCKS);
    // 楼梯
    public static final ExtShapeBlockTag STAIRS = new ExtShapeBlockTag(new Identifier("minecraft"
            , "stairs")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_STAIRS = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_stairs")).addToTag(STAIRS);
    public static final ExtShapeBlockTag TERRACOTTA_STAIRS = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_stairs")).addToTag(STAIRS);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_STAIRS = new ExtShapeBlockTag(new Identifier(
            "extshape", "stained_terracotta_stairs")).addToTag(TERRACOTTA_STAIRS);
    public static final ExtShapeBlockTag WOOLEN_STAIRS = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_stairs")).addToTag(WOOLEN_BLOCKS).addToTag(STAIRS);
    // 台阶
    public static final ExtShapeBlockTag SLABS = new ExtShapeBlockTag(new Identifier("minecraft",
            "slabs")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_slabs")).addToTag(SLABS);
    public static final ExtShapeBlockTag TERRACOTTA_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_slabs")).addToTag(SLABS);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "stained_terracotta_slabs")).addToTag(TERRACOTTA_SLABS);
    public static final ExtShapeBlockTag GLAZED_TERRACOTTA_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "glazed_terracotta_slabs")).addToTag(SLABS);
    public static final ExtShapeBlockTag WOOLEN_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_slabs")).addToTag(WOOLEN_BLOCKS).addToTag(SLABS);
    // 栅栏
    public static final ExtShapeBlockTag FENCES = new ExtShapeBlockTag(new Identifier("minecraft",
            "fences")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_FENCES = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_fences")).addToTag(FENCES);
    public static final ExtShapeBlockTag TERRACOTTA_FENCES = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_fences")).addToTag(FENCES);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCES = new ExtShapeBlockTag(new Identifier("extshape",
            "stained_terracotta_fences")).addToTag(TERRACOTTA_FENCES);
    public static final ExtShapeBlockTag WOOLEN_FENCES = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_fences")).addToTag(WOOLEN_BLOCKS).addToTag(FENCES);
    // 栅栏门
    public static final ExtShapeBlockTag FENCE_GATES = new ExtShapeBlockTag(new Identifier(
            "minecraft", "fence_gates")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_FENCE_GATES = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_fence_gates")).addToTag(FENCE_GATES);
    public static final ExtShapeBlockTag TERRACOTTA_FENCE_GATES = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_fence_gates")).addToTag(FENCE_GATES);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCE_GATES = new ExtShapeBlockTag(new Identifier(
            "extshape",
            "stained_terracotta_fence_gates")).addToTag(TERRACOTTA_FENCE_GATES);
    public static final ExtShapeBlockTag WOOLEN_FENCE_GATES = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_fence_gates")).addToTag(WOOLEN_BLOCKS).addToTag(FENCE_GATES);
    // 墙
    public static final ExtShapeBlockTag WALLS = new ExtShapeBlockTag(new Identifier("minecraft",
            "walls")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_WALLS = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_fence_walls")).addToTag(WALLS);
    public static final ExtShapeBlockTag TERRACOTTA_WALLS = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_fence_walls")).addToTag(WALLS);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_WALLS = new ExtShapeBlockTag(new Identifier(
            "extshape",
            "stained_terracotta_fence_walls")).addToTag(TERRACOTTA_WALLS);
    // 按钮
    public static final ExtShapeBlockTag BUTTONS = new ExtShapeBlockTag(new Identifier("minecraft",
            "buttons")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_BUTTONS = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_buttons")).addToTag(BUTTONS);
    public static final ExtShapeBlockTag TERRACOTTA_BUTTONS = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_buttons")).addToTag(BUTTONS);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_BUTTONS = new ExtShapeBlockTag(new Identifier("extshape",
            "stained_terracotta_buttons")).addToTag(TERRACOTTA_BUTTONS);
    public static final ExtShapeBlockTag WOOLEN_BUTTONS = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_buttons")).addToTag(WOOLEN_BLOCKS).addToTag(BUTTONS);
    // 压力板
    public static final ExtShapeBlockTag PRESSURE_PLATES = new ExtShapeBlockTag(new Identifier("minecraft",
            "pressure_plates")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag CONCRETE_PRESSURE_PLATES = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_pressure_plates")).addToTag(PRESSURE_PLATES);
    public static final ExtShapeBlockTag TERRACOTTA_PRESSURE_PLATES = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_pressure_plates")).addToTag(PRESSURE_PLATES);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_PRESSURE_PLATES = new ExtShapeBlockTag(new Identifier(
            "extshape",
            "stained_terracotta_pressure_plates")).addToTag(TERRACOTTA_PRESSURE_PLATES);
    public static final ExtShapeBlockTag WOOLEN_PRESSURE_PLATES = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_pressure_plates")).addToTag(PRESSURE_PLATES);
    // 纵向台阶
    public static final ExtShapeBlockTag VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "vertical_slabs")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag WOODEN_VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "wooden_vertical_slabs")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_SLABS);
    public static final ExtShapeBlockTag CONCRETE_VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "concrete_vertical_slabs")).addToTag(VERTICAL_SLABS);
    public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "terracotta_vertical_slabs")).addToTag(VERTICAL_SLABS);
    public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier(
            "extshape", "stained_terracotta_vertical_slabs")).addToTag(TERRACOTTA_VERTICAL_SLABS);
    public static final ExtShapeBlockTag WOOLEN_VERTICAL_SLABS = new ExtShapeBlockTag(new Identifier("extshape",
            "woolen_vertical_slabs")).addToTag(WOOLEN_BLOCKS).addToTag(VERTICAL_SLABS);
    // 横条
    public static final ExtShapeBlockTag QUARTER_PIECES = new ExtShapeBlockTag(new Identifier("extshape",
            "quarter_pieces")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag WOODEN_QUARTER_PIECES = new ExtShapeBlockTag(new Identifier(
            "extshape", "wooden_quarter_pieces")).addToTag(WOODEN_BLOCKS).addToTag(QUARTER_PIECES);
    // 纵楼梯
    public static final ExtShapeBlockTag VERTICAL_STAIRS = new ExtShapeBlockTag(new Identifier("extshape",
            "vertical_stairs")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag WOODEN_VERTICAL_STAIRS = new ExtShapeBlockTag(new Identifier(
            "extshape", "wooden_vertical_stairs")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_STAIRS);
    // 纵条
    public static final ExtShapeBlockTag VERTICAL_QUARTER_PIECES = new ExtShapeBlockTag(new Identifier("extshape",
            "vertical_quarter_pieces")).addToTag(EXTSHAPE_BLOCKS);
    public static final ExtShapeBlockTag WOODEN_VERTICAL_QUARTER_PIECES = new ExtShapeBlockTag(new Identifier(
            "extshape", "wooden_vertical_quarter_pieces")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_QUARTER_PIECES);

    public ExtShapeBlockTag() {
        super();
    }

    public ExtShapeBlockTag(Block... elements) {
        super(elements);
    }

    public ExtShapeBlockTag(Identifier identifier, Block... elements) {
        super(identifier, List.of(elements));
    }

    public ExtShapeBlockTag(Identifier identifier, List<Block> list) {
        super(identifier, list);
        ALL_EXTSHAPE_BLOCK_TAGS.add(this);
    }

    /**
     * 将自身<b>添加到</b>另一个方块标签中。返回标签自身，可以递归调用。
     *
     * @param tag 需要将此标签添加到的标签。
     */
    @Override
    public ExtShapeBlockTag addToTag(ExtShapeTag<Block> tag) {
        tag.addTag(this);
        return this;
    }

    /**
     * 获取某个方块的命名空间id。
     *
     * @param element 方块。
     * @return 方块的命名空间id。
     */
    @Override
    public Identifier getIdentifierOf(Block element) {
        return Registry.BLOCK.getId(element);
    }

    @Override
    public @NotNull String toString() {
        return "ExtShapeBlockTag{" + identifier + '}';
    }

    public JsonArray jsonTree() {
        JsonArray array = new JsonArray();
        for (var entry : this.entries) {
            if (entry instanceof ExtShapeBlockTag)
                array.add(((ExtShapeBlockTag) entry).jsonTree());
            else if (entry instanceof TagEntrySingleton)
                array.add(Registry.BLOCK.getId(((TagEntrySingleton<Block>) entry).get()).toString());
        }
        return array;
    }
}
