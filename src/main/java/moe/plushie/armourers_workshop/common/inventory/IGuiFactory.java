package moe.plushie.armourers_workshop.common.inventory;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public interface IGuiFactory {
    
    public Container getServerGuiElement(PlayerEntity player, World world, BlockPos pos);
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    public Screen getClientGuiElement(PlayerEntity player, World world, BlockPos pos);
}
