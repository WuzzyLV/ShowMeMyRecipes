package me.wuzzyxy.showmemyrecipes.utils;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ItemUtils {

    public static ItemStack getItemByName(String name) {
        ItemStack item = null;
        try {
            if (name.split(":")[0].equalsIgnoreCase("oraxen")) {
                var id = name.split(":")[1];
                item = OraxenItems.getItemById(id).build();
            }else {
                item = new ItemStack(
                        Objects.requireNonNull(Material.getMaterial(name))
                );
            }
        }catch (NullPointerException e){
            System.out.println("Item " + name + " not found");
            item = new ItemStack(Material.BARRIER);
        }
        return item;
    }

    public static ItemStack getItemByName(String id, String name, List<String> lore){
        ItemStack item = getItemByName(id);
        var meta = item.getItemMeta();
        if (name != null){
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        if (lore !=null){
            lore.replaceAll(s -> ChatColor.translateAlternateColorCodes('&', s));
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
}
