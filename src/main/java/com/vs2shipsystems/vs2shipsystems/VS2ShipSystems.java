package com.vs2shipsystems.vs2shipsystems;

import com.mojang.logging.LogUtils;
import com.vs2shipsystems.vs2shipsystems.config.VS2SSConfig;
import com.vs2shipsystems.vs2shipsystems.registry.ModBlocks;
import com.vs2shipsystems.vs2shipsystems.registry.ModCreativeTabs;
import com.vs2shipsystems.vs2shipsystems.registry.ModItems;
import com.vs2shipsystems.vs2shipsystems.ship.VS2ShipIntegration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * VS2 Ship Systems
 *
 * A Minecraft Forge 1.20.1 mod that extends Valkyrien Skies 2 (VS2) with
 * advanced ship systems and survival mechanics for ships.
 *
 * Planned features include:
 * - Hull integrity monitoring and damage
 * - Flood detection and water simulation on ships
 * - Environmental sealing (pressurized compartments, airlocks)
 * - Ship power / redstone systems
 * - Crew / life support mechanics
 * - And more...
 */
@Mod(VS2ShipSystems.MOD_ID)
public class VS2ShipSystems {
    public static final String MOD_ID = "vs2_ship_systems";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VS2ShipSystems() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VS2ShipIntegration.class);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // === Registration ===
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VS2SSConfig.COMMON_SPEC);

        // === VS2 Integration (will be expanded) ===
        // Ship attachments, chunk claims, assembly events, etc. are initialized here
        // See ship/ package and VS2ShipIntegration + ShipSystemsData
        //
        // Key feature: Environmentally sealed (or high-integrity) ships now block rain and water.
        // - Mixin on Level.isRainingAt
        // - Fluid place events
        // - Flood simulation respects sealing + rain

        LOGGER.info("VS2 Ship Systems initialized. Ships now block rain/water when sealed (main priority).");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("VS2 Ship Systems common setup complete.");

        // VS2-specific one-time setup (network, capabilities, attachment registration if needed)
        // Most VS2 integration happens via events and lazy attachment access on ships.
    }
}
