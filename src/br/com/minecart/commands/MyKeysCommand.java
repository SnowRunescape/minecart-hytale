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
                    ArrayList<Key> keys = MinecartAPI.myKeys(player.getDisplayName());

                    player.sendMessage(CommandMessages.PLAYER_LIST_KEYS_TITLE);
                    player.sendMessage(Message.raw(""));

                    if (keys.isEmpty()) {
                        player.sendMessage(CommandMessages.PLAYER_DONT_HAVE_KEY);
                    } else {
                        for (Key key : keys) {
                            Message keyMessage = Message.translation("commands.success.player-list-keys-key")
                                .color(java.awt.Color.GREEN)
                                .bold(true);

                            player.sendMessage(this.parseText(keyMessage, key));
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

    private Message parseText(Message message, Key key) {
        return message
            .param("key.code", key.getKey())
            .param("key.product_name", key.getProductName());
    }
}
