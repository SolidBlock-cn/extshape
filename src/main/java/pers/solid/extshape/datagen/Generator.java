package pers.solid.extshape.datagen;

import net.minecraft.Bootstrap;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.RegistrableBlock;
import pers.solid.extshape.block.SubBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

public class Generator {

    public Path basePath;

    public Generator(Path path) {
        this.basePath = path;
    }

    public Generator() {
        this(Path.of("generated"));
    }

    public static void generateAllData(Path basePath) {
        // 生成关于方块的数据
        Generator generator = new Generator(basePath);
        generator.writeModelFile("extshape","block/vertical_slab", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 0, 0 ],
                            "to": [  16, 16, 8 ],
                            "faces": {
                                "down":  {"texture": "#bottom", "cullface": "down" },
                                "up":    {"texture": "#top",    "cullface": "up" },
                                "north": {"texture": "#side",   "cullface": "north" },
                                "south": {"texture": "#side",   "cullface": "south" },
                                "west":  { "texture": "#side",   "cullface": "west" },
                                "east":  { "texture": "#side",   "cullface": "east" }
                            }
                        }
                    ]
                }""");
        for (final Block BLOCK : VERTICAL_SLABS) generator.writeAllFiles(BLOCK);

        try {
            for (final Block block : EXTSHAPE_BLOCKS) {
                if (!(block instanceof SubBlock)) continue;
                Block baseBlock = ((SubBlock) block).getBaseBlock();
                if (Mineable.VANILLA_AXE_MINEABLE.contains(baseBlock)) ExtShapeBlockTag.AXE_MINEABLE.add(block);
                if (Mineable.VANILLA_HOE_MINEABLE.contains(baseBlock)) ExtShapeBlockTag.HOE_MINEABLE.add(block);
                if (Mineable.VANILLA_PICKAXE_MINEABLE.contains(baseBlock)) ExtShapeBlockTag.PICKAXE_MINEABLE.add(block);
                if (Mineable.VANILLA_SHOVEL_MINEABLE.contains(baseBlock)) ExtShapeBlockTag.SHOVEL_MINEABLE.add(block);
            }
            System.out.println("mineable部分的方块数据已生成。");
        } catch (IllegalStateException e) {
            System.out.printf("由于发生错误，mineable部分的方块数据未生成，错误详情如下：\n%s\n", e);
        }

        // 生成方块标签
        for (final ExtShapeBlockTag tag : ExtShapeBlockTag.ALL_EXTSHAPE_BLOCK_TAGS) {
            if (tag.getIdentifier() != null)
                generator.writeBlockTagFile(tag);
        }

    }

    public void writeModelFile(String namespace, String path, String content) {
        this.write(String.format("assets/%s/models/%s.json", namespace, path), content);
    }


//    public void writeBlockModelFile(ExtendedStairsBlock block) {
//        Identifier identifier = (block).getBlockModelIdentifier();
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath(),
//                (block).getBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath() + "_inner",
//                (block).getInnerBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath() + "_outer",
//                (block).getOuterBlockModelString());
//    }
//
//    public void writeBlockModelFile(ExtendedSlabBlock block) {
//        Identifier identifier = block.getIdentifier();
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath(),block.getBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_top",block.getTopBlockModelString());
//    }
//
//    public void writeBlockModelFile(ExtendedFenceBlock block) {
//        Identifier identifier = block.getBlockModelIdentifier();
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath()+"_inventory",
//                block.getBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath()+"_side",
//                block.getSideBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_post",
//                block.getPostBlockModelString());
//    }
//
//    public void writeBlockModelFile(ExtendedFenceGateBlock block) {
//        Identifier identifier = block.getBlockModelIdentifier();
//        this.writeModelFile(block,identifier.getNamespace(), identifier.getPath(),
//                block.getBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_open",
//                block.getOpenBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_wall",
//                block.getWallBlockModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_wall_open",
//                block.getWallOpenBlockModelString());
//    }
//
//    public void writeBlockModelFile(ExtendedWallBlock block) {
//        Identifier identifier = block.getBlockModelIdentifier();
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_inventory",
//                block.getInventoryModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_post",
//                block.getPostModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_side",
//                block.getSideModelString());
//        this.writeModelFile(block,identifier.getNamespace(),identifier.getPath()+"_side_tall",
//                block.getSideTallModelString());
//    }

    public void writeBlockModelFiles(Block block) {
        assert block instanceof RegistrableBlock;
        List<Pair<Identifier, String>> collection = ((RegistrableBlock) block).getBlockModelCollection();
        for (Pair<Identifier, String> pair : collection) {
            Identifier identifier = pair.getLeft();
            String content = pair.getRight();
            this.writeModelFile(identifier.getNamespace(), identifier.getPath(), content);
        }
    }

    public void writeItemModelFile(Block block) {
        assert block instanceof RegistrableBlock;
        Identifier identifier = ((RegistrableBlock) block).getItemModelIdentifier();
        this.writeModelFile(identifier.getNamespace(), identifier.getPath(),
                ((RegistrableBlock) block).getItemModelString());
    }

    public void writeBlockStatesFile(Block block, String namespace, String path, String content) {
        assert block instanceof RegistrableBlock;
        this.write(String.format("assets/%s/blockstates/%s.json", namespace, path), content);
    }

    public void writeAllFiles(Block block) {
        this.writeAllFiles(block, true, true);
    }

    public void writeBlockStatesFile(Block block) {
        assert block instanceof RegistrableBlock;
        Identifier identifier = ((RegistrableBlock) block).getIdentifier();
        this.writeBlockStatesFile(block, identifier.getNamespace(), identifier.getPath(),
                ((RegistrableBlock) block).getBlockStatesString());
    }

    public void writeCraftingRecipeFile(Block block) {
        assert block instanceof RegistrableBlock;
        Identifier identifier = ((RegistrableBlock) block).getIdentifier();
        this.writeRecipeFile(block, identifier.getNamespace(),
                identifier.getPath(), ((RegistrableBlock) block).getCraftingRecipeString());
    }

    public void writeStoneCuttingRecipeFile(Block block) {
        assert block instanceof RegistrableBlock;
        Identifier identifier = ((RegistrableBlock) block).getIdentifier();
        this.writeRecipeFile(block, identifier.getNamespace(), identifier.getPath() + "_from_stonecutting",
                ((RegistrableBlock) block).getStoneCuttingRecipeString());
    }

    public void writeRecipeFiles(Block block) {
        this.writeCraftingRecipeFile(block);
        if (block instanceof SubBlock) {
            Block baseBlock = ((SubBlock) block).getBaseBlock();
            // 特定方块允许使用切石机合成。
            if (baseBlock == OBSIDIAN || baseBlock == CRYING_OBSIDIAN || STONES.contains(baseBlock) || CONCRETES.contains(baseBlock) || baseBlock == TERRACOTTA || STAINED_TERRACOTTAS.contains(baseBlock) || GLAZED_TERRACOTTAS.contains(baseBlock) || ORE_BLOCKS.contains(baseBlock) || SANDSTONES.contains(baseBlock) || baseBlock == PRISMARINE || baseBlock == DARK_PRISMARINE || baseBlock == PRISMARINE_BRICKS)
                this.writeStoneCuttingRecipeFile(block);
        }
    }

    public void writeRecipeFile(Block block, String namespace, String path, String content) {
        this.write(String.format("data/%s/recipes/%s.json", namespace, path), content);
    }


    public void writeLootTableFile(Block block) {
        assert block instanceof RegistrableBlock;
        this.writeLootTableFile(((RegistrableBlock) block).getIdentifier().getNamespace(), ((RegistrableBlock) block).getIdentifier().getPath(),
                ((RegistrableBlock) block).getLootTableString());
    }

    public void writeBlockTagFile(ExtShapeBlockTag blockTag) {
        Identifier identifier = blockTag.identifier;
        write(String.format("data/%s/tags/blocks/%s.json", identifier.getNamespace(), identifier.getPath()),
                blockTag.generateString());
    }


    public void writeLootTableFile(String namespace, String path, String content) {
        write(String.format("data/%s/loot_tables/blocks/%s.json", namespace, path), content);
    }


    public void write(String path, String content) {
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
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAllFiles(Block block, boolean assets, boolean data) {
        // 同时写好方块状态、方块模型和物品模型，以及合成配方、战利品表
        if (assets) {
            this.writeBlockStatesFile(block);
            this.writeBlockModelFiles(block);
            this.writeItemModelFile(block);
        }
        if (data) {
            this.writeLootTableFile(block);
            this.writeRecipeFiles(block);
        }
    }

    @Deprecated
    public static void main(String[] args) {
        // 该函数不太可能成功运行，建议在游戏启动后运行。
        Generator generator = new Generator(Path.of("generated"));

        // 此函数不应该在游戏运行时执行。
        Bootstrap.initialize();
        ExtShape.registerAll();

        System.out.println("%%% 开始生成数据 %%%");

        for (final Block BLOCK : EXTSHAPE_BLOCKS) generator.writeAllFiles(BLOCK);

        System.out.println("%%% 数据生成完成 %%%");
    }
}
