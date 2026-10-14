package pocketutils.component;

import net.fabricmc.api.ModInitializer;

public class InitializeModComponents implements ModInitializer {
    @Override
    public void onInitialize() {
        ModComponents.initialize();
    }
}
