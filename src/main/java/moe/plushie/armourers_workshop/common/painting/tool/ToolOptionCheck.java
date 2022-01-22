package moe.plushie.armourers_workshop.common.painting.tool;

import moe.plushie.armourers_workshop.client.gui.controls.GuiCheckBox;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class ToolOptionCheck extends ToolOption<Boolean> {
    
    public ToolOptionCheck(String key, Boolean defaultValue) {
        super(key, defaultValue);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public int getDisplayWidth() {
        return 180;
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public int getDisplayHeight() {
        return 9;
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public Button getGuiControl(int id, int x, int y, CompoundNBT compound) {
        return new GuiCheckBox(id, x, y, getLocalisedLabel(), (boolean) readFromNBT(compound, defaultValue));
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public void writeGuiControlToNBT(Button button, CompoundNBT compound) {
        writeToNBT(compound, ((GuiCheckBox)button).isChecked());
    }
}
