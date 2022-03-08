package me.lewin.dellunasystem.event;

import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerReloadEvent implements Listener {
    @EventHandler
    private void onServerReloadEvent(ServerLoadEvent event)
    {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String uuid = player.getUniqueId().toString();

            ChatDB.playerVillageMap.put(player, PlayerDB.getVillage(uuid));
            ChatDB.addVillageMemberMap(PlayerDB.getVillage(uuid), player);
            ChatDB.playerChatmodeMap.put(player, PlayerDB.getChatmode(uuid));

            if (player.isOp()) {
                ChatDB.GMList.add(player);
            }
        }
    }
}
