package me.stuntguy3000.java.bukkit.gamecore.game;

import org.bukkit.Location;

/**
 * Represents a Location used by a Minigame
 * <p/>
 * This class holds two pieces of information
 * Location - The Bukkit Location
 * Integer - A unique numeric identifier for this location
 */
public class GameLocation {
    private int id;
    private Location location;

    /**
     * Creates a new GameLocation
     *
     * @param location Location the location to be referencing
     * @param id       Integer the unique numeric identifier of this location
     */
    public GameLocation(Location location, int id) {
        this.location = location;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}