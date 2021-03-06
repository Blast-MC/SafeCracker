package me.blast.safecracker.commands;

import me.blast.safecracker.Files;
import me.blast.safecracker.NPCs.NPCHandler;
import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Tutorial;
import me.blast.safecracker.commands.subcommands.*;
import me.blast.safecracker.commands.subcommands.admin.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
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
            if(args.length == 0){
                if(player.hasPermission("safecracker.admin")){
                    player.sendMessage(SafeCracker.colorize("&cInvalid Arguments. [tutorial, start, check, solve, claim, hint, admin]"));
                    return true;
                }
                if(player.hasPermission("safecracker.player")) {
                    player.sendMessage(SafeCracker.colorize("&cInvalid Argument. [tutorial, start, check, solve, claim, hint]"));
                    return true;
                }
                player.sendMessage("Error 404. Command not found.");
                return true;
            }
            if(player.hasPermission("safecracker.admin") && args[0].equalsIgnoreCase("admin")){
                if(args.length >= 2) {
                    switch (args[1].toLowerCase()) {
                        case "edit":
                            new Edit(player);
                            return true;
                        case "create":
                            new Create(player, args);
                            return true;
                        case "scores":
                            new Scores(player);
                            return true;
                        case "reload":
                            new Reload(player);
                            return true;
                        case "settutorial":
                            new SetTutorial(player, args);
                            return true;
                        case "removetutorial":
                            new RemoveTutorial(player, args);
                            return true;
                        case "region":
                            new Region(player, args);
                            return true;
                        case "resetplayer":
                            new ResetPlayer(player, args);
                            return true;
                        case "sethintbook":
                            new SetHintBook(player);
                            return true;
                        default:
                    }
                }
                player.sendMessage(SafeCracker.colorize("&cInvalid second argument. [edit, create, reload, setTutorial, removeTutorial, region, resetPlayer, setHintBook]"));
                return true;
            }
            if(player.hasPermission("safecracker.player") || player.hasPermission("safecracker.admin")){
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
                        case "tutorial":
                            if(SafeCracker.getInstance().playersInTutorial.contains(player.getUniqueId().toString())){
                                return true;
                            }
                            SafeCracker.getInstance().playersInTutorial.add(player.getUniqueId().toString());
                            new Tutorial(player, 0).runTaskTimer(SafeCracker.getInstance(), 0, 20*6);
                            return true;
                        case "hint":
                            new Hint(player);
                            return true;
                        case "hints":
                            new Hint(player);
                            return true;
                        default:
                    }
                }
                player.sendMessage(SafeCracker.colorize("&cInvalid argument. [tutorial, start, check, solve, claim, hint]"));
                return true;
            }
            player.sendMessage(SafeCracker.colorize("Error 404. Command not found."));
            return true;
        }
        return true;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
