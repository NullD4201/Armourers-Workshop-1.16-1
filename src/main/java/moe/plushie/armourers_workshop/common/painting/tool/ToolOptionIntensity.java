package moe.plushie.armourers_workshop.common.painting.tool;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToolOptionIntensity extends ToolOption<Integer> {
    
    public ToolOptionIntensity(String key, Integer defaultValue) {
        super(key, defaultValue);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getDisplayWidth() {
        return 150;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getDisplayHeight() {
        return 20;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Button getGuiControl(int id, int x, int y, CompoundNBT compound) {
        GuiSlider sliderControl = new GuiSlider(id, x, y, getLocalisedLabel() + " ", 1, 64, (Integer) readFromNBT(compound, defaultValue), null);
        sliderControl.showDecimal = false;
        return sliderControl;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void writeGuiControlToNBT(Button button, CompoundNBT compound) {
        writeToNBT(compound, ((GuiSlider)button).getValueInt());
    }
}
