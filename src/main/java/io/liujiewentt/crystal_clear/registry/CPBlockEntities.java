package io.liujiewentt.crystal_clear.registry;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.engine_room.flywheel.lib.model.Models;

import static io.liujiewentt.crystal_clear.CrystalClear.REGISTRATE;

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

    // Glass Encased Pipe Block Entity (reuses FluidPipeBlockEntity, shared by glass + clear glass)
    public static final BlockEntityEntry<FluidPipeBlockEntity> GLASS_ENCASED_PIPE = REGISTRATE
            .blockEntity("glass_encased_pipe", FluidPipeBlockEntity::new)
            .validBlocks(CPBlocks.GLASS_ENCASED_PIPES.toArray())
            .validBlocks(CPBlocks.CLEAR_GLASS_ENCASED_PIPES.toArray())
            .register();

    // Illumination Encased Pipe Block Entity
    public static final BlockEntityEntry<FluidPipeBlockEntity> ILLUMINATION_ENCASED_PIPE = REGISTRATE
            .blockEntity("illumination_encased_pipe", FluidPipeBlockEntity::new)
            .validBlocks(CPBlocks.ILLUMINATION_ENCASED_PIPES.toArray())
            .register();

    public static void register() {
        // Static initialization
    }
}