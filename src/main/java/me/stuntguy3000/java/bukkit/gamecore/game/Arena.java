package me.stuntguy3000.java.bukkit.gamecore.game;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Represents an Arena
 * <p/>
 * Default Setting Values:
 * arenaName: (String) Unique identifier for the Arena
 * minPlayers: (Integer) The minimum amount of players for the game to start
 * maxPlayers: (Integer) The maximum amount of players the arena can hold
 */
public abstract class Arena {
    private HashMap<String, String> ARENA_SETTINGS;
    private HashMap<UUID, PlayerData> ARENA_PLAYERS;
    private HashMap<String, List<GameLocation>> ARENA_LOCATIONS;

    /**
     * Constructs a new Arena class
     *
     * @param id String the Arena's unique ID
     */
    public Arena(String id) {
        // Set the Arena's name
        setArenaSetting("arenaName", id);
    }

    /**
     * Constructs a new Arena class
     *
     * @param id         String the Arena's unique ID
     * @param minPlayers Integer the minimum number of players
     */
    public Arena(String id, int minPlayers) {
        // Set the Arena's name, minimum player count
        setArenaSetting("arenaName", id);
        setArenaSetting("minPlayers", minPlayers);
    }

    /**
     * Constructs a new Arena class
     *
     * @param id         String the Arena's unique ID
     * @param minPlayers Integer the minimum number of players
     * @param maxPlayers Integer the maximum number of players
     */
    public Arena(String id, int minPlayers, int maxPlayers) {
        // Set the Arena's name, minimum and maximum player counts
        setArenaSetting("arenaName", id);
        setArenaSetting("minPlayers", minPlayers);
        setArenaSetting("maxPlayers", maxPlayers);
    }

    /**
     * Sets an Arena's setting
     *
     * @param key   String the unique ID of the setting
     * @param value Object the value of the setting
     * @return String the existing value, null if non existant
     */
    public Object setArenaSetting(String key, Object value) {
        return ARENA_SETTINGS.put(key, value.toString());
    }
}
    