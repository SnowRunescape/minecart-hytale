package br.com.minecart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.minecart.entities.MinecartCash;
import br.com.minecart.entities.MinecartKey;
import br.com.minecart.utilities.Utils;
import br.com.minecart.utilities.http.HttpRequest;
import br.com.minecart.utilities.http.HttpRequestException;
import br.com.minecart.utilities.http.HttpResponse;

public class MinecartAPI {
    private final static String URL = "https://api.minecart.com.br";

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
}
