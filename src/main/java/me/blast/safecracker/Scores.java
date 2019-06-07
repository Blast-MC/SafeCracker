package me.blast.safecracker;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Scores {

    public Scores(long score, Player player) {
        getFiles().playerFile(player.getUniqueId()).set("score", score);
        getFiles().savePlayerData(player.getUniqueId());
        ArrayList<String> scores = new ArrayList<>((ArrayList<String>) getFiles().scoreFile().get("scores"));
        for(int i = 0; i < scores.size(); i++){
            if(scores.get(i).equalsIgnoreCase("")){
                scores.set(i, player.getDisplayName() + ": " + score);
                break;
            }
            String[] split = scores.get(i).split("\\s");
            if(Long.parseLong(split[1]) > score){
                scores.add(i, player.getDisplayName() + ": "+score);
                break;
            }
        }
        getFiles().scoreFile().set("scores", scores);
        getFiles().saveScore();
        SafeCracker.getInstance().log(player.getDisplayName() + " has finished the Safe Cracker event with a score of " + score + "!");
    }

    public Files getFiles(){
        return SafeCracker.getInstance().getFiles();
    }

}
