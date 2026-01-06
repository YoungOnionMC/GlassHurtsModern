package com.youngonion.glasshurts.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.youngonion.glasshurts.GlassHurts.MOD_ID;

public class GlassHurtsItemModelProvider extends ItemModelProvider {

    public GlassHurtsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        String baseTex = "item/glass_shard_tint";
        for (DyeColor color : DyeColor.values()) {
            singleTexture("glass_shard_" + color.getName(), mcLoc("item/generated"), "layer0", modLoc(baseTex));
        }
    }
}
