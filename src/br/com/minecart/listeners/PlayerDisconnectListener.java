package br.com.minecart.listeners;

import java.util.function.Consumer;

import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;

import br.com.minecart.core.PlayerSessionManager;

public class PlayerDisconnectListener implements Consumer<PlayerDisconnectEvent> {
    @Override
    public void accept(PlayerDisconnectEvent event) {
        String username = event.getPlayerRef().getUsername();

        PlayerSessionManager.getInstance().onQuit(username);
    }
}
