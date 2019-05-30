package me.blast.safecracker;

import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.files.Files;
import me.blast.safecracker.listeners.ChatEventListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    Files files = new Files();
    public Files getFiles(){return files;}

    ChatEventListener CEL = new ChatEventListener();
    public ChatEventListener getCEL(){return CEL;}

    @Override
    public void onEnable(){
        Commands commands = new Commands();
        getCommand("safecracker").setExecutor(commands);
        try{
            files.setup(this);
        } catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new NPCHandler(), this);
        Bukkit.getPluginManager().registerEvents(CEL, this);
    }

    @Override
    public void onDisable(){

    }

    public static String colorize(String string){
        return string.replaceAll("&", "§");
    }

    public void log(String string){
        Bukkit.getServer().getLogger().info(string);
    }

    public static Main instance;
    public Main(){
        if(instance == null){
            instance = this;
        }
    }
    public static Main getInstance(){
        if(instance == null){
            Bukkit.getServer().getLogger().info("Something dun fucked up.");
            return null;
        }
        return instance;
    }


}
