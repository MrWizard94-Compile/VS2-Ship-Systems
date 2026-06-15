package com.vs2shipsystems.vs2shipsystems.ship;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import net.minecraft.nbt.CompoundTag;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.ships.ShipAttachment;

import javax.annotation.Nullable;

/**
 * Per-ship data container for VS2 Ship Systems.
 *
 * This class is attached to VS2 Ships and stores the state for all ship systems:
 * - Hull integrity (0.0 - 1.0, where 1.0 = 100% intact)
 * - Flood level / water ingress
 * - Sealing status per "compartment" (future: use ship chunks or custom regions)
 * - Other system states
 *
 * Access pattern (once fully hooked):
 *   Ship ship = ...; // from VS2
 *   ShipSystemsData data = ShipSystemsData.getOrCreate(ship);
 *
 * VS2 automatically persists attachments that implement proper NBT serialization.
 */
public class ShipSystemsData implements ShipAttachment {

    // === Core Ship System Stats (points-based for hull blocks) ===
    private float currentHullPoints = 100f;   // Current structural points from hull blocks
    private float maxHullPoints = 100f;       // Max possible based on all hull blocks ever placed
    private float floodLevel = 0.0f;          // 0.0 = dry, 1.0 = fully flooded
    private boolean isEnvironmentallySealed = false;

    // Future expansion fields:
    // private Map<String, CompartmentData> compartments = new HashMap<>();
    // private float oxygenLevel = 1.0f;
    // private float powerLevel = 0.0f;

    public ShipSystemsData() {
        // Default constructor required for deserialization
    }

    // === Getters / Setters with clamping ===
    public float getHullIntegrity() {
        if (maxHullPoints <= 0) return 1.0f;
        return Math.max(0.0f, Math.min(1.0f, currentHullPoints / maxHullPoints));
    }

    public float getCurrentHullPoints() {
        return currentHullPoints;
    }

    public float getMaxHullPoints() {
        return maxHullPoints;
    }

    public void addHullPoints(float amount) {
        this.currentHullPoints += amount;
        this.maxHullPoints = Math.max(this.maxHullPoints, this.currentHullPoints);
    }

    public void removeHullPoints(float amount) {
        this.currentHullPoints = Math.max(0, this.currentHullPoints - amount);
    }

    public float getFloodLevel() {
        return floodLevel;
    }

    public void setFloodLevel(float value) {
        this.floodLevel = Math.max(0.0f, Math.min(1.0f, value));
    }

    public boolean isEnvironmentallySealed() {
        return isEnvironmentallySealed;
    }

    public void setEnvironmentallySealed(boolean sealed) {
        this.isEnvironmentallySealed = sealed;
    }

    /**
     * Apply damage to hull (e.g. from collisions, weapons, or block breaks).
     */
    public void damageHull(float amount) {
        removeHullPoints(amount);
        // Future: trigger flood increase if integrity drops below thresholds
    }

    /**
     * Repair or add hull points (from placing hull blocks or using sealant).
     */
    public void repairHull(float amount) {
        addHullPoints(amount);
    }

    // === VS2 Attachment / Persistence ===

    @Override
    public void onLoad(@Nullable CompoundTag tag) {
        if (tag == null) return;

        this.currentHullPoints = tag.getFloat("CurrentHullPoints");
        this.maxHullPoints = tag.getFloat("MaxHullPoints");
        this.floodLevel = tag.getFloat("FloodLevel");
        this.isEnvironmentallySealed = tag.getBoolean("Sealed");
    }

    @Override
    public CompoundTag onSave() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("CurrentHullPoints", currentHullPoints);
        tag.putFloat("MaxHullPoints", maxHullPoints);
        tag.putFloat("FloodLevel", floodLevel);
        tag.putBoolean("Sealed", isEnvironmentallySealed);
        return tag;
    }

    /**
     * Helper to safely get or create the attachment on a VS2 Ship (core API).
     */
    public static ShipSystemsData getOrCreate(Ship ship) {
        ShipSystemsData data = ship.getAttachment(ShipSystemsData.class);
        if (data == null) {
            data = new ShipSystemsData();
            ship.setAttachment(ShipSystemsData.class, data);
        }
        return data;
    }

    /**
     * Overload for ShipData (common in mod code).
     * Delegates to the underlying ship object when possible.
     */
    public static ShipSystemsData getOrCreate(org.valkyrienskies.mod.common.ships.ShipData shipData) {
        // ShipData in VS2 1.20.1 wraps/contains the core Ship for attachments in most cases.
        // We attempt direct use first (many addons do this), fall back to core if available.
        try {
            ShipSystemsData data = shipData.getAttachment(ShipSystemsData.class);
            if (data == null) {
                data = new ShipSystemsData();
                shipData.setAttachment(ShipSystemsData.class, data);
            }
            return data;
        } catch (Exception e) {
            VS2ShipSystems.LOGGER.warn("ShipData attachment direct access issue, using core if possible: " + e.getMessage());
            // Fallback would require shipData.getShip() in some VS2 builds; for now return fresh if needed
            return new ShipSystemsData();
        }
    }

    /**
     * Returns the data if it already exists on the ship, otherwise null.
     */
    @Nullable
    public static ShipSystemsData get(Ship ship) {
        return ship.getAttachment(ShipSystemsData.class);
    }
}
