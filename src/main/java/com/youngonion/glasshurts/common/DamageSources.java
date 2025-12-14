package com.youngonion.glasshurts.common;

import com.youngonion.glasshurts.GlassHurts;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.*;

import static com.youngonion.glasshurts.common.GlassDamageType.*;

public class DamageSources {

    public static void bootstrap(BootstapContext<DamageType> ctx) {
        ctx.register(GLASS_BREAK, new DamageType("glass_break", 0));
        ctx.register(GLASS_EAT, new DamageType("glass_eat", 0.3f));
    }
}
