package me.blast.safecracker.inventories;

import fr.minuskube.inv.SmartInventory;
import me.blast.safecracker.inventories.providers.NPCEditorGUIProvider;
import org.bukkit.entity.Player;

public class NPCEditorGUI extends InventoryUtils {

    public void openNPCEditorGUI(Player player){
        SmartInventory INV = SmartInventory.builder()
                .id("NPCEditor")
                .provider(new NPCEditorGUIProvider())
                .size(3, 9)
                .title(colorize("&3SafeCracker NPC Editor"))
                .build();

        INV.open(player);
    }

}
