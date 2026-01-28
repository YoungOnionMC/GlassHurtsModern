package com.youngonion.glasshurts.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import static com.youngonion.glasshurts.GlassHurts.MOD_ID;
import static com.youngonion.glasshurts.common.GlassHurtsItems.*;

public class GlassHurtsRecipeProvider extends RecipeProvider {

    public GlassHurtsRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LEATHER_GLOVE.get())
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .pattern("nll")
                .pattern("lil")
                .pattern("ll ")
                .define('n', Items.IRON_NUGGET)
                .define('l', Items.LEATHER)
                .define('i', Items.IRON_INGOT)
                .save(provider, new ResourceLocation(MOD_ID, "leather_glove"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.GLASS)
                .unlockedBy(getHasName(GLASS_SHARD.get()), has(GLASS_SHARD.get()))
                .requires(Ingredient.of(GLASS_SHARD.get()), 9)
                .save(provider, new ResourceLocation(MOD_ID, "glass_from_shards"));

        for (DyeColor color : DyeColor.values()) {
            var shard = COLORED_GLASS_SHARDS.get(color).get();
            Item stainedGlass = requireItem(new ResourceLocation("minecraft", color.getName() + "_stained_glass"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, stainedGlass)
                    .unlockedBy(getHasName(shard), has(shard))
                    .requires(Ingredient.of(shard), 9)
                    .save(provider, new ResourceLocation(MOD_ID, color.getName() + "_stained_glass_from_shards"));
        }
    }

    private static Item requireItem(ResourceLocation id) {
        Item item = ForgeRegistries.ITEMS.getValue(id);
        if (item == null) {
            throw new IllegalStateException("Missing item in registry: " + id);
        }
        return item;
    }
}
