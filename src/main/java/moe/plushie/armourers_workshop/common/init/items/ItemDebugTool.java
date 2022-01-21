package moe.plushie.armourers_workshop.common.init.items;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDebugTool extends AbstractModItem {

    public ItemDebugTool() {
        super(LibItemNames.DEBUG_TOOL, true);
        setSortPriority(-1);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            playerIn.openGui(ArmourersWorkshop.getInstance(), EnumGuiId.DEBUG_TOOL.ordinal(), worldIn, 0, 0, 0);
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, itemStack);
    }
    
    public static interface IDebug {   
        public void getDebugHoverText(World world, BlockPos pos, ArrayList<String> textLines);
    }
}
