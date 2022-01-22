package moe.plushie.armourers_workshop.common.init.items;

import java.util.List;

import moe.plushie.armourers_workshop.common.data.type.BipedRotations;
import moe.plushie.armourers_workshop.common.init.entities.EntityMannequin;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.utils.NBTHelper;
import moe.plushie.armourers_workshop.utils.TranslateUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemMannequinTool extends AbstractModItem {

    private static final String TAG_ROTATION_DATA = "rotationData";

    public ItemMannequinTool() {
        super(LibItemNames.MANNEQUIN_TOOL);
        setSortPriority(10);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onInteractEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() == this & event.getTarget() instanceof EntityMannequin) {
            EntityMannequin mannequin;
            event.setCancellationResult(ActionResultType.SUCCESS);
        }
    }

    private BipedRotations getRotationDataFromStack(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return null;
        }
        CompoundNBT compound = stack.getTagCompound();
        if (!compound.hasKey(TAG_ROTATION_DATA)) {
            return null;
        }
        CompoundNBT rotationCompound = compound.getCompoundTag(TAG_ROTATION_DATA);
        BipedRotations bipedRotations = new BipedRotations();
        bipedRotations.loadNBTData(rotationCompound);
        return bipedRotations;
    }

    private void setRotationDataOnStack(ItemStack stack, BipedRotations bipedRotations) {
        CompoundNBT compound = NBTHelper.getNBTForStack(stack);
        CompoundNBT rotationCompound = new CompoundNBT();
        bipedRotations.saveNBTData(rotationCompound);
        compound.setTag(TAG_ROTATION_DATA, rotationCompound);
        stack.setTagCompound(compound);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_ROTATION_DATA, NBT.TAG_COMPOUND)) {
            tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.settingsSaved"));
        } else {
            tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.noSettingsSaved"));
        }
    }
}
