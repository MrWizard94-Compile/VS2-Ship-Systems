package com.vs2shipsystems.vs2shipsystems.item;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import com.vs2shipsystems.vs2shipsystems.ship.ShipSystemsData;
import com.vs2shipsystems.vs2shipsystems.ship.VS2ShipIntegration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * Hull Analyzer tool.
 * Right-click on a block that is part of a VS2 ship to read current ship systems data.
 */
public class HullAnalyzerItem extends Item {

    public HullAnalyzerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ShipSystemsData data = VS2ShipIntegration.getShipDataAt(level, context.getClickedPos());
        if (data == null) {
            context.getPlayer().sendSystemMessage(Component.literal("No VS2 ship detected at this location."));
            return InteractionResult.SUCCESS;
        }

        float integrityPercent = data.getHullIntegrity() * 100f;
        float floodPercent = data.getFloodLevel() * 100f;
        boolean sealed = data.isEnvironmentallySealed();

        String message = String.format(
            "Ship Systems - Integrity: %.1f%% (%.0f/%.0f pts) | Flood: %.1f%% | Sealed: %s",
            integrityPercent,
            data.getCurrentHullPoints(),
            data.getMaxHullPoints(),
            floodPercent,
            sealed ? "Yes" : "No"
        );

        context.getPlayer().sendSystemMessage(Component.literal(message));
        VS2ShipSystems.LOGGER.info("Hull Analyzer used on ship: " + message);

        return InteractionResult.SUCCESS;
    }
}
