package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MotrUnbound {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MaterialsOfTheRift.MODID);

    public record UnboundBlockInfo(DeferredBlock<Block> block, Supplier<Item> baseItem, int stage) {}
    public record UnboundSimpleBlockInfo(DeferredBlock<Block> block, Supplier<Item> baseItem) {}

    public static class UnboundCropBlock extends CropBlock {

        public UnboundCropBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
            return true;
        }

        @Override
        public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
            return true;
        }

        @Override
        protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess,
                                                  @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos,
                                                  @NotNull BlockState neighborState, @NotNull RandomSource random) {
            return state;
        }

    }

    public static class UnboundNetherWartBlock extends NetherWartBlock {
        public UnboundNetherWartBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }

        @Override
        public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
            return true;
        }
    }

    public static class UnboundCocoaBlock extends HorizontalDirectionalBlock {

        public static EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
        private static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), Block.box(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), Block.box(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)};
        private static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), Block.box(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), Block.box(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)};
        private static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), Block.box(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), Block.box(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)};
        private static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), Block.box(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), Block.box(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)};
        private final int stage;

        public UnboundCocoaBlock(Properties pProperties, int stage) {
            super(pProperties);
            this.stage = stage;
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
            int i = this.stage;
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SOUTH_AABB[i];
                case WEST -> WEST_AABB[i];
                case EAST -> EAST_AABB[i];
                default -> NORTH_AABB[i];
            };
        }

        @Override
        protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
            return CocoaBlock.CODEC;
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(FACING);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            BlockState blockstate = this.defaultBlockState();
            LevelReader levelreader = pContext.getLevel();
            BlockPos blockpos = pContext.getClickedPos();

            for (Direction direction : pContext.getNearestLookingDirections()) {
                if (direction.getAxis().isHorizontal()) {
                    blockstate = blockstate.setValue(FACING, direction);
                    return blockstate;
                }
            }
            return null;
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }

        @Override
        public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
            return true;
        }
    }

    public static class UnboundBambooBlock extends Block {

        private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);

        public UnboundBambooBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
            return SHAPE;
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }

        @Override
        public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
            return true;
        }
    }

    public static class UnboundLilyPadBlock extends WaterlilyBlock {

        public UnboundLilyPadBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }

        @Override
        public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
            return true;
        }
    }

    public static class UnboundVineBlock extends VineBlock {

        public UnboundVineBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }

    }

    public static class UnboundSugarCaneBlock extends Block {

        public UnboundSugarCaneBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }
    }

    public static class UnboundCactusBlock extends Block {

        public UnboundCactusBlock(Properties properties) {
            super(properties);
        }

        @Override
        protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
            return true;
        }
    }

    private static Map<Integer, UnboundBlockInfo> createCropStages(String cropName, int stages, Supplier<Item> seedItem, BiFunction<String, Integer, Block> blockCreator) {
        Map<Integer, DeferredBlock<Block>> stageBlocks = new HashMap<>();
        for (int i = 0; i < stages; i++) {
            String name = "unbound_" + cropName + "_stage" + i;
            int stage = i;
            DeferredBlock<Block> block = BLOCKS.register(name, () -> blockCreator.apply(name, stage));
            MotrItems.registerSimpleBlockItem(name, block);
            stageBlocks.put(i, block);
        }

        Map<Integer, UnboundBlockInfo> infoMap = new HashMap<>();
        for (int i = 0; i < stages; i++) {
            DeferredBlock<Block> currentBlock = stageBlocks.get(i);
            final int stage = i;
            Supplier<Item> baseItem = (i == 0) ? seedItem : () -> stageBlocks.get(stage - 1).get().asItem();
            infoMap.put(i, new UnboundBlockInfo(currentBlock, baseItem, i));
        }
        return infoMap;
    }


    public static final Map<Integer, UnboundBlockInfo> UNBOUND_WHEAT_STAGES = createCropStages("wheat", 8,
            () -> Items.WHEAT_SEEDS, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_CARROT_STAGES = createCropStages("carrot", 4,
            () -> Items.CARROT, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_POTATO_STAGES = createCropStages("potato", 4,
            () -> Items.POTATO, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_BEETROOT_STAGES = createCropStages("beetroot", 4,
            () -> Items.BEETROOT_SEEDS, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_NETHER_WART_STAGES = createCropStages("nether_wart", 3,
            () -> Items.NETHER_WART, (name, stage) -> new UnboundNetherWartBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_MELON_STEM_STAGES = createCropStages("melon_stem", 8,
            () -> Items.MELON_SEEDS, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MELON_STEM).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_PUMPKIN_STEM_STAGES = createCropStages("pumpkin_stem", 8,
            () -> Items.PUMPKIN_SEEDS, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN_STEM).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_TORCHFLOWER_STAGES = createCropStages("torchflower", 2,
            () -> Items.TORCHFLOWER_SEEDS, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCHFLOWER_CROP).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_PITCHER_CROP_STAGES = createCropStages("pitcher_crop", 5,
            () -> Items.PITCHER_POD, (name, stage) -> new UnboundCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PITCHER_CROP).setId(blockId(name))));

    public static final Map<Integer, UnboundBlockInfo> UNBOUND_COCOA_STAGES = createCropStages("cocoa", 3,
            () -> Items.COCOA_BEANS, (name, stage) -> new UnboundCocoaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COCOA).setId(blockId(name)), stage));

    public static ResourceKey<Block> blockId(String name) {
        return ResourceKey.create(Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MaterialsOfTheRift.MODID, name));
    }

    public static final DeferredBlock<Block> UNBOUND_BAMBOO_SAPLING = BLOCKS.register("unbound_bamboo_sapling", () ->
            new UnboundBambooBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(1.0F).sound(SoundType.BAMBOO)
                    .noOcclusion().setId(blockId("unbound_bamboo_sapling"))));

    public static final UnboundSimpleBlockInfo UNBOUND_CACTUS = registerSimpleUnbound("unbound_cactus", () -> Items.CACTUS, (name) -> new UnboundCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL).setId(blockId(name))));
    public static final UnboundSimpleBlockInfo UNBOUND_SUGAR_CANE = registerSimpleUnbound("unbound_sugar_cane", () -> Items.SUGAR_CANE, (name) -> new UnboundSugarCaneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).setId(blockId(name))));
    public static final UnboundSimpleBlockInfo UNBOUND_VINE = registerSimpleUnbound("unbound_vine", () -> Items.VINE, (name) -> new UnboundVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.VINE).setId(blockId(name))));
    public static final UnboundSimpleBlockInfo UNBOUND_LILY_PAD = registerSimpleUnbound("unbound_lily_pad", () -> Items.LILY_PAD, (name) -> new UnboundLilyPadBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).setId(blockId(name))));

    public static final Map<String, UnboundSimpleBlockInfo> SIMPLE_UNBOUND_BLOCKS = Map.of(
            "cactus", UNBOUND_CACTUS,
            "sugar_cane", UNBOUND_SUGAR_CANE,
            "vine", UNBOUND_VINE,
            "lily_pad", UNBOUND_LILY_PAD
    );

    private static UnboundSimpleBlockInfo registerSimpleUnbound(String name, Supplier<Item> baseItem, Function<String, Block> blockCreator) {
        DeferredBlock<Block> block = BLOCKS.register(name, () -> blockCreator.apply(name));
        MotrItems.registerSimpleBlockItem(name, block);
        return new UnboundSimpleBlockInfo(block, baseItem);
    }

    static {
        MotrItems.registerSimpleBlockItem("unbound_bamboo_sapling", UNBOUND_BAMBOO_SAPLING);
    }

}
