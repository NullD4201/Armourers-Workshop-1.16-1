package moe.plushie.armourers_workshop.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public class RenderSpectralArrowSkinned extends RenderSkinnedArrow<SpectralArrowEntity> {

    public static final ResourceLocation RES_SPECTRAL_ARROW = new ResourceLocation("textures/entity/projectiles/spectral_arrow.png");

    public RenderSpectralArrowSkinned(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(SpectralArrowEntity entity) {
        return RES_SPECTRAL_ARROW;
    }
}
