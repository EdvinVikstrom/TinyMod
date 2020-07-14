package net.fabricmc.tiny.compat;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.tiny.screen.config.ConfigMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class ModMenuHelper implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return ConfigMenuScreen.Factory.INSTANCE;
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories()
    {
        return new HashMap<>();
    }
}
