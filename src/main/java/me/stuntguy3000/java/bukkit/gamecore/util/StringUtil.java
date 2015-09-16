package me.stuntguy3000.java.bukkit.gamecore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static String colour(char colourSymbol, String text) {
        if (text == null) {
            return null;
        } else {
            return ChatColor.translateAlternateColorCodes(colourSymbol, text);
        }
    }

    public static List<String> colour(char colourSymbol, List<String> text) {
        List<String> convertedText = new ArrayList<>();

        for (String textSegment : text) {
            convertedText.add(colour(colourSymbol, textSegment));
        }

        return convertedText;
    }

    public static String colour(String s) {
        return colour('&', s);
    }

    public static List<String> colour(List<String> s) {
        return colour('&', s);
    }

    public static String isPlural(Number amount) {
        return amount.toString().equalsIgnoreCase("1") ? "" : "s";
    }
}
    