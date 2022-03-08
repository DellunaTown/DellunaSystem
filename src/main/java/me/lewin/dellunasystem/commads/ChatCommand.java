package me.lewin.dellunasystem.commads;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String village = ChatDB.playerVillageMap.get(player);
            Integer chatmode = ChatDB.playerChatmodeMap.get(player);

            switch (chatmode) {
                case 0:
                    if (village.equals("델루나")) {
                        if (ChatDB.playerPartyMap.containsKey(player)) {
                            player.sendMessage(Reference.channel_2);
                            ChatDB.playerChatmodeMap.put(player, 2);
                        }
                        else {
                            if (player.isOp()) {
                                player.sendMessage(Reference.channel_3);
                                ChatDB.playerChatmodeMap.put(player, 3);
                            }
                            else {
                                player.sendMessage(Reference.FAIL + "소속된 마을이 없어 채팅모드를 변경할 수 없습니다");
                            }
                        }
                    }
                    else {
                        player.sendMessage(Reference.channel_1);
                        ChatDB.playerChatmodeMap.put(player, 1);
                    }
                    break;

                case 1:
                    if (ChatDB.playerPartyMap.containsKey(player)) {
                        player.sendMessage(Reference.channel_2);
                        ChatDB.playerChatmodeMap.put(player, 2);
                    }
                    else {
                        if (player.isOp()) {
                            player.sendMessage(Reference.channel_3);
                            ChatDB.playerChatmodeMap.put(player, 3);
                        }
                        else {
                            player.sendMessage(Reference.channel_0);
                            ChatDB.playerChatmodeMap.put(player, 0);
                            break;
                        }
                    }
                    break;

                case 2:
                    if (player.isOp()) {
                        player.sendMessage(Reference.channel_3);
                        ChatDB.playerChatmodeMap.put(player, 3);
                    }
                    else {
                        player.sendMessage(Reference.channel_0);
                        ChatDB.playerChatmodeMap.put(player, 0);
                        break;
                    }
                    break;

                case 3:
                    player.sendMessage(Reference.channel_0);
                    ChatDB.playerChatmodeMap.put(player, 0);
                    break;
            }
        }
        return true;
    }
}
