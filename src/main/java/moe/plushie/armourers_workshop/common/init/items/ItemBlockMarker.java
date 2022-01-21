package moe.plushie.armourers_workshop.common.init.items;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.common.skin.cubes.CubeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockMarker extends AbstractModItem {

    public ItemBlockMarker() {
        super(LibItemNames.BLOCK_MARKER);
        setCreativeTab(ArmourersWorkshop.TAB_PAINTING_TOOLS);
        setSortPriority(12);
    }
    
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        BlockState state = worldIn.getBlockState(pos);
        if (CubeRegistry.INSTANCE.isBuildingBlock(state.getBlock())) {
            if (!worldIn.isRemote) {
                int meta = state.getBlock().getMetaFromState(state);
                int newMeta = facing.ordinal() + 1;
                BlockState newState = null;
                if (newMeta == meta) {
                    // This side is already marked.
                    newState = state.getBlock().getStateFromMeta(0);
                } else {
                    newState = state.getBlock().getStateFromMeta(newMeta);
                }
                worldIn.setBlockState(pos, newState, 2);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
