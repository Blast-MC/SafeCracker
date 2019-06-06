package me.blast.safecracker.inventories;

import fr.minuskube.inv.SmartInventory;
import me.blast.safecracker.inventories.providers.CheckerGUIProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class CheckerGUI extends InventoryWrapper{

    public void openCheckerGUI(Player player){
        SmartInventory INV = SmartInventory.builder()
                .id("checkerGUI")
                .provider(new CheckerGUIProvider())
                .title(colorize("&3SafeCracker"))
                .size(6, 9)
                .build();


        INV.open(player);
    }

    public void checkAnswers(Player player){
        for(String npc : getFiles().playerFile(player.getUniqueId()).getConfigurationSection("").getKeys(false)){
            if(npc.equals("started")){
                continue;
            }
            if(isCorrect((String) getFiles().playerFile(player.getUniqueId()).get(npc + ".answer"), npc)) {
                getFiles().playerFile(player.getUniqueId()).set(npc + ".correct", "true");
            }
            else {
                getFiles().playerFile(player.getUniqueId()).set(npc + ".correct", "false");
            }
            getFiles().savePlayerData(player.getUniqueId());
        }
    }

    public boolean isCorrect(String answer, String npc){
        ArrayList<String> answers = (ArrayList<String>) getFiles().dataFile().get(npc + ".answers");
        for(String ans : answers){
            if(ans.equalsIgnoreCase(answer)){
                return true;
            }
        }
        return false;
    }

}
