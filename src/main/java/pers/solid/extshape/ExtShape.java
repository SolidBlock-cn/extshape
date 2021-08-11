package pers.solid.extshape;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import pers.solid.extshape.block.Blocks;
import pers.solid.extshape.block.RegistrableBlock;
import pers.solid.extshape.datagen.Generator;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

public class ExtShape implements ModInitializer {

    // 是否生成数据。构建模组时设为true，编译模组时设为false。
    final boolean DATA_GENERATION_SWITCH = true;

    public static void registerAll() {
        // 注册所有的方块及其物品。
        int sum = 0;
        for (final Block BLOCK : ExtShapeBlockTag.EXTSHAPE_BLOCKS) {
            ((RegistrableBlock) BLOCK).register();
            sum++;
        }
        System.out.printf("[ExtShape] 已注册%s个方块及其对应方块物品。%n", sum);
    }

    @Override
    public void onInitialize() {
        Blocks.init();
        registerAll();
        ItemGroups.init();

//         生成数据
        if (DATA_GENERATION_SWITCH)
            Generator.generateAllData(Path.of("../src/main/resources"));

        // 羊毛方块加入可燃方块
        for (Block BLOCK : ExtShapeBlockTag.WOOLEN_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(BLOCK, 30, 60);
            if (BLOCK instanceof StairsBlock) FuelRegistry.INSTANCE.add(BLOCK, 75);
            else if (BLOCK instanceof SlabBlock) FuelRegistry.INSTANCE.add(BLOCK, 50);
            else if (BLOCK instanceof AbstractButtonBlock) FuelRegistry.INSTANCE.add(BLOCK, 33);
            else FuelRegistry.INSTANCE.add(BLOCK, 100);
        }
    }

}
