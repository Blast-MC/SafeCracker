package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

public class Region extends Commands {
    public Region(Player player, String[] args){
        if(args.length < 3) {
            player.sendMessage(SafeCracker.colorize("&cPlease specify a region to set as the main region."));
            return;
        }
        getFiles().dataFile().set("main-region", args[2]);
        getFiles().saveData();
        player.sendMessage(SafeCracker.colorize("&3Successfully set &e" + args[2] + "&3 to the main region."));
    }
}
