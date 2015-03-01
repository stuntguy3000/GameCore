package me.stuntguy3000.java.bukkit.gamecore.config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class PermissionHolder {
    private static String NO_PERMISSION = ChatColor.RED + "You do not have permission to perform this action!";

    public PermissionHolder(String message) {
        NO_PERMISSION = message;
    }

    public static boolean validate(String node, Player player) {
        return validate(node, player, false);
    }

    public static boolean validate(String node, Player player, boolean silent) {
        boolean hasPermission = player.hasPermission(node);

        if (!hasPermission && !silent) {
            player.sendMessage(NO_PERMISSION);
        }

        return hasPermission;
    }
}
    