package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Files;
import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Scores;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Solve {

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }

    public Solve(Player player, String[] args) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String answer = sb.toString().trim();
        if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
            player.sendMessage(SafeCracker.colorize("&3You have not started the Safe Cracker event. Please use '&e/safecracker start&3' to begin."));
            return;
        }
        if(getFiles().playerFile(player.getUniqueId()).get("solved") != null){
            player.sendMessage(SafeCracker.colorize("&3You have already correctly answered the riddle! Please claim your rewards with '&e/safecracker claim&3'."));
            return;
        }
        if(!answer.equalsIgnoreCase((String) getFiles().dataFile().get("riddle-answer"))){
            player.sendMessage(SafeCracker.colorize("&3That answer is incorrect. Please try again."));
            return;
        }
        getFiles().playerFile(player.getUniqueId()).set("solved", SafeCracker.getInstance().dateFormatter());
        getFiles().savePlayerData(player.getUniqueId());
        player.sendMessage(SafeCracker.colorize("&3You correctly answered the Safe Cracker riddle! You score is &e" + SafeCracker.getInstance().timeSince(SafeCracker.getInstance().dateDeformaterr((String) getFiles().playerFile(player.getUniqueId()).get("started")))));
        ArrayList<String> commands = new ArrayList<>((ArrayList<String>) getFiles().dataFile().get("commands-upon-solve"));
        for(String command : commands){
            command.replaceAll("&player", player.getName());
            if(command.substring(0, 0).equalsIgnoreCase("/")){
                command = command.substring(1);
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
        new Scores(SafeCracker.getInstance().timeSince(SafeCracker.getInstance().dateDeformater((String) getFiles().playerFile(player.getUniqueId()).get("started"))), player);

    }
}
