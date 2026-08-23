package io.liujiewentt.crystal_clear.registry;

import io.liujiewentt.crystal_clear.CrystalClear;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CPCreativeTab {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrystalClear.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.crystal_clear.main"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> CPBlocks.ILLUMINATION_CASINGS.getCasing("brass").asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // ========== Glass Casings ==========
                        // Standard Glass Casings
                        CPBlocks.GLASS_CASINGS.blockEntryMap.values().forEach(entry -> output.accept(entry));
                        // Clear Glass Casings
                        CPBlocks.CLEAR_GLASS_CASINGS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Illumination Casings ==========
                        CPBlocks.ILLUMINATION_CASINGS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Glass Encased Shafts ==========
                        // Standard Glass Encased Shafts
                        CPBlocks.GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry -> output.accept(entry));
                        // Clear Glass Encased Shafts
                        CPBlocks.CLEAR_GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Illumination Encased Shafts ==========
                        CPBlocks.ILLUMINATION_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Glass Encased Cogwheels ==========
                        // Small Glass Encased Cogwheels
                        CPBlocks.SMALL_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));
                        CPBlocks.SMALL_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // Large Glass Encased Cogwheels
                        CPBlocks.LARGE_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));
                        CPBlocks.LARGE_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Illumination Encased Cogwheels ==========
                        // Small Illumination Encased Cogwheels
                        CPBlocks.SMALL_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // Large Illumination Encased Cogwheels
                        CPBlocks.LARGE_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry -> output.accept(entry));

                        // ========== Glass Scaffoldings ==========
                        // Standard Glass Scaffoldings
                        output.accept(CPBlocks.ANDESITE_GLASS_SCAFFOLD);
                        output.accept(CPBlocks.BRASS_GLASS_SCAFFOLD);
                        output.accept(CPBlocks.COPPER_GLASS_SCAFFOLD);

                        // Clear Glass Scaffoldings
                        output.accept(CPBlocks.CLEAR_ANDESITE_GLASS_SCAFFOLD);
                        output.accept(CPBlocks.CLEAR_BRASS_GLASS_SCAFFOLD);
                        output.accept(CPBlocks.CLEAR_COPPER_GLASS_SCAFFOLD);
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}