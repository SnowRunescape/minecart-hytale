package br.com.minecart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.commands.CommandMessages;
import br.com.minecart.entities.MinecartCash;
import br.com.minecart.entities.MinecartKey;
import br.com.minecart.utilities.Utils;
import br.com.minecart.utilities.http.HttpRequest;
import br.com.minecart.utilities.http.HttpRequestException;
import br.com.minecart.utilities.http.HttpResponse;

public class MinecartAPI {
    private final static String URL = "https://api.minecart.com.br";

    private final static int INVALID_KEY = 40010;
    private final static int INVALID_SHOP_SERVER = 40011;
    private final static int DONT_HAVE_CASH = 40012;
    private final static int COMMANDS_NOT_REGISTRED = 40013;

    public static ArrayList<MinecartKey> myKeys(String username) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("username", username);

        HttpResponse response = HttpRequest.httpRequest(MinecartAPI.URL + "/shop/player/mykeys", params);

        if (response.responseCode != 200) {
            throw new HttpRequestException(response);
        }

        JsonObject jsonObject = JsonParser.parseString(response.response).getAsJsonObject();
        JsonArray productsPlayer = jsonObject.getAsJsonArray("products");

        ArrayList<MinecartKey> minecartKeys = new ArrayList<MinecartKey>();

        for (JsonElement product : productsPlayer) {
            JsonObject productObj = product.getAsJsonObject();

            Integer id = productObj.get("id").getAsInt();
            String key = productObj.get("key").getAsString();
            String productName = productObj.get("product_name").getAsString();

            minecartKeys.add(new MinecartKey(id, productName, username, key, null, 0));
        }

        return minecartKeys;
    }

    public static MinecartCash redeemCash(String username) throws HttpRequestException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("username", username);

        HttpResponse response = HttpRequest.httpRequest(MinecartAPI.URL + "/shop/player/redeemcash", params);

        if (response.responseCode != 200) {
            throw new HttpRequestException(response);
        }

        JsonObject jsonObject = JsonParser.parseString(response.response).getAsJsonObject();

        int quantity = jsonObject.get("cash").getAsInt();
        String command = jsonObject.get("command").getAsString();

        return new MinecartCash(quantity, command);
    }

    public static MinecartKey redeemKey(String username, String key) throws HttpRequestException {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("username", username);
        params.put("key", key);

        HttpResponse response = HttpRequest.httpRequest(MinecartAPI.URL + "/shop/player/redeemkey", params);

        if (response.responseCode != 200) {
            throw new HttpRequestException(response);
        }

        JsonObject productObj = JsonParser.parseString(response.response).getAsJsonObject();

        Integer id = productObj.get("id").getAsInt();
        String productName = productObj.get("product_name").getAsString();
        String[] commands = Utils.convertJsonArrayToStringArray(productObj.get("commands").getAsJsonArray());

        return new MinecartKey(id, productName, username, key, commands, 0);
    }

    public static void processHttpError(Player player, HttpResponse response) {
        Message message = MinecartAPI.messageHttpError(player, response);

        if (response.responseCode == 401 && !player.hasPermission("minecart.admin")) {
            message = CommandMessages.INTERNAL_SERVER_ERROR;
        }

        player.sendMessage(message);
    }

    public static Message messageHttpError(Player player, HttpResponse response) {
        if (response.responseCode == 401) {
            return CommandMessages.ERROR_INVALID_SHOPKEY;
        }

        try {
            JsonObject jsonObject = JsonParser.parseString(response.response).getAsJsonObject();

            Integer errorCode = jsonObject.get("code").getAsInt();

            switch (errorCode) {
                case INVALID_KEY:
                    return CommandMessages.ERROR_INVALID_KEY;
                case INVALID_SHOP_SERVER:
                    return player.hasPermission("minecart.admin") ?
                        CommandMessages.ERROR_INVALID_SHOPSERVER :
                        CommandMessages.INTERNAL_SERVER_ERROR;
                case DONT_HAVE_CASH:
                    return CommandMessages.ERROR_DONT_HAVE_CASH;
                case COMMANDS_NOT_REGISTRED:
                    return CommandMessages.ERROR_COMMANDS_NOT_REGISTRED;
            }
        } catch (Exception e) {}

        return CommandMessages.INTERNAL_SERVER_ERROR;
    }
}
