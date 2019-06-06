package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Main;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

public class AdminCreate extends Commands {
    public AdminCreate(Player player, String[] args) {
        if (args.length >= 3) {
            if(getFiles().configFile().get("currentEvent") == null){
                player.sendMessage(Main.colorize("&3There is no event created. Please create one using '&e/safecracker admin edit&3'."));
                return;
            }
            getNPCs().create(args[2], player.getLocation());
            return;
        }
        player.sendMessage(Main.colorize("&cPlease specify an NPC name!"));
    }
}
