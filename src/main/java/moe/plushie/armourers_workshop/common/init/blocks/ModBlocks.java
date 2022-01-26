package moe.plushie.armourers_workshop.common.init.blocks;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#methods-for-registering
public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArmourersWorkshop.MOD_ID);

    public ModBlocks() {
        //Initialize instance of blocks here
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
