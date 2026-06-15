package com.vs2shipsystems.vs2shipsystems.registry;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import com.vs2shipsystems.vs2shipsystems.item.HullAnalyzerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VS2ShipSystems.MOD_ID);

    // BlockItems for the blocks above
    public static final RegistryObject<Item> HULL_PLATING = ITEMS.register("hull_plating",
            () -> new BlockItem(ModBlocks.HULL_PLATING.get(), new Item.Properties()));

    public static final RegistryObject<Item> REINFORCED_HULL = ITEMS.register("reinforced_hull",
            () -> new BlockItem(ModBlocks.REINFORCED_HULL.get(), new Item.Properties()));

    public static final RegistryObject<Item> FLOOD_DETECTOR = ITEMS.register("flood_detector",
            () -> new BlockItem(ModBlocks.FLOOD_DETECTOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> AIRLOCK_DOOR = ITEMS.register("airlock_door",
            () -> new BlockItem(ModBlocks.AIRLOCK_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENVIRONMENTAL_SEALER = ITEMS.register("environmental_sealer",
            () -> new BlockItem(ModBlocks.ENVIRONMENTAL_SEALER.get(), new Item.Properties()));

    // Tools / utility items
    public static final RegistryObject<Item> HULL_ANALYZER = ITEMS.register("hull_analyzer",
            () -> new HullAnalyzerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEALANT_CANISTER = ITEMS.register("sealant_canister",
            () -> new Item(new Item.Properties().stacksTo(16)));
}
