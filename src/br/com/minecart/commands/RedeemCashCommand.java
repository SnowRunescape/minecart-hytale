package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;

public class RedeemCashCommand extends AbstractAsyncCommand {
    public RedeemCashCommand() {
        super("redeemcash", "Redeems available cash");

        this.addAliases("resgatarcash");
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {

        return CompletableFuture.completedFuture(null);
    }
}
