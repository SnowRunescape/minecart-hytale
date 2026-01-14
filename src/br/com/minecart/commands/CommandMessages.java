package br.com.minecart.commands;

import com.hypixel.hytale.server.core.Message;

import java.awt.*;

public class CommandMessages {
    public static final Message PLAYER_LIST_KEYS_TITLE = Message.translation("success.player-list-keys-title").color(Color.GREEN).bold(true);
    public static final Message PLAYER_LIST_KEYS_KEY = Message.translation("success.player-list-keys-key").color(Color.GREEN).bold(true);

    public static final Message ERROR_REDEEM_KEY = Message.translation("error.redeem-key").color(Color.GREEN).bold(true);

    public static final Message SUCCESS_REDEEM_CASH = Message.translation("success.redeem-cash").color(Color.GREEN).bold(true);
    public static final Message ERROR_REDEEM_CASH = Message.translation("error.redeem-cash").color(Color.GREEN).bold(true);

    public static final Message PLAYER_ONLY = Message.translation("error.player-only").color(Color.GREEN).bold(true);
    public static final Message PLAYER_DONT_HAVE_KEY = Message.translation("error.player-dont-have-key").color(Color.GREEN).bold(true);

    public static final Message INTERNAL_SERVER_ERROR = Message.translation("error.internal-error").color(Color.GREEN).bold(true);
}
