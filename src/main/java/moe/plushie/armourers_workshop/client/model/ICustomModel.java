package moe.plushie.armourers_workshop.client.model;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public interface ICustomModel {

    @LogicalSidedProvider(LogicalSide.CLIENT)
    public void registerModels();

}
