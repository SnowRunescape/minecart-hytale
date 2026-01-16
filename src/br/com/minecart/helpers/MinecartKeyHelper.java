package br.com.minecart.helpers;

import java.util.ArrayList;

import br.com.minecart.Main;
import br.com.minecart.entities.MinecartKey;
import br.com.minecart.scheduler.sources.AutomaticDelivery;

public class MinecartKeyHelper {
    public static ArrayList<MinecartKey> filterByAutomaticDelivery(ArrayList<MinecartKey> minecartKeys) {
        ArrayList<MinecartKey> tempMinecartKeys = new ArrayList<MinecartKey>();

        Boolean preventLoginDelivery = Main.CONFIG.get().getPreventLoginDelivery();
        Integer timePreventLoginDelivery = Main.CONFIG.get().getTimePreventLoginDelivery();

        for (MinecartKey minecartKey : minecartKeys) {
            if (
                minecartKey.getDeliveryAutomaitc() == AutomaticDelivery.ANYTIME || (
                    !preventLoginDelivery
                    && PlayerHelper.playerOnline(minecartKey.getUsername())
                ) || (
                    preventLoginDelivery
                    && PlayerHelper.playerOnline(minecartKey.getUsername())
                    && PlayerHelper.playerTimeOnline(minecartKey.getUsername()) > timePreventLoginDelivery
                )
            ) {
                tempMinecartKeys.add(minecartKey);
            }
        }

        return tempMinecartKeys;
    }

    public static int[] getMinecartKeyIds(ArrayList<MinecartKey> minecartKeys) {
        int counter = 0;
        int quantity = minecartKeys.size();

        int[] ids = new int[quantity];

        for (MinecartKey minecartKey : minecartKeys) {
            ids[counter] = minecartKey.getId();
            counter++;
        }

        return ids;
    }
}