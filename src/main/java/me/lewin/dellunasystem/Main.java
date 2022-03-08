package me.lewin.dellunasystem;

import me.lewin.dellunasystem.commads.*;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.VillageDB;
import me.lewin.dellunasystem.event.*;
import me.lewin.dellunasystem.gui.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        mkdir();
        registerEvent();
        registerCommand();

        ChatDB.colorMap.put("델루나", "§x§f§b§d§2§a§7");
        for (String village : VillageDB.getVillages()) {
            ChatDB.colorMap.put(village, VillageDB.getColor(village));
        }
        Whitlist.findAll();

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(Reference.plugin, new TimerScheduler(), 0L, 0L);
    }

    // 기본적인 파일 구조 셋팅
    private void mkdir() {
        if (!Reference.plugin.getDataFolder().exists()) {
            Reference.plugin.getDataFolder().mkdir();
        }
    }

    // 이벤트 등록
    private void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PartyMainGui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat1Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat2Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat3Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat4Gui(), this);

        Bukkit.getPluginManager().registerEvents(new VillageMainGui(), this);
        Bukkit.getPluginManager().registerEvents(new VillageInviteGui(), this);
        Bukkit.getPluginManager().registerEvents(new VillageListGui(), this);
        Bukkit.getPluginManager().registerEvents(new VillageConfirmGui(), this);
        Bukkit.getPluginManager().registerEvents(new VillageRecordGui(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ServerReloadEvent(), this);
    }

    // 명령어 등록
    private void registerCommand() {
        Bukkit.getPluginCommand("c").setExecutor(new ChatCommand());
        Bukkit.getPluginCommand("dc").setExecutor(new PartyCommand());
        Bukkit.getPluginCommand("village").setExecutor(new VillageCommand());
        Bukkit.getPluginCommand("vacation").setExecutor(new VacationCommand());

        Bukkit.getPluginCommand("village").setTabCompleter(new TabCompleterCommand());
    }

    static class TimerScheduler implements Runnable {
        @Override
        public void run() {
            if (ChatDB.playerChatmodeMap != null) {
                for (Player p : ChatDB.playerChatmodeMap.keySet()) {
                    switch (ChatDB.playerChatmodeMap.get(p)) {
                        case 1:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\uF82C\uF82B\uF829\uF835ꢈ"));
                            break;
                        case 2:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\uF82C\uF82B\uF829\uF835ꢇ"));
                            break;
                        case 3:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\uF82C\uF82B\uF829\uF835ꢅ"));
                            break;
                    }
                }
            }
        }
    }
}
