package net.fabricmc.tiny.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.commands.arguments.EnumArgumentType;
import net.fabricmc.tiny.utils.CommonTexts;
import net.fabricmc.tiny.utils.property.AbstractProperty;
import net.fabricmc.tiny.utils.property.properties.BooleanProperty;
import net.fabricmc.tiny.utils.property.properties.EnumProperty;
import net.fabricmc.tiny.utils.property.properties.FloatProperty;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Map;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

public class ConfigCommand {

    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        LiteralCommandNode<CottonClientCommandSource> configNode =
                literal("config")
                .build();
        registerProperties(Config.getProperties(), configNode);
        dispatcher.register(literal("tiny").then(configNode));
    }

    private static void registerProperties(Map<String, AbstractProperty<?>> properties, LiteralCommandNode<CottonClientCommandSource> node)
    {
        for (String key : properties.keySet())
        {
            AbstractProperty<?> property = properties.get(key);
            ArgumentType<?> argumentType = StringArgumentType.word();
            if (property instanceof BooleanProperty) argumentType = BoolArgumentType.bool();
            else if (property instanceof FloatProperty) argumentType = DoubleArgumentType.doubleArg(((FloatProperty) property).getMin(), ((FloatProperty) property).getMax());
            else if (property instanceof EnumProperty) argumentType = EnumArgumentType.enumArg((EnumProperty) property);

            LiteralCommandNode<CottonClientCommandSource> configNode =
                    literal(key)
                    .build();

            /* set node */
            LiteralCommandNode<CottonClientCommandSource> configSetNode =
                    literal("set")
                                    .then(argument("value", argumentType)
                                            .executes(context -> executeSetProperty(context, key, property))
                                    )
                            .build();
            configNode.addChild(configSetNode);

            /* get node */
            LiteralCommandNode<CottonClientCommandSource> configGetNode =
                    literal("get")
                            .executes(context -> executeGetProperty(context, key, property))
                            .build();
            configNode.addChild(configGetNode);

            /* adding the nodes */
            node.addChild(configNode);
        }
    }

    private static int executeSetProperty(CommandContext<CottonClientCommandSource> ctx, String key, AbstractProperty<?> property)
    {
        String valueStr;
        if (property instanceof BooleanProperty)
        {
            boolean value = BoolArgumentType.getBool(ctx, "value");
            valueStr = value
                    ? Formatting.GREEN + CommonTexts.TRUE_TEXT.getString()
                    : Formatting.RED + CommonTexts.FALSE_TEXT.getString();
            ((BooleanProperty) property).set(value);
        }else if (property instanceof FloatProperty)
        {
            double value = DoubleArgumentType.getDouble(ctx, "value");
            valueStr = String.format("%.1f", value);
            ((FloatProperty) property).set(value);
        }else if (property instanceof EnumProperty)
        {
            int value = EnumArgumentType.getEnum(ctx, "value");
            valueStr = new TranslatableText("config." + key + "." + ((EnumProperty) property).getValid()[value]).getString();
            ((EnumProperty) property).set(value);
        }else
        {
            valueStr = StringArgumentType.getString(ctx, "value");
            property.fromString(valueStr);
        }
        Config.write();
        ctx.getSource().sendFeedback(new TranslatableText("text.property_update", new TranslatableText("config." + key), valueStr));
        return Command.SINGLE_SUCCESS;
    }

    private static int executeGetProperty(CommandContext<CottonClientCommandSource> ctx, String key, AbstractProperty<?> property)
    {
        Config.load();
        ctx.getSource().sendFeedback(new TranslatableText("text.property_update", new TranslatableText("config." + key), property.asString()));
        return Command.SINGLE_SUCCESS;
    }
}
