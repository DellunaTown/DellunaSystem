package me.lewin.dellunasystem.gui;

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

public class VillageRecordGui implements Listener {
    public static Inventory get(String village, Integer page) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§f\uF808ꢐ");

        ItemStack Item = new ItemStack(Material.BONE);
        ItemMeta Meta = Item.getItemMeta();
        Meta.setCustomModelData(1023);
        Meta.setDisplayName(village);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(page.toString());
        Meta.setLore(lore);
        Item.setItemMeta(Meta);
        inv.setItem(2, Item);

        int index = 9;
        int count = 0;
        for (ItemStack item : (ArrayList<ItemStack>) VillageDB.getRecord(village)) {
            if (count >= 36*(page-1) && count < 36*page) {
                inv.setItem(index++, item);
            }
            count += 1;
        }

        if (page == 1) {
            ItemStack Item2 = new ItemStack(Material.BONE);
            ItemMeta Meta2 = Item.getItemMeta();
            Meta2.setCustomModelData(1035);
            Meta2.setDisplayName("§7[ §x§9§3§8§d§9§4§l이전 §7]");
            Item2.setItemMeta(Meta2);
            inv.setItem(45, Item2);
        }
        else {
            ItemStack Item2 = new ItemStack(Material.BONE);
            ItemMeta Meta2 = Item.getItemMeta();
            Meta2.setCustomModelData(1034);
            Meta2.setDisplayName("§7[ §6§l이전 §7]");
            Item2.setItemMeta(Meta2);
            inv.setItem(45, Item2);
        }

        ItemStack Itemhome = new ItemStack(Material.BONE);
        ItemMeta Metahome = Item.getItemMeta();
        Metahome.setCustomModelData(1036);
        Metahome.setDisplayName("§6§l홈으로");
        Itemhome.setItemMeta(Metahome);
        inv.setItem(49, Itemhome);

        if (VillageDB.getRecord(village).size() > 36) {
            ItemStack Item3 = new ItemStack(Material.BONE);
            ItemMeta Meta3 = Item.getItemMeta();
            Meta3.setCustomModelData(1032);
            Meta3.setDisplayName("§7[ §6§l다음 §7]");
            Item3.setItemMeta(Meta3);
            inv.setItem(53, Item3);
        }
        else {
            ItemStack Item3 = new ItemStack(Material.BONE);
            ItemMeta Meta3 = Item.getItemMeta();
            Meta3.setCustomModelData(1033);
            Meta3.setDisplayName("§7[ §x§9§3§8§d§9§4§l다음 §7]");
            Item3.setItemMeta(Meta3);
            inv.setItem(53, Item3);
        }

        return inv;
    }
    @EventHandler
    private void onRecordInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢐ")) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory()) return;

            Player player = (Player) event.getWhoClicked();
            String village = event.getClickedInventory().getItem(2).getItemMeta().getDisplayName().replaceAll("§[a-zA-Z]", "").replaceAll("§[0-9]","");
            Integer page = Integer.parseInt(event.getClickedInventory().getItem(2).getItemMeta().getLore().get(0));

            switch (event.getSlot()) {
                case 45:
                    if (page > 1)
                        player.openInventory(get(village, page-1));
                    break;
                case 49:
                    player.openInventory(VillageMainGui.get(village, player));
                    break;
                case 53:
                    if (VillageDB.getRecord(village).size() > 36)
                        player.openInventory(get(village, page+1));
                    break;
            }
        }
    }
}