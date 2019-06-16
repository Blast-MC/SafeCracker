package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RemoveTutorial extends Commands {
    public RemoveTutorial(Player player, String[] args){
        if(args.length < 2){
            player.sendMessage(SafeCracker.colorize("&cPlease specify an ID to remove as a tutorial."));
            return;
        }
        if(isInt(args[2])){
            ArrayList<String> tutorialNPCs = new ArrayList<String>((ArrayList<String>) getFiles().configFile().get("tutorialNPCs"));
            if(!tutorialNPCs.contains(args[2])){
                player.sendMessage(SafeCracker.colorize("&3The specified ID is not registered as a tutorial."));
                return;
            }
            tutorialNPCs.remove(args[2]);
            getFiles().configFile().set("tutorialNPCs", tutorialNPCs);
            getFiles().saveConfig();
            player.sendMessage(SafeCracker.colorize("&3Successfully removed the NPC ID &e" + args[2] + " &3as a Safe Cracker tutorial."));
            return;
        }
        player.sendMessage(SafeCracker.colorize("&cPlease specify an integer as the ID."));
    }

}
