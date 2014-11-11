package me.stuntguy3000.java.bukkit.gamecore.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class GScoreboard {
    private Class<? extends GScoreboard> gScoreboard;
    private Objective objective;
    private HashMap<String, Score> scores;

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
     * Update the Scoreboard for the Player <p>This method will create a new instance if the scores HashMap is not
     * instantiated</p>
     *
     * @param player Player to send the scoreboard too
     */
    public void update(Player player) {
        if (scores == null) {
            scores = new HashMap<>();

            for (Field field : gScoreboard.getFields()) {
                GScoreboardField gScoreboardField = field.getAnnotation(GScoreboardField.class);
                String value = null;

                try {
                    field.setAccessible(true);
                    Object object = field.get(field);
                    if (object instanceof String) {
                        value = (String) object;
                    }
                } catch (IllegalAccessException e) {
                    // Ignore it
                    continue;
                }

                if (gScoreboardField != null && value != null) {
                    Score score = objective.getScore(value);
                    score.setScore(gScoreboardField.score());

                    scores.put(field.getName(), score);

                    if (gScoreboardField.scoreboardTeam() != null) {
                        Team team = objective.getScoreboard().getTeam(gScoreboardField.scoreboardTeam());
                        team.addPlayer(score.getPlayer());
                    }
                }
            }
        } else {
            for (Map.Entry<String, Score> id : scores.entrySet()) {
                try {
                    Field field = gScoreboard.getDeclaredField(id.getKey());
                    GScoreboardField gScoreboardField = field.getAnnotation(GScoreboardField.class);

                    id.getValue().setScore(gScoreboardField.score());
                } catch (NoSuchFieldException ignore) {
                    // Should never happen, so we will ignore it
                }
            }
        }

        player.setScoreboard(objective.getScoreboard());
    }
}
