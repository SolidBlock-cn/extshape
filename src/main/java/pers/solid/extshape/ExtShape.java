package pers.solid.extshape;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;
import pers.solid.extshape.tag.TagGenerator;

public class ExtShape implements ModInitializer {

    public static final Logger EXTSHAPE_LOGGER = LogManager.getLogger("EXTSHAPE");
    /**
     * 适用于整个模组的 ARRP 资源包，服务端和客户端都会运行。
     *
     * @see ExtShapeClient#EXTSHAPE_PACK_CLIENT
     */
    public static final RuntimeResourcePack EXTSHAPE_PACK = RuntimeResourcePack.create(new Identifier("extshape", "standard"));

    @Override
    public void onInitialize() {
        ExtShapeBlocks.init();
        ItemGroups.init();
        TagGenerator.writeAllBlockTagFiles();

        // 羊毛方块加入可燃方块
        for (final Block block : ExtShapeBlockTag.WOOLEN_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 30, 60);
            if (block instanceof SlabBlock || block instanceof VerticalSlabBlock) FuelRegistry.INSTANCE.add(block, 50);
            else if (block instanceof AbstractButtonBlock) FuelRegistry.INSTANCE.add(block, 33);
            else FuelRegistry.INSTANCE.add(block, 100);
        }

        // 木头加入可燃方块
        for (final Block block : ExtShapeBlockTag.OVERWORLD_WOODEN_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
            if (block instanceof VerticalSlabBlock) FuelRegistry.INSTANCE.add(block, 150);
            else if (block instanceof VerticalQuarterPieceBlock || block instanceof QuarterPieceBlock)
                FuelRegistry.INSTANCE.add(block, 75);
            else FuelRegistry.INSTANCE.add(block, 300);
        }

        RRPCallback.BEFORE_VANILLA.register(resources -> resources.add(EXTSHAPE_PACK));
    }
}
