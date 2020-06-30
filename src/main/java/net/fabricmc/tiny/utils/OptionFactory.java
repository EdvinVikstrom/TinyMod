package net.fabricmc.tiny.utils;

import net.fabricmc.tiny.utils.property.*;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public class OptionFactory {

    public static Option create(String key, AbstractProperty<?> property)
    {
        if (property instanceof BooleanProperty)
            return new BooleanOption(key,
                    gameOptions -> ((BooleanProperty) property).get(),
                    (gameOptions, value) -> ((BooleanProperty) property).set(value));
        else if (property instanceof FloatProperty)
            return new DoubleOption(key,
                    ((FloatProperty) property).getMin(),
                    ((FloatProperty) property).getMax(),
                    0.1F,
                    gameOptions -> ((FloatProperty) property).get(),
                    (gameOptions, value) -> ((FloatProperty) property).set(value),
                    (gameOptions, doubleOption) -> new TranslatableText(key, String.format("%.1f", ((FloatProperty) property).get())));
        else if (property instanceof IntProperty)
            return new DoubleOption(key,
                    ((IntProperty) property).getMin(),
                    ((IntProperty) property).getMax(),
                    1.0F,
                    gameOptions -> (double) (int) ((IntProperty) property).get(),
                    (gameOptions, value) -> ((IntProperty) property).set((int) (double) value),
                    (gameOptions, doubleOption) -> new TranslatableText(key, ((IntProperty) property).get()));
        else if (property instanceof EnumProperty)
            return new CyclingOption(key,
                    (gameOptions, integer) -> ((EnumProperty) property).cycle(),
                    (gameOptions, cyclingOption) -> new TranslatableText(key,
                            new TranslatableText(key + "." + ((EnumProperty) property).getCurrent())
                                    .getString()));
        return null;
    }

}
