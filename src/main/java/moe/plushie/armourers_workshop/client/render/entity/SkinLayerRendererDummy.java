package moe.plushie.armourers_workshop.client.render.entity;

import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;

public class SkinLayerRendererDummy extends SkinLayerRenderer<LivingEntity, LivingRenderer> {

    public SkinLayerRendererDummy(LivingRenderer<LivingEntity> renderLivingBase) {
        super(renderLivingBase);
    }
    
    @Override
    protected void setRotTranForPartType(LivingEntity entitylivingbaseIn, ISkinType skinType, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    }
}
