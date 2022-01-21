package moe.plushie.armourers_workshop.api.common;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d; // https://gist.github.com/gigaherz/2dfa77c6efc7d1248ef88ec1920c0a93 "1.15.2 -> 1.16.1 Migration Mappings.gist"
import net.minecraft.world.World;

public interface ISkinInventoryContainer {
    
    public void writeToNBT(CompoundNBT compound);
    
    public void readFromNBT(CompoundNBT compound);
    
    public void dropItems(World world, Vector3d pos);

    public void clear();
}
