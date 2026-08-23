package com.adonis.prism.registry;

import com.adonis.prism.CreatePrism;
import com.adonis.prism.ponder.CPPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = CreatePrism.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CPClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CPSpriteShifts.init();
        CPPartialModels.init();

        event.enqueueWork(() -> {

            // 为所有玻璃外壳设置渲染层
            CPBlocks.GLASS_CASINGS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));
            CPBlocks.CLEAR_GLASS_CASINGS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));

            // 为所有照明外壳设置渲染层
            CPBlocks.ILLUMINATION_CASINGS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.translucent()));

            // 为所有玻璃包覆轴设置渲染层
            CPBlocks.GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));
            CPBlocks.CLEAR_GLASS_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));

            // 为所有照明包覆轴设置渲染层
            CPBlocks.ILLUMINATION_ENCASED_SHAFTS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.translucent()));

            // 为所有玻璃包覆齿轮设置渲染层
            CPBlocks.SMALL_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));
            CPBlocks.SMALL_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));
            CPBlocks.LARGE_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));
            CPBlocks.LARGE_CLEAR_GLASS_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.cutout()));

            // 为所有照明包覆齿轮设置渲染层
            CPBlocks.SMALL_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.translucent()));
            CPBlocks.LARGE_ILLUMINATION_ENCASED_COGWHEELS.blockEntryMap.values().forEach(entry ->
                    ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.translucent()));

            // 为玻璃脚手架设置渲染层
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.ANDESITE_GLASS_SCAFFOLD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.BRASS_GLASS_SCAFFOLD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.COPPER_GLASS_SCAFFOLD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.CLEAR_ANDESITE_GLASS_SCAFFOLD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.CLEAR_BRASS_GLASS_SCAFFOLD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CPBlocks.CLEAR_COPPER_GLASS_SCAFFOLD.get(), RenderType.cutout());

            PonderIndex.addPlugin(new CPPonderPlugin());
        });
    }
}