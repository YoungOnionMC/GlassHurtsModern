package com.youngonion.glasshurts.client;

import com.youngonion.glasshurts.common.CommonProxy;
import com.youngonion.glasshurts.GlassHurts;
import com.youngonion.glasshurts.common.GlassHurtsItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
    }

    @Mod.EventBusSubscriber(modid = GlassHurts.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
            final int white = GlassHurtsItems.getColorARGB(DyeColor.WHITE);
            event.register((stack, tintIndex) -> tintIndex == 0 ? white : 0xFFFFFFFF, GlassHurtsItems.GLASS_SHARD.get());

            for (Map.Entry<DyeColor, net.minecraftforge.registries.RegistryObject<Item>> e : GlassHurtsItems.COLORED_GLASS_SHARDS.entrySet()) {
                final int argb = GlassHurtsItems.getColorARGB(e.getKey());
                event.register((stack, tintIndex) -> tintIndex == 0 ? argb : 0xFFFFFFFF, e.getValue().get());
            }
        }
    }
}
