package moe.plushie.armourers_workshop.client.model.armourer;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

@LogicalSidedProvider(LogicalSide.CLIENT)
public class ModelLegs extends AgeableModel {

    public static final ModelLegs MODEL = new ModelLegs();
    
    private ModelRenderer legLeft;
    private ModelRenderer legRight;

    public ModelLegs() {
        legLeft = new ModelRenderer(this, 0, 16);
        legLeft.mirror = true;
        legLeft.addBox(-2, -12, -2, 4, 12, 4);
        legLeft.setRotationPoint(0, 0, 0);

        legRight = new ModelRenderer(this, 0, 16);
        legRight.addBox(-2, -12, -2, 4, 12, 4);
        legRight.setRotationPoint(0, 0, 0);
    }

    public void renderLeftLeft(float scale) {
        legLeft.render(scale);
    }
    
    public void renderRightLeg(float scale) {
        legRight.render(scale);
    }
}
