package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.init.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/* Handles Data Generation for Recipes of the Wotr mod */
public class MotrRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    protected MotrRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);

        MotrNoGrav.REGISTERED_NOGRAV_BLOCKS.forEach((id, noGravInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, noGravInfo.block().get(), 8)
                    .pattern("GGG")
                    .pattern("GHG")
                    .pattern("GGG")
                    .define('G', noGravInfo.getBaseItem())
                    .define('H', Items.CHORUS_FRUIT)
                    .unlockedBy("has_" + id, this.has(noGravInfo.getBaseItem()))
                    .unlockedBy("has_chorus_fruit", this.has(Items.CHORUS_FRUIT))
                    .save(this.output);
        });

        MotrQuenched.REGISTERED_QUENCHED_BLOCKS.forEach((id, blockInfo) -> {
            ItemLike quenchedBlock = blockInfo.block().get();
            ItemLike vanillaBlock = blockInfo.getBaseItem();
            if (vanillaBlock == Items.AIR) {
                return;
            }

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, quenchedBlock, 1)
                    .requires(vanillaBlock)
                    .requires(Items.PRISMARINE_CRYSTALS)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "quenched_" + id + "_from_prismarine_crystals");

            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, quenchedBlock, 8)
                    .pattern("###")
                    .pattern("#W#")
                    .pattern("###")
                    .define('#', vanillaBlock)
                    .define('W', Items.WET_SPONGE)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "quenched_" + id + "_from_wet_sponge");

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, vanillaBlock, 1)
                    .requires(quenchedBlock)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, id + "_from_quenched");
        });

        MotrSlabs.REGISTERED_STANDARD_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_GLASS_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_DIRECTIONAL_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_TRIMM_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_SILKTOUCH_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_COPPER_SLABS.forEach((id, slabInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                    .pattern("###")
                    .define('#', slabInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrWalls.REGISTERED_STANDARD_WALLS.forEach((id, wallInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, wallInfo.wall().get(), 6)
                    .pattern("###")
                    .pattern("###")
                    .define('#', wallInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(wallInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrWalls.REGISTERED_GLASS_WALLS.forEach((id, wallInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, wallInfo.wall().get(), 6)
                    .pattern("###")
                    .pattern("###")
                    .define('#', wallInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(wallInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrFenceAndGate.REGISTERED_FENCES.forEach((id, fenceInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, fenceInfo.fence().get(), 3)
                    .pattern("#S#")
                    .pattern("#S#")
                    .define('#', fenceInfo.getBaseItem())
                    .define('S', Items.STICK)
                    .unlockedBy("has_" + id, has(fenceInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrButtons.REGISTERED_BUTTONS.forEach((id, buttonInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, buttonInfo.button().get(), 1)
                    .pattern("#")
                    .define('#', buttonInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(buttonInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrFenceAndGate.REGISTERED_FENCE_GATES.forEach((id, fenceGateInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, fenceGateInfo.fenceGate().get(), 3)
                    .pattern("S#S")
                    .pattern("S#S")
                    .define('#', fenceGateInfo.getBaseItem())
                    .define('S', Items.STICK)
                    .unlockedBy("has_" + id, has(fenceGateInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrStairs.REGISTERED_STANDARD_STAIRS.forEach((id, stairInfo) -> {
            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, stairInfo.stair().get(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .define('#', stairInfo.getBaseItem())
                    .unlockedBy("has_" + id, has(stairInfo.getBaseItem()))
                    .save(this.output);
        });

        MotrSlabs.REGISTERED_WAXED_COPPER_SLABS.forEach((id, waxedSlabInfo) -> {
            String nonWaxedId = id.replace("waxed_", "");
            MotrSlabs.SlabInfo nonWaxedSlabInfo = MotrSlabs.REGISTERED_COPPER_SLABS.get(nonWaxedId);

            if (nonWaxedSlabInfo != null) {
                ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, waxedSlabInfo.slab().get())
                        .requires(nonWaxedSlabInfo.slab().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy("has_" + nonWaxedId, has(nonWaxedSlabInfo.slab().get()))
                        .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                        .save(this.output, id + "_from_honeycomb");
            }
        });

        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_WHEAT_STAGES, "wheat", Items.WHEAT_SEEDS, Items.WHEAT);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_CARROT_STAGES, "carrot", Items.CARROT, Items.CARROT);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_POTATO_STAGES, "potato", Items.POTATO, Items.POTATO);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_BEETROOT_STAGES, "beetroot", Items.BEETROOT_SEEDS, Items.BEETROOT);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_NETHER_WART_STAGES, "nether_wart", Items.NETHER_WART, Items.NETHER_WART);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_MELON_STEM_STAGES, "melon_stem", Items.MELON_SEEDS, Items.MELON_SEEDS);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_PUMPKIN_STEM_STAGES, "pumpkin_stem", Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_TORCHFLOWER_STAGES, "torchflower", Items.TORCHFLOWER_SEEDS, Items.TORCHFLOWER);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_PITCHER_CROP_STAGES, "pitcher_crop", Items.PITCHER_POD, Items.PITCHER_PLANT);
        buildUnboundCropRecipes(getter, MotrUnbound.UNBOUND_COCOA_STAGES, "cocoa", Items.COCOA_BEANS, Items.COCOA_BEANS);

        MotrUnbound.SIMPLE_UNBOUND_BLOCKS.forEach((name, info) -> {
            buildSimpleUnboundRecipes(getter, info, name);
        });

        // Bamboo Recipes
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, MotrUnbound.UNBOUND_BAMBOO_SAPLING.get())
                .requires(Items.BAMBOO)
                .requires(Items.HANGING_ROOTS)
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(this.output, "unbound_bamboo_sapling_from_hanging_roots");

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, MotrUnbound.UNBOUND_BAMBOO_SAPLING.get(), 8)
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .define('#', Items.BAMBOO)
                .define('R', Items.ROOTED_DIRT)
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(this.output, "unbound_bamboo_sapling_from_rooted_dirt");

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, Items.BAMBOO)
                .requires(MotrUnbound.UNBOUND_BAMBOO_SAPLING.get())
                .unlockedBy("has_unbound_bamboo_sapling", has(MotrUnbound.UNBOUND_BAMBOO_SAPLING.get()))
                .save(this.output, "bamboo_from_unbound_sapling");

        // Carpet
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, MotrBlocks.HAY_CARPET.get(), 4)
                .pattern("GG")
                .define('G', Items.HAY_BLOCK)
                .unlockedBy("has_hay_block", this.has(Items.HAY_BLOCK))
                .save(this.output);

    }

    private void buildSimpleUnboundRecipes(HolderGetter<Item> getter, MotrUnbound.UnboundSimpleBlockInfo info, String name) {
        ItemLike baseItem = info.baseItem().get();
        ItemLike unboundItem = info.block().get();

        // Shapeless Crafting
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, unboundItem)
                .requires(baseItem)
                .requires(Items.HANGING_ROOTS)
                .unlockedBy("has_" + name, has(baseItem))
                .save(this.output, "unbound_" + name + "_from_hanging_roots");

        // Shaped Crafting
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, unboundItem, 8)
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .define('#', baseItem)
                .define('R', Items.ROOTED_DIRT)
                .unlockedBy("has_" + name, has(baseItem))
                .save(this.output, "unbound_" + name + "_from_rooted_dirt");

        // Reversion
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, baseItem)
                .requires(unboundItem)
                .unlockedBy("has_unbound_" + name, has(unboundItem))
                .save(this.output, name + "_from_unbound");
    }

    private void buildUnboundCropRecipes(HolderGetter<Item> getter, Map<Integer, MotrUnbound.UnboundBlockInfo> stages, String cropName, Item seedItem, Item finalDrop) {
        MotrUnbound.UnboundBlockInfo stage0Info = stages.get(0);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, stage0Info.block().get())
                .requires(seedItem)
                .requires(Items.HANGING_ROOTS)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(seedItem).getPath(), has(seedItem))
                .save(this.output, "unbound_" + cropName + "_stage0_from_hanging_roots");

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, stage0Info.block().get(), 8)
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .define('#', seedItem)
                .define('R', Items.ROOTED_DIRT)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(seedItem).getPath(), has(seedItem))
                .save(this.output, "unbound_" + cropName + "_stage0_from_rooted_dirt");

        for (int i = 0; i < stages.size(); i++) {
            MotrUnbound.UnboundBlockInfo currentStageInfo = stages.get(i);

            if (i < stages.size() - 1) {
                MotrUnbound.UnboundBlockInfo nextStageInfo = stages.get(i + 1);
                ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, nextStageInfo.block().get())
                        .requires(currentStageInfo.block().get())
                        .requires(Items.HANGING_ROOTS)
                        .unlockedBy("has_unbound_" + cropName + "_stage" + i, has(currentStageInfo.block().get()))
                        .save(this.output, "unbound_" + cropName + "_stage" + (i + 1) + "_from_stage" + i);
            }

            ItemLike revertTo = (i == stages.size() - 1) ? finalDrop : seedItem;
            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, revertTo)
                    .requires(currentStageInfo.block().get())
                    .unlockedBy("has_unbound_" + cropName + "_stage" + i, has(currentStageInfo.block().get()))
                    .save(this.output, cropName + "_from_unbound_stage" + i);
        }
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(
                HolderLookup.@NotNull Provider provider,
                @NotNull RecipeOutput output) {
            return new MotrRecipeProvider(provider, output);
        }

        @Override
        public @NotNull String getName() {
            return "Materials of the Rift's Recipes";
        }
    }
}
