package moe.plushie.armourers_workshop.client.render.entity;

import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.entity.monster.GhastEntity;
import org.lwjgl.opengl.GL11;

import moe.plushie.armourers_workshop.api.common.capability.IEntitySkinCapability;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinDescriptor;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.client.skin.cache.ClientSkinCache;
import moe.plushie.armourers_workshop.common.capability.entityskin.EntitySkinCapability;
import moe.plushie.armourers_workshop.common.skin.data.Skin;
import moe.plushie.armourers_workshop.common.skin.data.SkinProperties;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.client.renderer.entity.model.ModelGhast;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkinLayerRendererGhast extends SkinLayerRenderer<GhastEntity, GhastRenderer> {

    private ModelRenderer body = null;
    private ModelRenderer[] tentacles = null;
    
    public SkinLayerRendererGhast(GhastRenderer renderGhast) {
        super(renderGhast);
        if (renderGhast.getMainModel() instanceof ModelGhast) {
            try {
                body = ReflectionHelper.getPrivateValue(ModelGhast.class, (ModelGhast)renderGhast.getMainModel(), "field_78128_a", "body");
                tentacles = ReflectionHelper.getPrivateValue(ModelGhast.class, (ModelGhast)renderGhast.getMainModel(), "field_78127_b", "tentacles");
                MinecraftForge.EVENT_BUS.register(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @SubscribeEvent()
    public void onRenderLivingPre(RenderLivingEvent.Pre<GhastEntity> event) {
        IEntitySkinCapability skinCapability = EntitySkinCapability.get(event.getEntity());
        if (skinCapability == null) {
            return;
        }
        ISkinType skinType = SkinTypeRegistry.skinHead;
        // Hide parts of the model.
        for (int i = 0; i < skinCapability.getSlotCountForSkinType(skinType); i++) {
            ISkinDescriptor skinDescriptor = skinCapability.getSkinDescriptor(skinType, i);
            if (skinDescriptor != null) {
                Skin skin = ClientSkinCache.INSTANCE.getSkin(skinDescriptor, false);
                if (skin == null) {
                    continue;
                }
                if (SkinProperties.PROP_MODEL_OVERRIDE_HEAD.getValue(skin.getProperties())) {
                    body.isHidden = true;
                    for (ModelRenderer tentacle : tentacles) {
                        tentacle.isHidden = true;
                    }
                    return;
                }
            }
        }
    }
    
    @SubscribeEvent()
    public void onRenderLivingPost(RenderLivingEvent.Post<GhastEntity> event) {
        body.isHidden = false;
        for (ModelRenderer tentacle : tentacles) {
            tentacle.isHidden = false;
        }
    }
    
    @Override
    protected void setRotTranForPartType(GhastEntity entitylivingbaseIn, ISkinType skinType, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glTranslated(0, 25.65 * scale, 0);
        float headScale = 2.0001F;
        GL11.glScalef(headScale, headScale, headScale);
    }
}
