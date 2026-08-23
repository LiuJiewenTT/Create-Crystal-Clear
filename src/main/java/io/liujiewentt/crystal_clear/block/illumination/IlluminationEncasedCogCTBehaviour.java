package com.adonis.prism.block.illumination;

import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class IlluminationEncasedCogCTBehaviour extends EncasedCogCTBehaviour {

    // 构造函数用于大齿轮（只有主纹理）
    public IlluminationEncasedCogCTBehaviour(CTSpriteShiftEntry shift) {
        super(shift);
    }

    // 构造函数用于小齿轮（主纹理和两个侧面纹理）
    public IlluminationEncasedCogCTBehaviour(CTSpriteShiftEntry shift, Couple<CTSpriteShiftEntry> sideShifts) {
        super(shift, sideShifts);
    }

    @Override
    public boolean connectsTo(BlockState selfState, BlockState compareState, BlockAndTintGetter reader,
                              BlockPos pos, BlockPos otherPos, Direction face) {
        if (compareState.getBlock() instanceof EncasedShaftBlock) {
            return false;
        }

        return super.connectsTo(selfState, compareState, reader, pos, otherPos, face);
    }

    @Override
    public boolean buildContextForOccludedDirections() {
        return true;
    }
}