package me.lewin.dellunasystem.event;

import github.scarsz.discordsrv.DiscordSRV;
import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatEvent implements Listener {
    @EventHandler
    private void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        String village = ChatDB.playerVillageMap.get(player);
        Integer chatMode = ChatDB.playerChatmodeMap.get(player);

        switch (chatMode) {
            case 0: // 전체채팅
                String colorA = ChatDB.colorMap.get(village);
                String format = "§7[" + colorA + village + "§7] §f" + player.getName() + " : §r";

                try { DiscordSRV.getPlugin().getMainTextChannel().sendMessage("[" + village + "] " + player.getName() + " : " + message).queue(); }
                catch (NullPointerException exception) { System.out.println("'DiscordSRV' 에 문제가 발견되었습니다"); }

                event.setFormat(format + "§r%2$s");
                break;

            case 1: // 마을채팅
                event.setCancelled(true);

                String colorB = ChatDB.colorMap.get(village);
                String messageB = colorB + "<마을채팅> §r" + player.getDisplayName() + " : " + colorB + message;

                Reference.VillageLOG("<" + player.getName() + "> " + message, village);
                Reference.LOG.info("[VillageChat] <" + player.getName() + "> " + message);

                for (Player pp : ChatDB.villageMemberMap.get(village)) {
                    pp.sendMessage(messageB);
                }
                break;

            case 2:
                event.setCancelled(true);
                String party = "";

                ArrayList<Player> list = new ArrayList<>();
                switch (ChatDB.playerPartyMap.get(player)) {
                    case 1:
                        list = ChatDB.party1;
                        party = "조각달";
                        break;
                    case 2:
                        list = ChatDB.party2;
                        party = "미리내";
                        break;
                    case 3:
                        list = ChatDB.party3;
                        party = "여우별";
                        break;
                    case 4:
                        list = ChatDB.party4;
                        party = "햇무리";
                        break;
                }

                Reference.PartyLOG("<" + player.getName() + "> " + message, party);
                Reference.LOG.info("[" +party+ "] <" + player.getName() + "> " + message);
                String messageP = "§3[" +party+ "] §r" + player.getDisplayName() + " : §3" + message;

                for (Player pp : list) {
                    pp.sendMessage(messageP);
                }
                if (ChatDB.GMPList.size() != 0) {
                    for (Player ppp : ChatDB.GMPList) {
                        ppp.sendMessage(Reference.SUCCESS + messageP);
                    }
                }
                return;

            case 3: // 운영진 채팅
                event.setCancelled(true);

                String messageC = "§d<GM> §r" + player.getDisplayName() + " : §d" + message.replaceAll("&", "§");

                Reference.LOG.info("[GM] <" + player.getName() + "> " + message);

                for (Player p : ChatDB.GMList) {
                    if (p.isOp())
                        p.sendMessage(messageC);
                }
                break;
        }
    }
}
