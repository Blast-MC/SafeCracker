package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Main;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

public class Start extends Commands {

    public Start(Player player) {
        if(getFiles().playerFile(player.getUniqueId()).get("started") != null){
            player.sendMessage(Main.colorize("&3You have already started the Safe Cracker event!"));
            return;
        }
        getFiles().playerFile(player.getUniqueId()).set("started", Main.getInstance().dateFormatter());
        getFiles().savePlayerData(player.getUniqueId());
        player.sendMessage(Main.colorize("&3You have started the Safe Cracker event!"));
    }
}
