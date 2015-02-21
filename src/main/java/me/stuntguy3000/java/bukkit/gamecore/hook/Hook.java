package me.stuntguy3000.java.bukkit.gamecore.hook;

import java.util.ArrayList;
import java.util.List;

public abstract class Hook {
    String hookId = null;
    boolean isConnected = false;
    List<String> requiredPlugins = new ArrayList<>();

    /**
     * Adds a plugin to the list of required plugins
     *
     * @param pluginName String the name of the dependency
     */
    public void addRequiredPlugin(String pluginName) {
        requiredPlugins.add(pluginName);
    }

    /**
     * Returns a list of all required plugins
     *
     * @return List<String> a list of all required plugins
     */
    public List<String> getRequiredPlugins() {
        return requiredPlugins;
    }

    /**
     * Returns if the hook is connected
     *
     * @return true if the hook is connnected
     */
    public boolean isIsConnected() {
        return isConnected;
    }

    /**
     * Sets if the hook is connected
     *
     * @param connected boolean true if the hook is connected
     */
    public void setIsConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * Returns the name of the hook
     *
     * @return String the name of the hook
     */
    public String getHookId() {
        return hookId;
    }

    /**
     * Sets the name of the hook
     *
     * @param id String the name of the hook
     */
    public void setHookId(String id) {
        hookId = id;
    }

    /**
     * Connects the hook
     * <p>This method should be overridden</p>
     *
     * @return boolean true if successful
     */
    public boolean connectHook() {
        setIsConnected(true);
        return true;
    }

    /**
     * Disconnects the hook
     * <p>This method should be overridden</p>
     *
     * @return boolean true if successful
     */
    public boolean disconnectHook() {
        setIsConnected(false);
        return true;
    }
}
