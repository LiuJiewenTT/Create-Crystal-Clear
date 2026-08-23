package com.adonis.prism.registry;

import com.adonis.prism.CreatePrism;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;

import java.util.HashMap;
import java.util.Map;

public class CPSpriteShifts {

    private static final Map<String, Map<Boolean, CTSpriteShiftEntry>> GLASS_CASING_SHIFTS = new HashMap<>();
    private static final Map<String, CTSpriteShiftEntry> ILLUMINATION_CASING_SHIFTS = new HashMap<>();
    private static final Map<String, CTSpriteShiftEntry> SPRITE_SHIFTS = new HashMap<>();

    public static void init() {
        // Initialize glass casing shifts
        registerCasingShift("andesite");
        registerCasingShift("brass");
        registerCasingShift("copper");
        registerCasingShift("train");

        // Initialize illumination casing shifts
        registerIlluminationCasingShift("andesite");
        registerIlluminationCasingShift("brass");
        registerIlluminationCasingShift("copper");
        registerIlluminationCasingShift("train");
    }

    private static void registerIlluminationCasingShift(String name) {
        ILLUMINATION_CASING_SHIFTS.put(name,
                createOmniShift(name + "_illumination_casing"));
    }

    public static CTSpriteShiftEntry getIlluminationCasingShift(String casing) {
        return ILLUMINATION_CASING_SHIFTS.get(casing);
    }

    private static void registerCasingShift(String name) {
        Map<Boolean, CTSpriteShiftEntry> shifts = new HashMap<>();
        shifts.put(false, createOmniShift(name + "_glass_casing"));
        shifts.put(true, createOmniShift(name + "_clear_glass_casing"));
        GLASS_CASING_SHIFTS.put(name, shifts);
    }

    public static CTSpriteShiftEntry getCasingShift(String casing, boolean clear) {
        Map<Boolean, CTSpriteShiftEntry> shifts = GLASS_CASING_SHIFTS.get(casing);
        if (shifts != null) {
            return shifts.get(clear);
        }
        return null;
    }

    // Create omnidirectional connected texture
    public static CTSpriteShiftEntry omni(String name) {
        return SPRITE_SHIFTS.computeIfAbsent(name + "_omni",
                key -> getCT(AllCTTypes.OMNIDIRECTIONAL, name));
    }

    // Create horizontal connected texture
    public static CTSpriteShiftEntry horizontal(String name) {
        return SPRITE_SHIFTS.computeIfAbsent(name + "_horizontal",
                key -> getCT(AllCTTypes.HORIZONTAL, name));
    }

    // Create vertical connected texture
    public static CTSpriteShiftEntry vertical(String name) {
        return SPRITE_SHIFTS.computeIfAbsent(name + "_vertical",
                key -> getCT(AllCTTypes.VERTICAL, name));
    }

    private static CTSpriteShiftEntry createOmniShift(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(
                type,
                CreatePrism.asResource("block/" + blockTextureName),
                CreatePrism.asResource("block/" + connectedTextureName + "_connected")
        );
    }
}