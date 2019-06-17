package me.blast.safecracker;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Tutorial extends BukkitRunnable {
    private Player player;
    private int i;
    public Tutorial(Player player, int i){
        this.i = i;
        this.player = player;
    }
    public void run(){
        player.sendMessage(SafeCracker.colorize("&e[+] " + strings[i]));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
        if (i == 6) {
            SafeCracker.getInstance().getCEL().tutorialMap.add(player.getUniqueId().toString());
            this.cancel();
        }
        if(i == strings.length -1){
            SafeCracker.getInstance().playersInTutorial.remove(player.getUniqueId().toString());
            this.cancel();
        }
        i++;
    }
    String[] strings = {
            "&3Welcome to the &eSafe Cracker &3tutorial!",
            "&3This will show you the basics of how to play the game.",
            "&3Safe Cracker is a &etrivia game &3that will test your knowledge about the server.",
            "&3You will start by searching for &eNPCs &3around the map, and answering the &equestions &3they will ask.",
            "&3When you find an NPC, &eright click &3it to reveal the question.",
            "&3Answering the questions is easy. All you have to do is &etype your answers in chat&3.",
            "&3To exit answering, you simply type '&ecancel&3' in chat. Go ahead! Try it out now by &etyping whatever you want in chat&3.",
            "&eGreat! &3You now know how to answer questions. Next, you will check your answers to gain clues towards the &efinal riddle&3.",
            "&3You can do this by using &c/safecracker check&3, but don't do it now.",
            "&eClues &3will be applied to the lore in the GUI, so check there frequently for &ehints towards the final answer&3.",
            "&3Once you feel like you have correctly solved the riddle, you can use &c/safecracker solve <answer> &3to solve.",
            "&3If you are correct, you will be able to claim your reward using &c/safecracker claim&3.",
            "&3Once you are ready, run &c/safecracker start &3to begin playing &eSafe Cracker&3! To access this tutorial at any time, use &c/safecracker tutorial&3."
    };
}
