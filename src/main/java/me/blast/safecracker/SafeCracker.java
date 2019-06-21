package me.blast.safecracker;

import me.blast.safecracker.commands.Commands;
import me.blast.safecracker.listeners.ChatEventListener;
import me.blast.safecracker.listeners.DeleteNPCListener;
import me.blast.safecracker.listeners.NPCListener;
import me.blast.safecracker.listeners.RewardsGUIListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SafeCracker extends JavaPlugin {

    Files files = new Files();
    public Files getFiles(){return files;}

    ChatEventListener CEL = new ChatEventListener();
    public ChatEventListener getCEL(){return CEL;}

    public HashMap<String, String> adminEdit = new HashMap<>();

    public ArrayList<String> playersInTutorial = new ArrayList<>();

    @Override
    public void onEnable(){
        Commands commands = new Commands();
        getCommand("safecracker").setExecutor(commands);
        try{
            files.setup(this);
        } catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        if(files.configFile().get("currentEvent") != null){
            files.getCurrentEvent();
            try {
                files.setupEventFiles(this);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
        Bukkit.getPluginManager().registerEvents(CEL, this);
        Bukkit.getPluginManager().registerEvents(new RewardsGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteNPCListener(), this);
    }

    @Override
    public void onDisable(){
        if(getEvents().size() == 0){
            getFiles().configFile().set("currentEvent", null);
            getFiles().saveConfig();
        }
    }

    public static String colorize(String string){
        return string.replaceAll("&", "§");
    }

    public void log(String string){
        getLogger().info(string);
    }

    public static SafeCracker instance;
    public SafeCracker(){
        if(instance == null){
            instance = this;
        }
    }
    public static SafeCracker getInstance(){
        if(instance == null){
            Bukkit.getServer().getLogger().info("Something dun fucked up.");
            return null;
        }
        return instance;
    }

    public ArrayList<String> getEvents(){
        ArrayList<String> events = new ArrayList<>();
        for(String file : getDataFolder().list()){
            File temp = new File(SafeCracker.getInstance().getDataFolder() + File.separator + file);
            if(temp.isDirectory()){
                events.add(temp.getName());
            }
        }
        return events;
    }

    public String dateFormatter(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Date dateDeformatter(String date){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long timeSince(Date date){
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, localDateTime);
        return Math.abs(duration.getSeconds() - 1);
    }
}
