package me.lewin.dellunasystem.gui;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import me.lewin.dellunasystem.database.VillageDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class VillageConfirmGui implements Listener {
    public static Inventory get(String village, String uuid, String type) {
        Inventory inv = Bukkit.getServer().createInventory(null, 18, "§f\uF808ꢎ");

        inv.setItem(1, yesIcon());
        inv.setItem(2, yesIcon());
        inv.setItem(3, yesIcon());

        inv.setItem(5, noIcon());
        inv.setItem(6, noIcon());
        inv.setItem(7, noIcon());

        ItemStack parameter = new ItemStack(Material.BONE);
        ItemMeta parameterMeta = parameter.getItemMeta();
        parameterMeta.setCustomModelData(1023);
        parameterMeta.setDisplayName(village + "," + uuid + "," + type);
        parameter.setItemMeta(parameterMeta);
        inv.setItem(17, parameter);

        return inv;
    }

    private static ItemStack yesIcon() {

        return Icon.set(Material.BONE, "§a확인", 1023);
    }
    private static ItemStack noIcon() {

        return Icon.set(Material.BONE, "§c취소", 1023);
    }

    @EventHandler
    private void onconfirmInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢎ")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory()) return;

            Player player = (Player) event.getWhoClicked();
            String village = event.getClickedInventory().getItem(17).getItemMeta().getDisplayName().split(",")[0].replaceAll("§[a-zA-Z]", "").replaceAll("§[0-9]","");
            String uuid = event.getClickedInventory().getItem(17).getItemMeta().getDisplayName().split(",")[1];
            String type = event.getClickedInventory().getItem(17).getItemMeta().getDisplayName().split(",")[2];

            switch (event.getSlot()) {
                case 1:
                case 2:
                case 3:
                    switch (type) {
                        case "1": //이장
                            if (VillageDB.getHeader(village).equals(player.getUniqueId().toString()) || player.isOp()) {
                                if (VillageDB.getSubheader(village).equals(uuid)) {
                                    VillageDB.setSubheader(village, "공석");
                                }
                                PlayerDB.setHeader(VillageDB.getHeader(village), false);

                                VillageDB.setHeader(village, uuid);
                                PlayerDB.setHeader(uuid, true);

                                player.openInventory(VillageMainGui.get(village, player));
                            }
                            else {
                                player.sendMessage(Reference.FAIL + "권한이 없습니다.");
                                player.closeInventory();
                            }
                            return;

                        case "2": //부이장
                            if (VillageDB.getHeader(village).equals(uuid)) {
                                player.sendMessage(Reference.FAIL + "이장은 부이장으로 임명할 수 없습니다.");
                                player.closeInventory();
                                return;
                            }
                            if (!VillageDB.getSubheader(village).equals("공석")) {
                                PlayerDB.setHeader(VillageDB.getSubheader(village), false);
                            }
                            VillageDB.setSubheader(village, uuid);
                            PlayerDB.setHeader(uuid, true);

                            player.openInventory(VillageMainGui.get(village, player));
                            break;

                        case "3": //추방
                            if (VillageDB.getMember(village).size() == 1) {
                                PlayerDB.setVillage(uuid, "델루나");
                                PlayerDB.setHeader(uuid, false);
                                if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)).isOnline()) {
                                    ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), "델루나");
                                    ChatDB.removeVillageMemberMap(village, Bukkit.getPlayer(UUID.fromString(uuid)));
                                    if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(uuid))).equals(1)) {
                                        ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), 0);
                                    }
                                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.SUCCESS + "마을에서 추방당하셨습니다.");
                                }
                                VillageDB.removeVillageFile(village);
                                return;
                            }
                            else if (VillageDB.getMember(village).size() == 2) {
                                if (VillageDB.getHeader(village).equals(uuid) && !player.isOp()) {
                                    player.closeInventory();
                                    player.sendMessage(Reference.FAIL + "이장은 추방할 수 없습니다.");
                                    return;
                                }

                                for (String u : (ArrayList<String>) VillageDB.getMember(village)) {
                                    PlayerDB.setVillage(u, "델루나");
                                    PlayerDB.setHeader(u, false);

                                    if (Bukkit.getOfflinePlayer(UUID.fromString(u)).isOnline()) {
                                        ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(u)), "델루나");
                                        if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(u))).equals(1)) {
                                            ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(u)), 0);
                                        }
                                        Bukkit.getPlayer(UUID.fromString(u)).sendMessage(Reference.FAIL + "마을 최소 조건을 충족하지 못하여 마을이 삭제되었습니다.");
                                    }
                                }
                                ChatDB.colorMap.remove(village);
                                ChatDB.villageMemberMap.remove(village);
                                VillageDB.removeVillageFile(village);
                                return;
                            }
                            else {
                                VillageDB.removeMember(village, uuid);
                                if (PlayerDB.getHeader(uuid)) {
                                    if (VillageDB.getHeader(village).equals(uuid)) {
                                        if (player.isOp()) {
                                            if (VillageDB.getSubheader(village).equals("공석")) {
                                                VillageDB.setHeader(village, (String) VillageDB.getMember(village).get(0));
                                            }
                                            else {
                                                VillageDB.setHeader(village, VillageDB.getSubheader(village));
                                                VillageDB.setSubheader(village, "공석");
                                            }
                                        }
                                        else {
                                            player.closeInventory();
                                            player.sendMessage(Reference.FAIL + "이장은 추방할 수 없습니다.");
                                            return;
                                        }
                                    }
                                    else if (VillageDB.getSubheader(village).equals(uuid)) {
                                        VillageDB.setSubheader(village, "공석");
                                    }
                                }
                                PlayerDB.setVillage(uuid, "델루나");
                                PlayerDB.setHeader(uuid, false);
                                if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)).isOnline()) {
                                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.FAIL + "마을에서 추방당하셨습니다.");
                                    ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), "델루나");
                                    ChatDB.removeVillageMemberMap(village, Bukkit.getPlayer(UUID.fromString(uuid)));
                                    if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(uuid))).equals(1)) {
                                        ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), 0);
                                    }
                                }
                            }
                            player.openInventory(VillageMainGui.get(village, player));
                            break;

                        case "4":
                            if (VillageDB.getMember(village).size() >= 27) {
                                player.sendMessage(Reference.FAIL + "마을 인원이 가득 찼습니다.");
                                player.closeInventory();
                                return;
                            }
                            Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                            ChatDB.playerInviteMap.put(p, village);
                            p.sendMessage(Reference.SUCCESS + village + " 마을에서 초대장이 도착하였습니다. 마을에 가입하시려면 /village accept 혹은 /마을 수락 명령어를 입력해주세요.");
                            player.openInventory(VillageInviteGui.get(village, 1));
                            break;

                        case "5":
                            if (VillageDB.getMember(village).size() == 1) {
                                PlayerDB.setVillage(uuid, "델루나");
                                PlayerDB.setHeader(uuid, false);
                                if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)).isOnline()) {
                                    ChatDB.removeVillageMemberMap(village, Bukkit.getPlayer(UUID.fromString(uuid)));
                                    ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), "델루나");
                                    if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(uuid))).equals(1)) {
                                        ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), 0);
                                    }
                                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.FAIL + "마을에서 탈퇴하셨습니다.");
                                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.FAIL + "마을 최소 조건을 충족하지 못하여 마을이 삭제되었습니다.");
                                }
                                VillageDB.removeVillageFile(village);
                                return;
                            }
                            else if (VillageDB.getMember(village).size() == 2) {
                                for (String u : (ArrayList<String>) VillageDB.getMember(village)) {
                                    PlayerDB.setVillage(u, "델루나");
                                    PlayerDB.setHeader(u, false);

                                    if (Bukkit.getOfflinePlayer(UUID.fromString(u)).isOnline()) {
                                        ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(u)), "델루나");
                                        ChatDB.removeVillageMemberMap(village, Bukkit.getPlayer(UUID.fromString(uuid)));
                                        if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(u))).equals(1)) {
                                            ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(u)), 0);
                                        }
                                        Bukkit.getPlayer(UUID.fromString(u)).sendMessage(Reference.FAIL + "마을 최소 조건을 충족하지 못하여 마을이 삭제되었습니다.");
                                    }
                                }
                                Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.FAIL + "마을에서 탈퇴하셨습니다.");
                                ChatDB.colorMap.remove(village);
                                ChatDB.villageMemberMap.remove(village);
                                VillageDB.removeVillageFile(village);
                                return;
                            }
                            else {
                                VillageDB.removeMember(village, uuid);
                                if (PlayerDB.getHeader(uuid)) {
                                    if (VillageDB.getHeader(village).equals(uuid)) {
                                        if (VillageDB.getSubheader(village).equals("공석")) {
                                            VillageDB.setHeader(village, (String) VillageDB.getMember(village).get(0));
                                        }
                                        else {
                                            VillageDB.setHeader(village, VillageDB.getSubheader(village));
                                            VillageDB.setSubheader(village, "공석");
                                        }
                                    }
                                    else if (VillageDB.getSubheader(village).equals(uuid)) {
                                        VillageDB.setSubheader(village, "공석");
                                    }
                                }
                                PlayerDB.setVillage(uuid, "델루나");
                                PlayerDB.setHeader(uuid, false);
                                if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)).isOnline()) {
                                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Reference.FAIL + "마을에서 탈퇴하셨습니다.");
                                    ChatDB.playerVillageMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), "델루나");
                                    ChatDB.removeVillageMemberMap(village, Bukkit.getPlayer(UUID.fromString(uuid)));
                                    if (ChatDB.playerChatmodeMap.get(Bukkit.getPlayer(UUID.fromString(uuid))).equals(1)) {
                                        ChatDB.playerChatmodeMap.put(Bukkit.getPlayer(UUID.fromString(uuid)), 0);
                                    }
                                }
                            }
                            player.closeInventory();
                            break;
                    }
                    break;
                case 5:
                case 6:
                case 7:
                    switch (type) {
                        case "1": //이장
                        case "2": //부이장
                        case "3": //추방
                        case "5": //탈퇴
                            player.openInventory(VillageMainGui.get(village, player));
                            break;
                        case "4": //초대
                            player.openInventory(VillageInviteGui.get(village, 1));
                            break;
                    }
                    break;
            }
        }
    }
}
