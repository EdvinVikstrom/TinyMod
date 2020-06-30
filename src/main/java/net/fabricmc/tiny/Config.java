package net.fabricmc.tiny;

import net.fabricmc.tiny.utils.FileUtils;
import net.fabricmc.tiny.utils.property.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Set;

public class Config {

    private static final String CONFIG_FILE = "./tiny-mod.txt";

    private static final LinkedHashMap<String, AbstractProperty<?>> properties = new LinkedHashMap<>();
    static {
        properties.put("fastMath", new BooleanProperty(Category.PERFORMANCE, false));

        properties.put("cloudHeight", new IntProperty(Category.DETAILS, 100, 80, 500));
        properties.put("renderStars", new BooleanProperty(Category.DETAILS, true));
        properties.put("renderWeather", new BooleanProperty(Category.DETAILS, true));
        properties.put("renderSky", new BooleanProperty(Category.DETAILS, true));
        properties.put("renderFog", new BooleanProperty(Category.DETAILS, true));
        properties.put("renderFogStart", new EnumProperty(Category.DETAILS, 0, new String[]{
                "default", "near", "far"
        }));
        properties.put("bedrockFog", new BooleanProperty(Category.DETAILS, false));

        properties.put("textureAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("waterAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("lavaAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("itemAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("blockAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("playerAnimation", new BooleanProperty(Category.DETAILS, true));
        properties.put("entityAnimation", new BooleanProperty(Category.DETAILS, true));

        properties.put("zoomFactor", new FloatProperty(Category.OTHER, 19.0D, 1.0D, 40.0D));

        properties.put("zooming", new BooleanProperty(Category.HIDDEN, false));
        properties.put("shaderPack", new StringProperty(Category.HIDDEN, "none"));
    }

    public static LinkedHashMap<String, AbstractProperty<?>> getProperties()
    {
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
