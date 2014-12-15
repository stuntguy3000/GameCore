package me.stuntguy3000.java.bukkit.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ServerUtil {
    /**
     * Gets a list of players currently online
     * <p/>
     * This implementation includes a workaround for {@link org.bukkit.Bukkit#getOnlinePlayers()} returning an array in
     * older releases of CraftBukkit, instead of a Collection in more recent releases. Essentially, this adds backwards
     * compatibility with older versions of CraftBukkit without having to adjust much in your plugin.
     * <p/>
     * It's ugly, but it works and provides backwards compatibility
     *
     * @return a list of all online players
     * @author DSH105
     */
    public static List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        try {
            Method onlinePlayersMethod = Bukkit.class.getMethod("getOnlinePlayers");
            if (onlinePlayersMethod.getReturnType().equals(Collection.class)) {
                Collection<Player> playerCollection = (Collection<Player>) onlinePlayersMethod.invoke(null, new Object[0]);
                if (playerCollection instanceof List) {
                    onlinePlayers = (List<Player>) playerCollection;
                } else {
                    onlinePlayers = new ArrayList<>(playerCollection);
                }
            } else {
                onlinePlayers = Arrays.asList((Player[]) onlinePlayersMethod.invoke(null, new Object[0]));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {

        }
        return onlinePlayers;
    }
}
    