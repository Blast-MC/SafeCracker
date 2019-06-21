package me.blast.safecracker.NPCs;

import me.blast.safecracker.Files;
import me.blast.safecracker.SafeCracker;
import me.blast.safecracker.listeners.ChatEventListener;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class NPCHandler{

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }
    public ChatEventListener getCEL(){
        return SafeCracker.getInstance().getCEL();
    }

    public void create(String name, Location loc){
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        npc.spawn(loc);
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".id", npc.getId());
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".question", "");
        ArrayList<String> answers = new ArrayList<>();
        answers.add("");
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".answers", answers);
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".riddle", "");
        getFiles().saveData();
    }

}
