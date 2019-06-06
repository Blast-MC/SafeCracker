package me.blast.safecracker.commands;

import me.blast.safecracker.Main;
import me.blast.safecracker.NPCHandler;
import me.blast.safecracker.commands.subcommands.*;
import me.blast.safecracker.Files;
import me.blast.safecracker.inventories.AdminGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Commands implements CommandExecutor {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    NPCHandler NPCs = new NPCHandler();
    public NPCHandler getNPCs(){
        return NPCs;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("safecracker")){
            if(player.hasPermission("safecracker.admin") && args[0].equalsIgnoreCase("admin")){
                if(args.length >= 2) {
                    switch (args[1].toLowerCase()) {
                        case "edit":
                            new AdminEdit(player);
                            return true;
                        case "create":
                            new AdminCreate(player, args);
                            return true;
                        case "scores":
                            new AdminScores(player);
                            return true;
                        default:
                    }
                }
                player.sendMessage(Main.colorize("&cInvalid second argument. [edit, create]"));
                return true;
            }
            if(player.hasPermission("safecracker.player")){
                if(args.length >= 1){
                    switch(args[0].toLowerCase()){
                        case "start":
                            new Start(player);
                            return true;
                        case "check":
                            new Check(player);
                            return true;
                        case "solve":
                            new Solve(player, args);
                            return true;
                        case "claim":
                            new Claim(player);
                            return true;
                        default:
                    }
                }
                player.sendMessage(Main.colorize("&cInvalid argument. [start, check, complete, claim]"));
            }
            return true;
        }
        return false;
    }

}
