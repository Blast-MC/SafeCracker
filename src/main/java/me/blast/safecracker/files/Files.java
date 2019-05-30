package me.blast.safecracker.files;

import me.blast.safecracker.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Files {

    File cFile;
    FileConfiguration config;

    File dataFile;
    FileConfiguration dataConfig;

    public void setup(Plugin plugin) throws IOException, InvalidConfigurationException {
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
            File playerData = new File(plugin.getDataFolder() + File.separator + "playerData");
            playerData.mkdir();
        }
        cFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        config = new YamlConfiguration();
        dataFile = new File(plugin.getDataFolder() + File.separator + "data.yml");
        dataConfig = new YamlConfiguration();

        if(!cFile.exists()){
            cFile.createNewFile();
            config.save(cFile);
        }
        if(!dataFile.exists()){
            dataFile.createNewFile();
            dataConfig.save(dataFile);
        }

        config.load(cFile);
        dataConfig.load(dataFile);
    }

    public FileConfiguration configFile(){return config;}
    public FileConfiguration dataFile(){return dataConfig;}

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

    File playerFile;
    FileConfiguration playerConfig;

    public FileConfiguration playerFile(UUID uuid){
        String fileName = uuid.toString() + ".yml";
        playerFile = new File(Main.getInstance().getDataFolder() + File.separator + "playerData" + File.separator + fileName);
        if(!playerFile.exists()){
            Main.getInstance().log("That file is null; creating!");
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
        playerFile = new File(Main.getInstance().getDataFolder() + File.separator + "playerData" + File.separator + fileName);
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
