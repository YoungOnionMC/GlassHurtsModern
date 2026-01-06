package com.youngonion.glasshurts.data;

import com.youngonion.glasshurts.common.DamageSources;
import com.youngonion.glasshurts.common.GlassHurtsItems;
import com.youngonion.glasshurts.common.GlassHurtsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.youngonion.glasshurts.GlassHurts.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        var registries = event.getLookupProvider();
        if(event.includeServer()) {
            var set = Set.of(MOD_ID);
            generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                    packOutput, registries, new RegistrySetBuilder()
                    .add(Registries.DAMAGE_TYPE, DamageSources::bootstrap),
                    set
            ));
            generator.addProvider(true, new GlassLootModifierProvider(packOutput));
            generator.addProvider(true, new GlassHurtsRecipeProvider(packOutput));
            generator.addProvider(true, new GlassHurtsItemTags(packOutput, registries, fileHelper));
        }
        if (event.includeClient()) {
            generator.addProvider(true, new GlassHurtsItemModelProvider(packOutput, fileHelper));
        }
    }

    // Item Tag Provider
    private static class GlassHurtsItemTags extends ItemTagsProvider {
        public GlassHurtsItemTags(PackOutput output,
                                  java.util.concurrent.CompletableFuture<HolderLookup.Provider> registries,
                                  ExistingFileHelper existingFileHelper) {
            super(output,
                  registries,
                  java.util.concurrent.CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                  MOD_ID,
                  existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            var b = tag(GlassHurtsTags.Items.GLASS_SHARDS);
            b.add(GlassHurtsItems.GLASS_SHARD.get());
            for (var entry : GlassHurtsItems.COLORED_GLASS_SHARDS.values()) {
                b.add(entry.get());
            }
        }

        @Override
        public @NotNull String getName() {
            return "GlassHurts Item Tags";
        }
    }
}
