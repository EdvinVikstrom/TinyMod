package net.fabricmc.tiny;

import net.fabricmc.tiny.utils.FileUtils;
import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.Categories;
import net.fabricmc.tiny.utils.property.ICategory;
import net.fabricmc.tiny.utils.property.properties.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Config {

    private static final String CONFIG_FILE = "./tiny-mod.txt";

    //public static final BooleanProperty LINEAR_TEXTURES = new BooleanProperty(Categories.GRAPHICS, false, null);
    public static final BooleanProperty TINY_RENDERER = new BooleanProperty(Categories.GRAPHICS, false, null);

    public static final BooleanProperty FAST_MATH = new BooleanProperty(Categories.PERFORMANCE, false, null);

    public static final FloatProperty CLOUD_HEIGHT = new FloatProperty(Categories.DETAILS, 100D, 80D, 500D, 1.0F, null);
    public static final BooleanProperty BETTER_GRASS = new BooleanProperty(Categories.DETAILS, false, null);
    public static final BooleanProperty RENDER_STARS = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty RENDER_WEATHER = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty RENDER_SKY = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty CONNECTED_TEXTURES = new BooleanProperty(Categories.DETAILS, true, null);

    public static final FloatProperty RENDER_FOG$FAR_VALUE = new FloatProperty(Categories.DETAILS, 2.0D, 1.0D, 10.0D, 0.1F, null);
    public static final FloatProperty RENDER_FOG$NEAR_VALUE = new FloatProperty(Categories.DETAILS, 0.5D, 0.1D, 1.0D, 0.1F, null);
    public static final EnumProperty RENDER_FOG = new EnumProperty(Categories.DETAILS, 0, new String[]{
            "default", "far", "near", "off"
    }, null);

    //public static final BooleanProperty BEDROCK_FOG = new BooleanProperty(Categories.DETAILS, false, null);
    public static final BooleanProperty TEXTURE_ANIMATION = new BooleanProperty(Categories.DETAILS, true, null);
    public static final EnumProperty DROPPED_ITEM_RENDERING = new EnumProperty(Categories.DETAILS, 0, new String[]{
            "default", "simple", "fancy"
    }, null);
    //public static final BooleanProperty CHUNK_MAP_PREVIEW = new BooleanProperty(Categories.DETAILS, false, null);

    public static final FloatProperty ZOOM_FACTOR = new FloatProperty(Categories.OTHER, 19.0D, 1.0D, 40.0D, 1.0F, null);
    public static final FloatProperty DEBUG_TEXT_OPACITY = new FloatProperty(Categories.OTHER, 0.9568627451D, 0.0D, 1.0D, 0.01F, null);
    public static final FloatProperty DEBUG_OPACITY = new FloatProperty(Categories.OTHER, 0.5647058824D, 0.0D, 1.0D, 0.01F, null);
    //public static final BooleanProperty DEBUG_COLORS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty DEBUG_GRAPH = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_FPS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_TPS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_COORDS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_COLLISION = new BooleanProperty(Categories.OTHER, false, null);
    public static final EnumProperty SCREENSHOT_RESOLUTION = new EnumProperty(Categories.OTHER, 0, new String[]{
            "disabled", "2K", "4K", "8K", "12K", "16K", "24K", "32K", "48K", "64K"
    }, null);
    public static final BooleanProperty OPENGL_INFO = new BooleanProperty(Categories.OTHER, false, null);

    public static final FloatProperty ACTIVE_SHADER_PACK = new FloatProperty(Categories.HIDDEN, -1D, -1, Short.MAX_VALUE, 1.0F, null);

    public static final AtomicBoolean ZOOMING = new AtomicBoolean(false);
    public static final AtomicBoolean GL40_SUPPORTED = new AtomicBoolean(false);

    private static final Map<String, AbstractProperty<?>> properties = new LinkedHashMap<>();
    static {

        {
            TINY_RENDERER.putFlag(AbstractProperty.FLAG_DEPRECATED);
            FAST_MATH.putFlag(AbstractProperty.FLAG_DEPRECATED);
            RENDER_FOG.put("renderFog.far_value", RENDER_FOG$FAR_VALUE);
            RENDER_FOG.put("renderFog.near_value", RENDER_FOG$NEAR_VALUE);
        }

        //properties.put("linearTextures", LINEAR_TEXTURES);
        properties.put("tinyRenderer", TINY_RENDERER);

        properties.put("fastMath", FAST_MATH);

        properties.put("cloudHeight", CLOUD_HEIGHT);
        properties.put("betterGrass", BETTER_GRASS);
        properties.put("renderStars", RENDER_STARS);
        properties.put("renderWeather", RENDER_WEATHER);
        properties.put("renderSky", RENDER_SKY);
        properties.put("renderFog", RENDER_FOG);
        properties.put("connectedTextures", CONNECTED_TEXTURES);
        //properties.put("bedrockFog", BEDROCK_FOG);
        properties.put("textureAnimation", TEXTURE_ANIMATION);
        properties.put("droppedItemRendering", DROPPED_ITEM_RENDERING);
        //properties.put("chunkMapPreview", CHUNK_MAP_PREVIEW);

        properties.put("zoomFactor", ZOOM_FACTOR);
        properties.put("debugTextOpacity", DEBUG_TEXT_OPACITY);
        properties.put("debugOpacity", DEBUG_OPACITY);
        //properties.put("debugColors", DEBUG_COLORS);
        properties.put("debugGraph", DEBUG_GRAPH);
        properties.put("showFPS", SHOW_FPS);
        properties.put("showTPS", SHOW_TPS);
        properties.put("showCoords", SHOW_COORDS);
        properties.put("showCollision", SHOW_COLLISION);
        properties.put("screenshotResolution", SCREENSHOT_RESOLUTION);
        properties.put("openglInfo", OPENGL_INFO);

        properties.put("activeShaderPack", ACTIVE_SHADER_PACK);
    }

    public static Map<String, AbstractProperty<?>> getProperties()
    {
        return properties;
    }

    public static Map<String, AbstractProperty<?>> getProperties(ICategory category)
    {
        Map<String, AbstractProperty<?>> properties = new LinkedHashMap<>();
        Set<String> keys = getProperties().keySet();
        for (String key : keys)
        {
            AbstractProperty<?> property = getProperty(key);
            if (!property.getCategory().equals(category))
                continue;
            properties.put(key, property);
        }
        return properties;
    }

    public static AbstractProperty<?> getProperty(String key)
    {
        return properties.get(key);
    }

    public static void resetProperties()
    {
        for (AbstractProperty<?> property : properties.values())
            property.reset();
    }

    public static void resetProperties(ICategory category)
    {
        for (AbstractProperty<?> property : properties.values())
        {
            if (!property.getCategory().equals(category))
                continue;
            property.reset();
        }
    }

    public static void write()
    {
        StringBuilder builder = new StringBuilder();
        Set<String> keys = properties.keySet();
        for (String key : keys)
            builder.append(key).append(" ").append(properties.get(key).asString()).append("\n");
        FileUtils.write(CONFIG_FILE, builder.toString().getBytes());
    }

    public static void load()
    {
        File file = new File(CONFIG_FILE);
        if (file.exists() && file.isFile())
        {
            byte[] data = FileUtils.read(CONFIG_FILE);
            if (data != null)
            {
                String[] lines = new String(data).split("\n");
                for (String line : lines)
                {
                    String[] args = line.split(" ");
                    if (args.length < 2)
                        continue;
                    if (properties.containsKey(args[0]))
                        properties.get(args[0]).fromString(args[1]);
                }
            }
        }
    }

}
