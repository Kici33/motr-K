package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.Config;
import com.materialsoftherift.motr.MaterialsOfTheRift;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class MotrQuenched {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MaterialsOfTheRift.MODID);

    public record QuenchedBlockInfo(DeferredBlock<Block> block, Block baseBlock) {
        public Item getBaseItem() {
            return baseBlock.asItem();
        }
    }

    private static final class QuenchedCommon {
        static boolean canSurvive() {
            return true;
        }

        static boolean canBeHydrated() {
            return true;
        }

        static void tick() {
        }
    }

    public static class QuenchedCoralBlock extends CoralBlock {
        public QuenchedCoralBlock(Block deadBlock, Properties props) {
            super(deadBlock, props);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState s, @NotNull LevelReader l, @NotNull BlockPos p) {
            return QuenchedCommon.canSurvive();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState s,
                @NotNull BlockGetter g,
                @NotNull BlockPos p,
                @NotNull FluidState f,
                @NotNull BlockPos fp) {
            return QuenchedCommon.canBeHydrated();
        }

        @Override
        protected void tick(
                @NotNull BlockState s,
                @NotNull ServerLevel l,
                @NotNull BlockPos p,
                @NotNull RandomSource r) {
            QuenchedCommon.tick();
        }
    }

    public static class QuenchedCoralFanBlock extends CoralFanBlock {
        public QuenchedCoralFanBlock(Block deadBlock, Properties props) {
            super(deadBlock, props);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState s, @NotNull LevelReader l, @NotNull BlockPos p) {
            return QuenchedCommon.canSurvive();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState s,
                @NotNull BlockGetter g,
                @NotNull BlockPos p,
                @NotNull FluidState f,
                @NotNull BlockPos fp) {
            return QuenchedCommon.canBeHydrated();
        }

        @Override
        protected void tick(
                @NotNull BlockState s,
                @NotNull ServerLevel l,
                @NotNull BlockPos p,
                @NotNull RandomSource r) {
            QuenchedCommon.tick();
        }
    }

    public static class QuenchedFarmlandBlock extends FarmBlock {

        public QuenchedFarmlandBlock(Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 7));
        }

        @Override
        protected void randomTick(
                @NotNull BlockState state,
                @NotNull ServerLevel level,
                @NotNull BlockPos pos,
                @NotNull RandomSource source) {
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }
    }

    public static class QuenchedKelpBlock extends KelpBlock {

        public QuenchedKelpBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canGrowInto(@NotNull BlockState state) {
            return true;
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        protected @NotNull Block getBodyBlock() {
            return MotrQuenched.QUENCHED_KELP_PLANT.block().get();
        }

        @Override
        public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
            return this.defaultBlockState();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState state,
                @NotNull BlockGetter getter,
                @NotNull BlockPos pos,
                @NotNull FluidState fluid,
                @NotNull BlockPos fluidPos) {
            return true;
        }

        @Override
        public @NotNull FluidState getFluidState(@NotNull BlockState state) {
            return Fluids.EMPTY.defaultFluidState();
        }
    }

    public static class QuenchedKelpPlantBlock extends KelpPlantBlock {

        public QuenchedKelpPlantBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) MotrQuenched.QUENCHED_KELP.block().get();
        }

        @Override
        public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
            return this.defaultBlockState();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState state,
                @NotNull BlockGetter getter,
                @NotNull BlockPos pos,
                @NotNull FluidState fluid,
                @NotNull BlockPos fluidPos) {
            return true;
        }

        @Override
        public @NotNull FluidState getFluidState(@NotNull BlockState state) {
            return Fluids.EMPTY.defaultFluidState();
        }
    }

    public static class QuenchedSeagrassBlock extends SeagrassBlock {

        public QuenchedSeagrassBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
            return this.defaultBlockState();
        }

        @Override
        public @NotNull FluidState getFluidState(@NotNull BlockState state) {
            return Fluids.EMPTY.defaultFluidState();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState state,
                @NotNull BlockGetter getter,
                @NotNull BlockPos pos,
                @NotNull FluidState fluid,
                @NotNull BlockPos fluidPos) {
            return true;
        }
    }

    public static class QuenchedSeaPickleBlock extends SeaPickleBlock {

        public QuenchedSeaPickleBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
            return super.getStateForPlacement(context);
        }

        @Override
        public @NotNull FluidState getFluidState(@NotNull BlockState state) {
            return Fluids.EMPTY.defaultFluidState();
        }

        @Override
        public boolean canBeHydrated(
                @NotNull BlockState state,
                @NotNull BlockGetter getter,
                @NotNull BlockPos pos,
                @NotNull FluidState fluid,
                @NotNull BlockPos fluidPos) {
            return true;
        }
    }

    public static class QuenchedSugarCaneBlock extends SugarCaneBlock {

        public QuenchedSugarCaneBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }
    }

    public static final QuenchedBlockInfo QUENCHED_KELP = registerQuenchedBlock("quenched_kelp", Blocks.KELP,
            () -> new QuenchedKelpBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.KELP).setId(blockId("quenched_kelp"))));
    public static final QuenchedBlockInfo QUENCHED_KELP_PLANT = registerQuenchedBlock("quenched_kelp_plant",
            Blocks.KELP_PLANT, () -> new QuenchedKelpPlantBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT).setId(blockId("quenched_kelp_plant"))));
    public static final QuenchedBlockInfo QUENCHED_SEAGRASS = registerQuenchedBlock("quenched_seagrass",
            Blocks.SEAGRASS, () -> new QuenchedSeagrassBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).setId(blockId("quenched_seagrass"))));
    public static final QuenchedBlockInfo QUENCHED_SEA_PICKLE = registerQuenchedBlock("quenched_sea_pickle",
            Blocks.SEA_PICKLE, () -> new QuenchedSeaPickleBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_PICKLE)
                            .lightLevel(block -> 3 + 3 * block.getValue(SeaPickleBlock.PICKLES))
                            .setId(blockId("quenched_sea_pickle"))));

    public static final QuenchedBlockInfo QUENCHED_TUBE_CORAL_BLOCK = registerQuenchedBlock("quenched_tube_coral_block",
            Blocks.TUBE_CORAL_BLOCK,
            () -> new QuenchedCoralBlock(Blocks.DEAD_TUBE_CORAL_BLOCK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.TUBE_CORAL_BLOCK)
                            .setId(blockId("quenched_tube_coral_block"))));
    public static final QuenchedBlockInfo QUENCHED_BRAIN_CORAL_BLOCK = registerQuenchedBlock(
            "quenched_brain_coral_block", Blocks.BRAIN_CORAL_BLOCK,
            () -> new QuenchedCoralBlock(Blocks.DEAD_BRAIN_CORAL_BLOCK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_BLOCK)
                            .setId(blockId("quenched_brain_coral_block"))));
    public static final QuenchedBlockInfo QUENCHED_BUBBLE_CORAL_BLOCK = registerQuenchedBlock(
            "quenched_bubble_coral_block", Blocks.BUBBLE_CORAL_BLOCK,
            () -> new QuenchedCoralBlock(Blocks.DEAD_BUBBLE_CORAL_BLOCK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BUBBLE_CORAL_BLOCK)
                            .setId(blockId("quenched_bubble_coral_block"))));
    public static final QuenchedBlockInfo QUENCHED_FIRE_CORAL_BLOCK = registerQuenchedBlock("quenched_fire_coral_block",
            Blocks.FIRE_CORAL_BLOCK,
            () -> new QuenchedCoralBlock(Blocks.DEAD_FIRE_CORAL_BLOCK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE_CORAL_BLOCK)
                            .setId(blockId("quenched_fire_coral_block"))));
    public static final QuenchedBlockInfo QUENCHED_HORN_CORAL_BLOCK = registerQuenchedBlock("quenched_horn_coral_block",
            Blocks.HORN_CORAL_BLOCK,
            () -> new QuenchedCoralBlock(Blocks.DEAD_HORN_CORAL_BLOCK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HORN_CORAL_BLOCK)
                            .setId(blockId("quenched_horn_coral_block"))));

    public static final QuenchedBlockInfo QUENCHED_TUBE_CORAL_FAN = registerQuenchedBlock("quenched_tube_coral_fan",
            Blocks.TUBE_CORAL_FAN,
            () -> new QuenchedCoralFanBlock(Blocks.TUBE_CORAL_FAN,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.TUBE_CORAL_FAN)
                            .setId(blockId("quenched_tube_coral_fan"))));
    public static final QuenchedBlockInfo QUENCHED_BRAIN_CORAL_FAN = registerQuenchedBlock("quenched_brain_coral_fan",
            Blocks.BRAIN_CORAL_FAN,
            () -> new QuenchedCoralFanBlock(Blocks.BRAIN_CORAL_FAN,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BRAIN_CORAL_FAN)
                            .setId(blockId("quenched_brain_coral_fan"))));
    public static final QuenchedBlockInfo QUENCHED_BUBBLE_CORAL_FAN = registerQuenchedBlock("quenched_bubble_coral_fan",
            Blocks.BUBBLE_CORAL_FAN,
            () -> new QuenchedCoralFanBlock(Blocks.BUBBLE_CORAL_FAN,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BUBBLE_CORAL_FAN)
                            .setId(blockId("quenched_bubble_coral_fan"))));
    public static final QuenchedBlockInfo QUENCHED_FIRE_CORAL_FAN = registerQuenchedBlock("quenched_fire_coral_fan",
            Blocks.FIRE_CORAL_FAN,
            () -> new QuenchedCoralFanBlock(Blocks.FIRE_CORAL_FAN,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE_CORAL_FAN)
                            .setId(blockId("quenched_fire_coral_fan"))));
    public static final QuenchedBlockInfo QUENCHED_HORN_CORAL_FAN = registerQuenchedBlock("quenched_horn_coral_fan",
            Blocks.HORN_CORAL_FAN,
            () -> new QuenchedCoralFanBlock(Blocks.HORN_CORAL_FAN,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HORN_CORAL_FAN)
                            .setId(blockId("quenched_horn_coral_fan"))));

    public static final QuenchedBlockInfo QUENCHED_FARMLAND = registerQuenchedBlock("quenched_farmland",
            Blocks.FARMLAND, () -> new QuenchedFarmlandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND).setId(blockId("quenched_farmland"))));
    public static final QuenchedBlockInfo QUENCHED_SUGAR_CANE = registerQuenchedBlock("quenched_sugar_cane",
            Blocks.SUGAR_CANE, () -> new QuenchedSugarCaneBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE).setId(blockId("quenched_sugar_cane"))));

    public static final Map<String, QuenchedBlockInfo> REGISTERED_QUENCHED_BLOCKS = Map.ofEntries(
            Map.entry("kelp", QUENCHED_KELP), Map.entry("seagrass", QUENCHED_SEAGRASS),
            Map.entry("kelp_plant", QUENCHED_KELP_PLANT), Map.entry("sea_pickle", QUENCHED_SEA_PICKLE),
            Map.entry("tube_coral", QUENCHED_TUBE_CORAL_BLOCK), Map.entry("brain_coral", QUENCHED_BRAIN_CORAL_BLOCK),
            Map.entry("bubble_coral", QUENCHED_BUBBLE_CORAL_BLOCK), Map.entry("fire_coral", QUENCHED_FIRE_CORAL_BLOCK),
            Map.entry("horn_coral", QUENCHED_HORN_CORAL_BLOCK), Map.entry("tube_coral_fan", QUENCHED_TUBE_CORAL_FAN),
            Map.entry("brain_coral_fan", QUENCHED_BRAIN_CORAL_FAN),
            Map.entry("bubble_coral_fan", QUENCHED_BUBBLE_CORAL_FAN),
            Map.entry("fire_coral_fan", QUENCHED_FIRE_CORAL_FAN), Map.entry("horn_coral_fan", QUENCHED_HORN_CORAL_FAN),
            Map.entry("farmland", QUENCHED_FARMLAND), Map.entry("sugar_cane", QUENCHED_SUGAR_CANE)
    );

    private static MotrQuenched.QuenchedBlockInfo registerQuenchedBlock(
            String id,
            Block baseBlock,
            Supplier<Block> blockSupplier) {

        DeferredBlock<Block> block = BLOCKS.register(id, blockSupplier);

        MotrItems.registerQuenchedBlockItem(id, block);

        return new MotrQuenched.QuenchedBlockInfo(block, baseBlock);

    }

    public static ResourceKey<Block> blockId(String name) {
        return ResourceKey.create(Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MaterialsOfTheRift.MODID, name));
    }
}
