package moe.plushie.armourers_workshop.common.tileentities;

import moe.plushie.armourers_workshop.client.gui.GuiSkinningTable;
import moe.plushie.armourers_workshop.common.crafting.ItemSkinningRecipes;
import moe.plushie.armourers_workshop.common.inventory.ContainerSkinningTable;
import moe.plushie.armourers_workshop.common.inventory.IGuiFactory;
import moe.plushie.armourers_workshop.common.inventory.ModInventory;
import moe.plushie.armourers_workshop.common.inventory.ModInventory.IInventoryCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class TileEntitySkinningTable extends TileEntity implements IInventoryCallback, IGuiFactory {

    private final ModInventory craftingInventory;
    private final ModInventory outputInventory;
    
    public TileEntitySkinningTable() {
        craftingInventory = new ModInventory("skinningTablecrafting", 2, this, this);
        outputInventory = new ModInventory("skinningTableOutput", 1, this, this);
    }
    
    public ModInventory getCraftingInventory() {
        return craftingInventory;
    }
    
    public ModInventory getOutputInventory() {
        return outputInventory;
    }

    @Override
    public void setInventorySlotContents(IInventory inventory, int slotId, ItemStack stack) {
        if (inventory == craftingInventory) {
            checkForValidRecipe();
        }
    }
    
    @Override
    public void dirty() {
        markDirty();
    }
    
    private void checkForValidRecipe() {
        ItemStack stack = ItemSkinningRecipes.getRecipeOutput(craftingInventory);
        outputInventory.setInventorySlotContents(0, stack);
        markDirty();
    }
    
    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);
        craftingInventory.saveItemsToNBT(compound);
        outputInventory.saveItemsToNBT(compound);
        return compound;
    }
    
    @Override
    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
        craftingInventory.loadItemsFromNBT(compound);
        outputInventory.loadItemsFromNBT(compound);
    }

    @Override
    public Container getServerGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new ContainerSkinningTable(player.inventory, this);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public Screen getClientGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new GuiSkinningTable(player.inventory, this);
    }
}
