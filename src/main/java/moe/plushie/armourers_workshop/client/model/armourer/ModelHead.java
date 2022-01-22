package moe.plushie.armourers_workshop.client.model.armourer;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

import net.minecraftforge.fml.LogicalSide;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public class ModelHead extends AgeableModel {
    
    public static final ModelHead MODEL = new ModelHead();

    private ModelRenderer main;
    private ModelRenderer overlay;

    public ModelHead() {
        main = new ModelRenderer(this, 0, 0);
        main.addBox(-4F, -8F, -4F, 8, 8, 8);
        main.setRotationPoint(0, 0, 0);

        overlay = new ModelRenderer(this, 32, 0);
        overlay.addBox(-4F, -8F, -4F, 8, 8, 8, 0.5F);
        overlay.setRotationPoint(0F, 0F, 0F);
        overlay.setTextureSize(64, 32);
    }

    public void render(float scale, boolean showOverlay) {
        main.render(scale);
        if (showOverlay) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            overlay.render(scale);
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }
}
