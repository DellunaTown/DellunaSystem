package me.lewin.dellunasystem.event;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {
    @EventHandler
    private void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (ChatDB.playerChatmodeMap.get(player) == 2) {
            ChatDB.playerChatmodeMap.put(player, 0);
        }
        PlayerDB.setChatmode(uuid, ChatDB.playerChatmodeMap.get(player));

        if (ChatDB.playerInviteMap.containsKey(player)) {
            ChatDB.playerInviteMap.remove(player);
        }

        ChatDB.removeVillageMemberMap(ChatDB.playerVillageMap.get(player), player);
        ChatDB.playerVillageMap.remove(player);

        if (ChatDB.playerPartyMap.containsKey(player)) {
            switch (ChatDB.playerPartyMap.get(player)) {
                case 1:
                    ChatDB.party1.remove(player);
                    Reference.PartyLOG(player.getName() + " 퇴장", "조각달");
                    Reference.LOG.info("[조각달] " + player.getName() + " 퇴장");
                    String messageA = "§3[조각달] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 퇴장하셨습니다.";
                    for (Player p : ChatDB.party1) {
                        p.sendMessage(messageA);
                    }
                    break;
                case 2:
                    ChatDB.party2.remove(player);
                    Reference.PartyLOG(player.getName() + " 퇴장", "미리내");
                    Reference.LOG.info("[미리내] " + player.getName() + " 퇴장");
                    String messageB = "§3[미리내] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 퇴장하셨습니다.";
                    for (Player p : ChatDB.party2) {
                        p.sendMessage(messageB);
                    }
                    break;
                case 3:
                    ChatDB.party3.remove(player);
                    Reference.PartyLOG(player.getName() + " 퇴장", "여우별");
                    Reference.LOG.info("[여우별] " + player.getName() + " 퇴장");
                    String messageC = "§3[여우별] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 퇴장하셨습니다.";
                    for (Player p : ChatDB.party3) {
                        p.sendMessage(messageC);
                    }
                    break;
                case 4:
                    ChatDB.party4.remove(player);
                    Reference.PartyLOG(player.getName() + " 퇴장", "햇무리");
                    Reference.LOG.info("[햇무리] " + player.getName() + " 퇴장");
                    String messageD = "§3[햇무리] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 퇴장하셨습니다.";
                    for (Player p : ChatDB.party4) {
                        p.sendMessage(messageD);
                    }
                    break;
            }
            ChatDB.playerPartyMap.remove(player);
        }

        if (player.isOp()) {
            ChatDB.GMList.remove(player);
        }
    }
}
