package moe.plushie.armourers_workshop.client.model.armourer;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public class ModelFeet extends AgeableModel {
    
    private ModelRenderer legLeft;
    private ModelRenderer legRight;
    
    public ModelFeet() {
        legLeft = new ModelRenderer(this, 0, 16);
        legLeft.addBox(-2, -12, -2, 4, 12, 4);
        legLeft.setRotationPoint(0, 0, 0);

        legRight = new ModelRenderer(this, 0, 16);
        legRight.mirror = true;
        legRight.addBox(-2, -12, -2, 4, 12, 4);
        legRight.setRotationPoint(0, 0, 0);
    }

    public void renderLeftLeft() {
        float mult = 0.0625F;
        legLeft.render(mult);
    }
    
    public void renderRightLeg() {
        float mult = 0.0625F;
        legRight.render(mult);
    }
}
