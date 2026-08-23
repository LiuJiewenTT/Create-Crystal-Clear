package com.adonis.prism.block.glass;

import com.adonis.prism.registry.CPBlockEntities;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GlassEncasedCogwheel extends EncasedCogwheelBlock implements EncasedBlock {
    
    private final Supplier<Block> casing;

    public GlassEncasedCogwheel(Properties properties, boolean large, Supplier<Block> casing) {
        super(properties, large, casing);
        this.casing = casing;
    }

    @Override
    public BlockEntityType<? extends SimpleKineticBlockEntity> getBlockEntityType() {
        return isLarge ? CPBlockEntities.GLASS_ENCASED_LARGE_COG.get() : CPBlockEntities.GLASS_ENCASED_COG.get();
    }

    @Override
    public Block getCasing() {
        return casing.get();
    }

    @Override
    public boolean skipRendering(BlockState selfState, BlockState adjacentBlock, Direction side) {
        return adjacentBlock.getBlock() instanceof EncasedCogwheelBlock
                || adjacentBlock.getBlock() instanceof EncasedShaftBlock;
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