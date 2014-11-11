package me.stuntguy3000.java.bukkit.gamecore.scoreboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GScoreboardField {
    int score();

    String scoreboardTeam() default "";
}
