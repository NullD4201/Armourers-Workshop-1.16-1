package moe.plushie.armourers_workshop.common.capability.wardrobe.player;

import moe.plushie.armourers_workshop.api.common.skin.entity.ISkinnableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerWardrobeProvider implements ICapabilitySerializable<CompoundNBT> {

    private final PlayerWardrobeCap wardrobeCapability;
    
    public PlayerWardrobeProvider(PlayerEntity entityPlayer, ISkinnableEntity skinnableEntity) {
        this.wardrobeCapability = new PlayerWardrobeCap(entityPlayer, skinnableEntity);
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, Direction facing) {
        return capability != null && capability == PlayerWardrobeCap.PLAYER_WARDROBE_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, Direction facing) {
        if (hasCapability(capability, facing)) {
            return PlayerWardrobeCap.PLAYER_WARDROBE_CAP.cast(wardrobeCapability);
        }
        return null;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) PlayerWardrobeCap.PLAYER_WARDROBE_CAP.getStorage().writeNBT(PlayerWardrobeCap.PLAYER_WARDROBE_CAP, wardrobeCapability, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        PlayerWardrobeCap.PLAYER_WARDROBE_CAP.getStorage().readNBT(PlayerWardrobeCap.PLAYER_WARDROBE_CAP, wardrobeCapability, null, nbt);
    }
}
