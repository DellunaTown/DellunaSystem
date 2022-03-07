package me.lewin.dellunasystem.database;

import me.lewin.dellunasystem.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhitelistDB {
    public static void create() {
        FileConfiguration config = getWhitelistSettingConfig();

        config.set("limit", 41);

        config.set("bool", false);

        ArrayList<String> list = new ArrayList<>();
        config.set("vacation", list);

        saveDataFile(config, getWhitelistSettingFile());
    }

    public static void setLimit(Integer limit) {
        FileConfiguration config = getWhitelistSettingConfig();
        config.set("limit", limit);
        saveDataFile(config, getWhitelistSettingFile());
    }
    public static void setBool(Boolean bool) {
        FileConfiguration config = getWhitelistSettingConfig();
        config.set("bool", bool);
        saveDataFile(config, getWhitelistSettingFile());
    }
    public static void addVacation(String uuid) {
        FileConfiguration config = getWhitelistSettingConfig();
        ArrayList<String> list = (ArrayList<String>) config.get("vacation");
        list.add(uuid);
        config.set("vacation", list);
        saveDataFile(config, getWhitelistSettingFile());
    }
    public static void removeVacation(String uuid) {
        FileConfiguration config = getWhitelistSettingConfig();
        ArrayList<String> list = (ArrayList<String>) config.get("vacation");
        list.remove(uuid);
        config.set("vacation", list);
        saveDataFile(config, getWhitelistSettingFile());
    }

    private static File getWhitelistSettingFile() {
        return new File(Reference.plugin.getDataFolder() + "\\whitelist", "config.dat");
    }
    private static FileConfiguration getWhitelistSettingConfig() {
        return YamlConfiguration.loadConfiguration(getWhitelistSettingFile());
    }
    private static void saveDataFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("§cFile I/O Error!!");
        }
    }
}
