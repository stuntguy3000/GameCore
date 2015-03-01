package me.stuntguy3000.java.bukkit.gamecore.config;

import me.stuntguy3000.java.bukkit.gamecore.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class LanguageHolder {

    public static String PREFIX = "";

    public static String formulate(String message, Object... vars) {
        return StringUtil.colour('&', PREFIX + String.format(message, vars));
    }

    public static String formulateConsole(String message, Object... vars) {
        return ChatColor.stripColor(PREFIX + String.format(message, vars)).replaceAll("[^\\x00-\\x7f]", "").trim().replaceAll(" +", " ");
    }

    public static String formulateRaw(String message, Object... vars) {
        return StringUtil.colour('&', String.format(message, vars));
    }

    public static List<String> formulate(List<String> messages, Object... vars) {
        List<String> formattedMessages = new ArrayList<>();
        for (String message : messages) {
            formattedMessages.add(StringUtil.colour('&', PREFIX + String.format(message, vars)));
        }

        return formattedMessages;
    }

    public static List<String> formulateConsole(List<String> messages, Object... vars) {
        List<String> formattedMessages = new ArrayList<>();
        for (String message : messages) {
            formattedMessages.add(ChatColor.stripColor(PREFIX + String.format(message, vars)).replaceAll("[^\\x00-\\x7f]", "").trim().replaceAll(" +", " "));
        }

        return formattedMessages;
    }

    public static List<String> formulateRaw(List<String> messages, Object... vars) {
        List<String> formattedMessages = new ArrayList<>();
        for (String message : messages) {
            formattedMessages.add(StringUtil.colour('&', String.format(message, vars)));
        }
        return formattedMessages;
    }

    public void load(File configFile) throws IllegalAccessException, NoSuchFieldException, IOException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for (Field field : this.getClass().getFields()) {
            if (!config.contains(field.getName())) {
                config.set(field.getName(), field.get(this.getClass()));
            }
        }

        for (String message : config.getKeys(false)) {
            try {
                Field field = this.getClass().getDeclaredField(message);
                field.setAccessible(true);

                if (config.isList(message)) {
                    field.set(null, config.getStringList(message));
                } else {
                    field.set(null, config.getString(message));
                }

                if (field.getName().equalsIgnoreCase("prefix")) {
                    PREFIX = config.getString(message);
                }
            } catch (NoSuchFieldException ex) {
                config.set(message, null);
                config.save(configFile);
            }
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    