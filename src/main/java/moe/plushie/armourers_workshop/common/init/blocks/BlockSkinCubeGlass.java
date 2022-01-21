package moe.plushie.armourers_workshop.common.init.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockSkinCubeGlass extends BlockSkinCube {

    public BlockSkinCubeGlass(String name, boolean glowing) {
        super(name, glowing);
        setSortPriority(121);
        if (glowing) {
            setSortPriority(120);
        }
    }
    
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }
    
    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }
}
