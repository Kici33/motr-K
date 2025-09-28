package com.materialsoftherift.motr.datagen;

import com.google.gson.JsonObject;
import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.CompositeModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MotrModelProvider extends ModelProvider {

    private static final ResourceLocation DOT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MaterialsOfTheRift.MODID,
            "item/dot");

    private static final Map<String, String> COPPER_TEXTURE_OVERRIDES = Map.ofEntries(
            Map.entry("waxed_copper_block", "copper_block"), Map.entry("waxed_exposed_copper", "exposed_copper"),
            Map.entry("waxed_weathered_copper", "weathered_copper"),
            Map.entry("waxed_oxidized_copper", "oxidized_copper"),

            Map.entry("waxed_copper_bulb", "copper_bulb"),
            Map.entry("waxed_exposed_copper_bulb", "exposed_copper_bulb"),
            Map.entry("waxed_weathered_copper_bulb", "weathered_copper_bulb"),
            Map.entry("waxed_oxidized_copper_bulb", "oxidized_copper_bulb")
    );

    public MotrModelProvider(PackOutput output) {
        super(output, MaterialsOfTheRift.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {

        createOverlay(itemModels, DOT_TEXTURE);

        MotrNoGrav.REGISTERED_NOGRAV_BLOCKS.forEach((textureName, noGravInfo) -> {
            registerNoGravModel(blockModels, itemModels, noGravInfo, textureName);
        });

        blockModels.createTrivialBlock(MotrBlocks.HAY_CARPET.get(), TexturedModel.CARPET.updateTexture(
                mapping -> mapping.put(TextureSlot.WOOL, ResourceLocation.withDefaultNamespace("block/hay_block_top"))
        ));

        blockModels.createTrivialCube(MotrBlocks.MOTR.get());

        MotrSlabs.REGISTERED_STANDARD_SLABS.forEach((textureName, slabInfo) -> {
            registerStandardSlabModel(blockModels, slabInfo.slab().get(), textureName);
        });

        MotrSlabs.REGISTERED_GLASS_SLABS.forEach((textureId, slabInfo) -> {
            registerGlassSlabModel(blockModels, slabInfo.slab().get(), textureId);
        });

        MotrSlabs.REGISTERED_COPPER_SLABS.forEach((id, slabInfo) -> {
            if (id.contains("bulb")) {
                String baseTextureName = COPPER_TEXTURE_OVERRIDES.getOrDefault(id, id);

                System.out.println("Bulb texture mapping: " + id + " -> " + baseTextureName);

                registerCopperBulbSlabModel(blockModels, slabInfo.slab().get(), baseTextureName);

                ResourceLocation itemModel = MaterialsOfTheRift
                        .id("block/" + getBlockName(slabInfo.slab().get()) + "_unlit");
                itemModels.itemModelOutput.accept(
                        slabInfo.slab().get().asItem(), ItemModelUtils.plainModel(itemModel)
                );
            } else {
                String textureName = COPPER_TEXTURE_OVERRIDES.getOrDefault(id, id);
                registerTrimmSlabModel(blockModels, slabInfo.slab().get(), textureName, textureName, textureName);
            }
        });

        MotrSlabs.REGISTERED_SILKTOUCH_SLABS.forEach((textureId, slabInfo) -> {
            registerGlassSlabModel(blockModels, slabInfo.slab().get(), textureId);
        });

        MotrSlabs.REGISTERED_TRIMM_SLABS.forEach((id, slabInfo) -> {
            registerTrimmSlabModel(blockModels, slabInfo.slab().get(), id, id, id);
        });

        MotrSlabs.REGISTERED_DIRECTIONAL_SLABS.forEach((id, slabInfo) -> {
            {
                String side = id;
                String top = id;
                String bottom = id;
                switch (id) {
                    case "bone_block" -> {
                        side = "bone_block_side";
                        top = "bone_block_top";
                        bottom = "bone_block_top";
                    }
                    case "muddy_mangrove_roots" -> {
                        side = "muddy_mangrove_roots_side";
                        top = "muddy_mangrove_roots_top";
                        bottom = "muddy_mangrove_roots_top";
                    }
                    case "podzol", "mycelium", "dirt_path" -> {
                        side = id + "_side";
                        top = id + "_top";
                        bottom = "dirt";
                    }
                }
                registerDirectionalSlabModel(blockModels, slabInfo.slab().get(), side, top, bottom);
            }
        });

        MotrWalls.REGISTERED_STANDARD_WALLS.forEach((textureName, wallInfo) -> {
            registerWallModel(blockModels, wallInfo.wall().get(), textureName);

            ResourceLocation itemModel = ModelTemplates.WALL_INVENTORY.create(
                    wallInfo.wall().get(),
                    new TextureMapping().put(TextureSlot.WALL,
                            ResourceLocation.withDefaultNamespace("block/" + textureName)),
                    itemModels.modelOutput
            );

            itemModels.itemModelOutput.accept(
                    wallInfo.wall().get().asItem(), ItemModelUtils.plainModel(itemModel)
            );

        });

        MotrWalls.REGISTERED_GLASS_WALLS.forEach((textureName, wallInfo) -> {
            Block wall = wallInfo.wall().get();

            registerGlassWallModel(blockModels, wall, textureName);

            ResourceLocation itemModel = ExtendedModelTemplateBuilder.builder()
                    .parent(ResourceLocation.withDefaultNamespace("block/wall_inventory"))
                    .suffix("_inventory")
                    .requiredTextureSlot(TextureSlot.WALL)
                    .renderType("translucent")
                    .build()
                    .create(wall,
                            new TextureMapping().put(TextureSlot.WALL,
                                    ResourceLocation.withDefaultNamespace("block/" + textureName)),
                            blockModels.modelOutput);

            itemModels.itemModelOutput.accept(
                    wall.asItem(), ItemModelUtils.plainModel(itemModel)
            );
        });

        MotrButtons.REGISTERED_BUTTONS.forEach((textureName, buttonInfo) -> {
            registerButtonModel(blockModels, itemModels, buttonInfo.button().get(), textureName);
        });

        MotrFenceAndGate.REGISTERED_FENCES.forEach((textureName, fenceInfo) -> {
            registerFenceModel(blockModels, itemModels, fenceInfo.fence().get(), textureName);
        });

        MotrFenceAndGate.REGISTERED_FENCE_GATES.forEach((textureName, fenceGateInfo) -> {
            registerFenceGateModel(blockModels, itemModels, fenceGateInfo.fenceGate().get(), textureName);
        });

        MotrStairs.REGISTERED_STANDARD_STAIRS.forEach((textureName, stairInfo) -> {
            registerStandardStairModel(blockModels, stairInfo.stair().get(), textureName);
        });

        MotrQuenched.REGISTERED_QUENCHED_BLOCKS.forEach((texture, blockInfo) -> {

            Block base = blockInfo.baseBlock();
            Block quenched = blockInfo.block().get();

            if (base instanceof CoralFanBlock) {
                TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(base);
                ResourceLocation resLoc = texturedModel.create(base, blockModels.modelOutput);

                blockModels.blockStateOutput.accept(
                        BlockModelGenerators.createSimpleBlock(quenched, resLoc)
                );

                ResourceLocation defaultModel = ModelLocationUtils.getModelLocation(base.asItem());
                addWithOverlay(quenched.asItem(), defaultModel, DOT_TEXTURE, itemModels);
                return;
            }

            if (base instanceof SeaPickleBlock) {
                addWithOverlay(quenched.asItem(), ModelLocationUtils.getModelLocation(base), DOT_TEXTURE, itemModels);

                blockModels.blockStateOutput.accept(
                        MultiVariantGenerator.multiVariant(quenched)
                                .with(PropertyDispatch.property(SeaPickleBlock.PICKLES)
                                        .select(1, List.of(BlockModelGenerators.createRotatedVariants(
                                                ResourceLocation.withDefaultNamespace("block/sea_pickle"))))
                                        .select(2, List.of(BlockModelGenerators.createRotatedVariants(
                                                ResourceLocation.withDefaultNamespace("block/two_sea_pickles"))))
                                        .select(3, List.of(BlockModelGenerators.createRotatedVariants(
                                                ResourceLocation.withDefaultNamespace("block/three_sea_pickles"))))
                                        .select(4, List.of(BlockModelGenerators.createRotatedVariants(
                                                ResourceLocation.withDefaultNamespace("block/four_sea_pickles"))))
                                )
                );
                return;
            }

            if (base instanceof KelpBlock || base instanceof KelpPlantBlock || base instanceof SeagrassBlock
                    || base == Blocks.SUGAR_CANE) {

                ResourceLocation vanillaModel = ModelLocationUtils.getModelLocation(base);
                blockModels.blockStateOutput.accept(
                        BlockModelGenerators.createSimpleBlock(quenched, vanillaModel)
                );

                addWithOverlay(quenched.asItem(), ModelLocationUtils.getModelLocation(base), DOT_TEXTURE, itemModels);
                return;
            }

            addWithOverlay(quenched.asItem(), ModelLocationUtils.getModelLocation(base), DOT_TEXTURE, itemModels);

            ResourceLocation vanillaModel = ModelLocationUtils.getModelLocation(base);
            blockModels.blockStateOutput.accept(
                    BlockModelGenerators.createSimpleBlock(quenched, vanillaModel)
            );
        });

        Block windColumn = MotrBlocks.WIND_COLUMN.get();
        ResourceLocation windUpTexture = MaterialsOfTheRift.id("block/wind_column_up");
        ResourceLocation windDownTexture = MaterialsOfTheRift.id("block/wind_column_down");
        ResourceLocation windUpModel = ExtendedModelTemplateBuilder.builder()
            .parent(ResourceLocation.withDefaultNamespace("block/cube_all"))
            .requiredTextureSlot(TextureSlot.ALL)
            .renderType("translucent")
            .build()
            .create(MaterialsOfTheRift.id("block/wind_column_up"),
                TextureMapping.cube(windUpTexture),
                blockModels.modelOutput);
        ResourceLocation windDownModel = ExtendedModelTemplateBuilder.builder()
            .parent(ResourceLocation.withDefaultNamespace("block/cube_all"))
            .requiredTextureSlot(TextureSlot.ALL)
            .renderType("translucent")
            .build()
            .create(MaterialsOfTheRift.id("block/wind_column_down"),
                TextureMapping.cube(windDownTexture),
                blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(windColumn)
            .with(PropertyDispatch.property(MotrBlocks.WindColumnBlock.WIND_DIRECTION)
                .select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, windUpModel))
                .select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, windDownModel))
            )
        );
        addWithOverlay(windColumn.asItem(), windUpModel, DOT_TEXTURE, itemModels);
        addWithOverlay(MotrItems.WIND_CHARGE_ITEM.get(), ModelLocationUtils.getModelLocation(Items.WIND_CHARGE), DOT_TEXTURE, itemModels);
    }

    private void registerNoGravModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            MotrNoGrav.NoGravInfo info,
            String textureName) {
        var block = info.block().get();
        var item = block.asItem();
        ResourceLocation blockTex = ResourceLocation.withDefaultNamespace("block/" + textureName);
        ResourceLocation blockModel = ModelLocationUtils.getModelLocation(block);
        ModelTemplates.CUBE_ALL.create(blockModel, TextureMapping.cube(blockTex), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, blockModel));
        ResourceLocation itemModel = ModelLocationUtils.getModelLocation(item);
        ResourceLocation baseId = ResourceLocation.fromNamespaceAndPath(itemModel.getNamespace(),
                itemModel.getPath() + "_base");
        String base3dJson = """
                {
                  "parent": "%s"

                }
                """.formatted(blockModel);
        itemModels.modelOutput.accept(
                baseId, () -> com.google.gson.JsonParser.parseString(base3dJson).getAsJsonObject()
        );
        addWithOverlay(item, baseId, DOT_TEXTURE, itemModels);

    }

    private void createOverlay(ItemModelGenerators itemModels, ResourceLocation overlayTex) {

        String overlayGuiOnlyJson = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": { "layer0": "%s" },
                  "display": {
                    "gui": {
                      "translation": [0, 0, 6],
                      "scale": [1, 1, 1]
                    },
                    "thirdperson_righthand": { "scale": [0, 0, 0] },
                    "thirdperson_lefthand":  { "scale": [0, 0, 0] },
                    "firstperson_righthand": { "scale": [0, 0, 0] },
                    "firstperson_lefthand":  { "scale": [0, 0, 0] },
                    "ground":                { "scale": [0, 0, 0] },
                    "fixed":                 { "scale": [0, 0, 0] }
                  }
                }
                """.formatted(overlayTex);

        itemModels.modelOutput.accept(
                overlayTex.withSuffix("_overlay"),
                () -> com.google.gson.JsonParser.parseString(overlayGuiOnlyJson).getAsJsonObject()
        );

    }

    private void addWithOverlay(
            Item item,
            ResourceLocation baseModel,
            ResourceLocation overlay,
            ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(
                item, new CompositeModel.Unbaked(
                        java.util.List.of(
                                new BlockModelWrapper.Unbaked(overlay.withSuffix("_overlay"),
                                        java.util.Collections.emptyList()),
                                new BlockModelWrapper.Unbaked(baseModel, java.util.Collections.emptyList())
                        )
                )
        );
    }

    private void registerStandardSlabModel(BlockModelGenerators blockModels, Block slab, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.createWithSuffix(slab, "_top", mapping, blockModels.modelOutput);
        ResourceLocation cube = ModelTemplates.CUBE_ALL.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerStandardStairModel(BlockModelGenerators blockModels, Block stair, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stair, mapping, blockModels.modelOutput);
        ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stair, mapping, blockModels.modelOutput);
        ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stair, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stair, inner, straight, outer));
    }

    private void registerDirectionalSlabModel(
            BlockModelGenerators blockModels,
            Block slab,
            String sideTex,
            String topTex,
            String bottomTex) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + sideTex))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + topTex))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + bottomTex));

        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.createWithSuffix(slab, "_top", mapping, blockModels.modelOutput);
        ResourceLocation cube = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerGlassSlabModel(BlockModelGenerators blockModels, Block slab, String textureId) {
        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/" + textureId);

        TextureMapping slabMapping = new TextureMapping().put(TextureSlot.SIDE, texture)
                .put(TextureSlot.TOP, texture)
                .put(TextureSlot.BOTTOM, texture);

        TextureMapping cubeMapping = new TextureMapping().put(TextureSlot.ALL, texture);

        ResourceLocation bottom = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/glass_texture_slab_bottom"))
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.SIDE)
                .renderType("translucent")
                .build()
                .create(slab, slabMapping, blockModels.modelOutput);

        ResourceLocation top = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/glass_texture_slab_top"))
                .suffix("_top")
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.SIDE)
                .renderType("translucent")
                .build()
                .create(slab, slabMapping, blockModels.modelOutput);

        ResourceLocation cube = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all"))
                .suffix("_double")
                .requiredTextureSlot(TextureSlot.ALL)
                .renderType("translucent")
                .build()
                .create(slab, cubeMapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerTrimmSlabModel(
            BlockModelGenerators blockModels,
            Block slab,
            String sideTex,
            String topTex,
            String bottomTex) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + sideTex))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + topTex))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + bottomTex));

        ResourceLocation bottom = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_bottom"))
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ResourceLocation top = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_top"))
                .suffix("_top")
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ResourceLocation cube = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerWallModel(BlockModelGenerators blockModels, Block wall, String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation post = ModelTemplates.WALL_POST.create(wall, mapping, blockModels.modelOutput);
        ResourceLocation lowSide = ModelTemplates.WALL_LOW_SIDE.create(wall, mapping, blockModels.modelOutput);
        ResourceLocation tallSide = ModelTemplates.WALL_TALL_SIDE.create(wall, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createWall(wall, post, lowSide, tallSide)
        );

    }

    private void registerGlassWallModel(BlockModelGenerators blockModels, Block wall, String textureName) {
        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/" + textureName);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL, texture);

        ResourceLocation post = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_post"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .create(wall, mapping, blockModels.modelOutput);

        ResourceLocation lowSide = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_side"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .createWithSuffix(wall, "_side", mapping, blockModels.modelOutput);

        ResourceLocation tallSide = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_side_tall"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .createWithSuffix(wall, "_side_tall", mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createWall(wall, post, lowSide, tallSide)
        );
    }

    private void registerButtonModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block button,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.createWithSuffix(button, "_pressed", mapping,
                blockModels.modelOutput);
        ResourceLocation unpressed = ModelTemplates.BUTTON.create(button, mapping, blockModels.modelOutput);
        ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.createWithSuffix(button, "_inventory", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createButton(button, unpressed, pressed)
        );

        itemModels.itemModelOutput.accept(
                button.asItem(), ItemModelUtils.plainModel(inventory)
        );
    }

    private void registerFenceModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block fence,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation post = ModelTemplates.FENCE_POST.create(fence, mapping, blockModels.modelOutput);
        ResourceLocation side = ModelTemplates.FENCE_SIDE.create(fence, mapping, blockModels.modelOutput);
        ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.createWithSuffix(fence, "_inventory", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFence(fence, post, side)
        );

        itemModels.itemModelOutput.accept(
                fence.asItem(), ItemModelUtils.plainModel(inventory)
        );
    }

    private void registerFenceGateModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block fenceGate,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation closed = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, blockModels.modelOutput);
        ResourceLocation wall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping,
                blockModels.modelOutput);
        ResourceLocation wallOpen = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFenceGate(fenceGate, open, closed, wallOpen, wall, true)
        );

        itemModels.itemModelOutput.accept(
                fenceGate.asItem(), ItemModelUtils.plainModel(closed)
        );
    }

    private void registerCopperBulbSlabModel(BlockModelGenerators blockModels, Block slab, String baseTextureName) {

        System.out.println("=== DEBUG: Registering copper bulb slab model ===");
        System.out.println("Block: " + getBlockName(slab));
        System.out.println("Base texture: " + baseTextureName);

        BooleanProperty litProperty = null;
        BooleanProperty poweredProperty = null;

        System.out.println("Available properties:");
        for (var property : slab.getStateDefinition().getProperties()) {
            System.out.println("  - " + property.getName() + " (" + property.getClass().getSimpleName() + ")");
            if ("lit".equals(property.getName()) && property instanceof BooleanProperty) {
                litProperty = (BooleanProperty) property;
                System.out.println("    Found LIT property!");
            }
            if ("powered".equals(property.getName()) && property instanceof BooleanProperty) {
                poweredProperty = (BooleanProperty) property;
                System.out.println("    Found POWERED property!");
            }
        }

        if (litProperty == null) {
            litProperty = BlockStateProperties.LIT;
            System.out.println("Using fallback LIT property");
        }
        if (poweredProperty == null) {
            poweredProperty = BlockStateProperties.POWERED;
            System.out.println("Using fallback POWERED property");
        }

        TextureMapping unlitMapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + baseTextureName))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + baseTextureName))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + baseTextureName));

        TextureMapping unpoweredMapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_powered"))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_powered"))
                .put(TextureSlot.BOTTOM,
                        ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_powered"));

        TextureMapping litMapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit"))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit"))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit"));

        TextureMapping litPoweredMapping = new TextureMapping()
                .put(TextureSlot.SIDE,
                        ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit_powered"))
                .put(TextureSlot.TOP,
                        ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit_powered"))
                .put(TextureSlot.BOTTOM,
                        ResourceLocation.withDefaultNamespace("block/" + baseTextureName + "_lit_powered"));

        System.out.println("Creating models with textures:");
        System.out.println("  Unlit: " + baseTextureName);
        System.out.println("  Powered: " + baseTextureName + "_powered");
        System.out.println("  Lit: " + baseTextureName + "_lit");
        System.out.println("  Lit+Powered: " + baseTextureName + "_lit_powered");

        createCopperBulbSlabModels(blockModels, slab, unlitMapping, "_unlit");
        createCopperBulbSlabModels(blockModels, slab, unpoweredMapping, "_unpowered");
        createCopperBulbSlabModels(blockModels, slab, litMapping, "_lit");
        createCopperBulbSlabModels(blockModels, slab, litPoweredMapping, "_lit_powered");

        String blockName = getBlockName(slab);
        System.out.println("Generating blockstate for: " + blockName);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.properties(BlockStateProperties.SLAB_TYPE, litProperty, poweredProperty)
                        .select(SlabType.BOTTOM, false, false, Variant.variant()
                                .with(VariantProperties.MODEL, MaterialsOfTheRift.id("block/" + blockName + "_unlit")))
                        .select(SlabType.TOP, false, false,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_unlit_top")))
                        .select(SlabType.DOUBLE, false, false,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_unlit_double")))

                        .select(SlabType.BOTTOM, false, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_unpowered")))
                        .select(SlabType.TOP, false, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_unpowered_top")))
                        .select(SlabType.DOUBLE, false, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_unpowered_double")))

                        .select(SlabType.BOTTOM, true, false, Variant.variant()
                                .with(VariantProperties.MODEL, MaterialsOfTheRift.id("block/" + blockName + "_lit")))
                        .select(SlabType.TOP, true, false,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_lit_top")))
                        .select(SlabType.DOUBLE, true, false,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_lit_double")))

                        .select(SlabType.BOTTOM, true, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_lit_powered")))
                        .select(SlabType.TOP, true, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_lit_powered_top")))
                        .select(SlabType.DOUBLE, true, true,
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                MaterialsOfTheRift.id("block/" + blockName + "_lit_powered_double")))
                ));

        System.out.println("=== END DEBUG ===");
    }

    private void createCopperBulbSlabModels(
            BlockModelGenerators blockModels,
            Block slab,
            TextureMapping mapping,
            String suffix) {
        ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_bottom"))
                .suffix(suffix)
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_top"))
                .suffix(suffix + "_top")
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(slab, suffix + "_double", mapping, blockModels.modelOutput);
    }

    private String getBlockName(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}
