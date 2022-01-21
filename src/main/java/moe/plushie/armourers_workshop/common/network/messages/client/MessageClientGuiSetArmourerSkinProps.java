package moe.plushie.armourers_workshop.common.network.messages.client;

import io.netty.buffer.ByteBuf;
import moe.plushie.armourers_workshop.common.inventory.ContainerArmourer;
import moe.plushie.armourers_workshop.common.skin.data.SkinProperties;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageClientGuiSetArmourerSkinProps implements IMessage, IMessageHandler<MessageClientGuiSetArmourerSkinProps, IMessage> {

    SkinProperties skinProps;

    public MessageClientGuiSetArmourerSkinProps() {
    }
    
    public MessageClientGuiSetArmourerSkinProps(SkinProperties skinProps) {
        this.skinProps = skinProps;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        CompoundNBT compound = ByteBufUtils.readTag(buf);
        skinProps = new SkinProperties();
        skinProps.readFromNBT(compound);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        CompoundNBT compound = new CompoundNBT();
        skinProps.writeToNBT(compound);
        ByteBufUtils.writeTag(buf, compound);
    }
    
    @Override
    public IMessage onMessage(MessageClientGuiSetArmourerSkinProps message, MessageContext ctx) {
        ServerPlayerEntity player = ctx.getServerHandler().player;
        if (player == null) { return null; }
        Container container = player.openContainer;
        
        if (container != null && container instanceof ContainerArmourer) {
            TileEntityArmourer te = ((ContainerArmourer)container).getTileEntity();
            te.setSkinProps(message.skinProps);
        }
        return null;
    }
}
