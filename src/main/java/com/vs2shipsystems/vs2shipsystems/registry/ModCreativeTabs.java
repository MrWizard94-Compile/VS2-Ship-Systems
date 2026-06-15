package com.vs2shipsystems.vs2shipsystems.registry;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = VS2ShipSystems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VS2ShipSystems.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VS2_SHIP_SYSTEMS_TAB =
            CREATIVE_MODE_TABS.register("vs2_ship_systems", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModBlocks.HULL_PLATING.get().asItem().getDefaultInstance())
                    .title(Component.translatable("itemGroup.vs2_ship_systems"))
                    .build());

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == VS2_SHIP_SYSTEMS_TAB.getKey()) {
            // Add all our blocks/items here
            event.accept(ModBlocks.HULL_PLATING);
            event.accept(ModBlocks.REINFORCED_HULL);
            event.accept(ModBlocks.FLOOD_DETECTOR);
            event.accept(ModBlocks.AIRLOCK_DOOR);
            event.accept(ModBlocks.ENVIRONMENTAL_SEALER);

            // Items
            event.accept(ModItems.HULL_ANALYZER);
            event.accept(ModItems.SEALANT_CANISTER);
        }
    }
}
