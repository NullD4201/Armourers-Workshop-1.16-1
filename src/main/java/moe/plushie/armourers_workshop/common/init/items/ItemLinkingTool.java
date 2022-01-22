package moe.plushie.armourers_workshop.common.init.items;

import moe.plushie.armourers_workshop.common.init.blocks.BlockSkinnable;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.tileentities.TileEntitySkinnable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class ItemLinkingTool extends AbstractModItem {

    private static final String TAG_LINK_LOCATION = "linkLocation";
    
    public ItemLinkingTool() {
        super(LibItemNames.LINKING_TOOL);
        setSortPriority(7);
    }
    
    @Override
    public ActionResultType onItemUseFirst(PlayerEntity player, World world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, Hand hand) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (!hasLinkLocation(stack)) {
                if (!(block instanceof BlockSkinnable)) {
                    setLinkLocation(stack, new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                    player.sendMessage(new TranslationTextComponent("chat.armourers_workshop:linkingTool.start", (Object)null));
                    return ActionResultType.SUCCESS;
                } else {
                    player.sendMessage(new TranslationTextComponent("chat.armourers_workshop:linkingTool.linkedToSkinnable", (Object)null));
                    return ActionResultType.FAIL;
                }
            } else {
                BlockPos loc = getLinkLocation(stack);
                if (block instanceof BlockSkinnable) {
                    TileEntity te = world.getTileEntity(pos);
                    if (te != null && te instanceof TileEntitySkinnable) {
                        ((TileEntitySkinnable)te).getParent().setLinkedBlock(loc);
                        player.sendMessage(new TranslationTextComponent("chat.armourers_workshop:linkingTool.finish", (Object)null));
                        removeLinkLocation(stack);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
            removeLinkLocation(stack);
            player.sendMessage(new TranslationTextComponent("chat.armourers_workshop:linkingTool.fail", (Object)null));
        }
        return ActionResultType.PASS;
    }
    
    private void setLinkLocation(ItemStack stack, BlockPos loc) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new CompoundNBT());
        }
        stack.getTagCompound().setIntArray(TAG_LINK_LOCATION, new int[] {loc.getX(), loc.getY(), loc.getZ()});
    }
    
    private void removeLinkLocation(ItemStack stack) {
        if (stack.hasTagCompound()) {
            stack.getTagCompound().removeTag(TAG_LINK_LOCATION);
        }
    }
    
    private boolean hasLinkLocation(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return stack.getTagCompound().hasKey(TAG_LINK_LOCATION, NBT.TAG_INT_ARRAY);
        }
        return false;
    }
    
    private BlockPos getLinkLocation(ItemStack stack) {
        if (hasLinkLocation(stack)) {
            int[] loc = stack.getTagCompound().getIntArray(TAG_LINK_LOCATION);
            return new BlockPos(loc[0], loc[1], loc[2]);
        }
        return new BlockPos(0, 0, 0);
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public void registerModels() {
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (!hasLinkLocation(stack)) {
                    return new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey()), "normal");
                } else {
                    return new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey() + "-link"), "normal");
                }
            }
        });
        ModelBakery.registerItemVariants(this, new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey()), "normal"), new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey() + "-link"), "normal"));
    }
}
