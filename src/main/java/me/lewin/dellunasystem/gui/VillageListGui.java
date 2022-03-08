package me.lewin.dellunasystem.gui;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.VillageDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class VillageListGui implements Listener {
    public static Inventory get(Integer page) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§fDelluna Village List");

        int count = 0;
        int index = 0;
        for (String village : VillageDB.getVillages()) {
            if (count >= 45*(page-1) && count < 45*page) {
                inv.setItem(index++, villageIcon(village));
            }
            count++;
        }

        if (page == 1) {
            inv.setItem(45, backIcon_off());
        }
        else {
            inv.setItem(45, backIcon_on());
        }

        inv.setItem(49, homeIcon(page));

        if (VillageDB.getVillages().size() > page * 45) {
            inv.setItem(53, nextIcon_on());
        }
        else {
            inv.setItem(53, nextIcon_off());
        }

        return inv;
    }

    private static ItemStack villageIcon(String village) {
        String name = VillageDB.getColor(village) + village;
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§x§f§f§c§f§4§c이장 §f: " + Bukkit.getOfflinePlayer(UUID.fromString(VillageDB.getHeader(village))).getName());
        lore.add("§x§f§f§c§f§4§c마을 인원 §f: (" + VillageDB.getMember(village).size() + "/27)");
        return Icon.set(Material.BRICKS, name, lore);
    }
    private static ItemStack backIcon_off() {

        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l이전 §7]", 1035);
    }
    private static ItemStack backIcon_on() {

        return Icon.set(Material.BONE, "§7[ §6§l이전 §7]", 1034);
    }
    private static ItemStack homeIcon(Integer page) {

        return Icon.set(Material.BONE, Integer.toString(page), 1036);
    }
    private static ItemStack nextIcon_off() {

        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l다음 §7]", 1033);
    }
    private static ItemStack nextIcon_on() {

        return Icon.set(Material.BONE, "§7[ §6§l다음 §7]", 1032);
    }

    @EventHandler
    private void onListInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§fDelluna Village List")) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory()) return;
            if (event.getCurrentItem() == null) return;

            Player player = (Player) event.getWhoClicked();
            Integer page = Integer.parseInt(event.getClickedInventory().getItem(49).getItemMeta().getDisplayName());

            switch (event.getSlot()) {
                case 45:
                    if (page != 1) {
                        player.openInventory(get(page-1));
                    }
                    return;
                case 53:
                    if (VillageDB.getVillages().size() > page * 45) {
                        player.openInventory(get(page+1));
                    }
                    return;
            }

            if (event.getCurrentItem().getType().equals(Material.BRICKS)) {
                player.openInventory(VillageMainGui.get(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§[a-zA-Z]", "").replaceAll("§[0-9]",""), player));
                return;
            }
        }
        return;
    }
}
