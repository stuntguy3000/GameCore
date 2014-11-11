package me.stuntguy3000.java.bukkit.gamecore.scoreboard;

import org.bukkit.Bukkit;

public class ExampleClass extends GScoreboard {
    @GScoreboardField(score = 6)
    public String placeHolder1 = "&c";
    @GScoreboardField(score = 4)
    public String placeHolder2 = "&b";
    @GScoreboardField(score = 2)
    public String placeHolder3 = "&a";
    @GScoreboardField(score = 1, scoreboardTeam = "v4")
    public String valueFour = "&bValue 4";
    @GScoreboardField(score = 7, scoreboardTeam = "v1")
    public String valueOne = "&bValue 1";
    @GScoreboardField(score = 3, scoreboardTeam = "v3")
    public String valueThree = "&bValue 3";
    @GScoreboardField(score = 5, scoreboardTeam = "v2")
    public String valueTwo = "&bValue 2";

    public ExampleClass() {
        super(Bukkit.getScoreboardManager().getNewScoreboard().registerNewObjective("SBExample", "dummy"), ExampleClass.class);
    }
}
    