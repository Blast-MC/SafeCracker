package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.inventories.RewardsGUI;
import org.bukkit.entity.Player;

public class Claim extends Commands {
    public Claim(Player player){
        if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
            player.sendMessage(SafeCracker.colorize("&3You have not started the Safe Cracker event. Please use '&c/safecracker start&3' to begin."));
            return;
        }
        if(getFiles().playerFile(player.getUniqueId()).get("solved") == null){
            player.sendMessage(SafeCracker.colorize("&3You have not correctly answered the riddle! Please solve the event with '&c/safecracker solve&3'."));
            return;
        }
        new RewardsGUI(player);
    }

}
