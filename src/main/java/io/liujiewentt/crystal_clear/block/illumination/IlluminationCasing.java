package com.adonis.prism.block.illumination;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class IlluminationCasing extends CasingBlock {

    public IlluminationCasing(Properties properties) {
        super(properties.lightLevel(s -> 15));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return (state.getBlock() instanceof IlluminationCasing) &&
                (adjacentBlockState.getBlock() instanceof IlluminationCasing);
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