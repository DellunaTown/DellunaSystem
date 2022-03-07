package me.lewin.dellunasystem.database;

import me.lewin.dellunasystem.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class PlayerDB {
    public static void create(String uuid) {
        FileConfiguration config = getPlayerConfig(uuid);

        config.set("village", "델루나");
        config.set("header", false);
        config.set("chatmode", 0);
        config.set("online", LocalDate.now().toString());

        saveDataFile(config, getPlayerFile(uuid));
    }
    public static Boolean exists(String uuid) {

        return getPlayerFile(uuid).exists();
    }

    public static void setVillage(String uuid, String village) {
        FileConfiguration config = getPlayerConfig(uuid);
        config.set("village", village);
        saveDataFile(config, getPlayerFile(uuid));
    }
    public static void setHeader(String uuid, Boolean header) {
        FileConfiguration config = getPlayerConfig(uuid);
        config.set("header", header);
        saveDataFile(config, getPlayerFile(uuid));
    }
    public static void setChatmode(String uuid, Integer chatmode) {
        FileConfiguration config = getPlayerConfig(uuid);
        config.set("chatmode", chatmode);
        saveDataFile(config, getPlayerFile(uuid));
    }
    public static void setOnline(String uuid, String online) {
        FileConfiguration config = getPlayerConfig(uuid);
        config.set("online", online);
        saveDataFile(config, getPlayerFile(uuid));
    }

    public static String getVillage(String uuid) {
        FileConfiguration config = getPlayerConfig(uuid);
        return config.getString("village");
    }
    public static Boolean getHeader(String uuid) {
        FileConfiguration config = getPlayerConfig(uuid);
        return config.getBoolean("header");
    }
    public static Integer getChatmode(String uuid) {
        FileConfiguration config = getPlayerConfig(uuid);
        return config.getInt("chatmode");
    }
    public static String getOnline(String uuid) {
        FileConfiguration config = getPlayerConfig(uuid);
        return config.getString("online");
    }

    private static File getPlayerFile(String uuid) {

        return new File(Reference.plugin.getDataFolder() + "\\player", uuid + ".dat");
    }
    private static FileConfiguration getPlayerConfig(String uuid) {
        return YamlConfiguration.loadConfiguration(getPlayerFile(uuid));
    }
    private static void saveDataFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("§cFile I/O Error!!");
        }
    }
}
