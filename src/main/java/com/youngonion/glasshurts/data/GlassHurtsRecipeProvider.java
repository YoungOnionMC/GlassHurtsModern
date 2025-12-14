package com.youngonion.glasshurts.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.function.Consumer;

import static com.youngonion.glasshurts.items.Items.GLASS_SHARD;
import static com.youngonion.glasshurts.items.Items.LEATHER_GLOVE;

public class GlassHurtsRecipeProvider extends RecipeProvider {


    public GlassHurtsRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LEATHER_GLOVE.get())
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .pattern("nll")
                .pattern("lil")
                .pattern("ll ")
                .define('n', Items.IRON_NUGGET)
                .define('l', Items.LEATHER)
                .define('i', Items.IRON_INGOT)
                .save(provider);

        SmeltingRecipe

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.GLASS)
                .unlockedBy(getHasName(GLASS_SHARD.get()), has(GLASS_SHARD.get()))
                .requires(GLASS_SHARD.get(), 9)
                .save(provider);
    }
}
