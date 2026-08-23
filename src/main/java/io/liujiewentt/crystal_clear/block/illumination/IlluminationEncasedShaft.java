package com.adonis.prism.block.illumination;

import com.adonis.prism.block.glass.GlassEncasedShaft;
import com.adonis.prism.registry.CPBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

public class IlluminationEncasedShaft extends GlassEncasedShaft {

    public IlluminationEncasedShaft(Properties properties, Supplier<Block> casing) {
        super(properties.lightLevel(s -> 15), casing);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return CPBlockEntities.ILLUMINATION_ENCASED_SHAFT.get();
    }
}