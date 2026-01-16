package br.com.minecart;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.entity.entities.Player;

import br.com.minecart.commands.CommandMessages;
import br.com.minecart.core.utilities.http.HttpResponse;
import br.com.minecart.core.MinecartAPI;

public class MinecartHttpResponseTranslateMessage {
    public static void processHttpError(Player player, HttpResponse response) {
        Message message = MinecartHttpResponseTranslateMessage.messageHttpError(player, response);

        if (response.responseCode == 401 && !player.hasPermission("minecart.admin")) {
            message = CommandMessages.INTERNAL_SERVER_ERROR;
        }

        player.sendMessage(message);
    }

    public static Message messageHttpError(CommandSender player, HttpResponse response) {
        if (response.responseCode == 401) {
            return CommandMessages.ERROR_INVALID_SHOPKEY;
        }

        try {
            JsonObject jsonObject = JsonParser.parseString(response.response).getAsJsonObject();

            Integer errorCode = jsonObject.get("code").getAsInt();

            switch (errorCode) {
                case MinecartAPI.INVALID_KEY:
                    return CommandMessages.ERROR_INVALID_KEY;
                case MinecartAPI.INVALID_SHOP_SERVER:
                    return player.hasPermission("minecart.admin") ?
                        CommandMessages.ERROR_INVALID_SHOPSERVER :
                        CommandMessages.INTERNAL_SERVER_ERROR;
                case MinecartAPI.DONT_HAVE_CASH:
                    return CommandMessages.ERROR_DONT_HAVE_CASH;
                case MinecartAPI.COMMANDS_NOT_REGISTRED:
                    return CommandMessages.ERROR_COMMANDS_NOT_REGISTRED;
            }
        } catch (Exception e) {}

        return CommandMessages.INTERNAL_SERVER_ERROR;
    }
}
