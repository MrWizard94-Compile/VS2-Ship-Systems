package com.vs2shipsystems.vs2shipsystems.mixin;

import com.vs2shipsystems.vs2shipsystems.ship.VS2ShipIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to make sealed ships block rain and precipitation.
 *
 * When a position is part of an environmentally sealed ship (or high hull integrity),
 * we report that it is not raining at that location.
 *
 * This affects:
 * - Cauldrons filling from rain
 * - Certain mob behaviors
 * - Some weather-related checks (isRainingAt is used by vanilla for precipitation)
 *
 * For full rain particle suppression inside the ship volume, additional client mixins
 * to LevelRenderer can be added later if needed.
 */
@Mixin(Level.class)
public abstract class LevelMixin {

    @Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
    private void vs2ss$blockRainOnSealedShips(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level level = (Level) (Object) this;

        // Check if this position is on a VS2 ship that should block weather
        if (VS2ShipIntegration.shouldBlockPrecipitation(level, pos)) {
            cir.setReturnValue(false);
        }
    }
}
