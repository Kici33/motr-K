package com.materialsoftherift.motr.event;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.MotrQuenched;
import com.materialsoftherift.motr.init.MotrUnbound;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.stream.Stream;

@EventBusSubscriber(modid = MaterialsOfTheRift.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MotrQuenched.REGISTERED_QUENCHED_BLOCKS
                .forEach((name, info) -> ItemBlockRenderTypes.setRenderLayer(info.block().get(), RenderType.cutout()));

        getAllUnboundBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> {
                    if (level != null && pos != null) {
                        return BiomeColors.getAverageGrassColor(level, pos);
                    } else {
                        return GrassColor.getDefaultColor();
                    }
                }, MotrQuenched.QUENCHED_SUGAR_CANE.block().get()
        );

        event.register(
                (state, level, pos, tintIndex) -> {
                    if (level != null && pos != null) {
                        return BiomeColors.getAverageGrassColor(level, pos);
                    } else {
                        return GrassColor.getDefaultColor();
                    }
                }, getTintedUnboundBlocks()
        );

    }

    private static Stream<Block> getAllUnboundBlocks() {
        return Stream.concat(Stream.of(
                MotrUnbound.UNBOUND_WHEAT_STAGES.values(),
                MotrUnbound.UNBOUND_CARROT_STAGES.values(),
                MotrUnbound.UNBOUND_POTATO_STAGES.values(),
                MotrUnbound.UNBOUND_BEETROOT_STAGES.values(),
                MotrUnbound.UNBOUND_NETHER_WART_STAGES.values(),
                MotrUnbound.UNBOUND_MELON_STEM_STAGES.values(),
                MotrUnbound.UNBOUND_PUMPKIN_STEM_STAGES.values(),
                MotrUnbound.UNBOUND_TORCHFLOWER_STAGES.values(),
                MotrUnbound.UNBOUND_PITCHER_CROP_STAGES.values(),
                MotrUnbound.UNBOUND_COCOA_STAGES.values()
            ).flatMap(java.util.Collection::stream).map(info -> info.block().get()),
            Stream.concat(
                    Stream.of(MotrUnbound.UNBOUND_BAMBOO_SAPLING.get()),
                    MotrUnbound.SIMPLE_UNBOUND_BLOCKS.values().stream().map(info -> info.block().get())
            )
        );
    }

    private static Block[] getTintedUnboundBlocks() {
        return Stream.concat(
                Stream.of(
                        MotrUnbound.UNBOUND_WHEAT_STAGES.values(),
                        MotrUnbound.UNBOUND_MELON_STEM_STAGES.values(),
                        MotrUnbound.UNBOUND_PUMPKIN_STEM_STAGES.values()
                ).flatMap(java.util.Collection::stream).map(info -> info.block().get()),
                Stream.concat(
                        Stream.of(MotrUnbound.UNBOUND_BAMBOO_SAPLING.get()),
                        Stream.of(MotrUnbound.UNBOUND_VINE.block().get(), MotrUnbound.UNBOUND_LILY_PAD.block().get())
                )
        ).toArray(Block[]::new);
    }

}