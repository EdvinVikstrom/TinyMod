package net.fabricmc.tiny.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.tiny.utils.property.properties.EnumProperty;

import java.util.concurrent.CompletableFuture;

public class EnumArgumentType implements ArgumentType<Integer> {

    private final EnumProperty property;

    private EnumArgumentType(EnumProperty property)
    {
        this.property = property;
    }

    public static EnumArgumentType enumArg(EnumProperty property)
    {
        return new EnumArgumentType(property);
    }

    public static int getEnum(final CommandContext<?> context, final String name)
    {
        return context.getArgument(name, Integer.class);
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException
    {
        final int start = reader.getCursor();
        String value = reader.readUnquotedString();
        int index = -1;
        for (int i = 0; i < property.getValid().length; i++)
        {
            if (property.getValid()[i].equalsIgnoreCase(value))
            {
                index = i;
                break;
            }
        }
        if (index >= 0)
            return index;
        else
        {
            reader.setCursor(start);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
    {
        for (String e : property.getValid())
        {
            if (e.startsWith(builder.getRemaining().toLowerCase()))
                builder.suggest(e);
        }
        return builder.buildFuture();
    }
}
