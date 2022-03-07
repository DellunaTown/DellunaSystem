package me.lewin.dellunasystem.database;

import me.lewin.dellunasystem.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VillageDB {
    public static void create(String village, String headerUUID) {
        FileConfiguration config = getVillageConfig(village);

        ArrayList<ItemStack> record = new ArrayList<>();
        ArrayList<String> member = new ArrayList<>();
        member.add(headerUUID);

        config.set("color" , "§6");
        config.set("header", headerUUID);
        config.set("subheader", "공석");
        config.set("member", member);
        config.set("record", record);

        saveDataFile(config, getVillageFile(village));
    }

    public static void setColor(String village, String color) {
        FileConfiguration config = getVillageConfig(village);
        config.set("color" , color);
        saveDataFile(config, getVillageFile(village));
    }
    public static void setHeader(String village, String header) {
        FileConfiguration config = getVillageConfig(village);
        config.set("header" , header);
        saveDataFile(config, getVillageFile(village));
    }
    public static void setSubheader(String village, String subheader) {
        FileConfiguration config = getVillageConfig(village);
        config.set("subheader" , subheader);
        saveDataFile(config, getVillageFile(village));
    }
    public static void addMember(String village, String player) {
        FileConfiguration config = getVillageConfig(village);
        ArrayList<String> list = (ArrayList<String>) config.get("member");
        list.add(player);
        config.set("member" , list);
        saveDataFile(config, getVillageFile(village));
    }
    public static void removeMember(String village, String player) {
        FileConfiguration config = getVillageConfig(village);
        ArrayList<String> list = (ArrayList<String>) config.get("member");
        list.remove(player);
        config.set("member" , list);
        saveDataFile(config, getVillageFile(village));
    }
    public static void addRecord(String village, String record) {
        FileConfiguration config = getVillageConfig(village);
        ArrayList<String> list = (ArrayList<String>) config.get("member");
        list.add(record);
        config.set("record" , list);
        saveDataFile(config, getVillageFile(village));
    }
    public static void removeRecord(String village, Integer index) {
        FileConfiguration config = getVillageConfig(village);
        ArrayList<String> list = (ArrayList<String>) config.get("member");
        list.remove(index);
        config.set("record" , list);
        saveDataFile(config, getVillageFile(village));
    }
    public static void setName(String village, String toName) {
        FileConfiguration config = getVillageConfig(village);
        removeVillageFile(village);
        saveDataFile(config, getVillageFile(toName));
    }

    public static String getColor(String village, String color) {
        FileConfiguration config = getVillageConfig(village);
        return config.getString("color" , color);
    }
    public static String getHeader(String village, String header) {
        FileConfiguration config = getVillageConfig(village);
        return config.getString("header");
    }
    public static String getSubheader(String village, String subheader) {
        FileConfiguration config = getVillageConfig(village);
        return config.getString("subheader");
    }
    public static ArrayList getMember(String village, String player) {
        FileConfiguration config = getVillageConfig(village);
        return (ArrayList) config.get("member");
    }
    public static ArrayList getRecord(String village, String record) {
        FileConfiguration config = getVillageConfig(village);
        return (ArrayList) config.get("record");
    }

    public static void removeVillageFile(String villageName) {
        File file = getVillageFile(villageName);
        file.delete();
    }
    public static File[] getFiles() {

        return new File(Reference.plugin.getDataFolder() + "\\village").listFiles();
    }

    private static File getVillageFile(String villageName) {
        return new File(Reference.plugin.getDataFolder() + "\\village", villageName + ".dat");
    }
    private static FileConfiguration getVillageConfig(String villageName) {
        return YamlConfiguration.loadConfiguration(getVillageFile(villageName));
    }
    private static void saveDataFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("§cFile I/O Error!!");
        }
    }
}
