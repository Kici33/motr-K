package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MotrCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MaterialsOfTheRift.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOTR_TAB = CREATIVE_MODE_TABS.register(
            MaterialsOfTheRift.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.motr"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> new ItemStack(MotrBlocks.MOTR.get()))
                    .displayItems((parameters, output) -> {
                        // Changed to omit 'dev blocks'
                        MotrItems.BLOCK_ITEMS.forEach(blockItem -> output.accept(blockItem.get()));
                        output.accept(MotrItems.WIND_CHARGE_ITEM.get());
                    })
                    .build());

}