package moe.plushie.armourers_workshop.client.guidebook;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public interface IBookPage {

    public void renderPage(FontRenderer fontRenderer, int mouseX, int mouseY, boolean turning, int pageNumber);
    
    public void renderRollover(FontRenderer fontRenderer, int mouseX, int mouseY);
}
