package moe.plushie.armourers_workshop.common.skin.type;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.skin.data.ISkinProperties;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinProperty;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.skin.data.SkinProperties;
import net.minecraft.util.ResourceLocation;
// TODO: 2022-01-22 side
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public abstract class AbstractSkinTypeBase implements ISkinType {
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation(LibModInfo.ID, "textures/items/skin/template-" + getName().toLowerCase() + ".png");
    }
    
    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public ResourceLocation getSlotIcon() {
        return new ResourceLocation(LibModInfo.ID, "textures/items/slot/skin-" + getName().toLowerCase() + ".png");
    }
    
    @Override
    public boolean showSkinOverlayCheckbox() {
        return false;
    }
    
    @Override
    public boolean showHelperCheckbox() {
        return false;
    }
    
    @Override
    public int getVanillaArmourSlotId() {
        return -1;
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public boolean enabled() {
        return true;
    }
    
    @Override
    public ArrayList<ISkinProperty<?>> getProperties() {
        ArrayList<ISkinProperty<?>> properties = new ArrayList<ISkinProperty<?>>();
        properties.add(SkinProperties.PROP_ALL_FLAVOUR_TEXT);
        return properties;
    }
    
    @Override
    public boolean haveBoundsChanged(ISkinProperties skinPropsOld, ISkinProperties skinPropsNew) {
        return true;
    }
}
