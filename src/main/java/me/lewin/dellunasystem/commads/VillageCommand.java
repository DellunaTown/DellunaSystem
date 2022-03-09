package me.lewin.dellunasystem.commads;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import me.lewin.dellunasystem.database.VillageDB;
import me.lewin.dellunasystem.gui.VillageListGui;
import me.lewin.dellunasystem.gui.VillageMainGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class VillageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            onCommandNull(sender, label);
            return true;
        }

        switch (args[0]) {
            case "help":
            case "도움말":
                onCommandHelp(sender, label);
                break;

            case "info":
            case "정보":
                onCommandInfo(sender, args);
                break;

            case "accept":
            case "수락":
                onCommandAccept(sender, args);
                break;

            case "list":
            case "목록":
                onCommandList(sender, args);
                break;

            case "create":
            case "생성":
                onCommandCreate(sender, args);
                break;

            case "remove":
            case "제거":
                onCommandRemove(sender, args);
                break;

            case "color":
            case "색상":
                onCommandColor(sender, args);
                break;

            case "name":
            case "이름":
                onCommandName(sender, args);
                break;

            case "search":
            case "검색":
                onCommandSearch(sender, args);
                break;

            case "rcadd":
            case "기록추가":
                onCommandrcadd(sender, args);
                break;

            case "rcremove":
            case "기록삭제":
                onCommandrcremove(sender, args);
                break;

            case "test":
                System.out.println("playervillageMap : " + ChatDB.playerVillageMap);
                System.out.println("playerchatmodemap : " + ChatDB.playerChatmodeMap);
                System.out.println("playerpartymap : " + ChatDB.playerPartyMap);
                System.out.println("playerinvitemap : " + ChatDB.playerInviteMap);
                System.out.println("colormap : " + ChatDB.colorMap);
                System.out.println("villagemembermap : " + ChatDB.villageMemberMap);
                System.out.println("gmlist : " + ChatDB.GMList);
                System.out.println("party1 : " + ChatDB.party1);
                System.out.println("party2 : " + ChatDB.party2);
                System.out.println("party3 : " + ChatDB.party3);
                System.out.println("party4 : " + ChatDB.party4);
                break;

            default:
                onCommandNull(sender, label);
                break;
        }
        return true;
    }

    private void onCommandHelp(CommandSender sender, String label) {
        String[] arg;

        if (label.equals("마을")){
            arg = new String[]{"정보", "수락", "목록", "생성", "제거", "색상", "이름", "검색", "기록추가", "기록삭제"};
        }
        else {
            arg = new String[]{"info", "accept", "list", "create", "remove", "color", "name", "search", "rcadd", "rcremove"};
        }

        sender.sendMessage("");

        if (sender.isOp()) {
            sender.sendMessage("§6/" + label + " §7" + arg[0] + " [마을 이름] §f: 마을의 정보를 봅니다");
            sender.sendMessage("§6/" + label + " §7" + arg[1] + " §f: 마을의 초대를 수락합니다 (거절은 자동으로 이루어집니다)");
            sender.sendMessage("§6/" + label + " §7" + arg[7] + " [플레이어 이름] §f: 해당 플레이어의 소속 마을을 봅니다.");
            sender.sendMessage("§6/" + label + " §7" + arg[2] + " §f: 마을 목록을 확인합니다");
            sender.sendMessage("§6/" + label + " §7" + arg[3] + " [마을 이름] [이장 닉네임]§f: 마을을 생성합니다");
            sender.sendMessage("§6/" + label + " §7" + arg[4] + " [마을 이름] §f: 마을을 제거합니다");
            sender.sendMessage("§6/" + label + " §7" + arg[5] + " [마을 이름] [색상코드] §f: 마을의 색상을 변경합니다");
            sender.sendMessage("§6/" + label + " §7" + arg[6] + " [마을 이름] [바꿀 이름] §f: 마을의 이름을 변경합니다");
            sender.sendMessage("§6/" + label + " §7" + arg[8] + " [기록 이름] §f: (이장/부이장 권한) 마을 기록에 기록을 추가합니다.");
            sender.sendMessage("§6/" + label + " §7" + arg[9] + " §f: (이장/부이장 권한) 마을 기록에서 마지막 기록을 삭제합니다");
        }
        else {
            sender.sendMessage("§6/" + label + " §7" + arg[0] + " §f: 마을의 정보를 봅니다");
            sender.sendMessage("§6/" + label + " §7" + arg[1] + " §f: 마을의 초대를 수락합니다 (거절은 자동으로 이루어집니다)");
            sender.sendMessage("§6/" + label + " §7" + arg[7] + " [플레이어 이름] §f: 해당 플레이어의 소속 마을을 봅니다.");
            sender.sendMessage("§6/" + label + " §7" + arg[8] + " [기록 내용] §f: (이장/부이장 권한) 마을 기록에 기록을 추가합니다.");
            sender.sendMessage("§6/" + label + " §7" + arg[9] + " §f: (이장/부이장 권한) 마을 기록에서 마지막 기록을 삭제합니다");
        }

        sender.sendMessage("");
    }
    private void onCommandInfo(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String village = ChatDB.playerVillageMap.get(player);

            if (player.isOp() && args.length != 1) {
                if (!VillageDB.exists(args[1])) {
                    player.sendMessage(Reference.FAIL + "존재하지 않는 마을입니다.");
                }
                else {
                    player.openInventory(VillageMainGui.get(args[1], (Player) sender));
                }
            }
            else {
                if (village.equals("델루나")) {
                    player.sendMessage(Reference.FAIL + "소속된 마을이 없습니다.");
                }
                else {
                    player.openInventory(VillageMainGui.get(village, (Player) sender));
                }
            }
            return;
        }
    }
    private void onCommandAccept(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String uuid = player.getUniqueId().toString();

            if (args.length != 1) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (!PlayerDB.getVillage(uuid).equals("델루나")) {
                sender.sendMessage(Reference.FAIL + "이미 소속된 마을이 있습니다.");
                return;
            }
            if (!ChatDB.playerInviteMap.containsKey(player)) {
                sender.sendMessage(Reference.FAIL + "초대받은 마을이 없습니다.");
                return;
            }

            String village = ChatDB.playerInviteMap.get(player);

            /** 플레이어 파일 수정 **/
            PlayerDB.setVillage(uuid, village);

            VillageDB.addMember(village, uuid);

            ChatDB.playerVillageMap.put(player, village);

            /** 마을 멤버 지도 추가 **/
            ChatDB.addVillageMemberMap(village, player);

            sender.sendMessage(Reference.SUCCESS + village + " 마을에 성공적으로 가입하셨습니다.");

            String color = ChatDB.colorMap.get(village);
            String message = Reference.SUCCESS + color + player.getDisplayName() + " 님이 마을에 합류하셨습니다.";
            for (Player pp : ChatDB.villageMemberMap.get(village)) {
                pp.sendMessage(message);
            }

            ChatDB.playerInviteMap.remove(player);
        }
    }
    private void onCommandList(CommandSender sender, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            if (args.length == 1) {
                ((Player)sender).openInventory(VillageListGui.get(1));
            }
        }
    }
    private void onCommandCreate(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) return;
            if (args.length != 3) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (VillageDB.exists(args[1])) {
                sender.sendMessage(Reference.FAIL + "이미 존재하는 마을입니다.");
                return;
            }
            if (!PlayerDB.exists(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                sender.sendMessage(Reference.FAIL + "존재하지 않는 플레이어입니다.");
                return;
            }
            if (!PlayerDB.getVillage(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString()).equals("델루나")) {
                sender.sendMessage(Reference.FAIL + "이미 마을에 소속된 플레이어입니다.");
                return;
            }

            String village = args[1];
            String headerUUID = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();

            /** 마을 파일 생성 **/
            VillageDB.create(village, headerUUID);

            /** 플레이어 파일 수정 **/
            PlayerDB.setVillage(headerUUID, village);
            PlayerDB.setHeader(headerUUID, true);

            /** 마을 색 지도 추가 **/
            ChatDB.colorMap.put(village, "§6");

            if (Bukkit.getOfflinePlayer(UUID.fromString(headerUUID)).isOnline()) {
                Player player = (Player) Bukkit.getOfflinePlayer(UUID.fromString(headerUUID));

                /** 플레이어 마을 지도 추가 **/
                ChatDB.playerVillageMap.put(player, village);

                /** 마을 멤버 지도 추가 **/
                ChatDB.addVillageMemberMap(village, player);

                /** 마을 생성 메세지 **/
                player.sendMessage(Reference.SUCCESS + "§6" + village + "§a마을이 생성되었습니다.");
            }
            sender.sendMessage(Reference.SUCCESS + "§6" + village + "§a마을이 성공적으로 생성되었습니다.");
        }
    }
    private void onCommandRemove(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) return;
            if (args.length != 2) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (!VillageDB.exists(args[1])) {
                sender.sendMessage(Reference.FAIL + "존재하지 않는 마을입니다.");
                return;
            }

            String village = args[1];

            /** 플레이어 파일 수정 **/
            ArrayList<String> member = VillageDB.getMember(village);
            for (String uuid : member) {
                PlayerDB.setVillage(uuid, "델루나");
                PlayerDB.setHeader(uuid, false);
                if (PlayerDB.getChatmode(uuid) == 1) {
                    PlayerDB.setChatmode(uuid, 0);
                }
            }

            /** 마을 파일 삭제 **/
            VillageDB.removeVillageFile(village);

            /** 플레이어 채팅모드 및 마을 지도 수정 **/
            if (ChatDB.villageMemberMap.containsKey(village)) {
                ArrayList<Player> list = ChatDB.villageMemberMap.get(village);
                for (Player player : list) {
                    player.sendMessage(Reference.FAIL + ChatDB.colorMap.get(village) + village + " §c마을이 삭제되었습니다.");
                    ChatDB.playerVillageMap.put(player, "델루나");
                    if (ChatDB.playerChatmodeMap.get(player) == 1) {
                        ChatDB.playerChatmodeMap.put(player, 0);
                    }
                }
                /** 마을 멤버 지도 제거 **/
                ChatDB.villageMemberMap.remove(village);
            }

            /** 마을 색 지도 제거 **/
            ChatDB.colorMap.remove(village);

            sender.sendMessage(Reference.SUCCESS + "마을이 성공적으로 삭제되었습니다.");
        }
    }
    private void onCommandColor(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) return;
            if (args.length != 3) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (!VillageDB.exists(args[1])) {
                sender.sendMessage(Reference.FAIL + "존재하지 않는 마을입니다.");
                return;
            }
            if (args[2].split("").length != 6) {
                sender.sendMessage(Reference.FAIL + "잘못된 색상코드 입력입니다. 헥스코드 6자리를 입력해주세요.");
                return;
            }

            String village = args[1];
            String[] strArray = args[2].split("");
            String color = "§x§" + strArray[0] + "§" + strArray[1] + "§"+ strArray[2] + "§"+ strArray[3] + "§"+ strArray[4] + "§"+ strArray[5];

            /** 마을 색 지도 수정 **/
            ChatDB.colorMap.put(village, color);

            /** 마을 파일 수정 **/
            VillageDB.setColor(village, color);

            sender.sendMessage(Reference.SUCCESS + "마을 색상이 성공적으로 변경경되었습니다.");
        }
    }
    private void onCommandName(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) return;
            if (args.length != 3) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (!VillageDB.exists(args[1])) {
                sender.sendMessage(Reference.FAIL + "존재하지 않는 마을입니다.");
                return;
            }

            String village = args[1];
            String toVillage = args[2];

            /** 플레이어 파일 수정 **/
            ArrayList<String> member = VillageDB.getMember(village);
            for (String uuid : member) {
                PlayerDB.setVillage(uuid, toVillage);
            }

            /** 마을 파일 수정 **/
            VillageDB.setName(village, toVillage);

            /** 마을 색 지도 수정 **/
            ChatDB.colorMap.put(toVillage, ChatDB.colorMap.get(village));
            ChatDB.colorMap.remove(village);

            if (ChatDB.villageMemberMap.containsKey(village)) {
                /** 플레이어 채팅모드 및 마을 지도 수정 **/
                ArrayList<Player> list = ChatDB.villageMemberMap.get(village);
                ChatDB.villageMemberMap.remove(village);
                ChatDB.villageMemberMap.put(toVillage, list);
                for (Player player : list) {
                    ChatDB.playerVillageMap.put(player, toVillage);
                }
            }

            sender.sendMessage(Reference.SUCCESS + "마을 이름이 성공적으로 변경되었습니다.");
        }
    }
    private void onCommandSearch(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 2) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return;
            }
            if (Bukkit.getOfflinePlayer(args[1]) == null) {
                sender.sendMessage(Reference.FAIL + "플레이어를 찾을 수 없습니다.");
                return;
            }

            String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();
            if (PlayerDB.exists(uuid)) {
                String village = PlayerDB.getVillage(uuid);
                if (village.equals("델루나")) {
                    sender.sendMessage(Reference.SUCCESS + args[1] + "님은 소속된 마을이 없습니다.");
                }
                else {
                    sender.sendMessage(Reference.SUCCESS + args[1] + "님의 소속 마을은 " + village + "입니다.");
                }
                if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)).isOnline()) {
                    sender.sendMessage(Reference.SUCCESS + "접속 상태 : online");
                }
                else {
                    sender.sendMessage(Reference.SUCCESS + "접속 상태 : " + PlayerDB.getOnline(uuid));
                }
            }
            else {
                sender.sendMessage(Reference.FAIL + "플레이어를 찾을 수 없습니다.");
            }
            return;
        }
    }
    private void onCommandNull(CommandSender sender, String label) {
        if (label.equals("마을"))
            sender.sendMessage("§6/" + label + " 도움말");
        else
            sender.sendMessage("§6/" + label + " help");
    }
    private void onCommandrcadd(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String village = ChatDB.playerVillageMap.get(player);

            if (player.getItemInHand() == null) {
                player.sendMessage(Reference.FAIL + "설정할 아이템을 손에 들어주세요.");
                return;
            }

            ItemStack item = new ItemStack(player.getItemInHand().getType());
            ItemMeta meta = item.getItemMeta();
            String name = "";
            Integer count = 0;
            for (String string : args) {
                if (count++ == 0) continue;
                name = name + " " + string;
            }
            meta.setDisplayName(name);
            item.setItemMeta(meta);

            if (PlayerDB.getHeader(player.getUniqueId().toString())) {
                player.sendMessage(Reference.SUCCESS + "마을 기록이 추가되었습니다.");
                VillageDB.addRecord(village, item);
            }
            else {
                player.sendMessage(Reference.FAIL + "권한이 없습니다.");
            }
            return;
        }
    }
    private void onCommandrcremove(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String village = ChatDB.playerVillageMap.get(player);

            if (PlayerDB.getHeader(player.getUniqueId().toString())) {
                player.sendMessage(Reference.SUCCESS + "마을 기록이 제거되었습니다.");
                VillageDB.removeRecord(village);
            }
            else {
                player.sendMessage(Reference.FAIL + "권한이 없습니다.");
            }
            return;
        }
    }
}
