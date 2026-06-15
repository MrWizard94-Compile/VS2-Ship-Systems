package com.vs2shipsystems.vs2shipsystems.ship;

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

    // === Core Ship System Stats ===
    private float hullIntegrity = 1.0f;          // 1.0 = perfect, 0.0 = completely destroyed
    private float floodLevel = 0.0f;             // 0.0 = dry, 1.0 = fully flooded
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
        return hullIntegrity;
    }

    public void setHullIntegrity(float value) {
        this.hullIntegrity = Math.max(0.0f, Math.min(1.0f, value));
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
     * Apply damage to hull integrity.
     * Called when the ship takes structural damage (collisions, weapons, breaking critical blocks, etc.).
     */
    public void damageHull(float amount) {
        setHullIntegrity(hullIntegrity - amount);
        // Future: trigger flood increase if integrity drops below thresholds
    }

    /**
     * Called when a hull block is placed or repaired on the ship.
     */
    public void repairHull(float amount) {
        setHullIntegrity(hullIntegrity + amount);
    }

    // === VS2 Attachment / Persistence ===

    @Override
    public void onLoad(@Nullable CompoundTag tag) {
        if (tag == null) return;

        this.hullIntegrity = tag.getFloat("HullIntegrity");
        this.floodLevel = tag.getFloat("FloodLevel");
        this.isEnvironmentallySealed = tag.getBoolean("Sealed");
    }

    @Override
    public CompoundTag onSave() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("HullIntegrity", hullIntegrity);
        tag.putFloat("FloodLevel", floodLevel);
        tag.putBoolean("Sealed", isEnvironmentallySealed);
        return tag;
    }

    /**
     * Helper to safely get or create the attachment on a VS2 Ship.
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
     * Returns the data if it already exists on the ship, otherwise null.
     */
    @Nullable
    public static ShipSystemsData get(Ship ship) {
        return ship.getAttachment(ShipSystemsData.class);
    }
}
