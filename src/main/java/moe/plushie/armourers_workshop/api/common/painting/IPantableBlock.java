package moe.plushie.armourers_workshop.api.common.painting;

import moe.plushie.armourers_workshop.api.common.skin.cubes.ICubeColour;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface IPantableBlock {
    
    /** @deprecated Replaced by {@link #setColour(IBlockReader world, int x, int y, int z, byte[] rgb, int side)} */
    @Deprecated
    public boolean setColour(IBlockReader world, BlockPos pos, int colour, Direction facing);
    
    public boolean setColour(IBlockReader world, BlockPos pos, byte[] rgb, Direction facing);
    
    public int getColour(IBlockReader world, BlockPos pos, Direction facing);
    
    public void setPaintType(IBlockReader world, BlockPos pos, IPaintType paintType, Direction facing);
    
    public IPaintType getPaintType(IBlockReader world, BlockPos pos, Direction facing);
    
    public ICubeColour getColour(IBlockReader world, BlockPos pos);
    
    public boolean isRemoteOnly(IBlockReader world, BlockPos pos, Direction facing);
}
