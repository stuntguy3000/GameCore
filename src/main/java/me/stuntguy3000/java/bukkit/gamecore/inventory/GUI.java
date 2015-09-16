package me.stuntguy3000.java.bukkit.gamecore.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class GUI {
    private HashMap<Integer, GUIItem> items = new HashMap<>();

    public Inventory createInventory() {
        return null;
    }

    public static class GUIItem {
        private ItemStack item;
    }
}
