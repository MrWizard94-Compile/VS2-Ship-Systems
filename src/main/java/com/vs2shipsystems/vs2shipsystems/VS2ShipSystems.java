package com.vs2shipsystems.vs2shipsystems;

import com.mojang.logging.LogUtils;
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

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // TODO: Register DeferredRegisters here for Blocks, Items, BlockEntities, etc.
        // Example:
        // ModBlocks.BLOCKS.register(modEventBus);
        // ModItems.ITEMS.register(modEventBus);

        LOGGER.info("VS2 Ship Systems initialized. VS2 integration coming soon!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("VS2 Ship Systems common setup complete.");
        // TODO: Register VS2 ship attachments, events, network packets, etc.
    }
}
