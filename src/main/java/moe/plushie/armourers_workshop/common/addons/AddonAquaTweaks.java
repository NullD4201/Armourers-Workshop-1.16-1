package moe.plushie.armourers_workshop.common.addons;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.InterModComms;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;

public class AddonAquaTweaks extends ModAddon {

    public AddonAquaTweaks() {
        super("AquaTweaks", "Aqua Tweaks");
    }
    
    @Override
    public void init() {
        CompoundNBT compound = new CompoundNBT();
        
        compound.putString("modid", LibModInfo.ID);
        compound.putString("block", "block.mannequin");
        InterModComms.sendTo(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.putString("modid", LibModInfo.ID);
        compound.putString("block", "block.doll");
        InterModComms.sendTo(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.putString("modid", LibModInfo.ID);
        compound.putString("block", "block.miniArmourer");
        InterModComms.sendTo(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.putString("modid", LibModInfo.ID);
        compound.putString("block", "block.skinnable");
        InterModComms.sendTo(getModId(), "registerAquaConnectable", compound);
    }
}
