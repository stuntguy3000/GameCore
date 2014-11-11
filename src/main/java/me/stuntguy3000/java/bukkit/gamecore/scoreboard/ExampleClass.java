package me.stuntguy3000.java.bukkit.gamecore.scoreboard;

import org.bukkit.Bukkit;

public class ExampleClass extends GScoreboard {
    @GScoreboardField(score = 6)
    private String placeHolder1 = "&c";
    @GScoreboardField(score = 4)
    private String placeHolder2 = "&b";
    @GScoreboardField(score = 2)
    private String placeHolder3 = "&a";
    @GScoreboardField(score = 1, scoreboardTeam = "v4")
    private String valueFour = "&bValue 4";
    @GScoreboardField(score = 7, scoreboardTeam = "v1")
    private String valueOne = "&bValue 1";
    @GScoreboardField(score = 3, scoreboardTeam = "v3")
    private String valueThree = "&bValue 3";
    @GScoreboardField(score = 5, scoreboardTeam = "v2")
    private String valueTwo = "&bValue 2";

    public ExampleClass() {
        super(Bukkit.getScoreboardManager().getNewScoreboard().registerNewObjective("SBExample", "dummy"), ExampleClass.class);
    }
}
    