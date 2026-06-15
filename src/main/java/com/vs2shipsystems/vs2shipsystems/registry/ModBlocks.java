package com.vs2shipsystems.vs2shipsystems.registry;

import com.vs2shipsystems.vs2shipsystems.VS2ShipSystems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VS2ShipSystems.MOD_ID);

    // Core hull blocks - these will contribute to ship integrity calculations
    public static final RegistryObject<Block> HULL_PLATING = BLOCKS.register("hull_plating",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> REINFORCED_HULL = BLOCKS.register("reinforced_hull",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(8.0F, 10.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    // Detection and control blocks (stubs for now)
    public static final RegistryObject<Block> FLOOD_DETECTOR = BLOCKS.register("flood_detector",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(3.0F)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> AIRLOCK_DOOR = BLOCKS.register("airlock_door",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(4.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final RegistryObject<Block> ENVIRONMENTAL_SEALER = BLOCKS.register("environmental_sealer",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(4.0F)
                    .sound(SoundType.METAL)));
}
