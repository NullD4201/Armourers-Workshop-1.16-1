package moe.plushie.armourers_workshop.common.capability.entityskin;

import moe.plushie.armourers_workshop.api.common.capability.IEntitySkinCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EntitySkinStorage implements IStorage<IEntitySkinCapability> {

    @Override
    public NBTBase writeNBT(Capability<IEntitySkinCapability> capability, IEntitySkinCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        instance.getSkinInventoryContainer().writeToNBT(compound);
        return compound;
    }

    @Override
    public void readNBT(Capability<IEntitySkinCapability> capability, IEntitySkinCapability instance, Direction side, NBTBase nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        instance.getSkinInventoryContainer().readFromNBT(compound);
    }
}
