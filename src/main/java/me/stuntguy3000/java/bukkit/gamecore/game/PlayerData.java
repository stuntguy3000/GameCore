package me.stuntguy3000.java.bukkit.gamecore.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private float flyingSpeed;
    // Game Settings
    private GameTeam gameTeam;
    private boolean isAllowedFlight;
    private boolean isFlying;
    private ItemStack[] previousArmor;
    private int previousFireTicks;
    private GameMode previousGameMode;
    private double previousHealth;
    private double previousHealthScale;
    private int previousHunger;
    private ItemStack[] previousInventory;
    private int previousLevel;
    private Location previousLocation;
    private double previousMaxHealth;
    private List<PotionEffect> previousPotionEffects;
    private float previousXP;
    // Minecraft Settings
    private UUID uuid;
    private float walkingSpeed;

    public PlayerData(Player player) {
        uuid = player.getUniqueId();

        previousLocation = player.getLocation().clone();
        previousInventory = player.getInventory().getContents().clone();
        previousArmor = player.getInventory().getArmorContents().clone();
        previousMaxHealth = player.getMaxHealth();
        previousHealth = player.getHealth();
        previousHealthScale = player.getHealthScale();
        previousHunger = player.getFoodLevel();
        previousXP = player.getExp();
        previousLevel = player.getLevel();
        previousPotionEffects = new ArrayList<>(player.getActivePotionEffects());
        previousGameMode = player.getGameMode();
        previousFireTicks = player.getFireTicks();
        isAllowedFlight = player.getAllowFlight();
        isFlying = player.isFlying();
        walkingSpeed = player.getWalkSpeed();
        flyingSpeed = player.getFlySpeed();
    }

    /**
     * Restores the Player's state to how it was saved
     *
     * @return true if restored
     */
    public boolean restore() {
        if (uuid != null) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                // Teleport
                player.teleport(getPreviousLocation());
                // Restore Playerdata
                player.getInventory().setContents(getPreviousInventory());
                player.getInventory().setArmorContents(getPreviousArmor());
                player.setMaxHealth(getPreviousMaxHealth());
                player.setHealth(getPreviousHealth());
                player.setExp(getPreviousXP());
                player.setLevel(getPreviousLevel());
                player.setFireTicks(getPreviousFireTicks());
                player.setGameMode(getPreviousGameMode());
                player.setFoodLevel(getFoodLevel());
                player.setAllowFlight(isAllowedFlight());
                player.setFlying(isFlying());
                player.setWalkSpeed(getWalkingSpeed());
                player.setFlySpeed(getFlyingSpeed());
                player.setDisplayName(player.getName());
                player.updateInventory();

                for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                    player.removePotionEffect(potionEffect.getType());
                }

                for (PotionEffect potionEffect : getPreviousPotionEffects()) {
                    player.addPotionEffect(potionEffect);
                }
                return true;
            }
        }

        return false;
    }

    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    public int getFoodLevel() {
        return previousHunger;
    }

    public GameTeam getGameTeam() {
        return gameTeam;
    }

    public void setGameTeam(GameTeam gameTeam) {
        this.gameTeam = gameTeam;
    }

    public ItemStack[] getPreviousArmor() {
        return previousArmor;
    }

    public int getPreviousFireTicks() {
        return previousFireTicks;
    }

    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }

    public double getPreviousHealth() {
        return previousHealth;
    }

    public double getPreviousHealthScale() {
        return previousHealthScale;
    }

    public ItemStack[] getPreviousInventory() {
        return previousInventory;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public double getPreviousMaxHealth() {
        return previousMaxHealth;
    }

    public List<PotionEffect> getPreviousPotionEffects() {
        return previousPotionEffects;
    }

    public float getPreviousXP() {
        return previousXP;
    }

    public float getWalkingSpeed() {
        return walkingSpeed;
    }

    public boolean isAllowedFlight() {
        return isAllowedFlight;
    }

    public boolean isFlying() {
        return isFlying;
    }
}
    