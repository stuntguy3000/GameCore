package me.stuntguy3000.java.bukkit.gamecore.util;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public final class MetadataUtil {
    private Plugin plugin;
    private String pluginName;

    public MetadataUtil(Plugin plugin) {
        this.plugin = plugin;
        pluginName = plugin.getName();
    }

    public Object get(Metadatable m, String key) {
        return hasKey(m, key) ? m.getMetadata(pluginName + "_" + key).get(0).value() : null;
    }

    public boolean hasKey(Metadatable m, String key) {
        return m.getMetadata(pluginName + "_" + key).size() > 0;
    }

    public boolean hasKeys(Metadatable m, String... keys) {
        for (String key : keys) {
            if (!hasKey(m, key)) {
                return false;
            }
        }
        return true;
    }

    public void remove(Metadatable m, String key) {
        m.removeMetadata(pluginName + "_" + key, plugin);
    }

    public void removeAll(Metadatable m, String... keys) {
        for (String k : keys) {
            remove(m, k);
        }
    }

    public void set(Metadatable m, String key, Object value) {
        m.setMetadata(pluginName + "_" + key, new FixedMetadataValue(plugin, value));
    }
}