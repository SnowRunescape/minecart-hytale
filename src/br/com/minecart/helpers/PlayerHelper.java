package br.com.minecart.helpers;

import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

public class PlayerHelper {
    public static Boolean playerOnline(String username) {
        try {
            Universe universe = Universe.get();
            if (universe == null) {
                return false;
            }

            PlayerRef player = universe.getPlayerByUsername(username, NameMatching.EXACT_IGNORE_CASE);

            if (player != null) {
                return true;
            }
        } catch (Exception e) {}

        return false;
    }
}
