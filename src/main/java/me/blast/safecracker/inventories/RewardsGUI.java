package me.blast.safecracker.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RewardsGUI extends InventoryUtils {

    public RewardsGUI(Player player){
        ArrayList<ItemStack> items;
        Inventory INV = Bukkit.createInventory(null, 54, colorize("&3SafeCracker Rewards"));
        if(getFiles().playerFile(player.getUniqueId()).get("rewards") == null){
            items = new ArrayList<>((ArrayList<ItemStack>) getFiles().dataFile().get("rewards"));
        }
        else{
            items = new ArrayList<>((ArrayList<ItemStack>) getFiles().playerFile(player.getUniqueId()).get("rewards"));
        }

        int slot = 10;
        for(ItemStack item : items) {
            INV.setItem(slot, item);
            if(slot == 16){
                slot = 19;
                continue;
            }
            if(slot == 25){
                slot = 28;
                continue;
            }
            slot++;
        }
        player.openInventory(INV);
    }

}
