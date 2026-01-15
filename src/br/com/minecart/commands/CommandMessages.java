package br.com.minecart.commands;

import com.hypixel.hytale.server.core.Message;

import java.awt.*;

public class CommandMessages {
    public static final Message PLAYER_LIST_KEYS_TITLE = Message.translation("commands.success.player-list-keys-title").color(Color.GREEN).bold(true);
    public static final Message PLAYER_LIST_KEYS_KEY = Message.translation("commands.success.player-list-keys-key").color(Color.GREEN).bold(true);

    public static final Message ERROR_REDEEM_KEY = Message.translation("commands.error.redeem-key").color(Color.RED).bold(true);

    public static final Message SUCCESS_REDEEM_CASH = Message.translation("commands.success.redeem-cash").color(Color.RED).bold(true);
    public static final Message ERROR_REDEEM_CASH = Message.translation("commands.error.redeem-cash").color(Color.RED).bold(true);

    public static final Message PLAYER_ONLY = Message.translation("commands.error.player-only").color(Color.RED).bold(true);
    public static final Message PLAYER_DONT_HAVE_KEY = Message.translation("commands.error.player-dont-have-key").color(Color.RED).bold(true);

    public static final Message INTERNAL_SERVER_ERROR = Message.translation("commands.error.internal-error").color(Color.RED).bold(true);
}
