package moe.plushie.armourers_workshop.common.itemGroup;

import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

//TODO
public class ItemGroupMain extends ItemGroup {
    public ItemGroupMain() {
        super(ItemGroup.getGroupCountSafe(), LibModInfo.ID + "_main");
    }

    @Override
    public ItemStack makeIcon() {
        return null;
    }
}
