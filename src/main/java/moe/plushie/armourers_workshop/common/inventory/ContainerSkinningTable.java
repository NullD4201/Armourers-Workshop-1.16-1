package moe.plushie.armourers_workshop.common.inventory;

import moe.plushie.armourers_workshop.common.crafting.ItemSkinningRecipes;
import moe.plushie.armourers_workshop.common.inventory.slot.SlotInput;
import moe.plushie.armourers_workshop.common.inventory.slot.SlotOutput;
import moe.plushie.armourers_workshop.common.tileentities.TileEntitySkinningTable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSkinningTable extends ModTileContainer<TileEntitySkinningTable> {

    private final IInventory craftingInventory;
    private final IInventory outputInventory;
    
    public ContainerSkinningTable(PlayerInventory invPlayer, TileEntitySkinningTable tileEntity) {
        super(invPlayer, tileEntity);
        craftingInventory = tileEntity.getCraftingInventory();
        outputInventory = tileEntity.getOutputInventory();
        
        addPlayerSlots(8, 94);
        
        addSlotToContainer(new SlotInput(craftingInventory, 0, 37, 22, this));
        addSlotToContainer(new SlotInput(craftingInventory, 1, 37, 58, this));
        addSlotToContainer(new SlotOutput(outputInventory, 0, 119, 40, this));
    }
    
    @Override
    public void onCraftMatrixChanged(IInventory inv) {
        ItemSkinningRecipes.onCraft(craftingInventory);
        super.onCraftMatrixChanged(inv);
    }

    @Override
    protected ItemStack transferStackFromPlayer(PlayerEntity playerIn, int index) {
        Slot slot = getSlot(index);
        if (slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();

            boolean slotted = false;
            for (int i = 36; i < 38; i++) {
                Slot targetSlot = getSlot(i);
                if (this.mergeItemStack(stack, i, i + 1, false)) {
                    slotted = true;
                    break;
                }
            }
            if (!slotted) {
                return ItemStack.EMPTY;
            }

            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(playerIn, stack);

            return result;
        }
        return ItemStack.EMPTY;
    }
}
