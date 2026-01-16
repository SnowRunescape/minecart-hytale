package br.com.minecart.helpers;

import java.util.ArrayList;

import br.com.minecart.Main;
import br.com.minecart.core.PlayerSessionManager;
import br.com.minecart.core.entities.Key;
import br.com.minecart.scheduler.sources.AutomaticDelivery;

public class MinecartKeyHelper {
    public static ArrayList<Key> filterByAutomaticDelivery(ArrayList<Key> minecartKeys) {
        ArrayList<Key> tempMinecartKeys = new ArrayList<Key>();

        Boolean preventLoginDelivery = Main.CONFIG.get().getPreventLoginDelivery();
        Integer timePreventLoginDelivery = Main.CONFIG.get().getTimePreventLoginDelivery();

        for (Key minecartKey : minecartKeys) {
            if (
                minecartKey.getDeliveryAutomaitc() == AutomaticDelivery.ANYTIME || (
                    !preventLoginDelivery
                    && PlayerHelper.playerOnline(minecartKey.getUsername())
                ) || (
                    preventLoginDelivery
                    && PlayerHelper.playerOnline(minecartKey.getUsername())
                    && PlayerSessionManager.getInstance().getSessionDuration(minecartKey.getUsername()) > timePreventLoginDelivery
                )
            ) {
                tempMinecartKeys.add(minecartKey);
            }
        }

        return tempMinecartKeys;
    }

    public static int[] getMinecartKeyIds(ArrayList<Key> minecartKeys) {
        int counter = 0;
        int quantity = minecartKeys.size();

        int[] ids = new int[quantity];

        for (Key minecartKey : minecartKeys) {
            ids[counter] = minecartKey.getId();
            counter++;
        }

        return ids;
    }
}