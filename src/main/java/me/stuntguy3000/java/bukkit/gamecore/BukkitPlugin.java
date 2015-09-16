package me.stuntguy3000.java.bukkit.gamecore;

import me.stuntguy3000.java.bukkit.gamecore.hook.Hook;
import me.stuntguy3000.java.bukkit.gamecore.util.ClassUtil;
import me.stuntguy3000.java.bukkit.gamecore.util.StringUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * A class with common methods for bukkit plugins
 */
public abstract class BukkitPlugin {

    /**
     * Find and register listeners in a directory
     *
     * @param plugin           JavaPlugin the associated JavaPlugin
     * @param packageDirectory String the location of the packages
     */
    public static void registerListeners(JavaPlugin plugin, String packageDirectory) {
        List<Class<?>> listenerClasses = ClassUtil.getClassesForPackage(plugin, packageDirectory);
        int count = 0;

        for (Class<?> clazz : listenerClasses) {
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    plugin.getServer().getPluginManager().registerEvents((Listener) clazz.newInstance(), plugin);
                    count++;
                } catch (InstantiationException | IllegalAccessException e) {
                    plugin.getLogger().severe("Error loading listener " + clazz.getName());
                    e.printStackTrace();
                }
            }
        }

        plugin.getLogger().info("Registered " + count + " listener" + StringUtil.isPlural(count) + ".");
    }

    /**
     * Find and register any Hooks
     *
     * @param plugin           JavaPlugin the associated JavaPlugin
     * @param packageDirectory String the location of the packages
     */
    public static void registerHooks(JavaPlugin plugin, String packageDirectory) {
        int count = 0;
        List<Class<?>> hookClasses = ClassUtil.getClassesForPackage(plugin, packageDirectory);

        hookScanner:
        for (Class<?> clazz : hookClasses) {
            if (Hook.class.isAssignableFrom(clazz)) {
                try {
                    // Initialize the hook
                    Hook hook = (Hook) clazz.newInstance();

                    // Check all and any required plugins
                    for (String pluginName : hook.getRequiredPlugins()) {
                        Plugin hookPlugin = plugin.getServer().getPluginManager().getPlugin(pluginName);

                        if (hookPlugin == null || !hookPlugin.isEnabled()) {
                            plugin.getLogger().warning("Hook \"" + hook.getHookId() + "\" is missing the following plugin: \"" + pluginName + "\".");
                            break hookScanner;
                        }
                    }

                    // Connect the hook
                    if (hook.connectHook()) {
                        plugin.getLogger().info("Connected to hook \"" + hook.getHookId() + "\".");
                        count ++;
                    } else {
                        plugin.getLogger().warning("Error connecting hook \"" + hook.getHookId() + "\".");
                    }
                } catch (Exception ex) {
                    plugin.getLogger().severe("Error connecting hook: " + clazz.getName());
                    ex.printStackTrace();
                }
            }
        }

        plugin.getLogger().info("Registered " + count + " hooks" + StringUtil.isPlural(count) + ".");
    }
}
    