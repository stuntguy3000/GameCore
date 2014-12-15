package me.stuntguy3000.java.bukkit.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtil {

    /**
     * Verify if two item stacks have the same contents
     * <p/>
     * This means the lore and enchants can be in any order
     *
     * @param one the first ItemStack
     * @param two the second ItemStack
     * @return true if same
     */
    public static boolean areIdentical(ItemStack one, ItemStack two) {
        if (one != null && two != null) {
            if (one.getType() != two.getType()) {
                return false;
            }

            if (one.getDurability() != two.getDurability()) {
                return false;
            }

            if (one.hasItemMeta() != two.hasItemMeta()) {
                return false;
            }

            if (one.hasItemMeta() && two.hasItemMeta()) {
                if (one.getItemMeta().hasDisplayName() != two.getItemMeta().hasDisplayName()) {
                    return false;
                }

                if ((one.getItemMeta().hasDisplayName() && two.getItemMeta().hasDisplayName()) && !one.getItemMeta().getDisplayName().equals(two.getItemMeta().getDisplayName())) {
                    return false;
                }

                if ((one.getItemMeta().hasLore() && two.getItemMeta().hasLore()) && one.getItemMeta().getLore().size() != two.getItemMeta().getLore().size()) {
                    return false;
                }

                if (one.getItemMeta().hasLore() && two.getItemMeta().hasLore()) {
                    for (String lore : one.getItemMeta().getLore()) {
                        if (!two.getItemMeta().getLore().contains(lore)) {
                            return false;
                        }
                    }
                }
            }

            if (one.getEnchantments().size() != two.getEnchantments().size()) {
                return false;
            }

            for (Map.Entry<Enchantment, Integer> enchantment : one.getEnchantments().entrySet()) {
                if (two.getEnchantments().containsKey(enchantment.getKey())) {
                    int twoEnchantmentLevel = two.getEnchantments().get(enchantment.getKey());

                    if (twoEnchantmentLevel != enchantment.getValue()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Return an ItemStack from a ConfigurationSection
     *
     * @param section ConfigurationSection containing the item's settings
     * @return ItemStack the generated item
     */
    public static ItemStack getItem(ConfigurationSection section) {
        String itemName = ChatColor.translateAlternateColorCodes('&', section.getString("name", null));
        int itemID = section.getInt("id", -1);
        int itemAmount = section.getInt("amount", 1);
        Byte itemData = (byte) section.getInt("data", -1);

        if (itemID == -1) {
            throw new IllegalArgumentException("Item has no specified ID!");
        }

        if (itemAmount == -1) {
            throw new IllegalArgumentException("Item has no specified amount!");
        }

        List<ItemEnchant> itemEnchantments = new ArrayList<>();
        for (String id : section.getStringList("enchants")) {
            try {
                Enchantment e = EnchantmentUtil.getEnchantmentFromName(id.split(":")[0]);

                if (e != null) {
                    itemEnchantments.add(new ItemEnchant(e, NumberUtil.toInteger(id.split(":")[1]), id));
                }
            } catch (IndexOutOfBoundsException | NumberFormatException ignored) {

            }
        }

        List<String> itemLore = new ArrayList<>();
        for (String l : section.getStringList("lore")) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', l));
        }

        ItemStack item;
        if (itemData > 0) {
            item = new ItemStack(Material.getMaterial(itemID), itemAmount, itemData);
        } else {
            item = new ItemStack(Material.getMaterial(itemID), itemAmount);
        }

        if (item.getType() == null) {
            throw new NullPointerException("Item has null type!");
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemName != null && !itemName.isEmpty()) {
            itemMeta.setDisplayName(itemName);
        }

        if (!itemLore.isEmpty()) {
            itemMeta.setLore(itemLore);
        }

        item.setItemMeta(itemMeta);

        if (!itemEnchantments.isEmpty()) {
            for (ItemEnchant enchant : itemEnchantments) {
                if (enchant != null && enchant.getEnchantment() != null) {
                    try {
                        try {
                            item.addEnchantment(enchant.getEnchantment(), enchant.getLevel());
                        } catch (Exception ex) {
                            item.addUnsafeEnchantment(enchant.getEnchantment(), enchant.getLevel());
                        }
                    } catch (Exception ex) {
                        Bukkit.getLogger().severe("Enchantment input \"" + enchant.getRawInput() + "\" is invalid!");
                    }
                } else {
                    throw new NullPointerException("Enchantment is null!");
                }
            }
        }

        return item;
    }

    private static class ItemEnchant {
        private Enchantment enchantment;
        private int level;
        private String rawInput;

        public ItemEnchant(Enchantment enchantment, int level, String rawInput) {
            this.enchantment = enchantment;
            this.level = level;
            this.rawInput = rawInput;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public int getLevel() {
            return level;
        }

        public String getRawInput() {
            return rawInput;
        }
    }
}