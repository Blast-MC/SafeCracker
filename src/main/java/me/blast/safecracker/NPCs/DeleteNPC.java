package me.blast.safecracker.NPCs;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteNPC extends BukkitRunnable {

    NPC npc;
    public DeleteNPC(NPC npc){
        this.npc = npc;
    }

    @Override
    public void run() {
        npc.destroy();
        this.cancel();
    }
}
