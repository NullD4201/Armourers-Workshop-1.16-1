package moe.plushie.armourers_workshop.common.skin.entity;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.client.render.entity.SkinLayerRendererChicken;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkinnableEntityChicken extends SkinnableEntity {

    @Override
    public Class<? extends LivingEntity> getEntityClass() {
        return ChickenEntity.class;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public LayerRenderer<? extends LivingEntity> getLayerRenderer(LivingRenderer renderLivingBase) {
        if (renderLivingBase instanceof ChickenRenderer) {
            return new SkinLayerRendererChicken((ChickenRenderer) renderLivingBase);
        }
        return null;
    }

    @Override
    public void getValidSkinTypes(ArrayList<ISkinType> skinTypes) {
        skinTypes.add(SkinTypeRegistry.skinHead);
    }

    @Override
    public int getSlotsForSkinType(ISkinType skinType) {
        return 2;
    }
}
