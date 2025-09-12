package com.materialsoftherift.motr;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER.comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    // Wind Column
    public static final ModConfigSpec.IntValue WIND_COLUMN_MAX_HEIGHT = BUILDER
            .comment("The maximum height of a wind column")
            .defineInRange("windColumnMaxHeight", 32, 1, 256);

    public static final ModConfigSpec.DoubleValue UPDRAFT_ACCELERATION = BUILDER
            .comment("The acceleration of an updraft")
            .defineInRange("windColumnUpdraftAcceleration", 0.075, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue UPDRAFT_VELOCITY_CAP = BUILDER
            .comment("The maximum velocity of an updraft")
            .defineInRange("windColumnUpdraftVelocityCap", 0.9, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue DOWNDRAFT_ACCELERATION = BUILDER
            .comment("The acceleration of a downdraft")
            .defineInRange("windColumnDowndraftAcceleration", -0.09, -10D, 0D);

    public static final ModConfigSpec.DoubleValue DOWNDRAFT_VELOCITY_CAP = BUILDER
            .comment("The maximum velocity of a downdraft")
            .defineInRange("windColumnDowndraftVelocityCap", -1, -10D, 0D);

    public static final ModConfigSpec.BooleanValue WIND_COLUMN_FALL_DAMAGE_MITIGATION = BUILDER
            .comment("Whether being in a wind column mitigates fall damage")
            .define("windColumnFallDamageMitigation", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
