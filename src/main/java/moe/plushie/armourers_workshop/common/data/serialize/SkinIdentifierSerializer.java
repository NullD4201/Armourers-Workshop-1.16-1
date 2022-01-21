package moe.plushie.armourers_workshop.common.data.serialize;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import moe.plushie.armourers_workshop.api.common.library.ILibraryFile;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinIdentifier;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.common.library.LibraryFile;
import moe.plushie.armourers_workshop.common.skin.data.SkinIdentifier;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.common.util.Constants.NBT;

public class SkinIdentifierSerializer {
    
    private static final String TAG_SKIN_ID_DATA = "identifier";
    private static final String TAG_SKIN_LOCAL_ID = "localId";
    private static final String TAG_SKIN_LIBRARY_FILE = "libraryFile";
    private static final String TAG_SKIN_GLOBAL_ID = "globalId";
    private static final String TAG_SKIN_TYPE = "skinType";
    
    private static final String TAG_SKIN_OLD_ID = "skinId";
    
    public static void writeToCompound(ISkinIdentifier skinIdentifier, CompoundNBT compound) {
        
        CompoundNBT idDataCompound = new CompoundNBT();
        idDataCompound.setInteger(TAG_SKIN_LOCAL_ID, skinIdentifier.getSkinLocalId());
        if (skinIdentifier.getSkinLibraryFile() != null) {
            idDataCompound.setString(TAG_SKIN_LIBRARY_FILE, skinIdentifier.getSkinLibraryFile().getFullName());
        }
        idDataCompound.setInteger(TAG_SKIN_GLOBAL_ID, skinIdentifier.getSkinGlobalId());
        if (skinIdentifier.getSkinType() != null) {
            idDataCompound.setString(TAG_SKIN_TYPE, skinIdentifier.getSkinType().getRegistryName());
        }
        compound.setTag(TAG_SKIN_ID_DATA, idDataCompound);
    }
    
    public static SkinIdentifier readFromCompound(CompoundNBT compound) {
        int localId = 0;
        ILibraryFile libraryFile = null;
        int globalId = 0;
        ISkinType skinType = null;
        
        CompoundNBT idDataCompound = compound.getCompoundTag(TAG_SKIN_ID_DATA);
        localId = idDataCompound.getInteger(TAG_SKIN_LOCAL_ID);
        if (idDataCompound.hasKey(TAG_SKIN_LIBRARY_FILE, NBT.TAG_STRING)) {
            libraryFile = new LibraryFile(idDataCompound.getString(TAG_SKIN_LIBRARY_FILE));
        }
        globalId = idDataCompound.getInteger(TAG_SKIN_GLOBAL_ID);
        skinType = SkinTypeRegistry.INSTANCE.getSkinTypeFromRegistryName(idDataCompound.getString(TAG_SKIN_TYPE));
        
        if (compound.hasKey(TAG_SKIN_OLD_ID, NBT.TAG_INT)) {
            localId = compound.getInteger(TAG_SKIN_OLD_ID);
        }
        if (compound.hasKey(TAG_SKIN_TYPE, NBT.TAG_STRING)) {
            skinType = SkinTypeRegistry.INSTANCE.getSkinTypeFromRegistryName(compound.getString(TAG_SKIN_TYPE));
        }
        
        return new SkinIdentifier(localId, libraryFile, globalId, skinType);
    }
    
    public static void writeToStream (ISkinIdentifier skinIdentifier, DataOutputStream stream) throws IOException {
        CompoundNBT compound = new CompoundNBT();
        writeToCompound(skinIdentifier, compound);
        CompressedStreamTools.writeCompressed(compound, stream);
    }
    
    public static SkinIdentifier readFromStream (DataInputStream stream) throws IOException {
        CompoundNBT compound = CompressedStreamTools.readCompressed(stream);
        return readFromCompound(compound);
    }
    
    public static void writeToByteBuf (ISkinIdentifier skinIdentifier, ByteBuf buf) {
        CompoundNBT compound = new CompoundNBT();
        writeToCompound(skinIdentifier, compound);
        ByteBufUtils.writeTag(buf, compound);
    }
    
    public static SkinIdentifier readFromByteBuf (ByteBuf buf) {
        CompoundNBT compound = ByteBufUtils.readTag(buf);
        return readFromCompound(compound);
    }
}
