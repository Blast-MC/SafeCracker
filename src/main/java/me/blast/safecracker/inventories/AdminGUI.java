package me.blast.safecracker.inventories;

import fr.minuskube.inv.SmartInventory;
import me.blast.safecracker.inventories.providers.AdminGUIProvider;
import org.bukkit.entity.Player;

public class AdminGUI extends InventoryUtils {

    public void openAdminGUI(Player player){
        SmartInventory INV = SmartInventory.builder()
                .id("safeCrackerAdmin")
                .provider(new AdminGUIProvider())
                .size(6, 9)
                .title(colorize("&3SafeCracker Admin GUI"))
                .build();
        INV.open(player);
    }

}
