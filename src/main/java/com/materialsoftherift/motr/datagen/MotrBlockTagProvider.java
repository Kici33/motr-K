package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class MotrBlockTagProvider extends BlockTagsProvider {
    public MotrBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MaterialsOfTheRift.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // spotless:off
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MotrSlabs.WHITE_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_GRAY_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.GRAY_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.BLACK_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.BROWN_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.RED_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.ORANGE_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.YELLOW_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.LIME_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.GREEN_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.CYAN_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_BLUE_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.BLUE_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.PURPLE_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.MAGENTA_CONCRETE_SLAB.slab().get())
                .add(MotrSlabs.PINK_CONCRETE_SLAB.slab().get())

                .add(MotrSlabs.GLASS_SLAB.slab().get())
                .add(MotrSlabs.TINTED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.WHITE_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_GRAY_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.GRAY_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.BLACK_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.BROWN_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.RED_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.ORANGE_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.YELLOW_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.LIME_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.GREEN_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.CYAN_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_BLUE_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.BLUE_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.PURPLE_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.MAGENTA_STAINED_GLASS_SLAB.slab().get())
                .add(MotrSlabs.PINK_STAINED_GLASS_SLAB.slab().get())

                .add(MotrSlabs.TUBE_CORAL_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.BRAIN_CORAL_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.BUBBLE_CORAL_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.FIRE_CORAL_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.HORN_CORAL_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.GOLD_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.IRON_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.RAW_IRON_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.RAW_GOLD_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.PACKED_ICE_SLAB.slab().get())
                .add(MotrSlabs.ICE_SLAB.slab().get())
                .add(MotrSlabs.BLUE_ICE_SLAB.slab().get())
                .add(MotrSlabs.GILDED_BLACKSTONE_SLAB.slab().get())
                .add(MotrSlabs.BONE_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.CLAY_SLAB.slab().get())
                .add(MotrSlabs.RESIN_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.MAGMA_SLAB.slab().get())
                .add(getAllButtonBlocks())
                .add(getAllFenceBlocks())
                .add(getAllWallBlocks())
                .add(getAllStairBlocks())
                .add(getAllCopperBlocks());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(MotrSlabs.DIRT_SLAB.slab().get())
                .add(MotrSlabs.COARSE_DIRT_SLAB.slab().get())
                .add(MotrSlabs.ROOTED_DIRT_SLAB.slab().get())
                .add(MotrSlabs.PODZOL_SLAB.slab().get())
                .add(MotrSlabs.MYCELIUM_SLAB.slab().get())
                .add(MotrSlabs.DIRT_PATH_SLAB.slab().get())
                .add(MotrSlabs.SOUL_SAND_SLAB.slab().get())
                .add(MotrSlabs.SOUL_SOIL_SLAB.slab().get())
                .add(MotrSlabs.MUD_SLAB.slab().get())
                .add(MotrSlabs.MUDDY_MANGROVE_ROOTS_SLAB.slab().get())
                .add(MotrQuenched.QUENCHED_FARMLAND.block().get())

                .add(MotrSlabs.WHITE_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_GRAY_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.GRAY_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.BLACK_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.BROWN_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.RED_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.ORANGE_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.YELLOW_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.LIME_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.GREEN_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.CYAN_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.LIGHT_BLUE_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.BLUE_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.PURPLE_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.MAGENTA_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.PINK_CONCRETE_POWDER_SLAB.slab().get())
                .add(MotrSlabs.SNOW_SLAB.slab().get())
                .add(getAllNoGravBlocks());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(MotrSlabs.WARPED_WART_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.PALE_MOSS_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.MOSS_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.NETHER_WART_BLOCK_SLAB.slab().get())
                .add(getAllUnboundHoeBlocks());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(getAllUnboundAxeBlocks());

        tag(BlockTags.DIRT)
                .add(MotrSlabs.DIRT_SLAB.slab().get())
                .add(MotrSlabs.DIRT_PATH_SLAB.slab().get())
                .add(MotrSlabs.COARSE_DIRT_SLAB.slab().get())
                .add(MotrSlabs.ROOTED_DIRT_SLAB.slab().get())
                .add(MotrSlabs.PODZOL_SLAB.slab().get())
                .add(MotrSlabs.MYCELIUM_SLAB.slab().get());

        tag(BlockTags.SOUL_SPEED_BLOCKS)
                .add(MotrSlabs.SOUL_SAND_SLAB.slab().get())
                .add(MotrSlabs.SOUL_SOIL_SLAB.slab().get());

        tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(MotrSlabs.SOUL_SAND_SLAB.slab().get())
                .add(MotrSlabs.SOUL_SOIL_SLAB.slab().get());

        tag(BlockTags.ICE)
                .add(MotrSlabs.ICE_SLAB.slab().get())
                .add(MotrSlabs.PACKED_ICE_SLAB.slab().get())
                .add(MotrSlabs.BLUE_ICE_SLAB.slab().get());

        tag(BlockTags.WART_BLOCKS)
                .add(MotrSlabs.WARPED_WART_BLOCK_SLAB.slab().get())
                .add(MotrSlabs.NETHER_WART_BLOCK_SLAB.slab().get());

        tag(BlockTags.SLABS)
                .add(getAllSlabBlocks());

        tag(BlockTags.WALLS)
                .add(getAllWallBlocks());

        tag(BlockTags.FENCES)
                .add(getAllFenceBlocks());

        tag(BlockTags.BUTTONS)
                .add(getAllButtonBlocks());

        tag(BlockTags.FENCE_GATES)
                .add(getAllFenceGateBlocks());

        tag(BlockTags.STAIRS)
                .add(getAllStairBlocks());

        tag(BlockTags.WOOL_CARPETS)
                .add(MotrBlocks.HAY_CARPET.get());

        tag(BlockTags.INFINIBURN_END)
                .add(MotrSlabs.MAGMA_SLAB.slab().get());

        tag(BlockTags.INFINIBURN_NETHER)
                .add(MotrSlabs.MAGMA_SLAB.slab().get());

        tag(BlockTags.INFINIBURN_OVERWORLD)
                .add(MotrSlabs.MAGMA_SLAB.slab().get());

        tag(BlockTags.SNOW)
                .add(MotrSlabs.SNOW_SLAB.slab().get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(getAllCopperBlocks());

        tag(BlockTags.SAND)
                .add(MotrNoGrav.NOGRAV_SAND.block().get())
                .add(MotrNoGrav.NOGRAV_RED_SAND.block().get());

        tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(MotrNoGrav.NOGRAV_GRAVEL.block().get());

        tag(BlockTags.CLIMBABLE)
                .add(MotrUnbound.UNBOUND_VINE.block().get());

        // spotless:on

    }

    private Block[] getAllNoGravBlocks() {
        return MotrNoGrav.REGISTERED_NOGRAV_BLOCKS.values()
                .stream()
                .map(nogravInfo -> nogravInfo.block().get())
                .toArray(Block[]::new);
    }

    private Block[] getAllSlabBlocks() {
        return Stream.of(
                MotrSlabs.REGISTERED_STANDARD_SLABS.values().stream(),
                MotrSlabs.REGISTERED_GLASS_SLABS.values().stream(),
                MotrSlabs.REGISTERED_DIRECTIONAL_SLABS.values().stream(),
                MotrSlabs.REGISTERED_TRIMM_SLABS.values().stream(),
                MotrSlabs.REGISTERED_SILKTOUCH_SLABS.values().stream(),
                MotrSlabs.REGISTERED_COPPER_SLABS.values().stream()
        ).flatMap(stream -> stream).map(slabInfo -> slabInfo.slab().get()).toArray(Block[]::new);
    }

    private Block[] getAllCopperBlocks() {
        return Stream.of(
                MotrSlabs.REGISTERED_COPPER_SLABS.values().stream()
        ).flatMap(stream -> stream).map(slabInfo -> slabInfo.slab().get()).toArray(Block[]::new);
    }

    private Block[] getAllWallBlocks() {
        return Stream.concat(
                MotrWalls.REGISTERED_STANDARD_WALLS.values().stream(),
                MotrWalls.REGISTERED_GLASS_WALLS.values().stream()
        ).map(wallInfo -> wallInfo.wall().get()).toArray(Block[]::new);
    }

    private Block[] getAllButtonBlocks() {
        return MotrButtons.REGISTERED_BUTTONS.values()
                .stream()
                .map(buttonInfo -> buttonInfo.button().get())
                .toArray(Block[]::new);
    }

    private Block[] getAllFenceBlocks() {
        return MotrFenceAndGate.REGISTERED_FENCES.values()
                .stream()
                .map(fenceInfo -> fenceInfo.fence().get())
                .toArray(Block[]::new);
    }

    private Block[] getAllFenceGateBlocks() {
        return MotrFenceAndGate.REGISTERED_FENCE_GATES.values()
                .stream()
                .map(fenceGateInfo -> fenceGateInfo.fenceGate().get())
                .toArray(Block[]::new);
    }

    private Block[] getAllStairBlocks() {
        return MotrStairs.REGISTERED_STANDARD_STAIRS.values()
                .stream()
                .map(stairInfo -> stairInfo.stair().get())
                .toArray(Block[]::new);
    }

    private Block[] getAllUnboundHoeBlocks() {
        return Stream.concat(Stream.of(
                        MotrUnbound.UNBOUND_WHEAT_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_CARROT_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_POTATO_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_BEETROOT_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_NETHER_WART_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_MELON_STEM_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_PUMPKIN_STEM_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_TORCHFLOWER_STAGES.values().stream(),
                        MotrUnbound.UNBOUND_PITCHER_CROP_STAGES.values().stream()
                )
                .flatMap(s -> s)
                .map(unboundBlockInfo -> unboundBlockInfo.block().get()),
                MotrUnbound.SIMPLE_UNBOUND_BLOCKS.values().stream()
                        .map(unboundSimpleBlockInfo -> unboundSimpleBlockInfo.block().get()))
                .toArray(Block[]::new);
    }

    private Block[] getAllUnboundAxeBlocks() {
        return Stream.concat(
                MotrUnbound.UNBOUND_COCOA_STAGES.values()
                        .stream()
                        .map(unboundBlockInfo -> unboundBlockInfo.block().get()),
                Stream.of(MotrUnbound.UNBOUND_BAMBOO_SAPLING.get())
        ).toArray(Block[]::new);
    }

}
