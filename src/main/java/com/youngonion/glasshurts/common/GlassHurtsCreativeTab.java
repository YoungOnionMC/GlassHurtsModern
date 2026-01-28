package com.youngonion.glasshurts.common;

import com.youngonion.glasshurts.GlassHurts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.item.DyeColor;

@SuppressWarnings("unused")
public class GlassHurtsCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            GlassHurts.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GLASS_HURTS = TABS.register("glass_hurts",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creative_tab.glass_hurts"))
                    .icon(() -> new ItemStack(GlassHurtsItems.GLASS_SHARD.get()))
                    .displayItems(GlassHurtsCreativeTab::fillTab)
                    .build());

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        accept(out, GlassHurtsItems.LEATHER_GLOVE);
        accept(out, GlassHurtsItems.GLASS_SHARD);

        for (DyeColor color : DyeColor.values()) {
            accept(out, GlassHurtsItems.COLORED_GLASS_SHARDS.get(color));
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out,
                                                                                   Map<K1, Map<K2, R>> map, K1 key1, K2 key2) {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2)) {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map,
                                                                              K key) {
        if (map.containsKey(key)) {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg) {
        if (reg == null) return;
        if (reg.get().asItem() == Items.AIR) {
            GlassHurts.LOGGER.error("BlockItem with no Item added to creative tab: {}", reg.get().toString());
            return;
        }
        out.accept(reg.get());
    }
}