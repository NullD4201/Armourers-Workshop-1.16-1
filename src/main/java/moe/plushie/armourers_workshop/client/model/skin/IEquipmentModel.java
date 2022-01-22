package moe.plushie.armourers_workshop.client.model.skin;

import moe.plushie.armourers_workshop.api.common.IExtraColours;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinDye;
import moe.plushie.armourers_workshop.client.render.SkinRenderData;
import moe.plushie.armourers_workshop.common.skin.data.Skin;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public interface IEquipmentModel {
    
    public void render(Entity entity, Skin skin, float limb1, float limb2, float limb3, float headY, float headX);
    
    public void render(Entity entity, Skin skin, BipedModel BipedModel, boolean showSkinPaint, ISkinDye skinDye, IExtraColours extraColours, boolean itemRender, double distance, boolean doLodLoading);
    
    public void render(Entity entity, Skin skin, BipedModel BipedModel, SkinRenderData renderData);
}
