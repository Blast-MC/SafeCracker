package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Main;
import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.inventories.RewardsGUI;
import org.bukkit.entity.Player;

public class Claim extends Commands {
    public Claim(Player player){
        if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
            player.sendMessage(Main.colorize("&3You have not started the Safe Cracker event. Please use '&e/safecracker start&3' to begin."));
            return;
        }
        if(getFiles().playerFile(player.getUniqueId()).get("solved") == null){
            player.sendMessage(Main.colorize("&3You have not correctly answered the riddle! Please solve the event with '&e/safecracker solve&3'."));
            return;
        }
        new RewardsGUI(player);
    }

}
