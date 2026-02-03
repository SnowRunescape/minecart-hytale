package br.com.minecart.commands;

import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.MinecartHttpResponseTranslateMessage;
import br.com.minecart.core.MinecartAPI;
import br.com.minecart.core.entities.Key;
import br.com.minecart.core.utilities.http.HttpRequestException;

public class RedeemKeyCommand extends AbstractAsyncCommand {
    private RequiredArg<String> key;

    public RedeemKeyCommand() {
        super("redeemkey", "Redeems a specific key");

        this.addAliases("resgatarkey");

        this.key = this.withRequiredArg("key", "The key", ArgTypes.STRING);
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {
        CommandSender sender = commandContext.sender();

        if (sender instanceof Player player) {
            return CompletableFuture.runAsync(() -> {
                try {
                    String commandKey = commandContext.get(this.key);
                    Key key = MinecartAPI.redeemKey(player.getDisplayName(), commandKey);

                    this.delivery(player, key);
                } catch (HttpRequestException e) {
                    MinecartHttpResponseTranslateMessage.processHttpError(player, e.getResponse());
                }
            });
        } else {
            commandContext.sendMessage(CommandMessages.PLAYER_ONLY);
        }

        return CompletableFuture.completedFuture(null);
    }

    private void delivery(Player player, Key key)
    {
        if (this.executeCommands(player, key)) {
            player.sendMessage(this.parseText(CommandMessages.SUCCESS_ACTIVE_KEY, player, key));
        } else {
            this.sendMessageFailed(player, key);
        }
    }

    private Boolean executeCommands(Player player, Key key)
    {
        Boolean result = true;

        for (String command : key.getCommands()) {
            // TODO: implements CommandFailureLogger
            CommandManager.get().handleCommand(ConsoleSender.INSTANCE, command);
        }

        return result;
    }

    private void sendMessageFailed(Player player, Key key)
    {
        player.sendMessage(CommandMessages.INTERNAL_SERVER_ERROR);
        player.sendMessage(this.parseText(CommandMessages.ERROR_REDEEM_KEY, player, key));
    }

    private Message parseText(Message message, Player player, Key key) {
        return message
            .param("player.name", player.getDisplayName())
            .param("key.product_name", key.getProductName());
    }
}
