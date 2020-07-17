package net.fabricmc.tiny;

import com.google.gson.*;
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

    private static final String CONFIG_FILE = "./config/tiny-options.json";

    public static final FloatProperty RENDER_FOG$FAR_VALUE = new FloatProperty(Categories.DETAILS, 2.0D, 1.0D, 10.0D, 0.1F, null);
    public static final FloatProperty RENDER_FOG$NEAR_VALUE = new FloatProperty(Categories.DETAILS, 0.5D, 0.1D, 1.0D, 0.1F, null);

    public static final FloatProperty CLOUD_HEIGHT = new FloatProperty(Categories.DETAILS, 100D, 80D, 500D, 1.0F, null);
    public static final BooleanProperty BETTER_GRASS = new BooleanProperty(Categories.DETAILS, false, null);
    public static final BooleanProperty CONNECTED_TEXTURES = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty RENDER_STARS = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty RENDER_WEATHER = new BooleanProperty(Categories.DETAILS, true, null);
    public static final BooleanProperty RENDER_SKY = new BooleanProperty(Categories.DETAILS, true, null);
    public static final EnumProperty RENDER_FOG = new EnumProperty(Categories.DETAILS, 0, new String[]{
            "default", "far", "near", "off"
    }, null);
    public static final BooleanProperty TEXTURE_ANIMATION = new BooleanProperty(Categories.DETAILS, true, null);
    public static final EnumProperty ITEM_RENDERING = new EnumProperty(Categories.DETAILS, 0, new String[]{
            "default", "simple", "fancy"
    }, null);

    public static final FloatProperty ZOOM_FACTOR = new FloatProperty(Categories.OTHER, 19.0D, 1.0D, 40.0D, 1.0F, null);
    public static final FloatProperty DEBUG_TEXT_OPACITY = new FloatProperty(Categories.OTHER, 0.9568627451D, 0.0D, 1.0D, 0.01F, null);
    public static final FloatProperty DEBUG_OPACITY = new FloatProperty(Categories.OTHER, 0.5647058824D, 0.0D, 1.0D, 0.01F, null);
    public static final BooleanProperty DEBUG_GRAPH = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_FPS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_TPS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty SHOW_COORDS = new BooleanProperty(Categories.OTHER, false, null);
    public static final BooleanProperty CREATIVE_FIRE_OVERLAY = new BooleanProperty(Categories.OTHER, true, null);
    public static final EnumProperty SCREENSHOT_RESOLUTION = new EnumProperty(Categories.OTHER, 0, new String[]{
            "disabled", "2K", "4K", "8K", "12K", "16K", "24K", "32K", "48K", "64K"
    }, null);
    public static final BooleanProperty OPENGL_INFO = new BooleanProperty(Categories.OTHER, false, null);

    public static final AtomicBoolean ZOOMING = new AtomicBoolean(false);
    public static final AtomicBoolean GL40_SUPPORTED = new AtomicBoolean(false);

    private static final Map<String, AbstractProperty<?>> properties = new LinkedHashMap<>();
    static {
        BETTER_GRASS.putFlag(AbstractProperty.FLAG_WIP);
        CONNECTED_TEXTURES.putFlag(AbstractProperty.FLAG_WIP);
        DEBUG_GRAPH.putFlag(AbstractProperty.FLAG_WIP);
        SCREENSHOT_RESOLUTION.putFlag(AbstractProperty.FLAG_WIP);

        properties.put("cloud_height", CLOUD_HEIGHT);
        properties.put("better_grass", BETTER_GRASS);
        properties.put("connected_textures", CONNECTED_TEXTURES);
        properties.put("render_stars", RENDER_STARS);
        properties.put("render_weather", RENDER_WEATHER);
        properties.put("render_sky", RENDER_SKY);
        properties.put("render_fog", RENDER_FOG);
        properties.put("texture_animation", TEXTURE_ANIMATION);
        properties.put("item_rendering", ITEM_RENDERING);

        properties.put("zoom_factor", ZOOM_FACTOR);
        properties.put("debug_text_opacity", DEBUG_TEXT_OPACITY);
        properties.put("debug_opacity", DEBUG_OPACITY);
        properties.put("debug_graph", DEBUG_GRAPH);
        properties.put("show_fps", SHOW_FPS);
        properties.put("show_tps", SHOW_TPS);
        properties.put("show_coords", SHOW_COORDS);
        properties.put("creative_fire_overlay", CREATIVE_FIRE_OVERLAY);
        properties.put("screenshot_resolution", SCREENSHOT_RESOLUTION);
        properties.put("opengl_info", OPENGL_INFO);
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

    private static void writeProperties(Map<String, AbstractProperty<?>> properties, JsonObject jsonObject)
    {
        properties.forEach((key, property) -> {
            if (property instanceof FloatProperty)
                jsonObject.addProperty(key, ((FloatProperty) property).get());
            else if (property instanceof BooleanProperty)
                jsonObject.addProperty(key, ((BooleanProperty) property).get());
            else
                jsonObject.addProperty(key, property.asString());
        });
    }

    public static void write()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        JsonObject jsonObject = new JsonObject();
        writeProperties(properties, jsonObject);
        FileUtils.write(CONFIG_FILE, gson.toJson(jsonObject).getBytes());
    }

    public static void load()
    {
        File file = new File(CONFIG_FILE);
        if (file.exists() && file.isFile())
        {
            byte[] data = FileUtils.read(CONFIG_FILE);
            if (data != null)
            {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(new String(data)).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
                {
                    AbstractProperty<?> property = properties.get(entry.getKey());
                    if (property == null)
                        continue;
                    if (property instanceof FloatProperty)
                        ((FloatProperty) property).set(entry.getValue().getAsDouble());
                    else if (property instanceof BooleanProperty)
                        ((BooleanProperty) property).set(entry.getValue().getAsBoolean());
                    else
                        property.fromString(entry.getValue().getAsString());
                }
            }
        }
    }

}
