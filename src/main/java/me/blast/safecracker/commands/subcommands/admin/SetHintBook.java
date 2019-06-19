package me.blast.safecracker.commands.subcommands.admin;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.commands.Commands;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SetHintBook extends Commands {
    public SetHintBook(Player player) {
        if(player.getInventory().getItemInMainHand().getType() != Material.WRITTEN_BOOK){
            player.sendMessage(SafeCracker.colorize("&3You must be holding a &eWritten Book &3to set the hint book."));
            return;
        }
        getFiles().dataFile().set("hint-book", player.getInventory().getItemInMainHand());
        getFiles().saveData();
        player.sendMessage(SafeCracker.colorize("&3Successfully set the hint book to the held book."));
    }
}
