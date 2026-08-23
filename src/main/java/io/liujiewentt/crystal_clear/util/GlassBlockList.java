package com.adonis.prism.util;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GlassBlockList<T extends Block> {
    
    /**
     * A map that contains all block entries associated with their respective casing names
     */
    public final Map<String, BlockEntry<T>> blockEntryMap = new HashMap<>();
    
    /**
     * @param holders List of casing holders
     * @param filler Function to create BlockEntry from CasingHolder
     */
    public GlassBlockList(List<CasingHolder> holders, Function<CasingHolder, BlockEntry<T>> filler) {
        for (CasingHolder holder : holders) {
            blockEntryMap.put(holder.name(), filler.apply(holder));
        }
    }
    
    /**
     * Convert to array for block entity registration
     */
    @SuppressWarnings("unchecked")
    public BlockEntry<T>[] toArray() {
        BlockEntry<?>[] blockEntries = new BlockEntry<?>[blockEntryMap.size()];
        
        int i = 0;
        for (BlockEntry<T> value : blockEntryMap.values()) {
            blockEntries[i] = value;
            i++;
        }
        
        return (BlockEntry<T>[]) blockEntries;
    }
    
    /**
     * Get specific casing block by name
     */
    public Block getCasing(String casingName) {
        BlockEntry<T> entry = blockEntryMap.get(casingName);
        return entry != null ? entry.get() : null;
    }
    
    /**
     * Get block entry by name
     */
    public BlockEntry<T> getEntry(String casingName) {
        return blockEntryMap.get(casingName);
    }
}