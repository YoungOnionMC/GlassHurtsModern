package com.youngonion.glasshurts.common.items;

import com.youngonion.glasshurts.common.CommonForgeEvents;
import com.youngonion.glasshurts.common.GlassDamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GlassShard extends Item {

    public GlassShard(Properties props) {
        super(props);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, LivingEntity entity) {
        entity.hurt(CommonForgeEvents.glass_damage(level, GlassDamageType.GLASS_EAT), 2.0F);
        stack.shrink(1);
        return stack;
    }
}
