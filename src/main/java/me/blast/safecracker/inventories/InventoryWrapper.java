package me.blast.safecracker.inventories;

import me.blast.safecracker.Main;
import me.blast.safecracker.files.Files;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryWrapper {

    public ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Main.colorize("&r" + name));
        item.setItemMeta(meta);
        return item;
    }

    public ArrayList<String> loreBuilder(String string){
        ArrayList<String> lore = new ArrayList<>();
        int index = 35;
        while(string.length() > 35){
            if(!Character.isWhitespace(string.charAt(index))){
                Main.getInstance().log(string.charAt(index) + "");
                index++;
            } else {
                lore.add(string.substring(0, index));
                Main.getInstance().log(string.substring(0, index));
                string = string.substring(index + 1);
                index = 35;
            }
        }
        if(string.length() > 0){
            lore.add(string);
        }
        return lore;
    }

    public static String colorize(String string){
        return string.replaceAll("&", "§");
    }

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

}
