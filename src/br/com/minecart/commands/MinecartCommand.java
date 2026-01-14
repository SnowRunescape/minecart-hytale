package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;

public class MinecartCommand extends AbstractAsyncCommand {
    public MinecartCommand() {
        super("minecart", "Displays the Minecart plugin version");
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("[Minecart] Version [" + br.com.minecart.Main.VERSION + "]"));

        return CompletableFuture.completedFuture(null);
    }
}
