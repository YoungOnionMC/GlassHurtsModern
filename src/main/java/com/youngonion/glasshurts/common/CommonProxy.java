package com.youngonion.glasshurts.common;

import com.mojang.serialization.Codec;
import com.youngonion.glasshurts.GlassHurts;
import com.youngonion.glasshurts.data.GlassLootModifier;
import com.youngonion.glasshurts.items.Items;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.youngonion.glasshurts.GlassHurts.MOD_ID;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MOD_SERIALIZER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final RegistryObject<Codec<GlassLootModifier>> GLASS_LOOT = LOOT_MOD_SERIALIZER.register("glass_loot", GlassLootModifier.CODEC);

    public static void init() {
        GlassHurts.LOGGER.info("GlassHurts common proxy init!");
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {

    }

    @SubscribeEvent
    public static void modConstruct(FMLConstructModEvent event) {
        EVENT_BUS.register(CommonForgeEvents.class);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {

    }




}
