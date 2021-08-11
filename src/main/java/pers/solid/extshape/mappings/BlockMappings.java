package pers.solid.extshape.mappings;

import net.minecraft.block.*;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.VerticalSlabBlock;

import java.util.HashMap;
import java.util.Map;

public class BlockMappings {

    public static final Map<Block, StairsBlock> mappingOfStairs = new HashMap<>();
    public static final Map<Block, SlabBlock> mappingOfSlabs = new HashMap<>();
    public static final Map<Block, VerticalSlabBlock> mappingOfVerticalSlabs = new HashMap<>();

    public static final Map<Block, FenceBlock> mappingOfFences = new HashMap<>();
    public static final Map<Block, FenceGateBlock> mappingOfFenceGates = new HashMap<>();
    public static final Map<Block, WallBlock> mappingOfWalls = new HashMap<>();
    public static final Map<Block,AbstractButtonBlock> mappingOfButtons = new HashMap<>();
    public static final Map<Block,PressurePlateBlock> mappingOfPressurePlates = new HashMap<>();

    @Nullable
    public static Map<Block,? extends Block> getBlockMappingOf(Block block) {
        if (block instanceof StairsBlock) return mappingOfStairs;
        else if (block instanceof SlabBlock) return mappingOfSlabs;
        else if (block instanceof VerticalSlabBlock) return mappingOfVerticalSlabs;
        else if (block instanceof FenceBlock) return mappingOfFences;
        else if (block instanceof FenceGateBlock) return mappingOfFenceGates;
        else if (block instanceof WallBlock) return mappingOfWalls;
        else if (block instanceof AbstractButtonBlock) return mappingOfButtons;
        else if (block instanceof PressurePlateBlock) return mappingOfPressurePlates;
        else return null;
    }

    public static Block getBaseBlockOf(Block block) {
        Map<Block, ? extends Block> mapping = getBlockMappingOf(block);
        if (mapping==null) return null;
        for (Map.Entry<Block, ? extends Block> entry : mapping.entrySet()) {
            if (entry.getValue()==block) return entry.getKey();
        }
        return null;
    }
//
//    public static StairsBlock getStairsBlockOf(Block block) {
//        return mappingOfStairs.get(block);
//    }
//
//    public static SlabBlock getSlabBlockOf(Block block) {
//        return mappingOfSlabs.get(block);
//    }
//
//    public static VerticalSlabBlock getVerticalSlabBlockOf(Block block) {
//        return mappingOfVerticalSlabs.get(block);
//    }
//
//    public static FenceBlock getFenceBlockOf(Block block) {
//        return mappingOfFences.get(block);
//    }
//
//    public static FenceGateBlock getFenceGateBlockOf(Block block) {
//        return mappingOfFenceGates.get(block);
//    }
//
//    public static AbstractButtonBlock getButtonBlockOf(Block block) {
//        return mappingOfButtons.get(block);
//    }
//
//    public static PressurePlateBlock getPressurePlateBlockOf(Block block) {
//        return mappingOfPressurePlates.get(block);
//    }
}
