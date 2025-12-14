package com.youngonion.glasshurts;

import com.mojang.logging.LogUtils;
import com.youngonion.glasshurts.client.ClientProxy;
import com.youngonion.glasshurts.common.CommonProxy;
import com.youngonion.glasshurts.items.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.youngonion.glasshurts.common.CommonProxy.LOOT_MOD_SERIALIZER;

@Mod(GlassHurts.MOD_ID)
public class GlassHurts {
    public static final String MOD_ID = "glasshurts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GlassHurts() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        Items.ITEM_REGISTER.register(bus);
        Items.init();
        LOOT_MOD_SERIALIZER.register(bus);
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
