package br.com.minecart.scheduler.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.console.ConsoleSender;

import br.com.minecart.Main;
import br.com.minecart.MinecartHttpResponseTranslateMessage;
import br.com.minecart.core.MinecartAPI;
import br.com.minecart.core.entities.Key;
import br.com.minecart.core.utilities.http.HttpRequestException;
import br.com.minecart.helpers.MinecartKeyHelper;
import br.com.minecart.scheduler.SchedulerInterface;

public class AutomaticDelivery implements SchedulerInterface {
    public final static int DELAY = 60;

    public final static int NONE = 0;
    public final static int ONLY_PLAYER_ONLINE = 1;
    public final static int ANYTIME = 2;

    public void run() {
        try {
            ArrayList<Key> minecartKeys = MinecartKeyHelper.filterByAutomaticDelivery(MinecartAPI.deliveryPending());

            if (minecartKeys.isEmpty()) {
                return;
            }

            MinecartAPI.deliveryConfirm(MinecartKeyHelper.getMinecartKeyIds(minecartKeys));

            List<String> commands = new ArrayList<>();

            for (Key minecartKey : minecartKeys) {
                Collections.addAll(commands, minecartKey.getCommands());
            }

            this.executeCommands(commands);
        } catch (HttpRequestException e) {
            Message message = MinecartHttpResponseTranslateMessage.messageHttpError(ConsoleSender.INSTANCE, e.getResponse());
            ConsoleSender.INSTANCE.sendMessage(message);
        }
    }

    private void executeCommands(List<String> commands) {
        long delay = 0L;

        for (final String command : commands) {
            HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> {
                // TODO: implements CommandFailureLogger
                CommandManager.get().handleCommand(ConsoleSender.INSTANCE, command);
            }, delay, TimeUnit.MILLISECONDS);

            delay += Main.CONFIG.get().getDelayExecuteCommands() * 1L;
        }
    }
}
