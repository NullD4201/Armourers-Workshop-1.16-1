package moe.plushie.armourers_workshop.common.addons;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;

public class AddonAquaTweaks extends ModAddon {

    public AddonAquaTweaks() {
        super("AquaTweaks", "Aqua Tweaks");
    }
    
    @Override
    public void init() {
        CompoundNBT compound = new CompoundNBT();
        
        compound.setString("modid", LibModInfo.ID);
        compound.setString("block", "block.mannequin");
        FMLInterModComms.sendMessage(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.setString("modid", LibModInfo.ID);
        compound.setString("block", "block.doll");
        FMLInterModComms.sendMessage(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.setString("modid", LibModInfo.ID);
        compound.setString("block", "block.miniArmourer");
        FMLInterModComms.sendMessage(getModId(), "registerAquaConnectable", compound);
        
        compound = new CompoundNBT();
        compound.setString("modid", LibModInfo.ID);
        compound.setString("block", "block.skinnable");
        FMLInterModComms.sendMessage(getModId(), "registerAquaConnectable", compound);
    }
}
