package moe.plushie.armourers_workshop.common.capability.wardrobe.player;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

import moe.plushie.armourers_workshop.api.common.capability.IPlayerWardrobeCap;
import moe.plushie.armourers_workshop.api.common.skin.entity.ISkinnableEntity;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.common.capability.wardrobe.WardrobeCap;
import moe.plushie.armourers_workshop.common.config.ConfigHandler;
import moe.plushie.armourers_workshop.common.network.PacketHandler;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientUpdatePlayerWardrobeCap;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSyncPlayerWardrobeCap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.EquipmentSlotType.Group;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PlayerWardrobeCap extends WardrobeCap implements IPlayerWardrobeCap {

    @CapabilityInject(IPlayerWardrobeCap.class)
    public static final Capability<IPlayerWardrobeCap> PLAYER_WARDROBE_CAP = null;
    
    /** Bit set of what armour is hidden on the player. */
    private BitSet armourOverride;
    
    /** Number of slots the player has unlocked in the wardrobe */
    private HashMap<String, Integer> slotsUnlocked;
    
    public PlayerWardrobeCap(PlayerEntity entity, ISkinnableEntity skinnableEntity) {
        super(entity, skinnableEntity);
        armourOverride = new BitSet(4);
        ArrayList<ISkinType> validSkinTypes = new ArrayList<ISkinType>();
        skinnableEntity.getValidSkinTypes(validSkinTypes);
        slotsUnlocked = new HashMap<String, Integer>();
        for (int i = 0; i < validSkinTypes.size(); i++) {
            ISkinType skinType = validSkinTypes.get(i);
            slotsUnlocked.put(skinType.getRegistryName(), getUnlockedSlotsForSkinType(skinType));
        }
    }

    @Override
    public boolean getArmourOverride(EquipmentSlotType equipmentSlot) {
        if (equipmentSlot.getSlotType() == Group.ARMOR) {
            return armourOverride.get(equipmentSlot.getSlotIndex());
        }
        return false;
    }
    
    @Override
    public void setArmourOverride(EquipmentSlotType equipmentSlot, boolean override) {
        if (equipmentSlot.getSlotType() == Group.ARMOR) {
            armourOverride.set(equipmentSlot.getSlotIndex(), override);
        }
    }
    
    @Override
    public int getUnlockedSlotsForSkinType(ISkinType skinType) {
        if (slotsUnlocked.containsKey(skinType.getRegistryName())) {
            return slotsUnlocked.get(skinType.getRegistryName());
        } else {
            return Math.min(ConfigHandler.wardrobeStartingSlots, getMaxSlotsForSkinType(skinType));
        }
    }
    
    @Override
    public void setUnlockedSlotsForSkinType(ISkinType skinType, int value) {
        slotsUnlocked.put(skinType.getRegistryName(), MathHelper.clamp(value, 0, getMaxSlotsForSkinType(skinType)));
    }
    
    @Override
    public int getMaxSlotsForSkinType(ISkinType skinType) {
        return getSkinnableEntity().getSlotsForSkinType(skinType);
    }
    
    public static IPlayerWardrobeCap get(PlayerEntity entity) {
        return entity.getCapability(PLAYER_WARDROBE_CAP, null);
    }
    
    @Override
    protected IMessage getUpdateMessage() {
        CompoundNBT compound = (CompoundNBT)PLAYER_WARDROBE_CAP.getStorage().writeNBT(PLAYER_WARDROBE_CAP, this, null);
        return new MessageServerSyncPlayerWardrobeCap(entity.getEntityId(), compound);
    }
    
    @Override
    public void sendUpdateToServer() {
        CompoundNBT compound = (CompoundNBT)PLAYER_WARDROBE_CAP.getStorage().writeNBT(PLAYER_WARDROBE_CAP, this, null);
        MessageClientUpdatePlayerWardrobeCap message = new MessageClientUpdatePlayerWardrobeCap(compound);
        PacketHandler.networkWrapper.sendToServer(message);
    }
}
