package moe.plushie.armourers_workshop.common.capability.wardrobe;

import moe.plushie.armourers_workshop.api.common.skin.entity.ISkinnableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WardrobeProvider implements ICapabilitySerializable<CompoundNBT> {

    private final WardrobeCap wardrobeCapability;
    
    public WardrobeProvider(Entity entity, ISkinnableEntity skinnableEntity) {
        this.wardrobeCapability = new WardrobeCap(entity, skinnableEntity);
        if (entity instanceof PlayerEntity) {
            
        } else {
            
        }
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, Direction facing) {
        return capability != null && capability == WardrobeCap.WARDROBE_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, Direction facing) {
        if (hasCapability(capability, facing)) {
            return WardrobeCap.WARDROBE_CAP.cast(wardrobeCapability);
        }
        return null;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) WardrobeCap.WARDROBE_CAP.getStorage().writeNBT(WardrobeCap.WARDROBE_CAP, wardrobeCapability, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        WardrobeCap.WARDROBE_CAP.getStorage().readNBT(WardrobeCap.WARDROBE_CAP, wardrobeCapability, null, nbt);
    }
}
