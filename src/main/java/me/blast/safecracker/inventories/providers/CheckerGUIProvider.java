package me.blast.safecracker.inventories.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.blast.safecracker.inventories.CheckerGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class CheckerGUIProvider extends CheckerGUI implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {

        //CHECKER ITEM
        ItemStack checker = nameItem(new ItemStack(Material.NETHER_STAR), "&eCheck Answers");
        ItemMeta checkerMeta = checker.getItemMeta();
        ArrayList<String> checkerLore = new ArrayList<>();
        checkerLore.addAll(loreBuilder("&3", "Click me to check your answers. Any right answer will be marked with a &a✔ &3and wrong answers will be marked with a &c✘&3. If your answer is correct, a hint to the final riddle will be revealed."));
        checkerMeta.setLore(checkerLore);
        checker.setItemMeta(checkerMeta);
        contents.set(0, 4, ClickableItem.of(checker, e -> {
            checkAnswers(player);
            openCheckerGUI(player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
        }));

        ArrayList<ItemStack> foundItems = new ArrayList<>();
        ArrayList<ItemStack> notFoundItems = new ArrayList<>();
        for(String npcs : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            if(getFiles().dataFile().get(npcs + ".id") == null){
                continue;
            }
            NPC npc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(npcs + ".id"));
            if(npc == null){
                continue;
            }
            ItemStack item = nameItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), colorize("&e&l" + npc.getName()));
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            if(getFiles().playerFile(player.getUniqueId()).get(npcs + ".found") == null){
                lore.addAll(loreBuilder("&r", "You have not found this NPC yet. Look around the arena to find it!"));
                meta.setLore(lore);
                SkullMeta skull = (SkullMeta) meta;
                skull.setOwner(npc.getName());
                item.setItemMeta(meta);
                notFoundItems.add(item);
            }
            else {
                lore.add(colorize("&3Found:"));
                lore.add(colorize("&r" + getFiles().playerFile(player.getUniqueId()).get(npc.getName().toLowerCase() + ".found")));
                lore.add("");
                lore.add(colorize("&3Question:"));
                lore.addAll(loreBuilder("&r", "" + getFiles().dataFile().get(npcs + ".question")));
                lore.add("");
                if(getFiles().playerFile(player.getUniqueId()).get(npc.getName().toLowerCase() + ".correct") == null) {
                    lore.add(colorize("&3Answer:"));
                    lore.addAll(loreBuilder("&r", (String) getFiles().playerFile(player.getUniqueId()).get(npc.getName().toLowerCase() + ".answer")));
                }
                else {
                    if(getFiles().playerFile(player.getUniqueId()).get(npcs + ".correct").equals("true")){
                        lore.add(colorize("&3Answer: &a✔"));
                        lore.add("");
                        lore.add(colorize("&3Riddle:"));
                        lore.addAll(loreBuilder("&r", (String) getFiles().dataFile().get(npcs + ".riddle")));
                    }
                    else {
                        lore.add(colorize("&3Answer: &c✘"));
                        if (getFiles().playerFile(player.getUniqueId()).get(npc.getName().toLowerCase() + ".answer") == null) {
                            lore.addAll(loreBuilder("&7&l", "Unanswered"));
                        } else {
                            lore.addAll(loreBuilder("&r", (String) getFiles().playerFile(player.getUniqueId()).get(npc.getName().toLowerCase() + ".answer")));
                        }
                    }
                }
                lore.add("");
                lore.addAll(loreBuilder("&7&o", "Click me to teleport to this NPC"));
                meta.setLore(lore);
                SkullMeta skull = (SkullMeta) meta;
                skull.setOwner(npc.getName());
                item.setItemMeta(meta);
                foundItems.add(item);
            }
        }

        int row = 1;
        int column = 1;

        for(ItemStack item : foundItems){
            contents.set(row, column, ClickableItem.of(item, e -> {
                Location npcLoc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase() + ".id")).getStoredLocation();
                player.sendMessage(colorize("&3Successfully teleported you to &e" + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()) + "&3."));
                player.teleport(npcLoc);
                player.closeInventory();
            } ));
            if(column != 7){
                column++;
            } else {
                column = 1;
                row++;
            }
        }
        for(ItemStack item : notFoundItems){
            contents.set(row, column, ClickableItem.empty(item));
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
