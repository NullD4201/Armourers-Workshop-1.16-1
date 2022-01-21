package moe.plushie.armourers_workshop.api.common;

import moe.plushie.armourers_workshop.api.common.skin.data.ISkinDescriptor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface ISkinNBTUtils {
    
    // Item stack.
    public void setSkinDescriptor(ItemStack itemStack, ISkinDescriptor skinDescriptor);
    
    public ISkinDescriptor getSkinDescriptor(ItemStack itemStack);
    
    public void removeSkinDescriptor(ItemStack itemStack);
    
    public boolean hasSkinDescriptor(ItemStack itemStack);
    
    // Tag compound.
    public void setSkinDescriptor(CompoundNBT compound, ISkinDescriptor skinDescriptor);
    
    public ISkinDescriptor getSkinDescriptor(CompoundNBT compound);
    
    public void removeSkinDescriptor(CompoundNBT compound);
    
    public boolean hasSkinDescriptor(CompoundNBT compound);
}
