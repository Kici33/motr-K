package com.materialsoftherift.motr.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBehaviour.class)
public interface InvokerBlockBehaviour {

    @Invoker("getShape")
    VoxelShape invokeGetShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);

    @Invoker("getCollisionShape")
    VoxelShape invokeGetCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);
}
