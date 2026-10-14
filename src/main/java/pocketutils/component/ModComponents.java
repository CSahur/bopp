package pocketutils.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import pocketutils.PocketUtils;

public class ModComponents {

    public static final DataComponentType<?> GEM_COOLDOWN = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            PocketUtils.id("gem_cooldown"),
            DataComponentType.<Float>builder().persistent(ExtraCodecs.NON_NEGATIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build()
    );





    protected static void initialize() {
        PocketUtils.LOGGER.info("Registering {} components", PocketUtils.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }
}
