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

    /**
     * Auto sizes an Bukkit inventory
     * <p>Enter the amount of spaces required, converts into rows</p>
     *
     * @param original Integer the amount of spaces needed
     * @return Integer the amount of rows needed
     */
    public static int autoSizeInventory(int original) {
        return (original >= 0 ? ((original + 8) / 9) * 9 : (original / 9) * 9);
    }
}
    