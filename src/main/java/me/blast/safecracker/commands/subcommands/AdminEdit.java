package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.Main;
import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.inventories.AdminGUI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class AdminEdit extends Commands {
    public AdminEdit(Player player) {
        if(getFiles().configFile().get("currentEvent") == null){
            getFiles().configFile().set("currentEvent", player.getName() + "'s Event");
            getFiles().saveConfig();
            getFiles().changeCurrentEvent(player.getName() + "'s Event");
        }
        try {
            getFiles().setupEventFiles(Main.getInstance());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        new AdminGUI().openAdminGUI(player);
    }
}
