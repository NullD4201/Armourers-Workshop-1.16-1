package moe.plushie.armourers_workshop.common.skin.entity;

import moe.plushie.armourers_workshop.api.common.skin.entity.ISkinnableEntity;
import moe.plushie.armourers_workshop.client.render.entity.SkinLayerRendererDummy;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class SkinnableEntity implements ISkinnableEntity {
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addRenderLayer(EntityRendererManager renderManager) {
        EntityRenderer<LivingEntity> renderer = renderManager.getEntityClassRenderObject(getEntityClass());
        if (renderer != null && renderer instanceof LivingRenderer) {
            LayerRenderer<? extends LivingEntity> layerRenderer = getLayerRenderer((LivingRenderer) renderer);
            if (layerRenderer != null) {
                ((LivingRenderer<?>) renderer).addLayer(layerRenderer);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public LayerRenderer<? extends LivingEntity> getLayerRenderer(LivingRenderer renderLivingBase) {
        return new SkinLayerRendererDummy(renderLivingBase);
    }
    
    @Override
    public boolean canUseWandOfStyle(PlayerEntity user) {
        return true;
    }

    @Override
    public boolean canUseSkinsOnEntity() {
        return false;
    }
}
