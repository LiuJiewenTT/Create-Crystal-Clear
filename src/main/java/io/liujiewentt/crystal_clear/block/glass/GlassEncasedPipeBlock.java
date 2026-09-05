package io.liujiewentt.crystal_clear.block.glass;

import io.liujiewentt.crystal_clear.registry.CPBlockEntities;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GlassEncasedPipeBlock extends EncasedPipeBlock implements EncasedBlock, IBE<FluidPipeBlockEntity> {

    private final Supplier<Block> casing;

    public GlassEncasedPipeBlock(Properties properties, Supplier<Block> casing) {
        super(properties, casing);
        this.casing = casing;
    }

    @Override
    public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
        return CPBlockEntities.GLASS_ENCASED_PIPE.get();
    }

    @Override
    public Block getCasing() {
        return casing.get();
    }

    @Override
    public boolean skipRendering(BlockState selfState, BlockState adjacentBlock, Direction side) {
        return adjacentBlock.getBlock() instanceof EncasedPipeBlock;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0f;
    }
}
