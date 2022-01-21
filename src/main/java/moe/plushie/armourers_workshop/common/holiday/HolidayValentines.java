package moe.plushie.armourers_workshop.common.holiday;

import moe.plushie.armourers_workshop.common.init.items.ItemGiftSack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class HolidayValentines extends Holiday {

    public HolidayValentines(String name, int dayOfMonth, int month, int lengthInDays, int lengthInHours) {
        super(name, dayOfMonth, month, lengthInDays, lengthInHours);
    }
    
    @Override
    public ItemStack getGiftSack() {
        return ItemGiftSack.createStack(0xE5A2E5, 0x961596, this);
    }
    
    @Override
    public ItemStack getGift(PlayerEntity player) {
        return new ItemStack(Items.CAKE);
    }
}
