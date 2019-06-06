package me.blast.safecracker.inventories.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.blast.safecracker.Main;
import me.blast.safecracker.inventories.AdminGUI;
import me.blast.safecracker.inventories.EventSelectorGUI;
import me.blast.safecracker.inventories.NPCEditorGUI;
import me.blast.safecracker.inventories.RewardsEditGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class AdminGUIProvider extends AdminGUI implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {

        //EVENT ITEM
        ItemStack event = nameItem(new ItemStack(Material.ENCHANTED_BOOK), "&e" + getFiles().configFile().getString("currentEvent"));
        ItemMeta eventMeta = event.getItemMeta();
        ArrayList<String> eventLore = new ArrayList<>(loreBuilder("&7&o", "Click me to change the current event or create a new one."));
        eventMeta.setLore(eventLore);
        event.setItemMeta(eventMeta);
        contents.set(0, 4, ClickableItem.of(event, e -> new EventSelectorGUI().openEventSelectorGUI(player)));

        //RIDDLE-ANSWER ITEM
        ItemStack riddle = nameItem(new ItemStack(Material.PAPER), "&eRiddle Answer");
        ItemMeta riddleMeta = riddle.getItemMeta();
        ArrayList<String> riddleLore = new ArrayList<>();
        if(!getFiles().dataFile().get("riddle-answer").equals("")) {
            riddleLore.addAll(loreBuilder("&3", (String) getFiles().dataFile().get("riddle-answer")));
            riddleLore.add("");
        }
        riddleLore.addAll(loreBuilder("&7&o", "Click me to edit the answer to the main riddle"));
        riddleMeta.setLore(riddleLore);
        riddle.setItemMeta(riddleMeta);
        contents.set(0, 6, ClickableItem.of(riddle, e ->{
            Main.getInstance().getCEL().adminChatMap.put(player.getUniqueId().toString(), "riddle-answer");
            player.closeInventory();
            player.sendMessage(colorize("&3Please type the riddle answer in the chat, or type '&ecancel&3' to exit."));
        }));

        //REWARDS ITEM
        ItemStack rewards = nameItem(new ItemStack(Material.DIAMOND), "&eRewards");
        ItemMeta rewardsMeta = rewards.getItemMeta();
        ArrayList<String> rewardsLore = new ArrayList<>();
        rewardsLore.addAll(loreBuilder("&7&o", "Click me to edit the rewards for solving the final riddle"));
        rewardsMeta.setLore(rewardsLore);
        rewards.setItemMeta(rewardsMeta);
        contents.set(0, 2, ClickableItem.of(rewards, e -> new RewardsEditGUI(player)));

        //INFO ITEM
        contents.set(0, 8, ClickableItem.empty(infoItem("Click on the skull of the NPC you wish to edit. To edit any physical attributes of the NPC, use Citizen's &e/npc edit&3.")));

        //NPC ITEMS
        ArrayList<ItemStack> items = new ArrayList<>();
        for(String npcs : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            if(npcs.equals("created") || npcs.equals("riddle-answer") || npcs.equals("commands-upon-solve") || npcs.equals("rewards")){
                continue;
            }
            NPC npc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(npcs + ".id"));
            ItemStack item = nameItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), colorize("&e&l" + npc.getName()));
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add(colorize("&rID: &o" + npc.getId()));
            lore.add("");
            lore.add(colorize("&3Question:"));
            lore.addAll(loreBuilder("&r", "" + getFiles().dataFile().get(npcs + ".question")));
            lore.add("");
            lore.add(colorize("&3Answers:"));
            ArrayList<String> answersList = (ArrayList<String>) getFiles().dataFile().get(npcs + ".answers");
            for(String answer : answersList){
                if(!answer.equals("")){
                    lore.addAll(loreBuilder("&r"," &e- &r" + answer));
                }
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
            contents.set(row, column, ClickableItem.of(item, e -> {
                Main.getInstance().adminEdit.put(player.getUniqueId().toString(), ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                new NPCEditorGUI().openNPCEditorGUI(player);
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
