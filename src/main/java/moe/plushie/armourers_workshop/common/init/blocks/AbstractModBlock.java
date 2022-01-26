package moe.plushie.armourers_workshop.common.init.blocks;

import moe.plushie.armourers_workshop.client.model.ICustomModel;
import moe.plushie.armourers_workshop.common.itemGroup.ISortOrder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;

//TODO
public abstract class AbstractModBlock extends Block implements ISortOrder, ICustomModel {
    public ItemGroup itemGroup;
    public AbstractModBlock(String name) {
        super(Properties.of(Material.METAL));

    }
}
