package pers.solid.extshape.datagen;

import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.GlazedTerracottaSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Generator {

    public static final boolean DATA_GENERATION_SWITCH = true;
    private static int stat = 0;
    public Path basePath;

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
        GlazedTerracottaSlabGenerator.init(generator);
        for (ExtShapeBlockTag tag : ExtShapeBlockTag.ALL_EXTSHAPE_BLOCK_TAGS) {
            generator.writeBlockTagFile(tag);
        }
        System.out.printf("[EXTSHAPE] 数据生成完成，总共生成了%s个文件，好耶！！（你可能需要重新构建项目（无需重启游戏）才能看到更改。）%n", stat);
    }

    public void write(String path, @Nullable String content) {
        if (content == null) return;
        File file = new File(String.valueOf(basePath), path);
//        System.out.printf("正在写入文件：%s\n",file.getAbsolutePath());
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

    public void writeModelFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("assets/%s/models/%s.json", namespace, path), content);
    }

    public void writeBlockStatesFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("assets/%s/blockstates/%s.json", namespace, path), content);
    }

    public void writeRecipeFile(String namespace, String path, @Nullable String content) {
        this.write(String.format("data/%s/recipes/%s.json", namespace, path), content);
    }

    public void writeBlockTagFile(ExtShapeBlockTag blockTag) {
        @Nullable Identifier identifier = blockTag.identifier;
        if (blockTag.identifier == null) return;
        write(String.format("data/%s/tags/blocks/%s.json", identifier.getNamespace(), identifier.getPath()),
                blockTag.generateString());
    }

    public void writeLootTableFile(String namespace, String path, @Nullable String content) {
        write(String.format("data/%s/loot_tables/blocks/%s.json", namespace, path), content);
    }

    @Nullable
    public AbstractBlockGenerator<? extends Block> createGeneratorForBlock(Block block) {
        AbstractBlockGenerator<? extends Block> generator;
        Path path = basePath;
        if (block == ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB)
            generator = new SmoothStoneDoubleSlabGenerator(path, block);
        else if (block instanceof StairsBlock) generator = new StairsGenerator(path, (StairsBlock) block);
        else if (block instanceof GlazedTerracottaSlabBlock) generator = new GlazedTerracottaSlabGenerator(path,
                (GlazedTerracottaSlabBlock) block);
        else if (block instanceof SlabBlock) generator = new SlabGenerator(path, (SlabBlock) block);
        else if (block instanceof VerticalSlabBlock) generator = new VerticalSlabGenerator(path,
                (VerticalSlabBlock) block);
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

    public void generateForAllBlocks(ExtShapeBlockTag tag) {
        for (Block block : tag) {
            var generator = this.createGeneratorForBlock(block);
            if (generator != null) generator.writeAllFiles();
        }
    }
}
