package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;

public class MinecartCommand extends AbstractCommand  {
    public MinecartCommand() {
        super("minecart", "Displays the Minecart plugin version");
    }

    @Override
    protected CompletableFuture<Void> execute(CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("[Minecart] Version [" + br.com.minecart.Main.VERSION + "]"));

        return CompletableFuture.completedFuture(null);
    }
}
