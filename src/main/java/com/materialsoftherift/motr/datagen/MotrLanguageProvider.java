package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class MotrLanguageProvider extends LanguageProvider {

    public MotrLanguageProvider(PackOutput output) {
        super(output, MaterialsOfTheRift.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Helpers are available for various common object types. Every helper has two variants: an add() variant
        // for the object itself, and an addTypeHere() variant that accepts a supplier for the object.
        // The different names for the supplier variants are required due to generic type erasure.
        // All following examples assume the existence of the values as suppliers of the needed type.
        // See https://docs.neoforged.net/docs/1.21.1/resources/client/i18n/ for translation of other types.

        // Adds a block translation.

        addNoGravTranslations(MotrNoGrav.REGISTERED_NOGRAV_BLOCKS);

        addQuenchedBlockTranslations(MotrQuenched.REGISTERED_QUENCHED_BLOCKS);

        addWallTranslations(MotrWalls.REGISTERED_STANDARD_WALLS);
        addWallTranslations(MotrWalls.REGISTERED_GLASS_WALLS);

        addSlabTranslations(MotrSlabs.REGISTERED_STANDARD_SLABS);
        addSlabTranslations(MotrSlabs.REGISTERED_GLASS_SLABS);
        addSlabTranslations(MotrSlabs.REGISTERED_TRIMM_SLABS);
        addSlabTranslations(MotrSlabs.REGISTERED_DIRECTIONAL_SLABS);
        addSlabTranslations(MotrSlabs.REGISTERED_SILKTOUCH_SLABS);
        addSlabTranslations(MotrSlabs.REGISTERED_COPPER_SLABS);

        addButtonTranslations(MotrButtons.REGISTERED_BUTTONS);

        addFenceTranslations(MotrFenceAndGate.REGISTERED_FENCES);

        addFenceGateTranslations(MotrFenceAndGate.REGISTERED_FENCE_GATES);

        addStairTranslations(MotrStairs.REGISTERED_STANDARD_STAIRS);

        add("itemGroup." + MaterialsOfTheRift.MODID, "MotR");

        addBlock(MotrBlocks.HAY_CARPET, "Hay Carpet");
        addBlock(MotrBlocks.WIND_COLUMN, "Wind Column");
        addItem(MotrItems.WIND_CHARGE_ITEM, "Wind Charge");
    }

    private static @NotNull String getTranslationString(Block block) {
        String idString = BuiltInRegistries.BLOCK.getKey(block).getPath();
        return snakeCaseToCapitalizedCase(idString);
    }

    private static @NotNull String snakeCaseToCapitalizedCase(String idString) {
        StringBuilder sb = new StringBuilder();
        for (String word : idString.toLowerCase(Locale.ROOT).split("_")) {
            sb.append(word.substring(0, 1).toUpperCase(Locale.ROOT));
            sb.append(word.substring(1));
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    private void addNoGravTranslations(Map<String, MotrNoGrav.NoGravInfo> noGravMap) {
        noGravMap.forEach((baseName, noGravInfo) -> {
            String translation = "No Grav " + snakeCaseToCapitalizedCase(baseName);
            addBlock(noGravInfo.block(), translation);
        });
    }

    private void addStairTranslations(Map<String, MotrStairs.StairInfo> stairMap) {
        stairMap.forEach((baseName, stairInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Stairs";
            addBlock(stairInfo.stair(), translation);
        });
    }

    private void addQuenchedBlockTranslations(Map<String, MotrQuenched.QuenchedBlockInfo> blockMap) {
        blockMap.forEach((baseName, quenchedBlockInfo) -> {
            String translation = "Quenched " + snakeCaseToCapitalizedCase(baseName);
            addBlock(quenchedBlockInfo.block(), translation);
        });
    }

    private void addFenceGateTranslations(Map<String, MotrFenceAndGate.FenceGateInfo> gateMap) {
        gateMap.forEach((baseName, gateInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Fence Gate";
            addBlock(gateInfo.fenceGate(), translation);
        });
    }

    private void addFenceTranslations(Map<String, MotrFenceAndGate.FenceInfo> fenceMap) {
        fenceMap.forEach((baseName, fenceInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Fence";
            addBlock(fenceInfo.fence(), translation);
        });
    }

    private void addButtonTranslations(Map<String, MotrButtons.ButtonInfo> buttonMap) {
        buttonMap.forEach((baseName, buttonInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Button";
            addBlock(buttonInfo.button(), translation);
        });
    }

    private void addSlabTranslations(Map<String, MotrSlabs.SlabInfo> slabMap) {
        slabMap.forEach((baseName, slabInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Slab";
            addBlock(slabInfo.slab(), translation);
        });
    }

    private void addWallTranslations(Map<String, MotrWalls.WallInfo> wallMap) {
        wallMap.forEach((baseName, wallInfo) -> {
            String translation = snakeCaseToCapitalizedCase(baseName) + " Wall";
            addBlock(wallInfo.wall(), translation);
        });
    }

}