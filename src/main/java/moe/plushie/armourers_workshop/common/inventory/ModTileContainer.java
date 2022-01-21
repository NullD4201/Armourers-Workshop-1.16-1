package moe.plushie.armourers_workshop.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;

public abstract class ModTileContainer<TILETYPE extends TileEntity> extends ModContainer {

    protected final TILETYPE tileEntity;
    
    public ModTileContainer(PlayerInventory invPlayer, TILETYPE tileEntity) {
        super(invPlayer);
        this.tileEntity = tileEntity;
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return !playerIn.isDead & playerIn.getDistanceSq(tileEntity.getPos()) <= 64;
    }
    
    public TILETYPE getTileEntity() {
        return tileEntity;
    }
}
