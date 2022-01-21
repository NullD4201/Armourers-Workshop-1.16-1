package moe.plushie.armourers_workshop.common.capability.wardrobe.player;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.capability.IPlayerWardrobeCap;
import moe.plushie.armourers_workshop.api.common.capability.IWardrobeCap;
import moe.plushie.armourers_workshop.api.common.skin.entity.ISkinnableEntity;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.common.capability.wardrobe.WardrobeCap;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class PlayerWardrobeStorage implements IStorage<IPlayerWardrobeCap> {

    private static final String TAG_ARMOUR_OVERRIDE = "armourOverride";
    private static final String TAG_SLOTS_UNLOCKED = "slots-unlocked-";
    
    @Override
    public NBTBase writeNBT(Capability<IPlayerWardrobeCap> capability, IPlayerWardrobeCap instance, Direction side) {
        IStorage<IWardrobeCap> storage = WardrobeCap.WARDROBE_CAP.getStorage();
        CompoundNBT compound = (CompoundNBT) storage.writeNBT(WardrobeCap.WARDROBE_CAP, instance, side);
        for (int i = 0; i < 4; i++) {
            compound.setBoolean(TAG_ARMOUR_OVERRIDE + i, instance.getArmourOverride(EquipmentSlotType.values()[i + 2]));
        }
        ISkinnableEntity skinnableEntity = instance.getSkinnableEntity();
        ArrayList<ISkinType> skinTypes = new ArrayList<ISkinType>(); 
        skinnableEntity.getValidSkinTypes(skinTypes);
        for (ISkinType skinType : skinTypes) {
            compound.setInteger(TAG_SLOTS_UNLOCKED + skinType.getRegistryName(), instance.getUnlockedSlotsForSkinType(skinType));
        }
        return compound;
    }

    @Override
    public void readNBT(Capability<IPlayerWardrobeCap> capability, IPlayerWardrobeCap instance, Direction side, NBTBase nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        IStorage<IWardrobeCap> storage = WardrobeCap.WARDROBE_CAP.getStorage();
        storage.readNBT(WardrobeCap.WARDROBE_CAP, instance, side, compound);
        for (int i = 0; i < 4; i++) {
            instance.setArmourOverride(EquipmentSlotType.values()[i + 2], compound.getBoolean(TAG_ARMOUR_OVERRIDE + i));
        }
        ISkinnableEntity skinnableEntity = instance.getSkinnableEntity();
        ArrayList<ISkinType> skinTypes = new ArrayList<ISkinType>(); 
        skinnableEntity.getValidSkinTypes(skinTypes);
        for (ISkinType skinType : skinTypes) {
            if (compound.hasKey(TAG_SLOTS_UNLOCKED + skinType.getRegistryName(), NBT.TAG_INT)) {
                instance.setUnlockedSlotsForSkinType(skinType, compound.getInteger(TAG_SLOTS_UNLOCKED + skinType.getRegistryName()));
            }
        }
    }
}
