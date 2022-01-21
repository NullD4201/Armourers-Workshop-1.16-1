package moe.plushie.armourers_workshop.api.common;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface ISkinInventoryContainer {
    
    public void writeToNBT(CompoundNBT compound);
    
    public void readFromNBT(CompoundNBT compound);
    
    public void dropItems(World world, Vec3d pos);

    public void clear();
}
