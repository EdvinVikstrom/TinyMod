package net.fabricmc.tiny.commands;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MathCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated)
    {
        dispatcher.register(
                literal("math").then(
                        argument("expr", StringArgumentType.greedyString())
                        .executes(MathCommand::execute))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> ctx)
    {
        String expr = StringArgumentType.getString(ctx, "expr");
        double result = new DoubleEvaluator().evaluate(expr);
        ctx.getSource().sendFeedback(new LiteralText(Formatting.GREEN + "= " + result), false);
        return Command.SINGLE_SUCCESS;
    }
}
