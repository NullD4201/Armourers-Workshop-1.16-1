package moe.plushie.armourers_workshop.common.network.messages.client;

import io.netty.buffer.ByteBuf;
import moe.plushie.armourers_workshop.client.texture.PlayerTexture;
import moe.plushie.armourers_workshop.common.inventory.ContainerArmourer;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageClientGuiSetSkin implements IMessage, IMessageHandler<MessageClientGuiSetSkin, IMessage> {

    PlayerTexture playerTexture;
    
    public MessageClientGuiSetSkin() {
    }
    
    public MessageClientGuiSetSkin(PlayerTexture playerTexture) {
        this.playerTexture = playerTexture;
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        CompoundNBT compound = new CompoundNBT();
        playerTexture.writeToNBT(compound);
        ByteBufUtils.writeTag(buf, compound);
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        CompoundNBT compound = ByteBufUtils.readTag(buf);
        playerTexture = PlayerTexture.fromNBT(compound);
    }
    
    @Override
    public IMessage onMessage(MessageClientGuiSetSkin message, MessageContext ctx) {
        ServerPlayerEntity player = ctx.getServerHandler().player;
        if (player == null) { return null; }
        Container container = player.openContainer;
        if (container == null) { return null; }
        
        if (container != null && container instanceof ContainerArmourer) {
            TileEntityArmourer armourerBrain = ((ContainerArmourer) container).getTileEntity();
            armourerBrain.setTexture(message.playerTexture);
        }
        return null;
    }
}
