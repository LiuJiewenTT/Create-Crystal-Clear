package com.adonis.prism.block.glass;

import com.adonis.prism.registry.CPBlockEntities;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GlassEncasedShaft extends EncasedShaftBlock implements EncasedBlock, IBE<KineticBlockEntity> {

    private final Supplier<Block> casing;

    public GlassEncasedShaft(Properties properties, Supplier<Block> casing) {
        super(properties, casing);
        this.casing = casing;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return CPBlockEntities.GLASS_ENCASED_SHAFT.get();
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