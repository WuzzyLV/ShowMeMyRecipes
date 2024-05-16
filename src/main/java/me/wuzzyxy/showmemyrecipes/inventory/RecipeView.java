package me.wuzzyxy.showmemyrecipes.inventory;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import me.wuzzyxy.showmemyrecipes.RecipeManager;
import me.wuzzyxy.showmemyrecipes.ShowMeMyRecipes;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecipeView implements Listener {
    private final ShowMeMyRecipes plugin;
    private final Inventory inv;
    private final TexturedInventoryWrapper wrapper;

    public RecipeView(ShowMeMyRecipes plugin, RecipeManager recipeManager) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.wrapper = new TexturedInventoryWrapper(null,
                54,
                ChatColor.BLACK + "Recipes ",
                new FontImageWrapper("_iainternal:crafting"),
                16,
                -8
        );

        inv = wrapper.getInternal();

        recipeManager.getRecipes().forEach((key, recipe) -> {
            recipe.getRecipe();

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ItemStack item = recipe.getRecipe()[i][j];
                    if (item == null) continue;
                    inv.setItem((i * 9 + j + 10) + 9, item);
                }
            }
            ItemStack result = recipe.getResult();
            inv.setItem(34, result);
        });

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() != inv) return;
        e.setCancelled(true);
    }

    public void openInventory(Player player) {
        wrapper.showInventory(player);
    }
}
