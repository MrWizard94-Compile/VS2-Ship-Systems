package com.vs2shipsystems.vs2shipsystems.ship;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import com.vs2shipsystems.vs2shipsystems.config.VS2SSConfig;
import com.vs2shipsystems.vs2shipsystems.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.ships.ShipData;
import org.valkyrienskies.mod.common.util.VSGameUtilsKt;

import java.util.Collection;

/**
 * Central place for all Valkyrien Skies 2 integration logic.
 *
 * Responsibilities:
 * - Attaching / initializing ShipSystemsData on ships
 * - Reacting to ship assembly, loading, and physics ticks
 * - Tracking hull blocks placed/broken to update integrity
 * - Simulating flood progression based on integrity and environmental seal
 * - Providing utilities to get ship data from world positions
 */
@Mod.EventBusSubscriber(modid = VS2ShipSystems.MOD_ID)
public class VS2ShipIntegration {

    // Contribution values for hull blocks (tunable via future config or hard-coded for now)
    private static final float HULL_PLATING_CONTRIBUTION = 1.0f;
    private static final float REINFORCED_HULL_CONTRIBUTION = 2.5f;

    /**
     * Called every server tick.
     * Ensures attachments + runs flood simulation.
     */
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Collection<ShipData> ships = ValkyrienSkiesMod.getShipObjectWorld().getAllShips();

        for (ShipData ship : ships) {
            ShipSystemsData data = ShipSystemsData.getOrCreate(ship);

            // Flood simulation logic
            simulateFlood(data);

            // Future: environmental seal checks, oxygen drain, etc.
            if (data.isEnvironmentallySealed() && data.getFloodLevel() > 0) {
                // Slowly reduce flood if sealed (pumps or natural sealing)
                data.setFloodLevel(data.getFloodLevel() - 0.0001f);
            }
        }
    }

    private static void simulateFlood(ShipSystemsData data) {
        float integrity = data.getHullIntegrity();
        boolean sealed = data.isEnvironmentallySealed();

        if (sealed) {
            // Sealed ships resist flooding much better
            if (integrity < 0.3f) {
                data.setFloodLevel(data.getFloodLevel() + (float) VS2SSConfig.COMMON.floodIncreaseRate.get() * 0.1f);
            }
            return;
        }

        // Not sealed: flood based on how compromised the hull is
        float configRate = (float) VS2SSConfig.COMMON.floodIncreaseRate.get();
        if (integrity < 0.8f) {
            float floodIncrease = configRate * (1.0f - integrity) * 2.0f;
            data.setFloodLevel(data.getFloodLevel() + floodIncrease);
        } else if (data.getFloodLevel() > 0) {
            // Slight natural drainage if hull is mostly intact
            data.setFloodLevel(data.getFloodLevel() - configRate * 0.5f);
        }
    }

    /**
     * Utility: Get the ShipSystemsData for the ship that owns the given world position (if any).
     */
    public static ShipSystemsData getShipDataAt(Level level, BlockPos pos) {
        ShipData ship = VSGameUtilsKt.getShipObjectManagingPos(level, pos);
        if (ship == null) {
            return null; // Not on a ship
        }
        return ShipSystemsData.getOrCreate(ship);
    }

    // === Hull Block Tracking Events ===

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        Block block = event.getPlacedBlock().getBlock();
        float contribution = getHullContribution(block);
        if (contribution <= 0) return;

        ShipSystemsData data = getShipDataAt((Level) event.getLevel(), event.getPos());
        if (data != null) {
            data.repairHull(contribution);
            // Optional debug
            // ((ServerLevel) event.getLevel()).getServer().getPlayerList().broadcastSystemMessage(
            //     Component.literal("Hull repaired by " + contribution + " pts"), false);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        Block block = event.getState().getBlock();
        float contribution = getHullContribution(block);
        if (contribution <= 0) return;

        ShipSystemsData data = getShipDataAt((Level) event.getLevel(), event.getPos());
        if (data != null) {
            data.damageHull(contribution * (float) VS2SSConfig.COMMON.hullDamagePerBrokenBlock.get() * 50f);
            // The multiplier compensates for points scale
        }
    }

    private static float getHullContribution(Block block) {
        if (block == ModBlocks.HULL_PLATING.get()) return HULL_PLATING_CONTRIBUTION;
        if (block == ModBlocks.REINFORCED_HULL.get()) return REINFORCED_HULL_CONTRIBUTION;
        return 0f;
    }
}
