package me.stuntguy3000.java.bukkit.gamecore.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GScoreboard {
    private Class<? extends GScoreboard> gScoreboard;
    private Objective objective;
    private HashMap<UUID, HashMap<String, Score>> scores;

    /**
     * Creates a new instance
     *
     * @param objective   Objective for which the scores will be created upon
     * @param gScoreboard Class which extends @link{GScoreboard}
     */
    public GScoreboard(Objective objective, Class<? extends GScoreboard> gScoreboard) {
        this.objective = objective;
        this.gScoreboard = gScoreboard;
    }

    /**
     * Get the Objective of the Scoreboard
     *
     * @return Objective objective of the Scoreboard
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Remove a player's scores

     * @param uuid UUID of the player
     */
    public void removePlayer(UUID uuid) {
        scores.remove(uuid);
    }

    /**
     * Update the Scoreboard for the Player <p>This method will create a new instance if the scores HashMap is not
     * instantiated</p>
     *
     * @param player Player to send the scoreboard too
     */
    public void update(Player player) {
        if (scores == null) {

            scores = new HashMap<>();

            HashMap<String, Score> userScores = new HashMap<>();

            for (Field field : gScoreboard.getDeclaredFields()) {
                GScoreboardField gScoreboardField = field.getAnnotation(GScoreboardField.class);

                if (gScoreboardField == null) {
                    continue;
                }

                String value;

                try {
                    field.setAccessible(true);
                    value = (String) field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }

                if (value != null) {
                    if (value.length() > 16) {
                        value = value.substring(0, 15);
                    }
                    Score score = objective.getScore(value);
                    score.setScore(1);
                    score.setScore(gScoreboardField.score());

                    userScores.put(field.getName(), score);

                    if (gScoreboardField.scoreboardTeam() != null) {
                        Team team = objective.getScoreboard().getTeam(gScoreboardField.scoreboardTeam());

                        if (team == null) {
                            team = objective.getScoreboard().registerNewTeam(gScoreboardField.scoreboardTeam());
                        }

                        team.addPlayer(score.getPlayer());
                    }
                }
            }

            scores.put(player.getUniqueId(), userScores);
        } else {
            for (HashMap<String, Score> id : scores.values()) {
                for (Map.Entry<String, Score> score : id.entrySet()) {
                    try {
                        Field field = gScoreboard.getDeclaredField(score.getKey());
                        GScoreboardField gScoreboardField = field.getAnnotation(GScoreboardField.class);

                        score.getValue().setScore(gScoreboardField.score());
                    } catch (NoSuchFieldException ignore) {
                        // Should never happen, so we will ignore it
                    }
                }
            }
        }

        player.setScoreboard(objective.getScoreboard());
    }
}
