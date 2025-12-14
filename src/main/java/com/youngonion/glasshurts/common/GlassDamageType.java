package com.youngonion.glasshurts.common;

import com.youngonion.glasshurts.GlassHurts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class GlassDamageType {

   public static final ResourceKey<DamageType> GLASS_BREAK = ResourceKey.create(Registries.DAMAGE_TYPE, GlassHurts.id("glass_break"));
   public static final ResourceKey<DamageType> GLASS_EAT = ResourceKey.create(Registries.DAMAGE_TYPE, GlassHurts.id("glass_eat"));
}
