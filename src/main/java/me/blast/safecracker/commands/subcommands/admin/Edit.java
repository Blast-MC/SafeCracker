package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.inventories.AdminGUI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Edit extends Commands {
    public Edit(Player player) {
        if(getFiles().configFile().get("currentEvent") == null){
            getFiles().configFile().set("currentEvent", player.getName() + "'s Event");
            getFiles().saveConfig();
            getFiles().changeCurrentEvent(player.getName() + "'s Event");
        }
        try {
            getFiles().setupEventFiles(SafeCracker.getInstance());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        new AdminGUI().openAdminGUI(player);
    }
}
