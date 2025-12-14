package com.youngonion.glasshurts.data;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.youngonion.glasshurts.items.Items;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class GlassLootModifier extends LootModifier {

    public GlassLootModifier(LootItemCondition[] conditions, int min, int max) {
        super(conditions);
        this.min = min;
        this.max = max;
    }

    public static final Supplier<Codec<GlassLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance ->
            codecStart(instance)
                    .and(Codec.INT.fieldOf("min").forGetter( m-> m.min))
                    .and(Codec.INT.fieldOf("max").forGetter( m-> m.max))
                    .apply(instance, GlassLootModifier::new)));

    private final int min;
    private final int max;

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if(state != null && (state.is(Tags.Blocks.GLASS) || state.is(Tags.Blocks.GLASS_PANES)) && (tool == null || tool.isEmpty())) {
            //generatedLoot.clear();
            generatedLoot.add(new ItemStack(Items.GLASS_SHARD.get(), context.getRandom().nextInt(max - min) + min));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
