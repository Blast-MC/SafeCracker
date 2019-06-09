package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class AdminReload extends Commands {
    public AdminReload(Player player) {
        try{
            getFiles().setup(SafeCracker.getInstance());
        } catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        if(getFiles().configFile().get("currentEvent") != null){
            getFiles().getCurrentEvent();
            try {
                getFiles().setupEventFiles(SafeCracker.getInstance());
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        player.sendMessage(SafeCracker.colorize("&3Successfully reloaded the files."));
    }
}
