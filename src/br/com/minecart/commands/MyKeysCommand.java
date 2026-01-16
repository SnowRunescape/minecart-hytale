package br.com.minecart.commands;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.core.MinecartAPI;
import br.com.minecart.core.entities.Key;

public class MyKeysCommand extends AbstractAsyncCommand {
    public MyKeysCommand() {
        super("mykeys", "Shows your available keys");

        this.addAliases("minhaskeys");
    }

    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {
        CommandSender sender = commandContext.sender();

        if (sender instanceof Player player) {
            return CompletableFuture.runAsync(() -> {
                try {
                    ArrayList<Key> minecartKeys = MinecartAPI.myKeys(player.getDisplayName());

                    player.sendMessage(CommandMessages.PLAYER_LIST_KEYS_TITLE);
                    player.sendMessage(Message.raw(""));

                    if (minecartKeys.isEmpty()) {
                        player.sendMessage(CommandMessages.PLAYER_DONT_HAVE_KEY);
                    } else {
                        for (Key minecartKey : minecartKeys) {
                            player.sendMessage(this.parseText(CommandMessages.PLAYER_LIST_KEYS_KEY, player, minecartKey));
                        }
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

    private Message parseText(Message message, Player player, Key minecartKey) {
        return message
            .param("key.code", minecartKey.getKey())
            .param("key.product_name", minecartKey.getProductName());
    }
}
