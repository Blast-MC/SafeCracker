package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

public class Start extends Commands {

    public Start(Player player) {
        if(getFiles().playerFile(player.getUniqueId()).get("started") != null){
            player.sendMessage(SafeCracker.colorize("&3You have already started the Safe Cracker event!"));
            return;
        }
        getFiles().playerFile(player.getUniqueId()).set("started", SafeCracker.getInstance().dateFormatter());
        getFiles().savePlayerData(player.getUniqueId());
        player.sendMessage(SafeCracker.colorize("&3You have started the Safe Cracker event!"));
    }
}
