package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

public class Create extends Commands {
    public Create(Player player, String[] args) {
        if (args.length >= 3) {
            if(getFiles().configFile().get("currentEvent") == null){
                player.sendMessage(SafeCracker.colorize("&3There is no event created. Please create one using '&e/safecracker admin edit&3'."));
                return;
            }
            getNPCs().create(args[2], player.getLocation());
            return;
        }
        player.sendMessage(SafeCracker.colorize("&cPlease specify an NPC name!"));
    }
}
