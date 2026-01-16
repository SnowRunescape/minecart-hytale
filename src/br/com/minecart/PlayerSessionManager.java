package br.com.minecart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerSessionManager {
    private static final PlayerSessionManager INSTANCE = new PlayerSessionManager();

    private final Map<String, Long> playerJoinTimestamps = new ConcurrentHashMap<>();

    private PlayerSessionManager() {}

    public static PlayerSessionManager getInstance() {
        return INSTANCE;
    }

    public void onJoin(String username) {
        this.playerJoinTimestamps.put(this.normalize(username), System.currentTimeMillis());
    }

    public void onQuit(String username) {
        this.playerJoinTimestamps.remove(this.normalize(username));
    }

    public boolean exists(String username) {
        return this.playerJoinTimestamps.containsKey(this.normalize(username));
    }

    public Long getJoinTime(String username) {
        return this.playerJoinTimestamps.get(this.normalize(username));
    }

    public boolean isInCooldown(String username, long delayMs) {
        Long joinedAt = this.playerJoinTimestamps.get(this.normalize(username));

        if (joinedAt == null) {
            return false;
        }

        return System.currentTimeMillis() - joinedAt < delayMs;
    }

    public void clear() {
        this.playerJoinTimestamps.clear();
    }

    private String normalize(String username) {
        return username.toLowerCase();
    }
}