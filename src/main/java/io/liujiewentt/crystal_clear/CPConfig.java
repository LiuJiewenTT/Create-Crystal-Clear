package io.liujiewentt.crystal_clear;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CPConfig {

    public static final ModConfigSpec SPEC;
    public static final CPConfig INSTANCE;

    public final ModConfigSpec.BooleanValue showEncasedVariants;

    static {
        var pair = new ModConfigSpec.Builder()
                .configure(CPConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private CPConfig(ModConfigSpec.Builder builder) {
        builder.comment("Creative Mode display options")
                .translation("config.crystal_clear.creative")
                .push("creative");

        showEncasedVariants = builder
                .comment("Show encased shafts, cogwheels, and large cogwheels in the creative tab.",
                        "These are secondary states created by applying casings to Create's shafts/cogwheels.",
                        "Disabled by default to keep the tab clean. Enable if you want quick access for building.")
                .translation("config.crystal_clear.creative.show_encased_variants")
                .define("show_encased_variants", false);

        builder.pop();
    }

    public static boolean showEncasedVariants() {
        return INSTANCE.showEncasedVariants.get();
    }
}
