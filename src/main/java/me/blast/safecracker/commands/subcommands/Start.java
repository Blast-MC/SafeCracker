package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Start extends Commands {

    public Start(Player player) {
        if(getFiles().playerFile(player.getUniqueId()).get("started") != null){
            player.sendMessage(SafeCracker.colorize("&3You have already started the Safe Cracker event!"));
            return;
        }
        getFiles().playerFile(player.getUniqueId()).set("started", SafeCracker.getInstance().dateFormatter());
        getFiles().savePlayerData(player.getUniqueId());
        ArrayList<String> commands = new ArrayList<>((ArrayList<String>) getFiles().dataFile().get("commands-upon-start"));
        for(String command : commands){
            if(command.equals("")){
                continue;
            }
            if(command.substring(0, 0).equalsIgnoreCase("/")){
                command = command.substring(1);
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("&player", player.getName()));
        }
        player.sendMessage(SafeCracker.colorize("&3You have started the Safe Cracker event!"));
    }
}
