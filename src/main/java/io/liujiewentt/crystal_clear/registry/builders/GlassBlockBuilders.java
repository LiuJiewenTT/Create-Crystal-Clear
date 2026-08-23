package com.adonis.prism.registry.builders;

import com.adonis.prism.CreatePrism;
import com.adonis.prism.block.glass.*;
import com.adonis.prism.block.illumination.*;
import com.adonis.prism.registry.CPSpriteShifts;
import com.adonis.prism.util.CasingHolder;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.MetalScaffoldingBlockItem;
import com.simibubi.create.content.decoration.MetalScaffoldingCTBehaviour;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.createmod.catnip.data.Couple;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class GlassBlockBuilders {

    // Glass Casing Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<GlassCasing> glassCasing(
            T reg, CasingHolder holder, boolean clear) {
        String name = holder.name();
        String newName = name + (clear ? "_clear_glass_casing" : "_glass_casing");
        CTSpriteShiftEntry ctEntry = CPSpriteShifts.getCasingShift(name, clear);

        return reg.block(newName, GlassCasing::new)
                .initialProperties(() -> Blocks.GLASS)
                .properties(p -> p.sound(SoundType.GLASS).noOcclusion())
                .properties(GlassBlockBuilders::glassProperties)
                .addLayer(() -> RenderType::cutout)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ctEntry)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ctEntry)))
                .tag(AllTags.AllBlockTags.CASING.tag)
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, c.get())
                                .requires(holder.casing().get())
                                .requires(AllPaletteBlocks.FRAMED_GLASS.get())
                                .unlockedBy("has_casing", RegistrateRecipeProvider.has(AllTags.AllItemTags.CASING.tag))
                                .save(p, CreatePrism.asResource("crafting/glass_casing/" + c.getName())))
                .item()
                .tag(AllTags.AllItemTags.CASING.tag)
                .build()
                .register();
    }

    // Illumination Casing Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<IlluminationCasing> illuminationCasing(
            T reg, CasingHolder holder) {
        String name = holder.name();
        String newName = name + "_illumination_casing";
        CTSpriteShiftEntry ctEntry = CPSpriteShifts.getIlluminationCasingShift(name);

        return reg.block(newName, IlluminationCasing::new)
                .initialProperties(() -> Blocks.GLOWSTONE)
                .properties(p -> p.sound(SoundType.GLASS).noOcclusion().lightLevel(s -> 15))
                .addLayer(() -> RenderType::translucent)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ctEntry)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ctEntry)))
                .tag(AllTags.AllBlockTags.CASING.tag)
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, c.get())
                                .requires(holder.casing().get())
                                .requires(Items.GLOWSTONE_DUST, 4)
                                .unlockedBy("has_casing", RegistrateRecipeProvider.has(AllTags.AllItemTags.CASING.tag))
                                .save(p, CreatePrism.asResource("crafting/illumination_casing/" + c.getName())))
                .item()
                .tag(AllTags.AllItemTags.CASING.tag)
                .build()
                .register();
    }

    // Glass Encased Shaft Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<GlassEncasedShaft> glassEncasedShaft(
            T reg, String casing, boolean clear,
            NonNullFunction<BlockBehaviour.Properties, GlassEncasedShaft> factory) {
        String newName = casing + (clear ? "_clear_glass_encased_shaft" : "_glass_encased_shaft");
        CTSpriteShiftEntry ctEntry = CPSpriteShifts.getCasingShift(casing, clear);

        return reg.block(newName, factory)
                .initialProperties(() -> Blocks.GLASS)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .properties(GlassBlockBuilders::glassProperties)
                // Removed onRegister for EncasingRegistry - will be done in FMLCommonSetupEvent
                .loot((p, lb) -> p.dropOther(lb, AllBlocks.SHAFT.get()))
                .addLayer(() -> RenderType::cutout)
                .onRegister(connectedTextures(() -> new GlassEncasedCTBehaviour(ctEntry)))
                .onRegister(casingConnectivity((block, cc) ->
                        cc.make(block, ctEntry, (state, face) -> true)))
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) ->
                        axisBlock(ctx, prov, state -> prov.models()
                                .withExistingParent(ctx.getName(), CreatePrism.asResource("block/glass_encased_shaft/block"))
                                .texture("casing", CreatePrism.asResource("block/" + casing + (clear ? "_clear_glass" : "_glass") + "_casing"))
                                .texture("opening", getOpening(casing)), true))
                .item()
                .model((ctx, prov) -> prov
                        .withExistingParent(ctx.getName(), CreatePrism.asResource("block/glass_encased_shaft/block"))
                        .texture("casing", CreatePrism.asResource("block/" + casing + (clear ? "_clear_glass" : "_glass") + "_casing"))
                        .texture("opening", getOpening(casing)))
                .build()
                .register();
    }

    // Illumination Encased Shaft Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<IlluminationEncasedShaft> illuminationEncasedShaft(
            T reg, String casing,
            NonNullFunction<BlockBehaviour.Properties, IlluminationEncasedShaft> factory) {
        String newName = casing + "_illumination_encased_shaft";
        CTSpriteShiftEntry ctEntry = CPSpriteShifts.getIlluminationCasingShift(casing);

        return reg.block(newName, factory)
                .initialProperties(() -> Blocks.GLOWSTONE)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .properties(p -> p.lightLevel(s -> 15))
                .properties(GlassBlockBuilders::glassProperties)
                .loot((p, lb) -> p.dropOther(lb, AllBlocks.SHAFT.get()))
                .addLayer(() -> RenderType::translucent)
                .onRegister(connectedTextures(() -> new IlluminationEncasedCTBehaviour(ctEntry)))
                .onRegister(casingConnectivity((block, cc) ->
                        cc.make(block, ctEntry, (state, face) -> true)))
                // Removed onRegister for EncasingRegistry - will be done in FMLCommonSetupEvent
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) ->
                        axisBlock(ctx, prov, state -> prov.models()
                                .withExistingParent(ctx.getName(), CreatePrism.asResource("block/illumination_encased_shaft/block"))
                                .texture("casing", CreatePrism.asResource("block/" + casing + "_illumination_casing"))
                                .texture("opening", getOpening(casing)), true))
                .item()
                .model((ctx, prov) -> prov
                        .withExistingParent(ctx.getName(), CreatePrism.asResource("block/illumination_encased_shaft/block"))
                        .texture("casing", CreatePrism.asResource("block/" + casing + "_illumination_casing"))
                        .texture("opening", getOpening(casing)))
                .build()
                .register();
    }

    // Glass Encased Cogwheel Builder (Small and Large)
    public static <T extends AbstractRegistrate<?>> BlockEntry<GlassEncasedCogwheel> glassEncasedCogwheel(
            T reg, String casingType, boolean large, boolean clear,
            NonNullFunction<BlockBehaviour.Properties, GlassEncasedCogwheel> factory) {
        CTSpriteShiftEntry mainShift = CPSpriteShifts.getCasingShift(casingType, clear);
        String name = casingType + (clear ? "_clear" : "");
        String blockName = name + (large ? "_glass_encased_large_cogwheel" : "_glass_encased_cogwheel");

        return reg.block(blockName, factory)
                .initialProperties(() -> Blocks.GLASS)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .properties(GlassBlockBuilders::glassProperties)
                // Removed onRegister for EncasingRegistry - will be done in FMLCommonSetupEvent
                .loot((p, lb) -> {
                    if (large) {
                        p.dropOther(lb, AllBlocks.LARGE_COGWHEEL.get());
                    } else {
                        p.dropOther(lb, AllBlocks.COGWHEEL.get());
                    }
                })
                .addLayer(() -> RenderType::cutout)
                .onRegister(connectedTextures(() -> getCogCTBehaviour(mainShift, casingType, large)))
                .onRegister(casingConnectivity((block, cc) ->
                        cc.make(block, mainShift, (state, f) ->
                                state.getBlock() instanceof GlassEncasedCogwheel &&
                                        f.getAxis() == state.getValue(GlassEncasedCogwheel.AXIS) &&
                                        !state.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ?
                                                GlassEncasedCogwheel.TOP_SHAFT : GlassEncasedCogwheel.BOTTOM_SHAFT))))
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) ->
                        axisBlock(ctx, prov, blockState -> {
                            String suffix = (blockState.getValue(GlassEncasedCogwheel.TOP_SHAFT) ? "_top" : "")
                                    + (blockState.getValue(GlassEncasedCogwheel.BOTTOM_SHAFT) ? "_bottom" : "");
                            String modelName = ctx.getName() + suffix;
                            String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                            return prov.models()
                                    .withExistingParent(modelName, CreatePrism.asResource("block/" + blockFolder + "/block" + suffix))
                                    .texture("particle", CreatePrism.asResource("block/" + name + "_glass_casing"))
                                    .texture("casing", CreatePrism.asResource("block/" + name + "_glass_casing"))
                                    .texture("backing", getBacking(casingType))
                                    .texture("opening", getOpening(casingType))
                                    .texture("siding", getSiding(casingType, large));
                        }, false))
                .item()
                .model((ctx, prov) -> {
                    String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                    prov.withExistingParent(ctx.getName(), CreatePrism.asResource("block/" + blockFolder + "/item"))
                            .texture("casing", CreatePrism.asResource("block/" + name + "_glass_casing"))
                            .texture("backing", getBacking(casingType))
                            .texture("opening", getOpening(casingType))
                            .texture("siding", getSiding(casingType, large));
                })
                .build()
                .register();
    }

    // Illumination Encased Cogwheel Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<IlluminationEncasedCogwheel> illuminationEncasedCogwheel(
            T reg, String casingType, boolean large,
            NonNullFunction<BlockBehaviour.Properties, IlluminationEncasedCogwheel> factory) {
        CTSpriteShiftEntry mainShift = CPSpriteShifts.getIlluminationCasingShift(casingType);
        String name = casingType + "_illumination";
        String blockName = name + (large ? "_encased_large_cogwheel" : "_encased_cogwheel");

        return reg.block(blockName, factory)
                .initialProperties(() -> Blocks.GLOWSTONE)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .properties(p -> p.lightLevel(s -> 15))
                .properties(GlassBlockBuilders::glassProperties)
                .loot((p, lb) -> {
                    if (large) {
                        p.dropOther(lb, AllBlocks.LARGE_COGWHEEL.get());
                    } else {
                        p.dropOther(lb, AllBlocks.COGWHEEL.get());
                    }
                })
                .addLayer(() -> RenderType::translucent)
                .onRegister(connectedTextures(() -> getIlluminationCogCTBehaviour(mainShift, casingType, large)))
                .onRegister(casingConnectivity((block, cc) ->
                        cc.make(block, mainShift, (state, f) ->
                                state.getBlock() instanceof IlluminationEncasedCogwheel &&
                                        f.getAxis() == state.getValue(GlassEncasedCogwheel.AXIS) &&
                                        !state.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ?
                                                GlassEncasedCogwheel.TOP_SHAFT : GlassEncasedCogwheel.BOTTOM_SHAFT))))
                // Removed onRegister for EncasingRegistry - will be done in FMLCommonSetupEvent
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) ->
                        axisBlock(ctx, prov, blockState -> {
                            String suffix = (blockState.getValue(GlassEncasedCogwheel.TOP_SHAFT) ? "_top" : "")
                                    + (blockState.getValue(GlassEncasedCogwheel.BOTTOM_SHAFT) ? "_bottom" : "");
                            String modelName = ctx.getName() + suffix;
                            String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                            return prov.models()
                                    .withExistingParent(modelName, CreatePrism.asResource("block/" + blockFolder + "/block" + suffix))
                                    .texture("particle", CreatePrism.asResource("block/" + casingType + "_illumination_casing"))
                                    .texture("casing", CreatePrism.asResource("block/" + casingType + "_illumination_casing"))
                                    .texture("backing", getBacking(casingType))
                                    .texture("opening", getOpening(casingType))
                                    .texture("siding", getIlluminationSiding(casingType, large));
                        }, false))
                .item()
                .model((ctx, prov) -> {
                    String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                    prov.withExistingParent(ctx.getName(), CreatePrism.asResource("block/" + blockFolder + "/item"))
                            .texture("casing", CreatePrism.asResource("block/" + casingType + "_illumination_casing"))
                            .texture("backing", getBacking(casingType))
                            .texture("opening", getOpening(casingType))
                            .texture("siding", getIlluminationSiding(casingType, large));
                })
                .build()
                .register();
    }

    // Glass Scaffolding Builder
    public static <T extends AbstractRegistrate<?>> BlockEntry<MetalScaffoldingBlock> glassScaffolding(
            T reg, String casing, boolean clear,
            NonNullFunction<BlockBehaviour.Properties, MetalScaffoldingBlock> factory) {
        String name = casing + (clear ? "_clear" : "") + "_glass_scaffolding";
        CTSpriteShiftEntry mainShift = CPSpriteShifts.getCasingShift(casing, clear);
        CTSpriteShiftEntry side = getSideShift(casing);
        CTSpriteShiftEntry innerSide = getInnerSideShift(casing);

        return reg.block(name, factory)
                .initialProperties(() -> Blocks.SCAFFOLDING)
                .properties(p -> p.sound(SoundType.COPPER).noOcclusion())
                .addLayer(() -> RenderType::cutout)
                .onRegister(connectedTextures(() -> new MetalScaffoldingCTBehaviour(side, innerSide, mainShift)))
                .transform(pickaxeOnly())
                .tag(BlockTags.CLIMBABLE)
                .blockstate((c, p) -> p.getVariantBuilder(c.get())
                        .forAllStatesExcept(s -> {
                            String suffix = s.getValue(MetalScaffoldingBlock.BOTTOM) ? "_horizontal" : "";
                            return ConfiguredModel.builder()
                                    .modelFile(p.models()
                                            .withExistingParent(c.getName() + suffix,
                                                    CreatePrism.asResource("block/scaffold/block" + suffix))
                                            .texture("top", getTopTexture(casing))
                                            .texture("inside", getInsideTexture(casing))
                                            .texture("side", getSideTexture(casing))
                                            .texture("casing", CreatePrism.asResource("block/" + casing +
                                                    (clear ? "_clear" : "") + "_glass_casing"))
                                            .texture("particle", getSideTexture(casing)))
                                    .build();
                        }, MetalScaffoldingBlock.WATERLOGGED, MetalScaffoldingBlock.DISTANCE))
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, c.get())
                                .requires(getScaffoldingBase(casing))
                                .requires(AllPaletteBlocks.FRAMED_GLASS.get())
                                .unlockedBy("has_scaffolding", RegistrateRecipeProvider.has(getScaffoldingBase(casing)))
                                .save(p, CreatePrism.asResource("crafting/glass_scaffolding/" + c.getName())))
                .item(MetalScaffoldingBlockItem::new)
                .model((c, p) -> p.withExistingParent(c.getName(), CreatePrism.asResource("block/scaffold/item"))
                        .texture("top", getTopTexture(casing))
                        .texture("inside", getInsideTexture(casing))
                        .texture("side", getSideTexture(casing))
                        .texture("casing", CreatePrism.asResource("block/" + casing +
                                (clear ? "_clear" : "") + "_glass_casing"))
                        .texture("particle", getSideTexture(casing)))
                .build()
                .register();
    }

    private static GlassEncasedCogCTBehaviour getCogCTBehaviour(CTSpriteShiftEntry mainShift, String casingType, boolean large) {
        if (!large) {
            CTSpriteShiftEntry side = CPSpriteShifts.vertical("encased_cogwheels/" + casingType + "_encased_cogwheel_side");
            CTSpriteShiftEntry otherSide = CPSpriteShifts.horizontal("encased_cogwheels/" + casingType + "_encased_cogwheel_side");
            Couple<CTSpriteShiftEntry> sideShifts = Couple.create(side, otherSide);
            return new GlassEncasedCogCTBehaviour(mainShift, sideShifts);
        } else {
            return new GlassEncasedCogCTBehaviour(mainShift);
        }
    }

    private static IlluminationEncasedCogCTBehaviour getIlluminationCogCTBehaviour(CTSpriteShiftEntry mainShift, String casingType, boolean large) {
        if (!large) {
            CTSpriteShiftEntry side = CPSpriteShifts.vertical("encased_cogwheels/" + casingType + "_illumination_encased_cogwheel_side");
            CTSpriteShiftEntry otherSide = CPSpriteShifts.horizontal("encased_cogwheels/" + casingType + "_illumination_encased_cogwheel_side");
            Couple<CTSpriteShiftEntry> sideShifts = Couple.create(side, otherSide);
            return new IlluminationEncasedCogCTBehaviour(mainShift, sideShifts);
        } else {
            return new IlluminationEncasedCogCTBehaviour(mainShift);
        }
    }

    private static BlockBehaviour.Properties glassProperties(BlockBehaviour.Properties p) {
        return p.isValidSpawn(($, $$, $$$, $$$$) -> false)
                .isRedstoneConductor(($, $$, $$$) -> false)
                .isSuffocating(($, $$, $$$) -> false)
                .isViewBlocking(($, $$, $$$) -> false);
    }

    private static ResourceLocation getOpening(String casing) {
        if (casing.equals("andesite")) {
            return ResourceLocation.parse("create:block/gearbox");
        } else if (casing.equals("brass")) {
            return ResourceLocation.parse("create:block/brass_gearbox");
        } else {
            return CreatePrism.asResource("block/" + casing + "_gearbox");
        }
    }

    private static ResourceLocation getBacking(String casing) {
        if (casing.equals("andesite")) {
            return ResourceLocation.withDefaultNamespace("block/stripped_spruce_log_top");
        } else if (casing.equals("brass")) {
            return ResourceLocation.withDefaultNamespace("block/stripped_dark_oak_log_top");
        } else {
            return CreatePrism.asResource("block/" + casing + "_backing");
        }
    }

    private static ResourceLocation getSiding(String casing, boolean large) {
        return CreatePrism.asResource("block/encased_cogwheels/" +
                (large ? "large_" : "") + casing + "_encased_cogwheel_side");
    }

    private static ResourceLocation getIlluminationSiding(String casing, boolean large) {
        return CreatePrism.asResource("block/encased_cogwheels/" +
                (large ? "large_" : "") + casing + "_illumination_encased_cogwheel_side");
    }

    private static CTSpriteShiftEntry getSideShift(String casing) {
        if (casing.equals("andesite")) {
            return AllSpriteShifts.ANDESITE_SCAFFOLD;
        } else if (casing.equals("brass")) {
            return AllSpriteShifts.BRASS_SCAFFOLD;
        } else if (casing.equals("copper")) {
            return AllSpriteShifts.COPPER_SCAFFOLD;
        } else {
            return CPSpriteShifts.horizontal("scaffold/" + casing + "_scaffold");
        }
    }

    private static CTSpriteShiftEntry getInnerSideShift(String casing) {
        if (casing.equals("andesite")) {
            return AllSpriteShifts.ANDESITE_SCAFFOLD_INSIDE;
        } else if (casing.equals("brass")) {
            return AllSpriteShifts.BRASS_SCAFFOLD_INSIDE;
        } else if (casing.equals("copper")) {
            return AllSpriteShifts.COPPER_SCAFFOLD_INSIDE;
        } else {
            return CPSpriteShifts.horizontal("scaffold/" + casing + "_scaffold_inside");
        }
    }

    private static ResourceLocation getTopTexture(String casing) {
        if (casing.equals("andesite") || casing.equals("brass") || casing.equals("copper")) {
            return ResourceLocation.parse("create:block/funnel/" + casing + "_funnel_frame");
        } else {
            return CreatePrism.asResource("block/scaffold/" + casing + "_frame");
        }
    }

    private static ResourceLocation getInsideTexture(String casing) {
        if (casing.equals("andesite") || casing.equals("brass") || casing.equals("copper")) {
            return ResourceLocation.parse("create:block/scaffold/" + casing + "_scaffold_inside");
        } else {
            return CreatePrism.asResource("block/scaffold/" + casing + "_scaffold_inside");
        }
    }

    private static ResourceLocation getSideTexture(String casing) {
        if (casing.equals("andesite") || casing.equals("brass") || casing.equals("copper")) {
            return ResourceLocation.parse("create:block/scaffold/" + casing + "_scaffold");
        } else {
            return CreatePrism.asResource("block/scaffold/" + casing + "_scaffold");
        }
    }

    private static BlockEntry<?> getScaffoldingBase(String casing) {
        if (casing.equals("andesite")) {
            return AllBlocks.ANDESITE_SCAFFOLD;
        } else if (casing.equals("brass")) {
            return AllBlocks.BRASS_SCAFFOLD;
        } else if (casing.equals("copper")) {
            return AllBlocks.COPPER_SCAFFOLD;
        } else {
            return AllBlocks.ANDESITE_SCAFFOLD;
        }
    }
}