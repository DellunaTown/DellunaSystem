package me.lewin.dellunasystem.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Icon {
    /* 간편 GUI 아이콘 등록소스 */

    // 아이템만 반환
    public static ItemStack set(Material material) {
        return new ItemStack(material);
    }

    // "이름"이(가) 부여된 아이템 반환
    public static ItemStack set(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(displayName);

        item.setItemMeta(meta);
        return item;
    }

    // "이름", "로어"이(가) 부여된 아이템 반환
    public static ItemStack set(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    // "이름", "커스텀모델데이터"이(가) 부여된 아이템 반환
    public static ItemStack set(Material material, String displayName, int customModelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(displayName);
        meta.setCustomModelData(customModelData);

        item.setItemMeta(meta);
        return item;
    }

    // "이름", "로어", "커스텀모델데이터"이(가) 부여된 아이템 반환
    public static ItemStack set(Material material, String displayName, List<String> lore, int customModelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);

        item.setItemMeta(meta);
        return item;
    }
}
