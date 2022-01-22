package moe.plushie.armourers_workshop.common.skin.entity;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.client.render.entity.SkinLayerRendererGhast;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class SkinnableEntityGhast extends SkinnableEntity {

    @Override
    public Class<? extends LivingEntity> getEntityClass() {
        return GhastEntity.class;
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    @Override
    public LayerRenderer<? extends LivingEntity> getLayerRenderer(LivingRenderer renderLivingBase) {
        if (renderLivingBase instanceof GhastRenderer) {
            return new SkinLayerRendererGhast((GhastRenderer) renderLivingBase);
        }
        return null;
    }

    @Override
    public void getValidSkinTypes(ArrayList<ISkinType> skinTypes) {
        skinTypes.add(SkinTypeRegistry.skinHead);
    }

    @Override
    public int getSlotsForSkinType(ISkinType skinType) {
        return 1;
    }
}
