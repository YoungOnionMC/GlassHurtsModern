package com.youngonion.glasshurts.items;

import com.youngonion.glasshurts.common.CommonForgeEvents;
import com.youngonion.glasshurts.common.DamageSources;
import com.youngonion.glasshurts.common.GlassDamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlassShard extends Item {

    public GlassShard(Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.hurt(CommonForgeEvents.glass_damage(level, GlassDamageType.GLASS_EAT), 2.0F);
        stack.shrink(1);
        return stack;
    }
}
