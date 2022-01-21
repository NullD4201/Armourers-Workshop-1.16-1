package moe.plushie.armourers_workshop.common.init.items.paintingtool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.api.common.painting.IPaintType;
import moe.plushie.armourers_workshop.api.common.painting.IPaintingTool;
import moe.plushie.armourers_workshop.api.common.painting.IPantable;
import moe.plushie.armourers_workshop.api.common.painting.IPantableBlock;
import moe.plushie.armourers_workshop.client.particles.ModParticleManager;
import moe.plushie.armourers_workshop.client.particles.ParticlePaintSplash;
import moe.plushie.armourers_workshop.common.init.blocks.ModBlocks;
import moe.plushie.armourers_workshop.common.init.items.AbstractModItem;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.painting.IBlockPainter;
import moe.plushie.armourers_workshop.common.painting.PaintTypeRegistry;
import moe.plushie.armourers_workshop.common.painting.PaintingHelper;
import moe.plushie.armourers_workshop.common.painting.tool.IConfigurableTool;
import moe.plushie.armourers_workshop.common.painting.tool.ToolOption;
import moe.plushie.armourers_workshop.common.painting.tool.ToolOptions;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import moe.plushie.armourers_workshop.common.world.undo.UndoManager;
import moe.plushie.armourers_workshop.utils.TranslateUtils;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractPaintingTool extends AbstractModItem implements IPaintingTool, IBlockPainter {

    public AbstractPaintingTool(String name) {
        super(name);
        setCreativeTab(ArmourersWorkshop.TAB_PAINTING_TOOLS);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        IPaintType paintType = PaintingHelper.getToolPaintType(stack);
        if (paintType != PaintTypeRegistry.PAINT_TYPE_NORMAL) {
            return true;
        }
        return false;
    }
    
    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        BlockState state = worldIn.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);
        
        if (useOnColourMixer(player, worldIn, pos, stack)) {
            return ActionResultType.SUCCESS;
        }
        
        if (state.getBlock() instanceof IPantableBlock) {
            int newColour = getToolColour(stack);
            
            if (!worldIn.isRemote) {
                UndoManager.begin(player);
            }
            
            onPaint(stack, player, worldIn, pos, state.getBlock(), facing);
            
            if (!worldIn.isRemote) {
                UndoManager.end(player);
                playToolSound(player, worldIn, pos, stack);
            }
            
            return ActionResultType.SUCCESS;
        }
        
        if (useOnArmourer(player, worldIn, pos, stack)) {
            return ActionResultType.SUCCESS;
        }
        
        return ActionResultType.PASS;
    }
    
    public boolean useOnColourMixer(PlayerEntity player, World worldIn, BlockPos pos, ItemStack stack) {
        BlockState state = worldIn.getBlockState(pos);
        if (player.isSneaking() & state.getBlock() == ModBlocks.COLOUR_MIXER) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof IPantable) {
                if (!worldIn.isRemote) {
                    int colour = ((IPantable)te).getColour(0);
                    IPaintType paintType = ((IPantable)te).getPaintType(0);
                    setToolColour(stack, colour);
                    setToolPaintType(stack, paintType);
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean useOnArmourer(PlayerEntity player, World worldIn, BlockPos pos, ItemStack stack) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == ModBlocks.ARMOURER & player.isSneaking()) {
            if (!worldIn.isRemote) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null && te instanceof TileEntityArmourer) {
                    ((TileEntityArmourer)te).toolUsedOnArmourer(this, worldIn, stack, player);
                }
            }
            return true;
        }
        return false;
    }
    
    public void onPaint(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Block block, Direction usedFace) {
        boolean fullBlock = false;
        if (this instanceof IConfigurableTool) {
            ArrayList<ToolOption<?>> toolOptionList = new ArrayList<ToolOption<?>>();
            ((IConfigurableTool)this).getToolOptions(toolOptionList);
            if (toolOptionList.contains(ToolOptions.FULL_BLOCK_MODE)) {
                fullBlock = ToolOptions.FULL_BLOCK_MODE.getValue(stack);
            }
        }
        
        if (fullBlock) {
            for (int i = 0; i < 6; i++) {
                usedOnBlockSide(stack, player, world, pos, block, Direction.VALUES[i], usedFace == Direction.VALUES[i]);
            }
        } else {
            usedOnBlockSide(stack, player, world, pos, block, usedFace, true);
        }
    }
    
    public void playToolSound(PlayerEntity player, World world, BlockPos pos, ItemStack stack) {
    }
    
    @SideOnly(Side.CLIENT)
    protected void spawnPaintParticles (World world, BlockPos pos, Direction facing, int colour) {
        byte[] rtbt = PaintingHelper.intToBytes(colour);
        for (int i = 0; i < 3; i++) {
            ParticlePaintSplash particle = new ParticlePaintSplash(world, pos, rtbt[0], rtbt[1], rtbt[2], facing);
            ModParticleManager.spawnParticle(particle);
        }
        
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        Color c = new Color(PaintingHelper.getToolDisplayColourRGB(stack));
        IPaintType paintType = getToolPaintType(stack);
        String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
        String colourText = TranslateUtils.translate("item.armourers_workshop:rollover.colour", c.getRGB());
        String hexText = TranslateUtils.translate("item.armourers_workshop:rollover.hex", hex);
        String paintText = TranslateUtils.translate("item.armourers_workshop:rollover.paintType", paintType.getLocalizedName());
        tooltip.add(colourText);
        tooltip.add(hexText);
        tooltip.add(paintText);
    }
    
    @SideOnly(Side.CLIENT)
    protected void addOpenSettingsInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.openSettings"));
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (this instanceof IConfigurableTool) {
            if (playerIn.isSneaking()) {
                if (worldIn.isRemote) {
                    playerIn.openGui(ArmourersWorkshop.getInstance(), EnumGuiId.TOOL_OPTIONS.ordinal(), worldIn, 0, 0, 0);
                }
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public boolean getToolHasColour(ItemStack stack) {
        return true;
    }

    @Override
    public int getToolColour(ItemStack stack) {
        return PaintingHelper.getToolPaintColourRGB(stack);
    }

    @Override
    public void setToolColour(ItemStack stack, int colour) {
        PaintingHelper.setToolPaintColour(stack, colour);
    }
    
    @Override
    public void setToolPaintType(ItemStack stack, IPaintType paintType) {
        PaintingHelper.setToolPaint(stack, paintType);
    }
    
    @Override
    public IPaintType getToolPaintType(ItemStack stack) {
        return PaintingHelper.getToolPaintType(stack) ;
    }
}
