package me.blast.safecracker.inventories;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Files;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryWrapper {

    public ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SafeCracker.colorize("&r" + name));
        item.setItemMeta(meta);
        return item;
    }

    public ArrayList<String> loreBuilder(String string){
        ArrayList<String> lore = new ArrayList<>();
        int index = 35;
        while(string.length() >= 35){
            if(index >= string.length()){
                break;
            }
            if(!Character.isWhitespace(string.charAt(index))){
                index++;
            } else {
                lore.add(colorize(string.substring(0, index)));
                string = string.substring(index + 1);
                index = 35;
            }
        }
        if(string.length() > 0){
            lore.add(colorize(string));
        }
        return lore;
    }

    public ArrayList<String> loreBuilder(String color, String string){
        ArrayList<String> lore = new ArrayList<>();
        int index = 35;
        while(string.length() >= 35){
            if(index >= string.length()){
                break;
            }
            if(!Character.isWhitespace(string.charAt(index))){
                index++;
            } else {
                lore.add(colorize(color + string.substring(0, index)));
                string = string.substring(index + 1);
                index = 35;
            }
        }
        if(string.length() > 0){
            lore.add(colorize(color + string));
        }
        return lore;
    }

    public static String colorize(String string){
        return string.replaceAll("&", "§");
    }

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }

    public ItemStack goBackItem(){
        return nameItem(new ItemStack(Material.BARRIER), colorize("&c&m<- &c Back "));
    }

    public ItemStack infoItem(String info){
        ItemStack item = nameItem(new ItemStack(Material.BOOK), colorize("&eInfo"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.addAll(loreBuilder("&3", info));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
