package me.blast.safecracker.listeners;

import me.blast.safecracker.Files;
import me.blast.safecracker.Main;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class NPCListener implements Listener {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent event){
        int id = event.getNPC().getId();
        Player player = event.getClicker();
        for(String npc : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            if(npc.equals("created") || npc.equals("riddle-answer") || npc.equals("commands-upon-solve") || npc.equals("rewards")){
                continue;
            }
            if(id == (int) getFiles().dataFile().get(npc + ".id")){
                if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
                    player.sendMessage(Main.colorize("&3You have not started the Safe Cracker event! Use '&e/safecracker start&3' to begin."));
                    return;
                }
                if(getFiles().dataFile().get(npc + ".question").toString().equals("") || getFiles().dataFile().get(npc + ".riddle").toString().equals("")){
                    player.sendMessage(Main.colorize("&3This NPC has not been properly setup. Please have an admin configure it. &eNPC ID: " + getFiles().dataFile().get(npc + ".id")));
                    return;
                }
                ArrayList<String> answers = new ArrayList<String>((ArrayList<String>) getFiles().dataFile().get(npc + ".answers"));
                if(answers.contains("")){
                    player.sendMessage(Main.colorize("&3This NPC has not been properly setup. Please have an admin configure it. &eNPC ID: " + getFiles().dataFile().get(npc + ".id")));
                    return;
                }
                player.sendMessage(Main.colorize("&3&lQuestion: &e" + getFiles().dataFile().get(npc + ".question")));
                player.sendMessage(Main.colorize("&3Type your answer in chat, or type '&ecancel&3' to exit."));
                if(getFiles().playerFile(player.getUniqueId()).get(npc + ".found") == null) {
                    getFiles().playerFile(player.getUniqueId()).set(npc + ".found", Main.getInstance().dateFormatter());
                    getFiles().savePlayerData(player.getUniqueId());
                }
                Main.getInstance().getCEL().playerChatMap.put(event.getClicker().getUniqueId().toString(), npc);
                break;
            }
        }
    }
}
