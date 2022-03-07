package me.lewin.dellunasystem;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Reference {
    public static final Plugin plugin = JavaPlugin.getPlugin(Main.class);

    public static final String SUCCESS = "§7[§a ! §7] §a";
    public static final String FAIL = "§7[§c ! §7] §c";

    public static final String channel_0 = "§e 채팅모드 §f: 전체";
    public static final String channel_1 = "§e 채팅모드 §f: §b마을";
    public static final String channel_2 = "§e 채팅모드 §f: §x§6§6§A§E§D§B파티";
    public static final String channel_3 = "§e 채팅모드 §f: §dGM";

    public static Logger LOG = Logger.getLogger("Minecraft");
    public static void VillageLOG(String message, String village) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String path = plugin.getDataFolder().getPath() + "\\village_chat_Log" + "\\" + village;
        String date = form.format(new Date(System.currentTimeMillis()));

        File file = new File(path, date + ".log");

        if (!file.exists()) {
            file.mkdirs();
            if (!(file.isFile()))
                file.delete();
        }
        Villagewrite(message, file);
    }
    public static void Villagewrite(String message, File file) {
        try {
            SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
            PrintWriter w = new PrintWriter(new FileWriter(file, true));

            w.write("[" + form.format(new Date()) + "] " + message);
            w.println();
            w.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public static void PartyLOG(String message, String channel) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String path = plugin.getDataFolder().getPath() + "\\party_chat_Log" + "\\" + channel;
        String date = form.format(new Date(System.currentTimeMillis()));

        File file = new File(path, date + ".log");

        if (!file.exists()) {
            file.mkdirs();
            if (!(file.isFile()))
                file.delete();
        }

        Partywrite(message, file);
    }
    public static void Partywrite(String message, File file) {
        try {
            SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
            PrintWriter w = new PrintWriter(new FileWriter(file, true));

            w.write("[" + form.format(new Date()) + "] " + message);
            w.println();
            w.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
