package pocketutils.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item HEALTH_GEM = register(
            ModItemsIds.HEALTH_GEM, properties -> new GemItems(properties, MobEffects.HEALTH_BOOST, 1, "health", 500), new Item.Properties().stacksTo(1).fireResistant()); //500

    public static final Item MINING_GEM = register(
            ModItemsIds.MINING_GEM, properties -> new GemItems(properties, MobEffects.HASTE,1, "mining", 400), new Item.Properties().stacksTo(1).fireResistant()); //400

    public static final Item GRAVITY_GEM = register(
            ModItemsIds.GRAVITY_GEM, properties -> new GemItems(properties, null,0, "gravity", 500), new Item.Properties().stacksTo(1).fireResistant()); //500

    public static final Item MAGIC_GEM = register(
            ModItemsIds.MAGIC_GEM, properties -> new GemItems(properties, null,0, "magic", 500), new Item.Properties().stacksTo(1).fireResistant().useCooldown(100)); //500

    public static final Item FIRE_GEM = register(
            ModItemsIds.FIRE_GEM, properties -> new GemItems(properties, MobEffects.FIRE_RESISTANCE,1, "fire", 300), new Item.Properties().stacksTo(1).fireResistant().useCooldown(160)); //300

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(creativeTab -> {
                    creativeTab.accept(ModItems.HEALTH_GEM);
                    creativeTab.accept(ModItems.MINING_GEM);
                    creativeTab.accept(ModItems.GRAVITY_GEM);
                    creativeTab.accept(ModItems.MAGIC_GEM);
                    creativeTab.accept(ModItems.FIRE_GEM);
                });
        GemItems.initialize();

    }
    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
}
