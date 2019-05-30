package me.blast.safecracker.inventories.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.blast.safecracker.inventories.InventoryWrapper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class AdminGUIProvider extends InventoryWrapper implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for(String npcs : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            NPC npc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(npcs + ".id"));
            ItemStack item = nameItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), npc.getName());
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add(colorize("&rID: " + npc.getId()));
            for(String string : loreBuilder("&d&lQUESTION: &r" + getFiles().dataFile().get(npcs + ".question"))){
                lore.add(ChatColor.RESET + colorize(string));
            }
            lore.add("");
            for(String string : loreBuilder("&a&lANSWERS: &r" + getFiles().dataFile().get(npcs + ".answers"))){
                lore.add(ChatColor.RESET + colorize(string));
            }
            lore.add("");
            lore.add(colorize("&7&oClick me to edit this NPC"));
            meta.setLore(lore);
            SkullMeta skull = (SkullMeta) meta;
            skull.setOwner(npc.getName());
            item.setItemMeta(meta);
            items.add(item);
        }

        int row = 1;
        int column = 1;

        for(ItemStack item : items){
            contents.set(row, column, ClickableItem.empty(item));
            if(column != 7){
                column++;
            } else {
                column = 0;
                row++;
            }
        }

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
