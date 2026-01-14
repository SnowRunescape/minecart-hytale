package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.MinecartAPI;
import br.com.minecart.entities.MinecartCash;
import br.com.minecart.storage.LOGStorage;

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
                    MinecartCash minecartCash = MinecartAPI.redeemCash(player.getDisplayName());

                    if (minecartCash.getQuantity() > 0) {
                        this.delivery(player, minecartCash);
                    }
                } catch (Exception e) {
                    player.sendMessage(CommandMessages.INTERNAL_SERVER_ERROR);
                }
            });
        } else {
            commandContext.sendMessage(CommandMessages.PLAYER_ONLY);
        }

        return CompletableFuture.completedFuture(null);
    }

    private boolean delivery(Player player, MinecartCash minecartCash) {
        String command = minecartCash.getCommand();

        if (CommandManager.get().handleCommand(ConsoleSender.INSTANCE, command).isDone()) {
            player.sendMessage(this.parseText(CommandMessages.SUCCESS_REDEEM_CASH, player));
            return true;
        } else {
            player.sendMessage(CommandMessages.INTERNAL_SERVER_ERROR);
            player.sendMessage(this.parseText(CommandMessages.ERROR_REDEEM_CASH, player));

            LOGStorage.executeCommand(command);
            return false;
        }
    }

    private Message parseText(Message message, Player player) {
        return message
            .param("player.name", player.getDisplayName());
    }
}
