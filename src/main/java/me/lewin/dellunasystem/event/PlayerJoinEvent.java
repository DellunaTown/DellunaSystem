package me.lewin.dellunasystem.event;

import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    private void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (!PlayerDB.exists(uuid)) {
            PlayerDB.create(uuid);
        }

        ChatDB.playerVillageMap.put(player, PlayerDB.getVillage(uuid));
        ChatDB.addVillageMemberMap(PlayerDB.getVillage(uuid), player);

        if (PlayerDB.getVillage(uuid).equals("델루나") && PlayerDB.getChatmode(uuid) == 1){
            PlayerDB.setChatmode(uuid, 0);
        }
        ChatDB.playerChatmodeMap.put(player, PlayerDB.getChatmode(uuid));

        if (player.isOp()) {
            ChatDB.GMList.add(player);
        }
    }
}
