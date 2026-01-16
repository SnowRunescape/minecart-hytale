package br.com.minecart.helpers;

import java.util.concurrent.TimeUnit;

import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

import br.com.minecart.PlayerSessionManager;

public class PlayerHelper {
    public static Boolean playerOnline(String username) {
        try {
            Universe universe = Universe.get();
            if (universe == null) {
                return false;
            }

            PlayerRef player = universe.getPlayerByUsername(username, NameMatching.EXACT);

            if (player != null) {
                return true;
            }
        } catch (Exception e) {}

        return false;
    }

    public static long playerTimeOnline(String username) {
        long time = 0;

        username = username.toLowerCase();

        if (PlayerSessionManager.getInstance().exists(username)) {
            time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - PlayerSessionManager.getInstance().getJoinTime(username));
        }

        return time;
    }
}
