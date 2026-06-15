package com.vs2shipsystems.vs2shipsystems.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Configuration for VS2 Ship Systems.
 * Accessible via the standard Forge config GUI and files.
 */
@Mod.EventBusSubscriber(modid = "vs2_ship_systems", bus = Mod.EventBusSubscriber.Bus.MOD)
public class VS2SSConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.DoubleValue hullDamagePerBrokenBlock;
        public final ForgeConfigSpec.DoubleValue floodIncreaseRate;
        public final ForgeConfigSpec.DoubleValue sealRepairRate;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("VS2 Ship Systems - Core Balance Settings").push("general");

            hullDamagePerBrokenBlock = builder
                    .comment("How much hull integrity is lost when a critical hull block is broken on a ship (0.0 - 1.0 range per block).")
                    .defineInRange("hullDamagePerBrokenBlock", 0.015, 0.0, 1.0);

            floodIncreaseRate = builder
                    .comment("Rate at which flood level increases when hull integrity is below the breach threshold (per tick).")
                    .defineInRange("floodIncreaseRate", 0.0005, 0.0, 0.1);

            sealRepairRate = builder
                    .comment("Rate at which environmental sealers slowly repair hull integrity when active.")
                    .defineInRange("sealRepairRate", 0.0002, 0.0, 0.05);

            builder.pop();
        }
    }
}
