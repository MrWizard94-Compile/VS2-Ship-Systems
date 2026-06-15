package com.vs2shipsystems.vs2shipsystems.ship;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import net.minecraftforge.event.TickEvent;
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
 * - Providing utilities to get ship data from world positions
 */
@Mod.EventBusSubscriber(modid = VS2ShipSystems.MOD_ID)
public class VS2ShipIntegration {

    /**
     * Called every server tick.
     * We use this to lazily ensure all loaded ships have our attachment.
     */
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        // Get all currently loaded ships in the integrated server
        Collection<ShipData> ships = ValkyrienSkiesMod.getShipObjectWorld().getAllShips();

        for (ShipData ship : ships) {
            // Ensure our data attachment exists on every ship
            ShipSystemsData.getOrCreate(ship);
            // Future: run per-tick logic for flood simulation, seal checks, etc.
        }
    }

    /**
     * Utility: Get the ShipSystemsData for the ship that owns the given world position (if any).
     */
    public static ShipSystemsData getShipDataAt(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos) {
        ShipData ship = VSGameUtilsKt.getShipObjectManagingPos(level, pos);
        if (ship == null) {
            return null; // Not on a ship
        }
        return ShipSystemsData.getOrCreate(ship);
    }
}
