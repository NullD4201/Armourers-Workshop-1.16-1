package moe.plushie.armourers_workshop.common.capability.paint;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPaintingTool {
    
    public void usedOnBlock(PlayerEntity player, World world, BlockPos pos, Block block, Direction facing);
}
