package net.fabricmc.tiny;

import net.fabricmc.tiny.event.events.LinearTexEvent;
import net.fabricmc.tiny.utils.FileUtils;
import net.fabricmc.tiny.utils.HashMapBuilder;
import net.fabricmc.tiny.utils.property.*;
import net.fabricmc.tiny.utils.property.properties.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Config {

    private static final String CONFIG_FILE = "./tiny-mod.txt";

    private static final Map<String, AbstractProperty<?>> properties = new LinkedHashMap<>();
    static {
        //properties.put("bloom", new BooleanProperty(Category.GRAPHICS, false, null));
        properties.put("linear", new BooleanProperty(Categories.GRAPHICS, false, property -> LinearTexEvent.INSTANCE.update()));
        properties.put("betterGrass", new BooleanProperty(Categories.GRAPHICS, false, null));

        properties.put("fastMath", new BooleanProperty(Categories.PERFORMANCE, false, null));
        properties.put("optimizedInventory", new BooleanProperty(Categories.PERFORMANCE, false, null));

        properties.put("cloudHeight", new IntProperty(Categories.DETAILS, 100, 80, 500, null));
        properties.put("renderStars", new BooleanProperty(Categories.DETAILS, true, null));
        properties.put("renderWeather", new BooleanProperty(Categories.DETAILS, true, null));
        properties.put("renderSky", new BooleanProperty(Categories.DETAILS, true, null));
        properties.put("renderFog", new BooleanProperty(Categories.DETAILS, true, null));
        properties.put("renderFogStart", new EnumProperty(Categories.DETAILS, 0, new String[]{
                "default", "near", "far"
        }, null));
        properties.put("bedrockFog", new BooleanProperty(Categories.DETAILS, false, null));
        properties.put("textureAnimation", new BooleanProperty(Categories.DETAILS, true, null));

        //properties.put("fixedInventory", new BooleanProperty(Category.UI, false, null));

        properties.put("zoomFactor", new FloatProperty(Categories.OTHER, 19.0D, 1.0D, 40.0D, null));
        //properties.put("chatEmotes", new BooleanProperty(Category.OTHER, false, null));
        properties.put("debugPieColors", new BooleanProperty(Categories.OTHER, false, null));
        properties.put("debugPieBackgroundOpacity", new FloatProperty(Categories.OTHER, 144.0D / 255.0D, 0.0D, 1.0D, null));
        properties.put("debugPieTextOpacity", new FloatProperty(Categories.OTHER, 224.0D / 255.0D, 0.0D, 1.0D, null));
        properties.put("debugGraph", new BooleanProperty(Categories.OTHER, false, null));
        properties.put("debugHud", new ListProperty(Categories.OTHER,
                new HashMapBuilder<String, AbstractProperty<?>>()
                        .put("showFPS", new BooleanProperty(Categories.OTHER, false, null))
                        .put("showTPS", new BooleanProperty(Categories.OTHER, false, null))
                .build(),
                null
        ));
        properties.put("showCollision", new BooleanProperty(Categories.OTHER, false, null));
        //properties.put("outlines", new BooleanProperty(Category.OTHER, false, null));
        properties.put("openglInfo", new BooleanProperty(Categories.OTHER, false, null));

        properties.put("zooming", new BooleanProperty(Categories.HIDDEN, false, null));
        properties.put("shaderPack", new StringProperty(Categories.HIDDEN, "none", null));
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

    public static BooleanProperty getBoolean(String key)
    {
        return (BooleanProperty) properties.get(key);
    }
    public static FloatProperty getFloat(String key)
    {
        return (FloatProperty) properties.get(key);
    }
    public static IntProperty getInt(String key)
    {
        return (IntProperty) properties.get(key);
    }
    public static StringProperty getString(String key)
    {
        return (StringProperty) properties.get(key);
    }
    public static ListProperty getList(String key)
    {
        return (ListProperty) properties.get(key);
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
