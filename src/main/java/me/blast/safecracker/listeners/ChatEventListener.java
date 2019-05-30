package me.blast.safecracker.listeners;

import me.blast.safecracker.Main;
import me.blast.safecracker.files.Files;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class ChatEventListener implements Listener {

    public HashMap<String, String> playerChatMap = new HashMap<>();
    public HashMap<String, String> adminChatMap = new HashMap<>();

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Main.getInstance().log(event.getPlayer().getName() + " talked");
        if((playerChatMap.containsKey(event.getPlayer().getUniqueId().toString()) || adminChatMap.containsKey(event.getPlayer().getUniqueId().toString())) && event.getMessage().equalsIgnoreCase("cancel")){
            playerChatMap.remove(event.getPlayer().getUniqueId().toString());
            adminChatMap.remove(event.getPlayer().getUniqueId().toString());
            event.getPlayer().sendMessage(Main.colorize("&aCanceled."));
            event.setCancelled(true);
            return;
        }
        if(playerChatMap.containsKey(event.getPlayer().getUniqueId().toString())){
            getFiles().playerFile(event.getPlayer().getUniqueId()).set(playerChatMap.get(event.getPlayer().getUniqueId().toString()), event.getMessage());
            getFiles().savePlayerData(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(Main.colorize("&aYou answered: " + "&r" + event.getMessage()));
            playerChatMap.remove(event.getPlayer().getUniqueId().toString());
            event.setCancelled(true);
            return;
        }
        if(adminChatMap.containsKey(event.getPlayer().getUniqueId().toString())){
            getFiles().dataFile().set(adminChatMap.get(event.getPlayer().getUniqueId().toString()), event.getMessage());
            getFiles().saveData();
            adminChatMap.remove(event.getPlayer().getUniqueId().toString());
            event.setCancelled(true);
            return;
        }
    }

}
