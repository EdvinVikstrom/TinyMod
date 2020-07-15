package net.fabricmc.tiny.compat;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.tiny.commands.MathCommand;

public class ClientCommands implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher)
    {
        MathCommand.register(commandDispatcher);
    }
}
