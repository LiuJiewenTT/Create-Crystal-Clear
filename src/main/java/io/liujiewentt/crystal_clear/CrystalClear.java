package com.adonis.prism;

import com.adonis.prism.registry.*;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Random;

@Mod(CreatePrism.MOD_ID)
public class CreatePrism {
    public static final String MOD_ID = "createprism";
    public static final String NAME = "Create Prism";
    public static final Random RANDOM = new Random();

    public static final CPRegistrate REGISTRATE = CPRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public CreatePrism(IEventBus modEventBus, ModContainer modContainer) {
        // Register Registrate
        REGISTRATE.registerEventListeners(modEventBus);
        CPSpriteShifts.init();
        CPBlocks.register();
        CPBlockEntities.register();
        CPItems.register();
        CPCreativeTab.register(modEventBus);
        CPPartialModels.register();

        // Register events
        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register all encasing variants after all blocks are registered
            registerEncasingVariants();
            // Initialize partial models
            CPPartialModels.init();
        });
    }

    private void registerEncasingVariants() {
        // Shaft variants
        CPBlocks.GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.SHAFT.get(), entry.get()));
        CPBlocks.CLEAR_GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.SHAFT.get(), entry.get()));
        CPBlocks.ILLUMINATION_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.SHAFT.get(), entry.get()));

        // Small cogwheel variants
        CPBlocks.SMALL_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.COGWHEEL.get(), entry.get()));
        CPBlocks.SMALL_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.COGWHEEL.get(), entry.get()));
        CPBlocks.SMALL_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.COGWHEEL.get(), entry.get()));

        // Large cogwheel variants
        CPBlocks.LARGE_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.LARGE_COGWHEEL.get(), entry.get()));
        CPBlocks.LARGE_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.LARGE_COGWHEEL.get(), entry.get()));
        CPBlocks.LARGE_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.LARGE_COGWHEEL.get(), entry.get()));
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}