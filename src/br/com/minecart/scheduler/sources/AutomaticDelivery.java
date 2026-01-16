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
import br.com.minecart.MinecartAPI;
import br.com.minecart.entities.MinecartKey;
import br.com.minecart.helpers.MinecartKeyHelper;
import br.com.minecart.scheduler.SchedulerInterface;
import br.com.minecart.storage.LOGStorage;
import br.com.minecart.utilities.http.HttpRequestException;

public class AutomaticDelivery implements SchedulerInterface {
    public final static int DELAY = 60;

    public final static int NONE = 0;
    public final static int ONLY_PLAYER_ONLINE = 1;
    public final static int ANYTIME = 2;

    public void run() {
        try {
            ArrayList<MinecartKey> minecartKeys = MinecartKeyHelper.filterByAutomaticDelivery(MinecartAPI.deliveryPending());

            if (minecartKeys.isEmpty()) {
                return;
            }

            MinecartAPI.deliveryConfirm(MinecartKeyHelper.getMinecartKeyIds(minecartKeys));

            List<String> commands = new ArrayList<>();

            for (MinecartKey minecartKey : minecartKeys) {
                Collections.addAll(commands, minecartKey.getCommands());
            }

            this.executeCommands(commands);
        } catch (HttpRequestException e) {
            Message message = MinecartAPI.messageHttpError(ConsoleSender.INSTANCE, e.getResponse());
            ConsoleSender.INSTANCE.sendMessage(message);
        }
    }

    private void executeCommands(List<String> commands) {
        long delay = 0L;

        for (final String command : commands) {
            HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> {
                if (!CommandManager.get().handleCommand(ConsoleSender.INSTANCE, command).isDone()) {
                    LOGStorage.executeCommand(command);
                }
            }, delay, TimeUnit.MILLISECONDS);

            delay += Main.CONFIG.get().getDelayExecuteCommands() * 1L;
        }
    }
}
