package br.com.minecart.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class MinecartConfig {
    private String ShopKey = "";
    private String ShopServer = "";

    public static final BuilderCodec<MinecartConfig> CODEC = BuilderCodec.builder(MinecartConfig.class, MinecartConfig::new)
            .append(new KeyedCodec<String>("ShopKey", Codec.STRING),
                    (minecartConfig, value, extraInfo) -> minecartConfig.ShopKey = value,
                    (minecartConfig, extraInfo) -> minecartConfig.ShopKey).add()
            .append(new KeyedCodec<String>("ShopServer", Codec.STRING),
                    (minecartConfig, value, extraInfo) -> minecartConfig.ShopServer = value,
                    (minecartConfig, extraInfo) -> minecartConfig.ShopServer).add()
            .build();

    public String getShopKey() {
        return this.ShopKey;
    }

    public String getShopServer() {
        return this.ShopServer;
    }
}
