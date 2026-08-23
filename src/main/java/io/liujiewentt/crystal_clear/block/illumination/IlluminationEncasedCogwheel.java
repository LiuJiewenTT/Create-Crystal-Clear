package io.liujiewentt.crystal_clear.block.illumination;

import io.liujiewentt.crystal_clear.block.glass.GlassEncasedCogwheel;
import io.liujiewentt.crystal_clear.registry.CPBlockEntities;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

public class IlluminationEncasedCogwheel extends GlassEncasedCogwheel {

    public IlluminationEncasedCogwheel(Properties properties, boolean large, Supplier<Block> casing) {
        super(properties.lightLevel(s -> 15), large, casing);
    }

    @Override
    public BlockEntityType<? extends SimpleKineticBlockEntity> getBlockEntityType() {
        return isLarge ? CPBlockEntities.ILLUMINATION_ENCASED_LARGE_COG.get()
                : CPBlockEntities.ILLUMINATION_ENCASED_COG.get();
    }
}