package pers.solid.extshape.datagen;

import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.*;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * 数据生成器。
 */
public class Generator {

    /**
     * 数据生成总开关。如果设为 <code>false</code>，则不生成任何数据。
     */
    public static final boolean DATA_GENERATION_SWITCH = true;

    /**
     * 已生成的文件数据的统计。每成功写入一个文件，则该统计 +1。
     */
    private static int stat = 0;

    /**
     * 数据存放的基础路径。
     */
    public final Path basePath;

    /**
     * @param path 生成的数据所在的基础路径。
     */
    public Generator(Path path) {
        this.basePath = path;
    }

    public Generator() {
        this(Path.of("generated"));
    }

    public static void main() {
        if (!DATA_GENERATION_SWITCH) return;
        ExtShapeBlocks.init();
        System.out.println("[EXTSHAPE] 开始生成数据！（只有在开发环境中才应该出现这行字，否则是模组出bug了。）");
        Generator generator = new Generator(Path.of("../src/main/resources"));
        generator.generateForAllBlocks(ExtShapeBlockTag.EXTSHAPE_BLOCKS);
        VerticalSlabGenerator.init(generator);
        QuarterPieceGenerator.init(generator);
        GlazedTerracottaSlabGenerator.init(generator);
        VerticalStairsGenerator.init(generator);
        VerticalQuarterPieceGenerator.init(generator);
        TagGenerator.writeAllBlockTagFiles(generator);
        System.out.printf("[EXTSHAPE] 数据生成完成，总共生成了%s个文件，好耶！！（你可能需要重新构建项目（无需重启游戏）才能看到更改。）%n", stat);
    }

    /**
     * 在指定的位置，写入指定字符串的文件。如果目录不存在，则创建此目录。写入成功后，计入统计。
     *
     * @param path 文件路径（将会自动基于 {@link #basePath} 生成完整路径）。
     */
    public void write(String path, @Nullable String content) {
        if (content == null) return;
        File file = new File(String.valueOf(basePath), path);
        try {
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();// 能创建多级目录
            }
            file.createNewFile();//有路径才能创建文件
            FileWriter out = new FileWriter(file);
            out.write(content);
            stat += 1;
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入模型文件。
     *
     * @param namespace id的命名空间。
     * @param path      id的路径。
     * @param content   文件内容。
     */
    public void writeModelFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("assets/%s/models/%s.json", namespace, path), content);
    }

    /**
     * 写入方块状态定义文件。
     *
     * @param namespace id的命名空间。
     * @param path      id的路径。
     * @param content   文件内容。
     */
    public void writeBlockStatesFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("assets/%s/blockstates/%s.json", namespace, path), content);
    }

    /**
     * 写入（主要的）配方文件。
     *
     * @param namespace id的命名空间。
     * @param path      id的路径。
     * @param content   文件内容。
     */
    public void writeRecipeFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("data/%s/recipes/%s.json", namespace, path), content);
    }

    /**
     * 写入一个方块标签的json文件，文件路径和内容将由方块标签自动决定。
     *
     * @param blockTag 方块标签。
     */
    public void writeBlockTagFile(ExtShapeBlockTag blockTag) {
        @Nullable Identifier identifier = blockTag.identifier;
        if (blockTag.identifier == null) return;
        write(String.format("data/%s/tags/blocks/%s.json", identifier.getNamespace(), identifier.getPath()),
                blockTag.generateString());
    }

    /**
     * 写入一个方块标签对应的物品标签的json文件。
     *
     * @param blockTag 方块标签。
     */
    public void writeItemTagFile(ExtShapeBlockTag blockTag) {
        @Nullable Identifier identifier = blockTag.identifier;
        if (blockTag.identifier == null) return;
        write(String.format("data/%s/tags/items/%s.json", identifier.getNamespace(), identifier.getPath()),
                blockTag.generateString());
    }

    /**
     * 写入一个战利品表文件。
     *
     * @param namespace id的命名空间
     * @param path      id的路径。
     * @param content   文件内容。
     */
    public void writeLootTableFile(String namespace, String path, @Nullable String content) {
        write(String.format("data/%s/loot_tables/blocks/%s.json", namespace, path), content);
    }

    /**
     * 为指定的方块创建合适的生成器。
     *
     * @param block 需要创建对应生成器的方块。
     * @return 由方块创建的生成器。
     */
    @Nullable
    public AbstractBlockGenerator<? extends Block> createGeneratorForBlock(Block block) {
        AbstractBlockGenerator<? extends Block> generator;
        Path path = basePath;
        if (block instanceof StairsBlock) generator = new StairsGenerator(path, (StairsBlock) block);
        else if (block instanceof GlazedTerracottaSlabBlock) generator = new GlazedTerracottaSlabGenerator(path,
                (GlazedTerracottaSlabBlock) block);
        else if (block instanceof SlabBlock) generator = new SlabGenerator(path, (SlabBlock) block);
        else if (block instanceof VerticalSlabBlock) generator = new VerticalSlabGenerator(path,
                (VerticalSlabBlock) block);
        else if (block instanceof QuarterPieceBlock) generator = new QuarterPieceGenerator(path,
                (QuarterPieceBlock) block);
        else if (block instanceof VerticalQuarterPieceBlock) generator = new VerticalQuarterPieceGenerator(path,
                (VerticalQuarterPieceBlock) block);
        else if (block instanceof VerticalStairsBlock)
            generator = new VerticalStairsGenerator(path, (VerticalStairsBlock) block);
        else if (block instanceof FenceBlock) generator = new FenceGenerator(path, (FenceBlock) block);
        else if (block instanceof FenceGateBlock) generator = new FenceGateGenerator(path, (FenceGateBlock) block);
        else if (block instanceof WallBlock) generator = new WallGenerator(path, (WallBlock) block);
        else if (block instanceof AbstractButtonBlock)
            generator = new ButtonGenerator(path, (AbstractButtonBlock) block);
        else if (block instanceof PressurePlateBlock) generator = new PressurePlateGenerator(path,
                (PressurePlateBlock) block);
        else generator = null;
        return generator;
    }

    /**
     * 生成某方块标签内 <b>所有</b> 方块的数据。
     *
     * @param tag 方块标签。一般为 {@link ExtShapeBlockTag#ALL_EXTSHAPE_BLOCK_TAGS}。
     */
    public void generateForAllBlocks(ExtShapeBlockTag tag) {
        // 生成方块数据。
        for (Block block : tag) {
            var generator = this.createGeneratorForBlock(block);
            if (generator != null) generator.writeAllFiles();
        }

        // 生成双层平滑石头的数据。
        new AbstractBlockGenerator<>(basePath, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB) {
            @Override
            public Identifier getBlockModelIdentifier() {
                return new Identifier("minecraft", "block/smooth_stone_slab_double");
            }

            @Override
            public String getBlockModelString() {
                return null;
            }
        }.writeAllFiles();
        // 生成石化橡木木板的数据。
        new AbstractBlockGenerator<>(basePath, ExtShapeBlocks.PETRIFIED_OAK_PLANKS) {
            @Override
            public Identifier getBlockModelIdentifier() {
                return new Identifier("minecraft", "block/oak_planks");
            }

            @Override
            public @Nullable String getBlockModelString() {
                return null;
            }
        }.writeAllFiles();
        // 石化橡木台阶的合成表。
        new SlabGenerator(basePath, (SlabBlock) Blocks.PETRIFIED_OAK_SLAB) {
            @Override
            public Identifier getCraftingRecipeIdentifier() {
                return new Identifier("extshape", "petrified_oak_slab");
            }
        }.writeRecipeFiles();
        // 双层石台阶的合成表。
        new SlabGenerator(basePath, (SlabBlock) Blocks.SMOOTH_STONE_SLAB) {
            @Override
            public Identifier getCraftingRecipeIdentifier() {
                return new Identifier("extshape", "smooth_stone_slab");
            }
        }.writeRecipeFiles();
    }
}
