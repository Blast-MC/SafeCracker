package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class ResetPlayer extends Commands {
    public ResetPlayer(Player player, String[] args) {
        for(Player p : SafeCracker.getInstance().getServer().getOnlinePlayers()){
            if(p.getName().equalsIgnoreCase(args[2])){
                File playerFile = new File(SafeCracker.getInstance().getDataFolder() + File.separator + getFiles().currentEvent + File.separator + "playerData" + File.separator + SafeCracker.getInstance().getServer().getPlayer(args[2]).getUniqueId().toString() + ".yml");
                SafeCracker.getInstance().log("Deleting file: " + playerFile.getPath());
                playerFile.delete();
                ArrayList<String> scores = new ArrayList<>((ArrayList<String>) getFiles().scoreFile().get("scores"));
                for(int i = 0; i < scores.size(); i++){
                    String[] split = scores.get(i).split(":\\s");
                    if(split[0].equalsIgnoreCase(args[2])){
                        scores.remove(i);
                        continue;
                    }
                }
                getFiles().scoreFile().set("scores", scores);
                getFiles().saveScore();
                player.sendMessage(SafeCracker.colorize("&3Successfully reset all data for &e" + args[2] + " &3for the current event."));
                return;
            }
        }
    }
}
