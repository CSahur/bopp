package pocketutils.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import pocketutils.PocketUtils;

public class ModItemsIds {
    private static ResourceKey<Item> create(final String name) {
        return ResourceKey.create(Registries.ITEM, PocketUtils.id(name));
    }
    public static final ResourceKey<Item> HEALTH_GEM = create("health_gem");

    public static final ResourceKey<Item> GRAVITY_GEM = create("gravity_gem");

    public static final ResourceKey<Item> MAGIC_GEM = create("magic_gem");

    public static final ResourceKey<Item> FIRE_GEM = create("fire_gem");

    public static final ResourceKey<Item> MINING_GEM = create("mining_gem");
}
