package net.fabricmc.tiny.commands.exceptions;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class InvalidEnumException extends SimpleCommandExceptionType {

    public InvalidEnumException(String e)
    {
        super(new LiteralMessage("Invalid enum `" + e + "`"));
    }
}
