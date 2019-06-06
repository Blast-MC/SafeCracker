package me.blast.safecracker.inventories.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.blast.safecracker.Main;
import me.blast.safecracker.inventories.AdminGUI;
import me.blast.safecracker.inventories.NPCEditorGUI;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class NPCEditorGUIProvider extends NPCEditorGUI implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        String npc = Main.getInstance().adminEdit.get(player.getUniqueId().toString());

        //SKULL ITEM
        ItemStack skull = nameItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), colorize("&e" + npc));
        ItemMeta skullMeta = skull.getItemMeta();
        SkullMeta skullSkullMeta = (SkullMeta) skullMeta;
        skullSkullMeta.setOwner(npc);
        skull.setItemMeta(skullMeta);
        contents.set(0, 4, ClickableItem.empty(skull));

        //BACK ITEM
        contents.set(0, 0, ClickableItem.of(goBackItem(), e -> new AdminGUI().openAdminGUI(player)));

        //INFO ITEM
        contents.set(0, 8, ClickableItem.empty(infoItem("Click any item to edit the info of the NPC, or click the teleport item to be teleported to the NPC.")));

        //QUESTION ITEM
        ItemStack question = nameItem(new ItemStack(Material.BOOK_AND_QUILL), colorize("&eQuestion"));
        ItemMeta questionMeta = question.getItemMeta();
        ArrayList<String> questionLore = new ArrayList<>();
        if(!getFiles().dataFile().get(npc.toLowerCase() + ".question").toString().equals("")){
            questionLore.addAll(loreBuilder("&r", "" + getFiles().dataFile().get(npc.toLowerCase() + ".question")));
            questionLore.add("");
        }
        questionLore.add(colorize("&7&oClick me to edit"));
        questionMeta.setLore(questionLore);
        question.setItemMeta(questionMeta);
        contents.set(1, 1, ClickableItem.of(question, e -> {
            Main.getInstance().getCEL().adminChatMap.put(player.getUniqueId().toString(), npc.toLowerCase() + ".question");
            player.closeInventory();
            player.sendMessage(colorize("&3Please type the question in the chat, or type '&ecancel&3' to exit."));
        }));

        //ANSWERS ITEM
        ItemStack answers = nameItem(new ItemStack(Material.PAPER), colorize("&eAnswers"));
        ItemMeta answersMeta = answers.getItemMeta();
        ArrayList<String> answersLore = new ArrayList<>();
        ArrayList<String> answersList = (ArrayList<String>) getFiles().dataFile().get(npc.toLowerCase() + ".answers");
        for(String answer : answersList){
            if(!answer.equals("")){
                answersLore.addAll(loreBuilder("&r", "  &e- &r" + answer));
            }
        }
        if(!answersList.contains("")){
            answersLore.add("");
        }
        answersLore.add(colorize("&7&oClick me to edit"));
        answersMeta.setLore(answersLore);
        answers.setItemMeta(answersMeta);
        contents.set(1, 3, ClickableItem.of(answers, e ->{
            Main.getInstance().getCEL().adminAnswersChatMap.put(player.getUniqueId().toString(), npc.toLowerCase() + ".answers");
            player.closeInventory();
            player.sendMessage(colorize("&3Please type the answers in the chat separated by '&e||&3', or type '&ecancel' to exit."));
        }));

        //RIDDLE ITEM
        ItemStack riddle = nameItem(new ItemStack(Material.DIAMOND), colorize("&eRiddle Response"));
        ItemMeta riddleMeta = riddle.getItemMeta();
        ArrayList<String> riddleLore = new ArrayList<>();
        if(!getFiles().dataFile().get(npc.toLowerCase() + ".riddle").toString().equals("")) {
            for(String temp : loreBuilder(colorize("&r" + getFiles().dataFile().get(npc.toLowerCase() + ".riddle")))){
                riddleLore.add(colorize("&r" + temp));
            }
            riddleLore.add("");
        }
        riddleLore.add(colorize("&7&oClick me to edit"));
        riddleMeta.setLore(riddleLore);
        riddle.setItemMeta(riddleMeta);
        contents.set(1, 5, ClickableItem.of(riddle, e ->{
            player.closeInventory();
            Main.getInstance().getCEL().adminChatMap.put(player.getUniqueId().toString(), npc.toLowerCase() + ".riddle");
            player.sendMessage(colorize("&3Please type the riddle in the chat or type '&ecancel&3' to exit."));
        }));

        //TELEPORT ITEM
        ItemStack teleport = nameItem(new ItemStack(Material.COMPASS), colorize("&eTeleport"));
        ItemMeta teleportMeta = teleport.getItemMeta();
        ArrayList<String> teleportLore = new ArrayList<>();
        teleportLore.add(colorize("&7&oClick me to teleport to the NPC"));
        teleportMeta.setLore(teleportLore);
        teleport.setItemMeta(teleportMeta);
        Location npcLoc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(npc.toLowerCase() + ".id")).getStoredLocation();
        contents.set(1, 7, ClickableItem.of(teleport, e -> {
            player.teleport(npcLoc);
            player.closeInventory();
        }));

        //DELETE ITEM
        ItemStack delete = nameItem(new ItemStack(Material.TNT), colorize("&4Delete NPC"));
        ItemMeta deleteMeta = delete.getItemMeta();
        ArrayList<String> deleteLore = new ArrayList<>();
        deleteLore.addAll(loreBuilder("&7&o", "Right click me to delete this NPC forever."));
        deleteLore.add("");
        deleteLore.addAll(loreBuilder("&4", "&lWARNING: &4This cannot be undone and deletes all the data for this NPC."));
        deleteMeta.setLore(deleteLore);
        delete.setItemMeta(deleteMeta);
        contents.set(2, 4, ClickableItem.of(delete, e ->{
            if(e.isRightClick()){
                player.closeInventory();
                player.sendMessage(colorize("&3Please confirm this action by typing the name of the NPC you wish to delete or type '&ecancel&3' to exit."));
                Main.getInstance().getCEL().adminDeleteChatMap.put(player.getUniqueId().toString(), npc);
            }
            else{
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 15, 4);
            }
        }));

        Main.getInstance().adminEdit.remove(player.getUniqueId().toString());
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}