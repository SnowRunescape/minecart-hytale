package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.MinecartHttpResponseTranslateMessage;
import br.com.minecart.core.MinecartAPI;
import br.com.minecart.core.entities.Cash;
import br.com.minecart.core.utilities.http.HttpRequestException;

public class RedeemCashCommand extends AbstractAsyncCommand {
    public RedeemCashCommand() {
        super("redeemcash", "Redeems available cash");

        this.addAliases("resgatarcash");
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {
        CommandSender sender = commandContext.sender();

        if (sender instanceof Player player) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Cash minecartCash = MinecartAPI.redeemCash(player.getDisplayName());

                    if (minecartCash.getQuantity() > 0) {
                        this.delivery(player, minecartCash);
                    }
                } catch (HttpRequestException e) {
                    MinecartHttpResponseTranslateMessage.processHttpError(player, e.getResponse());
                }
            });
        } else {
            commandContext.sendMessage(CommandMessages.PLAYER_ONLY);
        }

        return CompletableFuture.completedFuture(null);
    }

    private boolean delivery(Player player, Cash minecartCash) {
        String command = minecartCash.getCommand();

        // TODO: implements CommandFailureLogger
        CommandManager.get().handleCommand(ConsoleSender.INSTANCE, command);

        player.sendMessage(this.parseText(CommandMessages.SUCCESS_REDEEM_CASH, player));
        return true;
    }

    private Message parseText(Message message, Player player) {
        return message
            .param("player.name", player.getDisplayName());
    }
}
