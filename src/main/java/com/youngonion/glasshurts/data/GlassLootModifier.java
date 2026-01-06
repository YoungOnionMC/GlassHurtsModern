package com.youngonion.glasshurts.data;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.youngonion.glasshurts.common.GlassHurtsItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;

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
        boolean hasSilk = tool != null && !tool.isEmpty() && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;
        if(state != null && (!hasSilk)){
            Item shardItem = resolveShardForState(state);
            if (state.is(Tags.Blocks.GLASS)) {
                generatedLoot.add(new ItemStack(shardItem, context.getRandom().nextInt(max * 2 - min) + min));
            } else if (state.is(Tags.Blocks.GLASS_PANES)) {
                generatedLoot.add(new ItemStack(shardItem, context.getRandom().nextInt(max - min) + min));
            }
        }
        return generatedLoot;
    }

    private Item resolveShardForState(BlockState state) {
        if (state.getBlock() instanceof StainedGlassBlock stained) {
            DyeColor color = stained.getColor();
            var regObj = GlassHurtsItems.COLORED_GLASS_SHARDS.get(color);
            if (regObj != null) return regObj.get();
            return GlassHurtsItems.GLASS_SHARD.get();
        }
        if (state.getBlock() instanceof StainedGlassPaneBlock stainedPane) {
            DyeColor color = stainedPane.getColor();
            var regObj = GlassHurtsItems.COLORED_GLASS_SHARDS.get(color);
            if (regObj != null) return regObj.get();
            return GlassHurtsItems.GLASS_SHARD.get();
        }
        return GlassHurtsItems.GLASS_SHARD.get();
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
