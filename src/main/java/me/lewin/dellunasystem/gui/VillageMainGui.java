package me.lewin.dellunasystem.gui;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import me.lewin.dellunasystem.database.VillageDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VillageMainGui implements Listener {
    public static Inventory get(String village, Player player) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§f\uF808ꢑ");

        inv.setItem(1, homeIcon(village));
        inv.setItem(3, villageChatIcon());
        inv.setItem(5, recordIcon());
        inv.setItem(7, inviteIcon());

        ArrayList<String> member = VillageDB.getMember(village);

        int index = 18;
        for (String uuid : member) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(p);
            List<String> lore = new ArrayList<>();
            if (p.isOnline()) {
                meta.setDisplayName("§a" + p.getName());
                lore.add("§x§f§f§c§f§4§c접속 정보 : §aOnline");
            }
            else {
                meta.setDisplayName("§7" + p.getName());
                lore.add("§x§f§f§c§f§4§c접속 정보 : §7" + PlayerDB.getOnline(uuid));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(index++, item);
        }

        if (player.isOp() || PlayerDB.getHeader(player.getUniqueId().toString())) {
            inv.setItem(45, helpIcon());
        }
        inv.setItem(53, exitIcon());

        return inv;
    }

    private static ItemStack homeIcon(String village) {
        String name = VillageDB.getColor(village) + village;
        String header = "공석";
        if (!VillageDB.getHeader(village).equals("공석")) {
            header = Bukkit.getOfflinePlayer(UUID.fromString(VillageDB.getHeader(village))).getName();
        }
        String subheader = "공석";
        if (!VillageDB.getSubheader(village).equals("공석")) {
            subheader = Bukkit.getOfflinePlayer(UUID.fromString(VillageDB.getSubheader(village))).getName();
        }
        ArrayList<String> member = VillageDB.getMember(village);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§x§f§f§c§f§4§c이장 §f: " + header);
        lore.add("§x§f§f§c§f§4§c부이장 §f: " + subheader);
        lore.add("§x§f§f§c§f§4§c마을 인원 §f: (" + member.size() + "/27)");
        return Icon.set(Material.BONE, name, lore,1023);
    }
    private static ItemStack villageChatIcon() {

        return Icon.set(Material.BONE, "§x§f§f§c§f§4§c마을 채팅으로 전환",1023);
    }
    private static ItemStack recordIcon() {

        return Icon.set(Material.BONE, "§x§f§f§c§f§4§c마을 연혁 보기",1023);
    }
    private static ItemStack inviteIcon() {

        return Icon.set(Material.BONE, "§x§f§f§c§f§4§c마을 초대하기",1023);
    }
    private static ItemStack exitIcon() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§x§f§f§c§f§4§c쉬프트+좌클릭 §f: 마을 탈퇴");
        return Icon.set(Material.BONE, "§6마을 탈퇴", lore,1037);
    }
    private static ItemStack helpIcon() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§x§f§f§c§f§4§c쉬프트+좌클릭 §f: 이장 임명");
        lore.add("§x§f§f§c§f§4§c쉬프트+우클릭 §f: 부이장 임명");
        lore.add("§x§f§f§c§f§4§c휠클릭 §f: 추방");
        return Icon.set(Material.BONE, "§6도움말", lore,1038);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢑ")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory()) return;

            Player player = (Player) event.getWhoClicked();
            String village = event.getClickedInventory().getItem(1).getItemMeta().getDisplayName().replaceAll("§[a-zA-Z]", "").replaceAll("§[0-9]","");

            switch (event.getSlot()) {
                case 3:
                    player.sendMessage(Reference.channel_1);
                    ChatDB.playerChatmodeMap.put(player, 1);
                    player.closeInventory();
                    return;
                case 5:
                    player.openInventory(VillageRecordGui.get(village, 1));
                    return;
                case 7:
                    if (player.isOp() || PlayerDB.getHeader(player.getUniqueId().toString())) {
                        player.openInventory(VillageInviteGui.get(village, 1));
                    } else {
                        player.closeInventory();
                        player.sendMessage(Reference.FAIL + "이장이나 부이장이 아닙니다.");
                    }
                    return;
                case 53:
                    if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
                        player.openInventory(VillageConfirmGui.get(village, player.getUniqueId().toString(), "5"));
                    }
                    return;
            }

            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                String uuid = ((SkullMeta) event.getCurrentItem().getItemMeta()).getOwningPlayer().getUniqueId().toString();
                switch (event.getClick()) {
                    case SHIFT_LEFT:
                        if (player.isOp() || PlayerDB.getHeader(player.getUniqueId().toString())) {
                            player.openInventory(VillageConfirmGui.get(village, uuid, "1"));
                        }
                        return;
                    case SHIFT_RIGHT:
                        if (player.isOp() || PlayerDB.getHeader(player.getUniqueId().toString())) {
                            player.openInventory(VillageConfirmGui.get(village, uuid, "2"));
                        }
                        return;
                    case MIDDLE:
                        if (player.isOp() || PlayerDB.getHeader(player.getUniqueId().toString())) {
                            player.openInventory(VillageConfirmGui.get(village, uuid, "3"));
                        }
                        return;
                }
            }
        }
    }
}
