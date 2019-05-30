package me.blast.safecracker.commands;

import me.blast.safecracker.Main;
import me.blast.safecracker.NPCHandler;
import me.blast.safecracker.files.Files;
import me.blast.safecracker.inventories.AdminGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Commands implements CommandExecutor {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    NPCHandler NPCs = new NPCHandler();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("safecracker")){
            if(player.hasPermission("safecracker.admin") && args[0].equalsIgnoreCase("admin")){
                if(args[1].equalsIgnoreCase("edit")) {
                    new AdminGUI().openAdminGUI(player);
                    return true;
                }
                if(args[1].equalsIgnoreCase("create")){
                    NPCs.create(args[2], player.getLocation());
                    return true;
                }
            }
            if(player.hasPermission("safecracker.player")){
                if(args.length >= 1){
                    switch(args[0].toLowerCase()){
                        case "start":
                            break;
                        case "check":
                            break;
                        case "complete":
                            break;
                        case "claim":
                            break;
                        case "answer":
                            break;
                        default:
                    }
                }
                player.sendMessage(Main.colorize("&4Invalid argument. [start, check, complete, claim]"));
            }
            return true;
        }
        return false;
    }

    public String getDate() {
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
            return formatter.format(currentDate.getTime());
    }

}
