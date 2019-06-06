package me.blast.safecracker.inventories.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.blast.safecracker.Main;
import me.blast.safecracker.Files;
import me.blast.safecracker.inventories.AdminGUI;
import me.blast.safecracker.inventories.EventSelectorGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EventSelectorGUIProvider extends EventSelectorGUI implements InventoryProvider {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        //BACK ITEM
        contents.set(0, 0, ClickableItem.of(goBackItem(), e -> new AdminGUI().openAdminGUI(player)));

        //CREATE-NEW ITEM
        ItemStack create = nameItem(new ItemStack(Material.EMERALD_BLOCK), "&aCreate new event");
        contents.set(0, 4, ClickableItem.of(create, e -> {
            Main.getInstance().getCEL().adminCreateEventMap.add(player.getUniqueId().toString());
            player.closeInventory();
            player.sendMessage(colorize("&3Please type the name of the new event you wish to create, or type '&ecancel&3' to exit."));
        }));

        //EVENT ITEMS
        ArrayList<ItemStack> items = new ArrayList<>();
        for(String eventName : Main.getInstance().getEvents()){
            ItemStack event;
            if(eventName.equals(getFiles().configFile().get("currentEvent"))){
                event = nameItem(new ItemStack(Material.ENCHANTED_BOOK),"&e" + eventName);
            }
            else {
                event = nameItem(new ItemStack(Material.BOOK),"&e" + eventName);
            }
            ItemMeta eventMeta = event.getItemMeta();
            ArrayList<String> eventLore = new ArrayList<>();
            eventLore.add("");
            eventLore.addAll(loreBuilder("&3", "Created:"));
            eventLore.add(colorize("&e" + getFiles().tempDataFile(eventName).get("created")));
            eventLore.add("");
            eventLore.addAll(loreBuilder("&7&o", "Click me to set as current event"));
            eventMeta.setLore(eventLore);
            event.setItemMeta(eventMeta);
            items.add(event);
        }
        int row = 1;
        int column = 1;

        for(ItemStack item : items){
            contents.set(row, column, ClickableItem.of(item, e -> {
                getFiles().configFile().set("currentEvent", ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                getFiles().saveConfig();
                new EventSelectorGUI().openEventSelectorGUI(player);
            } ));
            if(column != 7){
                column++;
            } else {
                column = 1;
                row++;
            }
        }
    }



    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
