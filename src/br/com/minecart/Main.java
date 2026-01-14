package br.com.minecart;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import br.com.minecart.commands.MinecartCommand;
import br.com.minecart.commands.MyKeysCommand;
import br.com.minecart.commands.RedeemCashCommand;
import br.com.minecart.commands.RedeemKeyCommand;

public class Main extends JavaPlugin {
    public static final String VERSION = "1.0.0";

    public Main(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        this.getCommandRegistry().registerCommand(new MinecartCommand());
        this.getCommandRegistry().registerCommand(new MyKeysCommand());
        this.getCommandRegistry().registerCommand(new RedeemKeyCommand());
        this.getCommandRegistry().registerCommand(new RedeemCashCommand());
    }
}
