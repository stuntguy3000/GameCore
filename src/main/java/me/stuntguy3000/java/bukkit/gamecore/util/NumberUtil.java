package me.stuntguy3000.java.bukkit.gamecore.util;

public class NumberUtil {
    /**
     * Attempts to convert a string into an integer value using Regex
     *
     * @param string the String to be checked
     * @return the converted integer
     * @throws java.lang.NumberFormatException if the conversion failed
     */
    public static int toInteger(String string) throws NumberFormatException {
        try {
            return Integer.parseInt(string.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(string + " isn't a number!");
        }
    }
}
    