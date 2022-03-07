package me.lewin.dellunasystem;

import me.lewin.dellunasystem.event.*;
import me.lewin.dellunasystem.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        mkdir();
        registerEvent();
        registerCommand();
    }

    // 기본적인 파일 구조 셋팅
    private void mkdir() {
        if (!Reference.plugin.getDataFolder().exists()) {
            Reference.plugin.getDataFolder().mkdir();
        }
        if (!Reference.plugin.getDataFolder().exists()) {
            new File(Reference.plugin.getDataFolder() + "\\village").mkdir();
        }
    }
    // 이벤트 등록
    private void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PartyMainGui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat1Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat2Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat3Gui(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChat4Gui(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvent(), this);
    }
    // 명령어 등록
    private void registerCommand() {

    }
}
