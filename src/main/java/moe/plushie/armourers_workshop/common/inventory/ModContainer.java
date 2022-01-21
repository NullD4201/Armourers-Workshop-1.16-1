package moe.plushie.armourers_workshop.common.inventory;

import javax.annotation.Nonnull;

import moe.plushie.armourers_workshop.common.inventory.slot.SlotHidable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ModContainer extends Container {

    protected final PlayerInventory invPlayer;
    
    private int playerInvStartIndex;
    private int playerInvEndIndex;
    
    public ModContainer(PlayerInventory invPlayer) {
        this.invPlayer = invPlayer;
    }
    
    protected void addPlayerSlots(int posX, int posY) {
        playerInvStartIndex = inventorySlots.size();
        int playerInvY = posY;
        int hotBarY = playerInvY + 58;
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new SlotHidable(invPlayer, x, posX + 18 * x, hotBarY));
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new SlotHidable(invPlayer, x + y * 9 + 9, posX + 18 * x, playerInvY + y * 18));
            }
        }
        playerInvEndIndex  = inventorySlots.size();
    }
    
    public int getPlayerInvStartIndex() {
        return playerInvStartIndex;
    }
    
    public int getPlayerInvEndIndex() {
        return playerInvEndIndex;
    }
    
    public boolean isSlotPlayerInv(int index) {
        return index >= playerInvStartIndex & index < playerInvEndIndex;
    }
    
    protected boolean canSlotHoldItem(int slotIndex, ItemStack itemStack) {
        Slot slot = getSlot(slotIndex);
        return canSlotHoldItem(slot, itemStack);
    }
    
    protected boolean canSlotHoldItem(Slot slot, ItemStack itemStack) {
        return slot.isEnabled() & slot.isItemValid(itemStack);
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        if (!isSlotPlayerInv(index)) {
            Slot slot = getSlot(index);
            if (slot.getHasStack()) {
                ItemStack stack = slot.getStack();
                ItemStack result = stack.copy();
                // Moving from tile entity to player.
                if (!this.mergeItemStack(stack, playerInvStartIndex + 9, playerInvEndIndex, false)) {
                    if (!this.mergeItemStack(stack, playerInvStartIndex, playerInvStartIndex + 9, false)) {
                        return ItemStack.EMPTY;
                    }
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
        } else {
            return transferStackFromPlayer(playerIn, index);
        }
    }
    
    @Nonnull
    protected ItemStack transferStackFromPlayer(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return !playerIn.isDead;
    }
}
