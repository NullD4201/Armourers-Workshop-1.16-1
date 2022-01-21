package moe.plushie.armourers_workshop.common.skin.entity;

import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;

public class SkinnableEntitySlime extends SkinnableEntity {

    @Override
    public Class<? extends LivingEntity> getEntityClass() {
        return SlimeEntity.class;
    }

    @Override
    public void getValidSkinTypes(ArrayList<ISkinType> skinTypes) {
        skinTypes.add(SkinTypeRegistry.skinHead);
    }
    
    @Override
    public int getSlotsForSkinType(ISkinType skinType) {
        return 1;
    }
}
