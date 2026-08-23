package com.adonis.prism.registry;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.engine_room.flywheel.lib.model.Models;

import static com.adonis.prism.CreatePrism.REGISTRATE;

public class CPBlockEntities {

    // Glass Encased Shaft Block Entity
    public static final BlockEntityEntry<KineticBlockEntity> GLASS_ENCASED_SHAFT = REGISTRATE
            .blockEntity("glass_encased_shaft", KineticBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlocks(CPBlocks.GLASS_ENCASED_SHAFTS.toArray())
            .validBlocks(CPBlocks.CLEAR_GLASS_ENCASED_SHAFTS.toArray())
            .renderer(() -> ShaftRenderer::new)
            .register();

    // Glass Encased Small Cogwheel Block Entity
    public static final BlockEntityEntry<SimpleKineticBlockEntity> GLASS_ENCASED_COG = REGISTRATE
            .blockEntity("glass_encased_cog", SimpleKineticBlockEntity::new)
            .visual(() -> (context, blockEntity, partialTick) ->
                    new EncasedCogVisual(context, blockEntity, false, partialTick,
                            Models.partial(AllPartialModels.SHAFTLESS_COGWHEEL)))
            .validBlocks(CPBlocks.SMALL_GLASS_ENCASED_COGWHEELS.toArray())
            .validBlocks(CPBlocks.SMALL_CLEAR_GLASS_ENCASED_COGWHEELS.toArray())
            .renderer(() -> (context) -> new EncasedCogRenderer(context, false))
            .register();

    // Glass Encased Large Cogwheel Block Entity
    public static final BlockEntityEntry<SimpleKineticBlockEntity> GLASS_ENCASED_LARGE_COG = REGISTRATE
            .blockEntity("glass_encased_large_cog", SimpleKineticBlockEntity::new)
            .visual(() -> (context, blockEntity, partialTick) ->
                    new EncasedCogVisual(context, blockEntity, true, partialTick,
                            Models.partial(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL)))
            .validBlocks(CPBlocks.LARGE_GLASS_ENCASED_COGWHEELS.toArray())
            .validBlocks(CPBlocks.LARGE_CLEAR_GLASS_ENCASED_COGWHEELS.toArray())
            .renderer(() -> (context) -> new EncasedCogRenderer(context, true))
            .register();

    // 在现有注册后添加：

    // Illumination Encased Shaft Block Entity
    public static final BlockEntityEntry<KineticBlockEntity> ILLUMINATION_ENCASED_SHAFT = REGISTRATE
            .blockEntity("illumination_encased_shaft", KineticBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlocks(CPBlocks.ILLUMINATION_ENCASED_SHAFTS.toArray())
            .renderer(() -> ShaftRenderer::new)
            .register();

    // Illumination Encased Small Cogwheel Block Entity
    public static final BlockEntityEntry<SimpleKineticBlockEntity> ILLUMINATION_ENCASED_COG = REGISTRATE
            .blockEntity("illumination_encased_cog", SimpleKineticBlockEntity::new)
            .visual(() -> (context, blockEntity, partialTick) ->
                    new EncasedCogVisual(context, blockEntity, false, partialTick,
                            Models.partial(AllPartialModels.SHAFTLESS_COGWHEEL)))
            .validBlocks(CPBlocks.SMALL_ILLUMINATION_ENCASED_COGWHEELS.toArray())
            .renderer(() -> (context) -> new EncasedCogRenderer(context, false))
            .register();

    // Illumination Encased Large Cogwheel Block Entity
    public static final BlockEntityEntry<SimpleKineticBlockEntity> ILLUMINATION_ENCASED_LARGE_COG = REGISTRATE
            .blockEntity("illumination_encased_large_cog", SimpleKineticBlockEntity::new)
            .visual(() -> (context, blockEntity, partialTick) ->
                    new EncasedCogVisual(context, blockEntity, true, partialTick,
                            Models.partial(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL)))
            .validBlocks(CPBlocks.LARGE_ILLUMINATION_ENCASED_COGWHEELS.toArray())
            .renderer(() -> (context) -> new EncasedCogRenderer(context, true))
            .register();

    public static void register() {
        // Static initialization
    }
}