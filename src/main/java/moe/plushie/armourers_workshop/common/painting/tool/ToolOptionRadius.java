package moe.plushie.armourers_workshop.common.painting.tool;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.client.config.GuiSlider;

public class ToolOptionRadius extends ToolOption<Integer> {
    
    public ToolOptionRadius(String key, Integer defaultValue) {
        super(key, defaultValue);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public int getDisplayWidth() {
        return 150;
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public int getDisplayHeight() {
        return 20;
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public Button getGuiControl(int id, int x, int y, CompoundNBT compound) {
        GuiSlider sliderControl = new GuiSlider(id, x, y, getLocalisedLabel() + " ", 1, 6, (Integer) readFromNBT(compound, defaultValue), null);
        sliderControl.showDecimal = false;
        return sliderControl;
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public void writeGuiControlToNBT(Button button, CompoundNBT compound) {
        writeToNBT(compound, ((GuiSlider)button).getValueInt());
    }
}
