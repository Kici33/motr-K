package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = MaterialsOfTheRift.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        event.createProvider(MotrModelProvider::new);
        // Tags

        event.createProvider(MotrRecipeProvider.Runner::new);

        event.createBlockAndItemTags(MotrBlockTagProvider::new, MotrItemTagProvider::new);

        event.createProvider((output, lookupProvider) -> new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MotrBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));

        event.createProvider(MotrLanguageProvider::new);
    }
}