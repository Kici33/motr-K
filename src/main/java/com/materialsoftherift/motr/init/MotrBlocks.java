package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.Config;
import com.materialsoftherift.motr.MaterialsOfTheRift;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MotrBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MaterialsOfTheRift.MODID);

    public static class WindColumnBlock extends Block {

        public static final EnumProperty<Direction> WIND_DIRECTION = BlockStateProperties.FACING;

        public WindColumnBlock(Properties properties) {
            super(properties);
            registerDefaultState(this.getStateDefinition().any().setValue(WIND_DIRECTION, Direction.UP));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(WIND_DIRECTION);
        }

        @Override
        protected void entityInside(@NotNull BlockState state, @NotNull Level level,
                                    @NotNull BlockPos pos, @NotNull Entity entity) {
            Direction dir = state.getValue(WIND_DIRECTION);
            Vec3 motion = entity.getDeltaMovement();

            if (dir == Direction.UP) {
                double yMotion = Math.min(Config.UPDRAFT_VELOCITY_CAP.get(), motion.y + Config.UPDRAFT_ACCELERATION.get());
                entity.setDeltaMovement(motion.x, yMotion, motion.z);
            } else if (dir == Direction.DOWN) {
                double yMotion = Math.max(Config.DOWNDRAFT_VELOCITY_CAP.get(), motion.y + Config.DOWNDRAFT_ACCELERATION.get());
                entity.setDeltaMovement(motion.x, yMotion, motion.z);
            }

            if (Config.WIND_COLUMN_FALL_DAMAGE_MITIGATION.get()) {
                entity.fallDistance = 0.0F;
            }

        }

        @Override
        protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess,
                                                  @NotNull BlockPos pos, @NotNull Direction facing, @NotNull BlockPos neighborPos,
                                                  @NotNull BlockState neighborState, @NotNull RandomSource random) {

            Direction dir = state.getValue(WIND_DIRECTION);

            if (facing == dir.getOpposite() && !state.is(this)) {
                tick(state, (ServerLevel) level, pos, random);
            }

            return super.updateShape(state, level, scheduledTickAccess, pos, facing, neighborPos, neighborState, random);
        }

        @Override
        public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
            if (random.nextInt(4) == 0) {
                Direction dir = state.getValue(WIND_DIRECTION);
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                if (dir == Direction.UP) {
                    level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0D, 0.1D, 0.0D);
                    return;
                }
                level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0D, -0.1D, 0.0D);
            }
        }
    }

    public static final DeferredBlock<Block> MOTR = registerDevBlock("motr",
            () -> new Block(BlockBehaviour.Properties.of().setId(blockId("motr"))));

    public static final DeferredBlock<CarpetBlock> HAY_CARPET = registerCarpet("hay_carpet", Blocks.HAY_BLOCK);

    public static final DeferredBlock<WindColumnBlock> WIND_COLUMN = registerBlock("wind_column",
            () -> new WindColumnBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POWDER_SNOW).setId(blockId("wind_column")).strength(0.5f).noCollission().noLootTable()));

    public static <T extends Block> DeferredBlock<T> registerBlock(String key, Supplier<T> sup) {
        DeferredBlock<T> register = BLOCKS.register(key, sup);
        MotrItems.registerSimpleBlockItem(key, register);
        return register;
    }

    private static <T extends Block> DeferredBlock<T> registerDevBlock(String key, Supplier<T> sup) {
        DeferredBlock<T> register = BLOCKS.register(key, sup);
        MotrItems.registerSimpleDevBlockItem(key, register);
        return register;
    }

    private static DeferredBlock<CarpetBlock> registerCarpet(String id, Block baseBlock) {
        return registerBlock(id, () -> new CarpetBlock(
                BlockBehaviour.Properties.ofFullCopy(baseBlock).setId(blockId(id))
        ));
    }

    public static ResourceKey<Block> blockId(String name) {
        return ResourceKey.create(Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MaterialsOfTheRift.MODID, name));
    }
}