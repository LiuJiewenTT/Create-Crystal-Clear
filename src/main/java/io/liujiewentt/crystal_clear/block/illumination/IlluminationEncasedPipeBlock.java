package io.liujiewentt.crystal_clear.block.illumination;

import io.liujiewentt.crystal_clear.block.glass.GlassEncasedPipeBlock;
import io.liujiewentt.crystal_clear.registry.CPBlockEntities;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

public class IlluminationEncasedPipeBlock extends GlassEncasedPipeBlock {

    public IlluminationEncasedPipeBlock(Properties properties, Supplier<Block> casing) {
        super(properties.lightLevel(s -> 15), casing);
    }

    @Override
    public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
        return CPBlockEntities.ILLUMINATION_ENCASED_PIPE.get();
    }
}
