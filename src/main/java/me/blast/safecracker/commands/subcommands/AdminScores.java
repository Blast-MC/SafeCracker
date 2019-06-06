package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Files;
import me.blast.safecracker.Main;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AdminScores extends Commands {
    public AdminScores(Player player) {
        ArrayList<String> scores = new ArrayList<>((ArrayList<String>) getFiles().scoreFile().get("scores"));
        if(scores.get(0).equalsIgnoreCase("")){
            player.sendMessage(Main.colorize("&3No one has correctly solved the current Safe Cracker event. When someone solves it correctly, scores will be reported here."));
            return;
        }
        player.sendMessage(Main.colorize("&3The scores for the &e" + getFiles().configFile().get("currentEvent") + " &3Safe Cracker event:"));
        for(String score: scores){
            player.sendMessage(Main.colorize("&r &3- &e" + score));
        }
    }
}
