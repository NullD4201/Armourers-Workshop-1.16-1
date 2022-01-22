package moe.plushie.armourers_workshop.common.tileentities;

import moe.plushie.armourers_workshop.client.gui.globallibrary.GuiGlobalLibrary;
import moe.plushie.armourers_workshop.common.inventory.ContainerGlobalSkinLibrary;
import moe.plushie.armourers_workshop.common.inventory.IGuiFactory;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiButton.IButtonPress;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class TileEntityGlobalSkinLibrary extends TileEntity implements IButtonPress, IGuiFactory {

    public TileEntityGlobalSkinLibrary() {
    }

    @Override
    public void buttonPressed(ServerPlayerEntity player, byte buttonId) {
        if (buttonId == 0) {

        }
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos());
    }

    @Override
    public Container getServerGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new ContainerGlobalSkinLibrary(player.inventory, this);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public Screen getClientGuiElement(PlayerEntity player, World world, BlockPos pos) {
        return new GuiGlobalLibrary(this, player.inventory);
    }
}
