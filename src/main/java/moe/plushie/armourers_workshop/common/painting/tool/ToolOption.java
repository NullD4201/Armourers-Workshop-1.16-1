package moe.plushie.armourers_workshop.common.painting.tool;

import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public abstract class ToolOption<T> {
    
    private final static String TAG_TOOL_OPTIONS = "toolOptions";
    
    protected final String key;
    protected final T defaultValue;
    
    public ToolOption(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
    
    public T getDefaultValue() {
        return defaultValue;
    }
    
    public T getValue(ItemStack stack) {
        return (T) readFromNBT(stack, defaultValue);
    }
    
    public void setValue(ItemStack stack, T value) {
        writeToNBT(stack, value);
    }
    
    public String getOptionName() {
        return key;
    }
    
    public String getUnlocalisedlabel() {
        return "tooloption." + LibModInfo.ID.toLowerCase() + ":" + getOptionName();
    }
    
    public String getLocalisedLabel() {
        return I18n.format(getUnlocalisedlabel());
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    public abstract int getDisplayWidth();
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    public abstract int getDisplayHeight();
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    public abstract Button getGuiControl(int id, int x, int y, CompoundNBT compound);
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    public abstract void writeGuiControlToNBT(Button button, CompoundNBT compound);
    
    public void writeToNBT(ItemStack stack, T value) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new CompoundNBT());
        }
        writeToNBT(stack.getTagCompound(), value);
    }
    
    protected void writeToNBT(CompoundNBT compound, T value) {
        if (!compound.hasKey(TAG_TOOL_OPTIONS, NBT.TAG_COMPOUND)) {
            compound.setTag(TAG_TOOL_OPTIONS, new CompoundNBT());
        }
        CompoundNBT optionsCompund = compound.getCompoundTag(TAG_TOOL_OPTIONS);
        
        if (value instanceof String) {
            optionsCompund.setString(key, (String) value);
        }
        if (value instanceof Integer) {
            optionsCompund.setInteger(key, (Integer) value);
        }
        if (value instanceof Double) {
            optionsCompund.setDouble(key, (Double) value);
        }
        if (value instanceof Boolean) {
            optionsCompund.setBoolean(key, (Boolean) value);
        }
    }
    
    protected Object readFromNBT(ItemStack stack, T defaultValue) {
        return readFromNBT(stack.getTagCompound(), defaultValue);
    }
    
    protected Object readFromNBT(CompoundNBT compound, T defaultValue) {
        if (compound == null) {
            return defaultValue;
        }
        if (!compound.hasKey(TAG_TOOL_OPTIONS, NBT.TAG_COMPOUND)) {
            return defaultValue;
        }
        CompoundNBT optionsCompund = compound.getCompoundTag(TAG_TOOL_OPTIONS);
        if (defaultValue instanceof String) {
            return optionsCompund.getString(key);
        }
        if (defaultValue instanceof Integer) {
            return optionsCompund.getInteger(key);
        }
        if (defaultValue instanceof Double) {
            return optionsCompund.getDouble(key);
        }
        if (defaultValue instanceof Boolean) {
            return optionsCompund.getBoolean(key);
        }
        return defaultValue;
    }
}
