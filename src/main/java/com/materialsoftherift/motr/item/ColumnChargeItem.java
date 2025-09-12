package com.materialsoftherift.motr.item;

import com.materialsoftherift.motr.Config;
import com.materialsoftherift.motr.init.MotrBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColumnChargeItem extends Item {

    public ColumnChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.literal("Right-click the top of a downdraft column to remove the top block"));
        tooltip.add(Component.literal("Right-click ground to create a 1-block Updraft column"));
        tooltip.add(Component.literal("Right-click the top of an Updraft column to extend it upward by 1 block"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("Sneak + Right-click the top of an Updraft column to remove the top block"));
        tooltip.add(Component.literal("Sneak + Right-click ground to create a 1-block Downdraft column"));
        tooltip.add(Component.literal("Sneak + Right-click the bottom of a Downdraft column to extend it downward by 1 block"));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.PASS;

        BlockPos clickedPos = context.getClickedPos();
        boolean isSneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (clickedState.isAir()) return InteractionResult.PASS;

        if (clickedState.is(MotrBlocks.WIND_COLUMN.get())) {
            Direction direction = clickedState.getValue(MotrBlocks.WindColumnBlock.WIND_DIRECTION);
            if (direction == Direction.UP) {
                BlockPos topPos = findColumnEnd(level, clickedPos, Direction.UP);
                if (isSneaking) {
                    level.removeBlock(topPos, false);
                    return InteractionResult.SUCCESS;
                }
                if (level.isEmptyBlock(topPos.above()) && canExtend(level, clickedPos, Direction.UP)) {
                    level.setBlock(topPos.above(), MotrBlocks.WIND_COLUMN.get().defaultBlockState()
                            .setValue(MotrBlocks.WindColumnBlock.WIND_DIRECTION, Direction.UP), 3);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.FAIL;
            }

            if (isSneaking) {
                BlockPos columnEnd = findColumnEnd(level, clickedPos, Direction.DOWN);
                if (level.isEmptyBlock(columnEnd.below()) && canExtend(level, clickedPos, Direction.DOWN)) {
                    level.setBlock(columnEnd.below(), MotrBlocks.WIND_COLUMN.get().defaultBlockState()
                            .setValue(MotrBlocks.WindColumnBlock.WIND_DIRECTION, Direction.DOWN), 3);
                }
                return InteractionResult.SUCCESS;
            }

            BlockPos topPos = findColumnEnd(level, clickedPos, Direction.DOWN);
            level.removeBlock(topPos, false);
            return InteractionResult.SUCCESS;

        }

        if (level.isEmptyBlock(clickedPos.relative(context.getClickedFace()))) {
            level.setBlock(clickedPos.relative(context.getClickedFace()), MotrBlocks.WIND_COLUMN.get().defaultBlockState()
                    .setValue(MotrBlocks.WindColumnBlock.WIND_DIRECTION, isSneaking ? Direction.DOWN : Direction.UP), 3);
        }
        return InteractionResult.SUCCESS;
    }

    private BlockPos findColumnEnd(Level level, BlockPos startPos, Direction direction) {
        BlockPos.MutableBlockPos mutable = startPos.mutable();
        while (level.getBlockState(mutable.relative(direction)).is(MotrBlocks.WIND_COLUMN.get())) {
            mutable.move(direction);
        }
        return mutable.immutable();
    }

    private boolean canExtend(Level level, BlockPos pos, Direction direction) {
        int height = 0;
        BlockPos.MutableBlockPos mutable = pos.mutable();
        while (level.getBlockState(mutable).is(MotrBlocks.WIND_COLUMN.get())) {
            height++;
            mutable.move(direction.getOpposite());
        }
        return height < Config.WIND_COLUMN_MAX_HEIGHT.get();
    }
}
