package me.blast.safecracker;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Files {

    File cFile;
    FileConfiguration config;

    File dataFile;
    FileConfiguration dataConfig;

    File scoreFile;
    FileConfiguration scoreConfig;

    public void setup(Plugin plugin) throws IOException, InvalidConfigurationException {
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        cFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        config = new YamlConfiguration();
        if(!cFile.exists()){
            cFile.createNewFile();
            config.save(cFile);
        }

        config.load(cFile);
        if(config.get("tutorialNPCs") == null){
            ArrayList<String> tutorialNPCs = new ArrayList<>();
            tutorialNPCs.add("");
            config.set("tutorialNPCs", tutorialNPCs);
            config.save(cFile);
        }
    }

    String currentEvent;
    public void getCurrentEvent(){
        currentEvent = (String) configFile().get("currentEvent");
    }

    public void changeCurrentEvent(String eventName){
        currentEvent = eventName;
        configFile().set("currentEvent", eventName);
        saveConfig();
        try {
            setupEventFiles(SafeCracker.getInstance());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setupEventFiles(Plugin plugin) throws IOException, InvalidConfigurationException {
        File eventFolder = new File(plugin.getDataFolder() + File.separator + currentEvent);
        eventFolder.mkdir();

        File playerData = new File(plugin.getDataFolder() + File.separator + currentEvent + File.separator + "playerData");
        playerData.mkdir();

        dataFile = new File(plugin.getDataFolder() + File.separator + currentEvent + File.separator  + "data.yml");
        dataConfig = new YamlConfiguration();
        if(!dataFile.exists()){
            dataFile.createNewFile();
            dataConfig.save(dataFile);
        }
        dataConfig.load(dataFile);
        if(dataConfig.get("created") == null){
            dataConfig.set("created", SafeCracker.getInstance().dateFormatter());
            dataConfig.save(dataFile);
        }
        if(dataConfig.get("riddle-answer") == null){
            dataConfig.set("riddle-answer", "");
            dataConfig.save(dataFile);
        }
        if(dataConfig.get("commands-upon-solve") == null){
            ArrayList<String> commands = new ArrayList<>();
            commands.add("");
            dataConfig.set("commands-upon-solve", commands);
            dataConfig.save(dataFile);
        }
        if(dataConfig.get("main-region") == null){
            dataConfig.set("main-region", "");
            dataConfig.save(dataFile);
        }

        scoreFile = new File(plugin.getDataFolder() + File.separator + currentEvent + File.separator + "scores.yml");
        scoreConfig = new YamlConfiguration();
        if(!scoreFile.exists()){
            scoreFile.createNewFile();
            scoreConfig.save(scoreFile);
        }
        scoreConfig.load(scoreFile);
        if(scoreConfig.get("scores") == null){
            ArrayList<String> scores = new ArrayList<>();
            scores.add("");
            scoreConfig.set("scores", scores);
            scoreConfig.save(scoreFile);
        }
    }

    public FileConfiguration configFile(){return config;}
    public FileConfiguration dataFile(){return dataConfig;}
    public FileConfiguration scoreFile(){return scoreConfig;}

    public FileConfiguration tempDataFile(String event){
        File tempDataFile = new File(SafeCracker.getInstance().getDataFolder() + File.separator + event + File.separator + "data.yml");
        FileConfiguration tempDataConfig = new YamlConfiguration();
        try {
            tempDataConfig.load(tempDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return tempDataConfig;
    }

    public void saveConfig(){
        try{
            config.save(cFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveData(){
        try{
            dataConfig.save(dataFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveScore(){
        try {
            scoreConfig.save(scoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    File playerFile;
    FileConfiguration playerConfig;

    public FileConfiguration playerFile(UUID uuid){
        String fileName = uuid.toString() + ".yml";
        playerFile = new File(SafeCracker.getInstance().getDataFolder() + File.separator + currentEvent + File.separator + "playerData" + File.separator + fileName);
        if(!playerFile.exists()){
            try{
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playerConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return playerConfig;
    }

    public void savePlayerData(UUID uuid){
        String fileName = uuid.toString() + ".yml";
        playerFile = new File(SafeCracker.getInstance().getDataFolder() + File.separator + currentEvent + File.separator + "playerData" + File.separator + fileName);
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
