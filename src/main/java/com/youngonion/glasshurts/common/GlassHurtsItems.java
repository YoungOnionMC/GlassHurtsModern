package com.youngonion.glasshurts.common;

import com.youngonion.glasshurts.GlassHurts;
import com.youngonion.glasshurts.common.items.GlassShard;
import com.youngonion.glasshurts.common.items.LeatherGlove;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;

public class GlassHurtsItems {

    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, GlassHurts.MOD_ID);

    public static RegistryObject<Item> GLASS_SHARD;
    public static RegistryObject<Item> LEATHER_GLOVE;
    public static final Map<DyeColor, RegistryObject<Item>> COLORED_GLASS_SHARDS = new EnumMap<>(DyeColor.class);

    public static void init() {
        GLASS_SHARD = ITEM_REGISTER.register("glass_shard", () -> new GlassShard(new Item.Properties().food(
                new FoodProperties.Builder().alwaysEat()
                        .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS), 0.9f).build())));

        LEATHER_GLOVE = ITEM_REGISTER.register("leather_glove", () -> new LeatherGlove(new Item.Properties().durability(LeatherGlove.MAX_DURABILITY)));
        for (DyeColor color : DyeColor.values()) {
            String id = "glass_shard_" + color.getName();
            COLORED_GLASS_SHARDS.put(color,
                    ITEM_REGISTER.register(id, () -> new GlassShard(new Item.Properties().food(
                            new FoodProperties.Builder().alwaysEat()
                                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS), 0.9f).build()
                    )))
            );
        }
    }

    public static int getColorARGB(DyeColor color) {
        int rgb = color.getTextColor();
        return 0xFF000000 | rgb;
    }
}
