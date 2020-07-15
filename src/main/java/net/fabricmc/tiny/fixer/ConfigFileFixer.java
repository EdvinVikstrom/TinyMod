package net.fabricmc.tiny.fixer;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.event.RenderEvent;
import net.fabricmc.tiny.event.TickEvent;
import net.fabricmc.tiny.utils.FileUtils;
import net.fabricmc.tiny.utils.property.AbstractProperty;

import java.io.File;
import java.util.Map;

public class ConfigFileFixer implements ITinyFixer {

    private static final String CONFIG_FILE_0_0_1 = "tiny-mod.txt";

    private void loadConfig_0_0_1(Map<String, AbstractProperty<?>> properties)
    {
        byte[] data = FileUtils.read(CONFIG_FILE_0_0_1);
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

    @Override
    public void init()
    {
        File oldConfigFile = new File("tiny-mod.txt");
        boolean rewrite = true;
        if (oldConfigFile.exists())
        {
            loadConfig_0_0_1(Config.getProperties());
            oldConfigFile.delete();
        }else
            rewrite = false;
        if (rewrite)
            Config.write();
    }

    @Override
    public TickEvent.Event tickEvent()
    {
        return null;
    }

    @Override
    public RenderEvent.Event renderEvent()
    {
        return null;
    }
}
