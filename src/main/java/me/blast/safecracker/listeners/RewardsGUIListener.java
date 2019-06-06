package me.blast.safecracker.listeners;

import me.blast.safecracker.Files;
import me.blast.safecracker.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RewardsGUIListener implements Listener {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    @EventHandler
    public void savePlayerRewards(InventoryCloseEvent e){
        if(!e.getInventory().getTitle().equals(Main.colorize("&3SafeCracker Rewards"))){
            return;
        }
        Player player = (Player) e.getPlayer();
        ItemStack[] items = e.getInventory().getContents();
        ArrayList<ItemStack> itemArray = new ArrayList<>();
        for (ItemStack item : items){
            if(item != null){
                itemArray.add(item);
            }
        }
        getFiles().playerFile(player.getUniqueId()).set("rewards", itemArray);
        getFiles().savePlayerData(player.getUniqueId());
    }

    @EventHandler
    public void saveRewards(InventoryCloseEvent e){
        if(!e.getInventory().getTitle().equals(Main.colorize("&3SafeCracker Rewards Edit"))){
            return;
        }
        Player player = (Player) e.getPlayer();
        ItemStack[] items = e.getInventory().getContents();
        ArrayList<ItemStack> itemArray = new ArrayList<>();
        for (ItemStack item : items){
            if(item != null){
                itemArray.add(item);
            }
        }
        getFiles().dataFile().set("rewards", itemArray);
        getFiles().saveData();
        player.sendMessage(Main.colorize("&3Saved the rewards for the &e" + getFiles().configFile().get("currentEvent") + "&3 event."));
    }



}
