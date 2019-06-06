package me.blast.safecracker.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RewardsEditGUI extends InventoryWrapper {

    public RewardsEditGUI(Player player){
        Inventory INV = Bukkit.createInventory(null, 54, colorize("&3SafeCracker Rewards Edit"));
        if(getFiles().dataFile().get("rewards") == null){
            player.openInventory(INV);
            return;
        }
        ArrayList<ItemStack> items = new ArrayList<>((ArrayList<ItemStack>) getFiles().dataFile().get("rewards"));
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
