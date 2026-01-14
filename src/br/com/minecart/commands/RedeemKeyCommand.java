package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;

public class RedeemKeyCommand extends AbstractAsyncCommand {
    private RequiredArg<String> key;

    public RedeemKeyCommand() {
        super("redeemkey", "Redeems a specific key");

        this.addAliases("resgatarkey");

        this.key = this.withRequiredArg("key", "The key", ArgTypes.STRING);
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {

        return CompletableFuture.completedFuture(null);
    }
}
