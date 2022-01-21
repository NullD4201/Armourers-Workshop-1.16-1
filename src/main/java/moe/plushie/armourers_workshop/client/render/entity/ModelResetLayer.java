package moe.plushie.armourers_workshop.client.render.entity;

import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelResetLayer implements LayerRenderer<PlayerEntity> {

    private final PlayerRenderer renderPlayer;
    
    public ModelResetLayer(PlayerRenderer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }
    
    @Override
    public void doRenderLayer(PlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderPlayer.getMainModel().bipedLeftArm.isHidden = false;
        renderPlayer.getMainModel().bipedRightArm.isHidden = false;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
