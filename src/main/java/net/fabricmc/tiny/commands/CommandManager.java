package net.fabricmc.tiny.commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class CommandManager {

    public static final CommandManager INSTANCE = new CommandManager();

    public void registerCommands()
    {
        CommandRegistrationCallback.EVENT.register(MathCommand::register);
    }

}
