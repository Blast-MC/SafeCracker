package me.blast.safecracker.listeners;

import me.blast.safecracker.Files;
import me.blast.safecracker.SafeCracker;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DeleteNPCListener implements Listener {
    public Files getFiles(){return SafeCracker.getInstance().getFiles();}

    @EventHandler
    public void onNPCRemove(NPCRemoveEvent event) {
        for (String npc : getFiles().dataFile().getConfigurationSection("").getKeys(false)) {
            if (getFiles().dataFile().get(npc + ".id") == null) {
                continue;
            }
            if (event.getNPC().getId() == (int) getFiles().dataFile().get(npc + ".id")) {
                    getFiles().dataFile().set(npc, null);
                    getFiles().saveData();
                    SafeCracker.getInstance().log("A Safe Cracker NPC was removed via /npc remove. NPC ID: " + event.getNPC().getId());
                    for(Player player : SafeCracker.getInstance().getServer().getOnlinePlayers()){
                        if(player.hasPermission("safecracker.admin")){
                            player.sendMessage(SafeCracker.colorize("&cAn NPC that was setup as a Safe Cracker NPC was just removed via /npc remove. It has been removed from the Safe Cracker files as well."));
                        }
                    }
                break;
            }
        }
    }

    @EventHandler
    public void onNPCDespawn(NPCDespawnEvent event){
        for (String npc : getFiles().dataFile().getConfigurationSection("").getKeys(false)) {
            if (getFiles().dataFile().get(npc + ".id") == null) {
                continue;
            }
            if(event.getReason() != DespawnReason.REMOVAL){
                return;
            }
            if (event.getNPC().getId() == (int) getFiles().dataFile().get(npc + ".id")) {
                for(Player player : SafeCracker.getInstance().getServer().getOnlinePlayers()){
                    player.sendMessage(SafeCracker.colorize("&cAn NPC that was setup as a Safe Cracker NPC was just despawned. If this was intentional, ignore this message."));
                }
            }
            break;
        }
    }
}
