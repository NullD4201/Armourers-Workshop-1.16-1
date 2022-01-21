package moe.plushie.armourers_workshop.common.inventory;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiFactory {
    
    public Container getServerGuiElement(PlayerEntity player, World world, BlockPos pos);
    
    @SideOnly(Side.CLIENT)
    public Screen getClientGuiElement(PlayerEntity player, World world, BlockPos pos);
}
