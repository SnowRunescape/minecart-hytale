package br.com.minecart;

import java.util.concurrent.TimeUnit;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

import br.com.minecart.commands.MinecartCommand;
import br.com.minecart.commands.MyKeysCommand;
import br.com.minecart.commands.RedeemCashCommand;
import br.com.minecart.commands.RedeemKeyCommand;
import br.com.minecart.config.MinecartConfig;
import br.com.minecart.listeners.PlayerConnectListener;
import br.com.minecart.listeners.PlayerDisconnectListener;
import br.com.minecart.scheduler.sources.AutomaticDelivery;

public class Main extends JavaPlugin {
    public static final String VERSION = "3.0.0";

    public static Config<MinecartConfig> CONFIG;

    public Main(JavaPluginInit init) {
        super(init);
        CONFIG = this.withConfig("Minecart", MinecartConfig.CODEC);
    }

    @Override
    protected void setup() {
        super.setup();

        CONFIG.save();

        this.getCommandRegistry().registerCommand(new MinecartCommand());
        this.getCommandRegistry().registerCommand(new MyKeysCommand());
        this.getCommandRegistry().registerCommand(new RedeemKeyCommand());
        this.getCommandRegistry().registerCommand(new RedeemCashCommand());

        this.getEventRegistry().registerGlobal(PlayerConnectEvent.class, new PlayerConnectListener());
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, new PlayerDisconnectListener());

        HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
            new AutomaticDelivery().run();
        }, AutomaticDelivery.DELAY, AutomaticDelivery.DELAY, TimeUnit.SECONDS);
    }
}
