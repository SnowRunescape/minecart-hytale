package br.com.minecart.listeners;

import java.util.function.Consumer;

import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;

import br.com.minecart.core.PlayerSessionManager;

public class PlayerConnectListener implements Consumer<PlayerConnectEvent> {
    @Override
    public void accept(PlayerConnectEvent event) {
        String username = event.getPlayerRef().getUsername();

        PlayerSessionManager.getInstance().onJoin(username);
    }
}
