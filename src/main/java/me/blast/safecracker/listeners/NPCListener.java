package me.blast.safecracker.listeners;

import me.blast.safecracker.Files;
import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.Tutorial;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class NPCListener implements Listener {

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent event){
        int id = event.getNPC().getId();
        Player player = event.getClicker();
        ArrayList<String> tutorialNPCs = new ArrayList<String>((ArrayList<String>) getFiles().configFile().get("tutorialNPCs"));
        if(tutorialNPCs.contains(Integer.toString(id)) && !SafeCracker.getInstance().playersInTutorial.contains(player.getUniqueId().toString())){
            SafeCracker.getInstance().playersInTutorial.add(player.getUniqueId().toString());
            new Tutorial(player, 0).runTaskTimer(SafeCracker.getInstance(), 0, 20*10);
            return;
        }
        for(String npc : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            if(npc.equals("created") || npc.equals("riddle-answer") || npc.equals("commands-upon-solve") || npc.equals("rewards")){
                continue;
            }
            if(id == (int) getFiles().dataFile().get(npc + ".id")){
                if(getFiles().playerFile(player.getUniqueId()).get("started") == null){
                    player.sendMessage(SafeCracker.colorize("&3You have not started the Safe Cracker event! Use '&c/safecracker start&3' to begin."));
                    return;
                }
                if(getFiles().dataFile().get(npc + ".question").toString().equals("") || getFiles().dataFile().get(npc + ".riddle").toString().equals("")){
                    player.sendMessage(SafeCracker.colorize("&3This NPC has not been properly setup. Please have an admin configure it. &eNPC ID: " + getFiles().dataFile().get(npc + ".id")));
                    return;
                }
                ArrayList<String> answers = new ArrayList<String>((ArrayList<String>) getFiles().dataFile().get(npc + ".answers"));
                if(answers.contains("")){
                    player.sendMessage(SafeCracker.colorize("&3This NPC has not been properly setup. Please have an admin configure it. &eNPC ID: " + getFiles().dataFile().get(npc + ".id")));
                    return;
                }
                player.sendMessage(SafeCracker.colorize("&3&lQuestion: &e" + getFiles().dataFile().get(npc + ".question")));
                player.sendMessage(SafeCracker.colorize("&3Type your answer in chat, or type '&ecancel&3' to exit."));
                if(getFiles().playerFile(player.getUniqueId()).get(npc + ".found") == null) {
                    getFiles().playerFile(player.getUniqueId()).set(npc + ".found", SafeCracker.getInstance().dateFormatter());
                    getFiles().savePlayerData(player.getUniqueId());
                }
                SafeCracker.getInstance().getCEL().playerChatMap.put(event.getClicker().getUniqueId().toString(), npc);
                break;
            }
        }
    }
}
