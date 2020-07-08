package net.fabricmc.tiny.utils.cape;

import net.minecraft.util.Identifier;

public abstract class AbstractCape {

    protected final String username;

    public AbstractCape(String username) {
        this.username = username;
    }

    public abstract boolean downloadCape();
    public abstract boolean hasCape();
    public abstract Identifier getCapeTexture();

}
