package com.youngonion.glasshurts.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import static com.youngonion.glasshurts.GlassHurts.MOD_ID;

public final class GlassHurtsTags {
    private GlassHurtsTags() {}

    public static final class Items {
        public static final TagKey<Item> GLASS_SHARDS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "glass_shards"));
        private Items() {}
    }
}

