package moe.plushie.armourers_workshop.common.init.items;

import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class ItemArmourersHammer extends AbstractModItem {

    public ItemArmourersHammer() {
        super(LibItemNames.ARMOURERS_HAMMER);
        setSortPriority(9);
    }
    
    @Override
    public ActionResultType onItemUseFirst(PlayerEntity player, World world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, Hand hand) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock().rotateBlock(world, pos, side)) {
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, PlayerEntity player) {
        return true;
    }
}
