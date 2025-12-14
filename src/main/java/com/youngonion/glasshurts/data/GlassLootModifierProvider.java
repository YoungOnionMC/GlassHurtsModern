package com.youngonion.glasshurts.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class GlassLootModifierProvider extends GlobalLootModifierProvider {
    public GlassLootModifierProvider(PackOutput output) {
        super(output, "glasshurts");
    }

    @Override
    protected void start() {
        add("glass_loot", new GlassLootModifier(new LootItemCondition[0], 1, 3));
    }
}
