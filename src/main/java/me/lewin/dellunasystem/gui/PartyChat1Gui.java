package me.lewin.dellunasystem.gui;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PartyChat1Gui implements Listener {
    public static Inventory get() {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§f\uF808ꢌ");

        Integer index = 19;
        for (Player player : ChatDB.party1){

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(player);
            meta.setDisplayName("§a" + player.getName());
            item.setItemMeta(meta);

            inv.setItem(index, item);

            index++;
            if (index == 26) {
                index = 28;
            }
        }

        inv.setItem(46, yesIcon());
        inv.setItem(47, yesIcon());
        inv.setItem(48, yesIcon());

        inv.setItem(50, noIcon());
        inv.setItem(51, noIcon());
        inv.setItem(52, noIcon());

        return inv;
    }

    private static ItemStack yesIcon() {

        return Icon.set(Material.BONE, "§a§l입장", 1023);
    }
    private static ItemStack noIcon() {

        return Icon.set(Material.BONE, "§c§l퇴장", 1023);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢌ")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            switch (event.getSlot()) {
                case 46:
                case 47:
                case 48:
                    join(player);
                    player.closeInventory();
                    break;
                case 50:
                case 51:
                case 52:
                    leave(player);
                    player.closeInventory();
                    break;
            }
        }
    }

    private static void join(Player player) {
        if (ChatDB.playerPartyMap.containsKey(player)) {
            player.sendMessage(Reference.FAIL + "이미 접속 중인 채팅채널이 있습니다.");
            return;
        }

        if (ChatDB.party1.size() >= 14 && !player.isOp()) {
            player.sendMessage(Reference.FAIL + "조각달 채널이 가득 찼습니다.");
            return;
        }

        ChatDB.playerPartyMap.put(player, 1);
        ChatDB.playerChatmodeMap.put(player, 2);
        ChatDB.party1.add(player);
        player.sendMessage(Reference.SUCCESS + "조각달 채널에 접속하셨습니다.");

        Reference.PartyLOG(player.getName() + " 입장", "조각달");
        Reference.LOG.info("[조각달] " + player.getName() + " 입장");
        String message = "§3[조각달] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 입장하셨습니다.";
        for (Player p : ChatDB.party1) {
            if (p.equals(player)) continue;
            p.sendMessage(message);
        }
    }
    private static void leave(Player player) {
        if (!ChatDB.playerPartyMap.containsKey(player) || ChatDB.playerPartyMap.get(player) != 1) {
            player.sendMessage(Reference.FAIL + "조각달 채널에 접속중이지 않습니다.");
            return;
        }

        ChatDB.playerPartyMap.remove(player);
        ChatDB.playerChatmodeMap.put(player, 0);
        ChatDB.party1.remove(player);
        player.sendMessage(Reference.SUCCESS + "조각달 채널에서 퇴장하셨습니다.");

        Reference.PartyLOG(player.getName() + " 퇴장", "조각달");
        Reference.LOG.info("[조각달] " + player.getName() + " 퇴장");
        String message = "§3[조각달] §r" + player.getDisplayName() + " : §3 님이 파티채팅에 퇴장하셨습니다.";
        for (Player p : ChatDB.party1) {
            p.sendMessage(message);
        }
    }
}
