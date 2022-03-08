package me.lewin.dellunasystem.gui;

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

public class VillageInviteGui implements Listener {
    public static Inventory get(String village, Integer page) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§f\uF808ꢏ");
        inv.setItem(2, nameIcon(village, page));

        int index = 9;
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ChatDB.playerVillageMap.get(player).equals("델루나")) continue;

            if (count >= 36*(page-1) && count < 36*page) {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwningPlayer(player);
                meta.setDisplayName("§a" + player.getName());
                item.setItemMeta(meta);
                inv.setItem(index++, item);
            }

            count += 1;
        }

        if (page == 1) {
            inv.setItem(45, backIcon_off());
        }
        else {
            inv.setItem(45, backIcon_on());
        }

        inv.setItem(49, homeIcon());

        if (Bukkit.getOnlinePlayers().size() > page * 36) {
            inv.setItem(53, nextIcon_on());
        }
        else {
            inv.setItem(53, nextIcon_off());
        }
        return inv;
    }

    private static ItemStack nameIcon(String village, Integer page) {
        return Icon.set(Material.BONE, village + "," + page.toString(), 1023);
    }
    private static ItemStack backIcon_off() {

        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l이전 §7]", 1035);
    }
    private static ItemStack backIcon_on() {

        return Icon.set(Material.BONE, "§7[ §6§l이전 §7]", 1034);
    }
    private static ItemStack homeIcon() {

        return Icon.set(Material.BONE, "§6§l홈으로", 1036);
    }
    private static ItemStack nextIcon_off() {

        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l다음 §7]", 1033);
    }
    private static ItemStack nextIcon_on() {

        return Icon.set(Material.BONE, "§7[ §6§l다음 §7]", 1032);
    }

    @EventHandler
    private void onInviteInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢏ")) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory()) return;

            Player player = (Player) event.getWhoClicked();
            String itemName = event.getClickedInventory().getItem(2).getItemMeta().getDisplayName().replaceAll("§[a-zA-Z]", "").replaceAll("§[0-9]","");
            String village = itemName.split(",")[0];
            Integer page = Integer.parseInt(itemName.split(",")[1]);

            switch (event.getSlot()) {
                case 45:
                    if (page > 1)
                        player.openInventory(get(village, page-1));
                    break;
                case 49:
                    player.openInventory(VillageMainGui.get(village, player));
                    break;
                case 53:
                    if (Bukkit.getOnlinePlayers().size() > 36)
                        player.openInventory(get(village, page+1));
                    break;
            }

            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                    String uuid = ((SkullMeta) event.getCurrentItem().getItemMeta()).getOwningPlayer().getUniqueId().toString();
                    //player.openInventory(confirmInventory(village, uuid, "4"));
                }
            }
        }
    }
}
