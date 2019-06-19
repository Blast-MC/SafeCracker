package me.blast.safecracker.commands.subcommands;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hint extends Commands {
    public Hint(Player player) {
        if(getFiles().dataFile().get("hint-book") == null){
            player.sendMessage(SafeCracker.colorize("&3There is no available hint book for this Safe Cracker event."));
            return;
        }
        try {
            if(player.getInventory().addItem((ItemStack) getFiles().dataFile().get("hint-book")).isEmpty()){
                player.sendMessage(SafeCracker.colorize("&3You have been given the hint book."));
                return;
            }
            player.sendMessage(SafeCracker.colorize("&3Your inventory was full so you could not be given the hint book."));
        } catch (Exception e) {
            player.sendMessage(SafeCracker.colorize("&3There was an error getting you the hint book. &ePlease report this to an administrator."));
            SafeCracker.getInstance().log("There was an error in giving the player the hint book.");
            SafeCracker.getInstance().log("Please either update the book in the data.yml file to a proper ItemStack or set it to null.");
        }
    }
}
