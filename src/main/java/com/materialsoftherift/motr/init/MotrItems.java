package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.item.ColumnChargeItem;
import com.materialsoftherift.motr.item.QuenchedBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WindChargeItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MotrItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MaterialsOfTheRift.MODID);
    public static final List<DeferredItem<BlockItem>> BLOCK_ITEMS = new ArrayList<>();
    public static final List<DeferredItem<BlockItem>> DEV_BLOCK_ITEMS = new ArrayList<>();

    public static <T extends Block> DeferredItem<BlockItem> registerSimpleBlockItem(String id, DeferredBlock<T> block) {
        DeferredItem<BlockItem> simpleBlockItem = ITEMS.registerSimpleBlockItem(id, block);
        BLOCK_ITEMS.add(simpleBlockItem);
        return simpleBlockItem;
    }

    public static <T extends Block> void registerQuenchedBlockItem(String id, DeferredBlock<T> block) {
        DeferredItem<BlockItem> simpleBlockItem = ITEMS.register(id,
                () -> new QuenchedBlockItem(block.get(),
                        new Item.Properties().setId(ResourceKey.create(Registries.ITEM, block.getId()))
                                .useBlockDescriptionPrefix()));

        BLOCK_ITEMS.add(simpleBlockItem);
    }

    public static <T extends Block> DeferredItem<BlockItem> registerSimpleDevBlockItem(
            String id,
            DeferredBlock<T> block) {
        DeferredItem<BlockItem> simpleBlockItem = ITEMS.registerSimpleBlockItem(id, block);
        DEV_BLOCK_ITEMS.add(simpleBlockItem);
        return simpleBlockItem;
    }

    public static final DeferredItem<ColumnChargeItem> WIND_CHARGE_ITEM = ITEMS.register("wind_charge",
            () -> new ColumnChargeItem(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,  ResourceLocation.fromNamespaceAndPath(MaterialsOfTheRift.MODID, "wind_charge")))));

}
