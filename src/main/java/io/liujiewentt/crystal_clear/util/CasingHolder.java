package com.adonis.prism.util;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.tterrag.registrate.util.entry.BlockEntry;

import java.util.function.Supplier;

public record CasingHolder(String name, Supplier<CasingBlock> casing) {

    public static CasingHolder of(String name, Supplier<CasingBlock> casing) {
        return new CasingHolder(name, casing);
    }

    public static CasingHolder of(String name, BlockEntry<? extends CasingBlock> casing) {
        return new CasingHolder(name, casing::get);
    }
}