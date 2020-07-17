package net.fabricmc.tiny;

import net.minecraft.util.Identifier;

public class Constants {

    public static final String MODID = "tiny";

    public static final Texture WIDGET_TEXTURE = new Texture(new Identifier(MODID, "textures/gui/widget.png"), 64, 64);

    public static final class Texture {
        public final Identifier identifier;
        public final int width, height;

        public Texture(Identifier identifier, int width, int height)
        {
            this.identifier = identifier;
            this.width = width;
            this.height = height;
        }
    }

}
