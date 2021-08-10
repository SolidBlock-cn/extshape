package pers.solid.extshape;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.Blocks;
import pers.solid.extshape.block.RegistrableBlock;
import pers.solid.extshape.data.Main;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class ExtShape implements ModInitializer {

    // 是否生成数据。构建模组时设为true，编译模组时设为false。
//    final boolean DATA_GENERATION_SWITCH = true;

    @Deprecated
    private static void runDataGenerator(@NotNull Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate) {
        // 这是之前尝试使用 Minecraft 原版代码制作的数据生成器的代码。
        // 已废弃。
        System.out.printf("数据生成开始！生成的数据位于%s。\n", output.toAbsolutePath());
        try {
            DataGenerator dataGenerator = Main.create(output, inputs, includeClient, includeServer, includeDev, includeReports,
                    validate);
            dataGenerator.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("已完成数据生成！");
    }

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

        // 生成数据
//        if (DATA_GENERATION_SWITCH)
//            Generator.generateAllData(Path.of("../src/main/resources"));

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
