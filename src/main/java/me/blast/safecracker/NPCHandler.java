package me.blast.safecracker;

        import me.blast.safecracker.files.Files;
        import me.blast.safecracker.listeners.ChatEventListener;
        import net.citizensnpcs.api.CitizensAPI;
        import net.citizensnpcs.api.event.NPCRightClickEvent;
        import net.citizensnpcs.api.npc.NPC;
        import org.bukkit.Location;
        import org.bukkit.entity.EntityType;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;

        import java.util.ArrayList;

public class NPCHandler implements Listener {

    public Files getFiles(){
        return Main.getInstance().getFiles();
    }
    public ChatEventListener getCEL(){
        return Main.getInstance().getCEL();
    }

    public void create(String name, Location loc){
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        npc.spawn(loc);
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".id", npc.getId());
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".question", "");
        ArrayList<String> answers = new ArrayList<>();
        answers.add("");
        getFiles().dataFile().set(npc.getName().toLowerCase() + ".answers", answers);
        getFiles().saveData();
    }

    public void remove(int id){
        NPC npc = CitizensAPI.getNPCRegistry().getById(id);
        npc.despawn();
        getFiles().dataFile().set(npc.getName().toLowerCase(), null);
        getFiles().saveData();
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent event){
        int id = event.getNPC().getId();
        Player player = event.getClicker();
        for(String npc : getFiles().dataFile().getConfigurationSection("").getKeys(false)){
            if(id == (int) getFiles().dataFile().get(npc + ".id")){
                player.sendMessage("Question: " + getFiles().dataFile().get(npc + ".question"));
                player.sendMessage("Type your answer in chat, or type 'cancel' to exit.");
                getCEL().playerChatMap.put(event.getClicker().getUniqueId().toString(), "answers." + npc);
                break;
            }
        }
    }

}
