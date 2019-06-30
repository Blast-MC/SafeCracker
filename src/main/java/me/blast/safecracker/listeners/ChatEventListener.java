package me.blast.safecracker.listeners;

import me.blast.safecracker.Files;
import me.blast.safecracker.NPCs.DeleteNPC;
import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Tutorial;
import me.blast.safecracker.inventories.AdminGUI;
import me.blast.safecracker.inventories.CheckerGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ChatEventListener implements Listener {

    public HashMap<String, String> playerChatMap = new HashMap<>();
    public HashMap<String, String> adminChatMap = new HashMap<>();
    public HashMap<String, String> adminAnswersChatMap = new HashMap<>();
    public HashMap<String, String> adminDeleteChatMap = new HashMap<>();
    public ArrayList<String> adminCreateEventMap = new ArrayList<>();
    public ArrayList<String> tutorialMap = new ArrayList<>();

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }

    public boolean isInMap(String player){
        if(playerChatMap.containsKey(player)){return true;}
        if(adminChatMap.containsKey(player)){return true;}
        if(adminAnswersChatMap.containsKey(player)){return true;}
        if(adminDeleteChatMap.containsKey(player)){return true;}
        if(adminCreateEventMap.contains(player)){return true;}
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        String uuid = event.getPlayer().getUniqueId().toString();
        if(isInMap(event.getPlayer().getUniqueId().toString()) && event.getMessage().equalsIgnoreCase("cancel")){
            playerChatMap.remove(uuid);
            adminChatMap.remove(uuid);
            event.getPlayer().sendMessage(SafeCracker.colorize("&eCanceled."));
            event.setCancelled(true);
            return;
        }
        if(playerChatMap.containsKey(uuid)){
            String npc = playerChatMap.get(uuid).toLowerCase();
            getFiles().playerFile(event.getPlayer().getUniqueId()).set(npc + ".answer", event.getMessage());
            getFiles().savePlayerData(event.getPlayer().getUniqueId());
            getFiles().playerFile(event.getPlayer().getUniqueId()).set(npc + ".correct", null);
            getFiles().savePlayerData(event.getPlayer().getUniqueId());
            if(new CheckerGUI().isCorrect(event.getMessage(), npc)){
                event.getPlayer().sendMessage(SafeCracker.colorize("&3&l" + playerChatMap.get(uuid) + ":&e " + correctResponses[(int) Math.ceil(Math.random() * correctResponses.length - 1)]));
            } else {
                event.getPlayer().sendMessage(SafeCracker.colorize("&3&l" + playerChatMap.get(uuid) + ":&e " + wrongResponses[(int) Math.ceil(Math.random() * wrongResponses.length - 1)]));
            }
            playerChatMap.remove(uuid);
            event.setCancelled(true);
            return;
        }
        if(adminChatMap.containsKey(uuid)){
            getFiles().dataFile().set(adminChatMap.get(uuid), event.getMessage());
            getFiles().saveData();
            event.getPlayer().sendMessage(SafeCracker.colorize("&3You responded with: &e'" + event.getMessage() + "' &3Saved!"));
            adminChatMap.remove(uuid);
            event.setCancelled(true);
            new AdminGUI().openAdminGUI(event.getPlayer());
            return;
        }
        if(adminAnswersChatMap.containsKey(uuid)){
            String string = event.getMessage();
            String[] split = string.split("\\s\\|\\|\\s");
            ArrayList<String> splitString = new ArrayList<>(Arrays.asList(split));
            getFiles().dataFile().set(adminAnswersChatMap.get(uuid), splitString);
            getFiles().saveData();
            event.getPlayer().sendMessage(SafeCracker.colorize("&3Set the answers to:"));
            for(String str : split){
                event.getPlayer().sendMessage(SafeCracker.colorize("&r &e- " + str));
            }
            adminAnswersChatMap.remove(uuid);
            event.setCancelled(true);
            new AdminGUI().openAdminGUI(event.getPlayer());
            return;
        }
        if(adminDeleteChatMap.containsKey(uuid)){
            if(event.getMessage().equals(adminDeleteChatMap.get(uuid))){
                NPC npc = CitizensAPI.getNPCRegistry().getById((int) getFiles().dataFile().get(event.getMessage().toLowerCase() + ".id"));
                new DeleteNPC(npc).runTaskTimer(SafeCracker.getInstance(), 0, 0);
                getFiles().dataFile().set(adminDeleteChatMap.get(uuid).toLowerCase(), null);
                getFiles().saveData();
                adminDeleteChatMap.remove(uuid);
                event.setCancelled(true);
                event.getPlayer().sendMessage(SafeCracker.colorize(    "&3Successfully deleted the '&e" + event.getMessage() + "&3' NPC. You may have to remove it physically by doing /npc remove."));
                new AdminGUI().openAdminGUI(event.getPlayer());
                return;
            }
            else {
                event.getPlayer().sendMessage(SafeCracker.colorize("&3Your response did not match. &eCanceling."));
                adminDeleteChatMap.remove(uuid);
                event.setCancelled(true);
                new AdminGUI().openAdminGUI(event.getPlayer());
                return;
            }
        }
        if(adminCreateEventMap.contains(uuid)){
            for(String eventList : SafeCracker.getInstance().getEvents()) {
                if (eventList.equalsIgnoreCase(event.getMessage())){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(SafeCracker.colorize("&3That event name is already registered. Please choose a different name."));
                    return;
                }
            }
            getFiles().changeCurrentEvent(event.getMessage());
            adminCreateEventMap.remove(uuid);
            event.getPlayer().sendMessage(SafeCracker.colorize("&3Successfully created the new event '&e" + event.getMessage() + "&3' and set it as the current event!"));
            event.setCancelled(true);
            new AdminGUI().openAdminGUI(event.getPlayer());
            return;
        }
        if(tutorialMap.contains(uuid)){
            event.setCancelled(true);
            tutorialMap.remove(uuid);
            UUID playerUUID = UUID.fromString(uuid);
            new Tutorial(Bukkit.getPlayer(playerUUID), 6).runTaskTimer(SafeCracker.getInstance(), 10, 20*7);
            return;
        }
    }

    public String[] correctResponses = {
            "Wow! That's correct!",
            "You just answered correctly!",
            "Amazing job. That's right!",
            "That's right! Wooooo!"
            };

    public String[] wrongResponses = {
            "Sadly, that's not right.",
            "That answer isn't correct; try again.",
            "You gave me a wrong response. Maybe try again?",
            "That's incorrect, but I believe in you!"
    };
}
