package pers.solid.extshape;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import pers.solid.extshape.block.Blocks;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.datagen.Generator;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

public class ExtShape implements ModInitializer {

    // 是否生成数据。构建模组时设为true，编译模组时设为false。
    final boolean DATA_GENERATION_SWITCH = true;

    @Override
    public void onInitialize() {
        Blocks.init();
        ItemGroups.init();

//         生成数据
        if (DATA_GENERATION_SWITCH)
            Generator.generateAllData(Path.of("../src/main/resources"));

        // 羊毛方块加入可燃方块
        for (final Block block : ExtShapeBlockTag.WOOLEN_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 30, 60);
            if (block instanceof SlabBlock || block instanceof VerticalSlabBlock) FuelRegistry.INSTANCE.add(block, 50);
            else if (block instanceof AbstractButtonBlock) FuelRegistry.INSTANCE.add(block, 33);
            else FuelRegistry.INSTANCE.add(block, 100);
        }

        // 木头加入可燃方块
        for (final Block block : ExtShapeBlockTag.OVERWORLD_WOODEN_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(block,5,20);
            if (block instanceof VerticalSlabBlock) FuelRegistry.INSTANCE.add(block,150);
        }
    }
}
