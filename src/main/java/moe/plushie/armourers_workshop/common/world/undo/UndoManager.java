package moe.plushie.armourers_workshop.common.world.undo;

import java.util.HashMap;

import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 *
 * @author RiskyKen
 *
 */
public final class UndoManager {
    
    private static HashMap<String, PlayerUndoData> playerUndoData = new HashMap<String, PlayerUndoData>();

    public static void begin(PlayerEntity player) {
        if (!playerUndoData.containsKey(player.getName())) {
            playerUndoData.put(player.getName(), new PlayerUndoData(player));
        }
        PlayerUndoData playerData = playerUndoData.get(player.getName());
        playerData.begin();
    }
    
    public static void end(PlayerEntity player) {
        if (!playerUndoData.containsKey(player.getName())) {
            playerUndoData.put(player.getName(), new PlayerUndoData(player));
        }
        PlayerUndoData playerData = playerUndoData.get(player.getName());
        playerData.end();
    }
    
    @Deprecated()
    public static void blockPainted(PlayerEntity player, World world, BlockPos pos, int oldColour, byte oldPaintType, Direction facing) {
        blockPainted(player, world, pos.getX(), pos.getY(), pos.getZ(), oldColour, oldPaintType, facing);
    }
    
    @Deprecated()
    public static void blockPainted(PlayerEntity player, World world, int x, int y, int z, int oldColour, byte oldPaintType, Direction facing) {
        byte[] oldrgb = new byte[3];
        oldrgb[0] = (byte) ((oldColour >> 16) & 0xFF);
        oldrgb[1] = (byte) ((oldColour >> 8) & 0xFF);
        oldrgb[2] = (byte) ((oldColour) & 0xFF);
        blockPainted(player, world, x, y, z, oldrgb, oldPaintType, facing);
    }
    
    public static void blockPainted(PlayerEntity player, World world, int x, int y, int z, byte[] oldrgb, byte oldPaintType, Direction facing) {
        UndoData undoData = new UndoData(x, y, z, world.provider.getDimension(), oldrgb, oldPaintType, facing);
        if (!playerUndoData.containsKey(player.getName())) {
            playerUndoData.put(player.getName(), new PlayerUndoData(player));
        }
        
        PlayerUndoData playerData = playerUndoData.get(player.getName());
        playerData.addUndoData(undoData);
    }
    
    public static void undoPressed(PlayerEntity player) {
        String key = player.getName();
        if (!playerUndoData.containsKey(key)) {
            player.sendMessage(new TranslationTextComponent("chat." + LibModInfo.ID + ":undo.outOfUndos"));
            return;
        }
        PlayerUndoData playerData = playerUndoData.get(key);
        World world = player.getEntityWorld();
        player.sendMessage(new TranslationTextComponent("chat." + LibModInfo.ID + ":undo.undoing"));
        playerData.playerPressedUndo(world);
        if (playerData.getAvalableUndos() < 1) {
            playerUndoData.remove(key);
        }
    }
}
