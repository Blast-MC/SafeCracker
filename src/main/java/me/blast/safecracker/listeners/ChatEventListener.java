package me.blast.safecracker.listeners;

import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Files;
import me.blast.safecracker.Tutorial;
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
            getFiles().playerFile(event.getPlayer().getUniqueId()).set(playerChatMap.get(uuid) + ".answer", event.getMessage());
            getFiles().savePlayerData(event.getPlayer().getUniqueId());
            getFiles().playerFile(event.getPlayer().getUniqueId()).set(playerChatMap.get(uuid) + ".correct", null);
            getFiles().savePlayerData(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(SafeCracker.colorize("&3You answered: &e'" + event.getMessage() + "'"));
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
            return;
        }
        if(adminDeleteChatMap.containsKey(uuid)){
            if(event.getMessage().equals(adminDeleteChatMap.get(uuid))){
                getFiles().dataFile().set(adminDeleteChatMap.get(uuid).toLowerCase(), null);
                getFiles().saveData();
                event.getPlayer().sendMessage(SafeCracker.colorize(    "&3Successfully deleted the '&e" + adminDeleteChatMap.get(uuid) + "&3' NPC. You may have to remove it physically by doing /npc remove."));
                adminDeleteChatMap.remove(uuid);
                event.setCancelled(true);
                return;
            }
            else {
                event.getPlayer().sendMessage(SafeCracker.colorize("&3Your reponse did not match. &eCanceling."));
                adminDeleteChatMap.remove(uuid);
                event.setCancelled(true);
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
            return;
        }
        if(tutorialMap.contains(uuid)){
            event.setCancelled(true);
            tutorialMap.remove(uuid);
            UUID playerUUID = UUID.fromString(uuid);
            new Tutorial(Bukkit.getPlayer(playerUUID), 6).runTaskTimer(SafeCracker.getInstance(), 10, 20*10);
            return;
        }
    }

}
