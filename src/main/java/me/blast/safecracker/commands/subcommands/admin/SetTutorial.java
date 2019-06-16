package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetTutorial extends Commands {
    public SetTutorial(Player player, String[] args) {
        if(args.length < 2){
            player.sendMessage(SafeCracker.colorize("&cPlease specify an ID to set as a tutorial."));
            return;
        }
        if(isInt(args[2])){
            ArrayList<String> tutorialNPCs = new ArrayList<String>((ArrayList<String>) getFiles().configFile().get("tutorialNPCs"));
            if(tutorialNPCs.contains("")){
                tutorialNPCs.remove("");
            }
            tutorialNPCs.add(args[2]);
            getFiles().configFile().set("tutorialNPCs", tutorialNPCs);
            getFiles().saveConfig();
            player.sendMessage(SafeCracker.colorize("&3Successfully set the NPC ID &e" + args[2] + " &3to a Safe Cracker tutorial."));
            return;
        }
        player.sendMessage(SafeCracker.colorize("&cPlease specify an integer as the ID."));
    }
}
