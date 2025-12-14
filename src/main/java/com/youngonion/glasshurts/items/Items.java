package com.youngonion.glasshurts.items;

import com.youngonion.glasshurts.GlassHurts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {

    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, GlassHurts.MOD_ID);

    public static RegistryObject<Item> GLASS_SHARD;
    public static RegistryObject<Item> LEATHER_GLOVE;

    public static void init() {
        GLASS_SHARD = ITEM_REGISTER.register("glass_shard", () -> new GlassShard(new Item.Properties().food(
                new FoodProperties.Builder().alwaysEat()
                        .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS), 0.9f).build())));

        LEATHER_GLOVE = ITEM_REGISTER.register("leather_glove", () -> new LeatherGlove(new Item.Properties().durability(LeatherGlove.MAX_DURABILITY)));

    }

}
