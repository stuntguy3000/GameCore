package me.stuntguy3000.java.bukkit.gamecore.util;

import org.bukkit.ChatColor;

public class StringUtil {
    public static String colour(char colourSymbol, String text) {
        if (text == null) {
            return null;
        } else {
            return ChatColor.translateAlternateColorCodes(colourSymbol, text);
        }
    }

    public static String colour(String s) {
        return colour('&', s);
    }

    public static String isPlural(int amount) {
        return amount == 1 ? "" : "s";
    }
}
    