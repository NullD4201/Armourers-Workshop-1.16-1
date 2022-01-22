package moe.plushie.armourers_workshop.common.network;

import moe.plushie.armourers_workshop.common.init.entities.EntityMannequin;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiAdminPanel;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiArmourerBlockUtil;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiBipedRotations;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiButton;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiColourUpdate;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiLoadSaveArmour;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiSetArmourerSkinProps;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiSetArmourerSkinType;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiSetSkin;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiSkinLibraryCommand;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiToolOptionUpdate;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiUpdateMannequin;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiUpdateTileProperties;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientKeyPress;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientLoadArmour;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientRequestGameProfile;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientRequestSkinData;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientSkinPart;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientToolPaintBlock;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientUpdatePlayerWardrobeCap;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientUpdateWardrobeCap;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerClientCommand;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerGameProfile;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerLibraryFileList;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerLibrarySendSkin;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSendSkinData;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSyncConfig;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSyncPlayerWardrobeCap;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSyncSkinCap;
import moe.plushie.armourers_workshop.common.network.messages.server.MessageServerSyncWardrobeCap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public final class PacketHandler {

    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LibModInfo.CHANNEL);
    private static int packetId = 0;

    public static void init() {
        // Client messages.
        registerMessage(MessageClientUpdateWardrobeCap.class, MessageClientUpdateWardrobeCap.class, LogicalSide.SERVER);
        registerMessage(MessageClientUpdatePlayerWardrobeCap.class, MessageClientUpdatePlayerWardrobeCap.class, LogicalSide.SERVER);

        registerMessage(MessageClientLoadArmour.class, MessageClientLoadArmour.class, LogicalSide.SERVER);
        registerMessage(MessageClientKeyPress.class, MessageClientKeyPress.class, LogicalSide.SERVER);
        registerMessage(MessageClientRequestSkinData.class, MessageClientRequestSkinData.class, LogicalSide.SERVER);
        registerMessage(MessageClientSkinPart.class, MessageClientSkinPart.class, LogicalSide.SERVER);
        registerMessage(MessageClientToolPaintBlock.class, MessageClientToolPaintBlock.class, LogicalSide.SERVER);
        registerMessage(MessageClientRequestGameProfile.class, MessageClientRequestGameProfile.class, LogicalSide.SERVER);

        // Client GUI messages.
        registerMessage(MessageClientGuiLoadSaveArmour.class, MessageClientGuiLoadSaveArmour.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiColourUpdate.class, MessageClientGuiColourUpdate.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiButton.class, MessageClientGuiButton.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiSetSkin.class, MessageClientGuiSetSkin.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiToolOptionUpdate.class, MessageClientGuiToolOptionUpdate.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiSetArmourerSkinProps.class, MessageClientGuiSetArmourerSkinProps.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiBipedRotations.class, MessageClientGuiBipedRotations.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiSetArmourerSkinType.class, MessageClientGuiSetArmourerSkinType.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiUpdateTileProperties.class, MessageClientGuiUpdateTileProperties.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiAdminPanel.class, MessageClientGuiAdminPanel.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiSkinLibraryCommand.class, MessageClientGuiSkinLibraryCommand.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiArmourerBlockUtil.class, MessageClientGuiArmourerBlockUtil.class, LogicalSide.SERVER);
        registerMessage(MessageClientGuiUpdateMannequin.Handler.class, MessageClientGuiUpdateMannequin.class, LogicalSide.SERVER);

        // Server messages.
        registerMessage(MessageServerSyncSkinCap.class, MessageServerSyncSkinCap.class, LogicalSide.CLIENT);
        registerMessage(MessageServerSyncWardrobeCap.class, MessageServerSyncWardrobeCap.class, LogicalSide.CLIENT);
        registerMessage(MessageServerSyncPlayerWardrobeCap.class, MessageServerSyncPlayerWardrobeCap.class, LogicalSide.CLIENT);
        registerMessage(MessageServerLibraryFileList.class, MessageServerLibraryFileList.class, LogicalSide.CLIENT);
        registerMessage(MessageServerSendSkinData.class, MessageServerSendSkinData.class, LogicalSide.CLIENT);
        registerMessage(MessageServerClientCommand.class, MessageServerClientCommand.class, LogicalSide.CLIENT);
        registerMessage(MessageServerLibrarySendSkin.class, MessageServerLibrarySendSkin.class, LogicalSide.CLIENT);
        registerMessage(MessageServerSyncConfig.class, MessageServerSyncConfig.class, LogicalSide.CLIENT);
        registerMessage(MessageServerGameProfile.class, MessageServerGameProfile.class, LogicalSide.CLIENT);
        
        DataSerializers.registerSerializer(EntityMannequin.BIPED_ROTATIONS_SERIALIZER);
        DataSerializers.registerSerializer(EntityMannequin.TEXTURE_DATA_SERIALIZER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, LogicalSide side) {
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId, side);
        packetId++;
    }

    private static class DelayedPacket {
        IMessage message;
        ServerPlayerEntity player;
        int delay;

        public DelayedPacket(IMessage message, ServerPlayerEntity player, int delay) {
            this.message = message;
            this.player = player;
            this.delay = delay;
        }
    }
}
