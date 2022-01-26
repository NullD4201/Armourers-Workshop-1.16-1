package moe.plushie.armourers_workshop.client.model;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomModel {
    @OnlyIn(Dist.CLIENT)
    public void registerModels();
}
