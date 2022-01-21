package moe.plushie.armourers_workshop.common.library;

import java.util.ArrayList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface ILibraryManager {
    
    public void reloadLibrary();
    
    public void reloadLibrary(ILibraryCallback callback);
    
    public LibraryFileList getClientPublicFileList();
    
    public LibraryFileList getServerPublicFileList();
    
    public LibraryFileList getServerPrivateFileList(PlayerEntity player);
    
    public void setFileList(ArrayList<LibraryFile> fileList, LibraryFileType listType);
    
    public void addFileToListType(LibraryFile file, LibraryFileType listType, PlayerEntity player);
    
    public void removeFileFromListType(LibraryFile file, LibraryFileType listType, PlayerEntity player);
    
    public void syncLibraryWithPlayer(ServerPlayerEntity player);
}
