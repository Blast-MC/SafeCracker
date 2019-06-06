package me.blast.safecracker.inventories;

import fr.minuskube.inv.SmartInventory;
import me.blast.safecracker.inventories.providers.EventSelectorGUIProvider;
import org.bukkit.entity.Player;

public class EventSelectorGUI extends InventoryWrapper {

    public void openEventSelectorGUI(Player player){
        SmartInventory INV = SmartInventory.builder()
                .id("eventSelector")
                .size(6, 9)
                .title(colorize("&3Safe Cracker Event Selector"))
                .provider(new EventSelectorGUIProvider())
                .build();

        INV.open(player);
    }

}
