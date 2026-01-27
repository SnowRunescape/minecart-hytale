package br.com.minecart.helpers;

import java.util.ArrayList;

import br.com.minecart.Main;
import br.com.minecart.core.PlayerSessionManager;
import br.com.minecart.core.entities.Key;
import br.com.minecart.scheduler.sources.AutomaticDelivery;

public class MinecartKeyHelper {
    public static ArrayList<Key> filterByAutomaticDelivery(ArrayList<Key> keys) {
        ArrayList<Key> tempMinecartKeys = new ArrayList<Key>();

        Boolean preventLoginDelivery = Main.CONFIG.get().getPreventLoginDelivery();
        Integer timePreventLoginDelivery = Main.CONFIG.get().getTimePreventLoginDelivery();

        for (Key key : keys) {
            if (
                    key.getDeliveryAutomaitc() == AutomaticDelivery.ANYTIME || (
                    !preventLoginDelivery
                    && PlayerHelper.playerOnline(key.getUsername())
                ) || (
                    preventLoginDelivery
                    && PlayerHelper.playerOnline(key.getUsername())
                    && PlayerSessionManager.getInstance().getSessionDuration(key.getUsername()) > timePreventLoginDelivery
                )
            ) {
                tempMinecartKeys.add(key);
            }
        }

        return tempMinecartKeys;
    }

    public static int[] getMinecartKeyIds(ArrayList<Key> keys) {
        int counter = 0;
        int quantity = keys.size();

        int[] ids = new int[quantity];

        for (Key key : keys) {
            ids[counter] = key.getId();
            counter++;
        }

        return ids;
    }
}