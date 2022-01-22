package moe.plushie.armourers_workshop.client.guidebook;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public interface IBook {
    
    public String getUnlocalizedName();
    
    public int getNumberOfChapters();
    
    public void addChapter(IBookChapter chapter);
    
    public IBookChapter getChapterNumber(int chapterNumber);
    
    public IBookChapter getChapterFromPageNumber(int pageNumber);
    
    public int getChapterIndexFromPageNumber(int pageNumber);
    
    public int getTotalNumberOfPages();
    
    public IBookPage getPageNumber(int pageNumber);
    
    public boolean isFirstPage(int pageNumber);
    
    public boolean isLastPage(int pageNumber);
}
