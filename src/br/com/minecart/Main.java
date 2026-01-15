package br.com.minecart;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

import br.com.minecart.commands.MinecartCommand;
import br.com.minecart.commands.MyKeysCommand;
import br.com.minecart.commands.RedeemCashCommand;
import br.com.minecart.commands.RedeemKeyCommand;
import br.com.minecart.config.MinecartConfig;

public class Main extends JavaPlugin {
    public static Config<MinecartConfig> CONFIG;

    public static final String VERSION = "3.0.0";

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
    }
}
