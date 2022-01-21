package moe.plushie.armourers_workshop.common.tileentities;

import moe.plushie.armourers_workshop.client.gui.GuiDyeTable;
import moe.plushie.armourers_workshop.common.inventory.ContainerDyeTable;
import moe.plushie.armourers_workshop.common.inventory.IGuiFactory;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDyeTable extends AbstractTileEntityInventory implements IGuiFactory {
    
    private static final int INVENTORY_SIZE = 10;
    
    public TileEntityDyeTable() {
        super(INVENTORY_SIZE);
    }
    
    @Override
    public String getName() {
        return LibBlockNames.DYE_TABLE;
    }
    
    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);
        return compound;
    }
    
    @Override
    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
    }

    @Override
    public Container getServerGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new ContainerDyeTable(player.inventory, this);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Screen getClientGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new GuiDyeTable(player.inventory, this);
    }
}
