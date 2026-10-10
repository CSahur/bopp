package pocketutils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> GEMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pocketutils", "gems"));
}
