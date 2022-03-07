package me.lewin.dellunasystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PartyMainGui implements Listener {
    public static Inventory get() {
        Inventory inv = Bukkit.getServer().createInventory(null, 18, "§f\uF808ꢍ");

        inv.setItem(1, chat1Icon());
        inv.setItem(3, chat2Icon());
        inv.setItem(5, chat3Icon());
        inv.setItem(7, chat4Icon());

        return inv;
    }

    private static ItemStack chat1Icon() {
        return Icon.set(Material.BONE, "§9§l조각달 채널",1023);
    }
    private static ItemStack chat2Icon() {
        return Icon.set(Material.BONE, "§9§l미리내 채널",1023);
    }
    private static ItemStack chat3Icon() {
        return Icon.set(Material.BONE, "§9§l여우별 채널",1023);
    }
    private static ItemStack chat4Icon() {
        return Icon.set(Material.BONE, "§9§l햇무리 채널",1023);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§f\uF808ꢍ")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            switch (event.getSlot()) {
                case 1:
                    player.openInventory(PartyChat1Gui.get());
                    break;
                case 3:
                    player.openInventory(PartyChat2Gui.get());
                    break;
                case 5:
                    player.openInventory(PartyChat3Gui.get());
                    break;
                case 7:
                    player.openInventory(PartyChat4Gui.get());
                    break;
            }
        }
    }
}
