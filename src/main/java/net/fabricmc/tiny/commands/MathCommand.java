package net.fabricmc.tiny.commands;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.*;

public class MathCommand {

    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        dispatcher.register(
                literal("math").then(
                        argument("expr", StringArgumentType.greedyString())
                        .executes(MathCommand::execute))
        );
    }

    private static int execute(CommandContext<CottonClientCommandSource> ctx)
    {
        String expr = StringArgumentType.getString(ctx, "expr");
        double result = new DoubleEvaluator().evaluate(expr);
        ctx.getSource().sendFeedback(new LiteralText(Formatting.GREEN + "= " + result), false);
        return Command.SINGLE_SUCCESS;
    }
}
