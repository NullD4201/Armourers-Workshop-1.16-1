package moe.plushie.armourers_workshop.common.init.items.paintingtool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.api.common.painting.IPaintType;
import moe.plushie.armourers_workshop.api.common.painting.IPantableBlock;
import moe.plushie.armourers_workshop.common.init.blocks.ModBlocks;
import moe.plushie.armourers_workshop.common.init.items.AbstractModItem;
import moe.plushie.armourers_workshop.common.init.sounds.ModSounds;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.common.network.PacketHandler;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientToolPaintBlock;
import moe.plushie.armourers_workshop.common.painting.IBlockPainter;
import moe.plushie.armourers_workshop.common.painting.PaintTypeRegistry;
import moe.plushie.armourers_workshop.common.painting.tool.IConfigurableTool;
import moe.plushie.armourers_workshop.common.painting.tool.ToolOption;
import moe.plushie.armourers_workshop.common.painting.tool.ToolOptions;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import moe.plushie.armourers_workshop.common.world.undo.UndoManager;
import moe.plushie.armourers_workshop.utils.TranslateUtils;
import moe.plushie.armourers_workshop.utils.UtilColour;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class ItemDodgeTool extends AbstractModItem implements IConfigurableTool, IBlockPainter {

    public ItemDodgeTool() {
        super(LibItemNames.DODGE_TOOL);
        setCreativeTab(ArmourersWorkshop.TAB_PAINTING_TOOLS);
        setSortPriority(17);
    }
    
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        BlockState state = worldIn.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);
        
        if (state.getBlock() instanceof IPantableBlock) {
            if (!worldIn.isRemote) {
                UndoManager.begin(player);
            }
            if (ToolOptions.FULL_BLOCK_MODE.getValue(stack)) {
                for (int i = 0; i < 6; i++) {
                    usedOnBlockSide(stack, player, worldIn, pos, state.getBlock(), Direction.values()[i], facing == Direction.values()[i]);
                }
            } else {
                usedOnBlockSide(stack, player, worldIn, pos, state.getBlock(), facing, true);
            }
            if (!worldIn.isRemote) {
                UndoManager.end(player);
                worldIn.playSound(null, pos, ModSounds.DODGE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            }
            return ActionResultType.SUCCESS;
        }
        
        if (state.getBlock() == ModBlocks.ARMOURER & player.isSneaking()) {
            if (!worldIn.isRemote) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null && te instanceof TileEntityArmourer) {
                    ((TileEntityArmourer)te).toolUsedOnArmourer(this, worldIn, stack, player);
                }
            }
            return ActionResultType.SUCCESS;
        }
        
        return ActionResultType.PASS;
    }
    
    @Override
    public void usedOnBlockSide(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Block block, Direction face, boolean spawnParticles) {
        int intensity = ToolOptions.INTENSITY.getValue(stack);
        IPantableBlock worldColourable = (IPantableBlock) block;
        if (worldColourable.isRemoteOnly(world, pos, face) & world.isRemote) {
            byte[] rgbt = new byte[4];
            int oldColour = worldColourable.getColour(world, pos, face);
            IPaintType oldPaintType = worldColourable.getPaintType(world, pos, face);
            Color c = UtilColour.makeColourBighter(new Color(oldColour), intensity);
            rgbt[0] = (byte)c.getRed();
            rgbt[1] = (byte)c.getGreen();
            rgbt[2] = (byte)c.getBlue();
            rgbt[3] = (byte)oldPaintType.getId();
            if (block == ModBlocks.BOUNDING_BOX && oldPaintType == PaintTypeRegistry.PAINT_TYPE_NONE) {
                rgbt[3] = (byte)PaintTypeRegistry.PAINT_TYPE_NORMAL.getId();
            }
            MessageClientToolPaintBlock message = new MessageClientToolPaintBlock(pos, face, rgbt);
            PacketHandler.networkWrapper.sendToServer(message);
        } else if(!worldColourable.isRemoteOnly(world, pos, face) & !world.isRemote) {
            int oldColour = worldColourable.getColour(world, pos, face);
            byte oldPaintType = (byte) worldColourable.getPaintType(world, pos, face).getId();
            int newColour = UtilColour.makeColourBighter(new Color(oldColour), intensity).getRGB();
            UndoManager.blockPainted(player, world, pos, oldColour, oldPaintType, face);
            ((IPantableBlock) block).setColour(world, pos, newColour, face);
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isSneaking()) {
            if (worldIn.isRemote) {
                playerIn.openGui(ArmourersWorkshop.getInstance(), EnumGuiId.TOOL_OPTIONS.ordinal(), worldIn, 0, 0, 0);
            }
            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int intensity = ToolOptions.INTENSITY.getValue(stack);
        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.intensity", intensity));
        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.openSettings"));
    }
    
    @Override
    public void getToolOptions(ArrayList<ToolOption<?>> toolOptionList) {
        toolOptionList.add(ToolOptions.FULL_BLOCK_MODE);
        toolOptionList.add(ToolOptions.INTENSITY);
    }
}
