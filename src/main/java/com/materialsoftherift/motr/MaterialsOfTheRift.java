package com.materialsoftherift.motr;

import com.materialsoftherift.motr.init.*;
import com.materialsoftherift.motr.init.recipe.MotrRecipeCategories;
import com.materialsoftherift.motr.init.recipe.MotrRecipeDisplayTypes;
import com.materialsoftherift.motr.init.recipe.MotrRecipeSerializers;
import com.materialsoftherift.motr.init.recipe.MotrRecipeTypes;
import com.materialsoftherift.motr.init.recipe.MotrSlotDisplayTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(MaterialsOfTheRift.MODID)
public class MaterialsOfTheRift {
    public static final String MODID = "motr";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MaterialsOfTheRift(IEventBus modEventBus, ModContainer modContainer) {
        // Vanilla elements
        MotrBlocks.BLOCKS.register(modEventBus);
        MotrBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MotrCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        MotrItems.ITEMS.register(modEventBus);

        // Recipes
        MotrRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MotrRecipeTypes.RECIPE_TYPES.register(modEventBus);
        MotrRecipeCategories.RECIPE_BOOK_CATEGORIES.register(modEventBus);
        MotrSlotDisplayTypes.SLOT_DISPLAY_TYPES.register(modEventBus);
        MotrRecipeDisplayTypes.RECIPE_DISPLAY_TYPES.register(modEventBus);

        // Block variants
        MotrQuenched.BLOCKS.register(modEventBus);
        MotrNoGrav.BLOCKS.register(modEventBus);
        MotrSlabs.BLOCKS.register(modEventBus);
        MotrFenceAndGate.BLOCKS.register(modEventBus);
        MotrStairs.BLOCKS.register(modEventBus);
        MotrWalls.BLOCKS.register(modEventBus);
        MotrButtons.BLOCKS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Helper method to get a {@code ResourceLocation} with our Mod Id and a passed in name
     *
     * @param name the name to create the {@code ResourceLocation} with
     * @return A {@code ResourceLocation} with the given name
     */
    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    /**
     * Helper method to get a translationId string containing our mod id.
     *
     * @param category The category of the translationId (becomes a prefix)
     * @param item     The translationId item
     * @return A combination of category, our mod id and the item. e.g. if category is "item" and item is
     *         "nosering.description" the result is "item.wotr.nosering.description"
     */
    public static String translationId(String category, String item) {
        return category + "." + MODID + "." + item;
    }

    /**
     * Helper method to get a translationId string containing any mod id.
     *
     * @param category The category of the translationId (becomes a prefix)
     * @param item     The ResourceLocation item
     * @return A combination of category, the mod id and the item. e.g. if category is "item" and item is
     *         "wotr:nosering.description" the result is "item.wotr.nosering.description"
     */
    public static String translationId(String category, ResourceLocation item) {
        return category + "." + item.getNamespace() + "." + item.getPath();
    }

    /**
     * Helper method to get a {@code TagKey} with our Mod Id and a passed in name
     *
     * @param name the name to create the {@code TagKey} with
     * @return A {@code TagKey} with the given name
     */
    public static <T> TagKey<T> tagId(ResourceKey<? extends Registry<T>> registry, String name) {
        return TagKey.create(registry, id(name));
    }

}
