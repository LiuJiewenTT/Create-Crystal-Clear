package io.liujiewentt.crystal_clear;

import io.liujiewentt.crystal_clear.registry.*;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Random;

@Mod(CrystalClear.MOD_ID)
public class CrystalClear {
    public static final String MOD_ID = "crystal_clear";
    public static final String NAME = "Create Crystal Clear";
    public static final Random RANDOM = new Random();

    public static final CPRegistrate REGISTRATE = CPRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    static {
        // Add non-block/item lang entries (itemGroup, config) to datagen output.
        // Registrate only auto-generates lang for registered blocks/items;
        // these keys are used by the creative tab and NeoForge/Configured config screen.
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            provider.add("itemGroup.crystal_clear.main", "Create Crystal Clear");

            provider.add("config.crystal_clear.creative", "Creative Mode");
            provider.add("config.crystal_clear.creative.tooltip", "Creative mode display options");
            provider.add("config.crystal_clear.creative.show_encased_variants", "Show Encased Variants");
            provider.add("config.crystal_clear.creative.show_encased_variants.tooltip",
                    "Show encased shafts, cogwheels, and large cogwheels in the creative tab. " +
                    "These are secondary states created by applying casings to Create's shafts/cogwheels. " +
                    "Disabled by default to keep the tab clean.");
        });
    }

    public CrystalClear(IEventBus modEventBus, ModContainer modContainer) {
        // Register Registrate
        REGISTRATE.registerEventListeners(modEventBus);
        CPSpriteShifts.init();
        CPBlocks.register();
        CPBlockEntities.register();
        CPItems.register();
        CPCreativeTab.register(modEventBus);
        CPPartialModels.register();

        // Register config
        modContainer.registerConfig(ModConfig.Type.CLIENT, CPConfig.SPEC);

        // Register built-in config screen (NeoForge ConfigurationScreen)
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new ConfigurationScreen(mc, parent));

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

        // Fluid pipe variants
        CPBlocks.GLASS_ENCASED_PIPES.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.FLUID_PIPE.get(), entry.get()));
        CPBlocks.CLEAR_GLASS_ENCASED_PIPES.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.FLUID_PIPE.get(), entry.get()));
        CPBlocks.ILLUMINATION_ENCASED_PIPES.blockEntryMap.values().forEach(entry ->
                EncasingRegistry.addVariant(AllBlocks.FLUID_PIPE.get(), entry.get()));
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}