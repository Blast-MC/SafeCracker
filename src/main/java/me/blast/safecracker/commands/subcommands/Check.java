package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.inventories.CheckerGUI;
import org.bukkit.entity.Player;

public class Check extends Commands {
    public Check(Player player) {
        if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
            player.sendMessage(SafeCracker.colorize("&3You have not started the Safe Cracker event! Use '&c/safecracker start&3' to begin."));
            return;
        }
        if(getFiles().playerFile(player.getUniqueId()).get("solved") != null){
            player.sendMessage(SafeCracker.colorize("&3You have already solved this Safe Cracker event!"));
            return;
        }
        new CheckerGUI().openCheckerGUI(player);
    }
}
