package me.wuzzyxy.showmemyrecipes.inventory;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import me.wuzzyxy.showmemyrecipes.PluginConfig;
import me.wuzzyxy.showmemyrecipes.RecipeManager;
import me.wuzzyxy.showmemyrecipes.ShowMeMyRecipes;
import me.wuzzyxy.showmemyrecipes.recipes.CustomRecipe;
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

import java.util.ArrayList;
import java.util.List;

public class RecipeView implements Listener {
    private final ShowMeMyRecipes plugin;
    private final PluginConfig config;
    private final RecipeManager recipeManager;
    private Inventory inv;
    private TexturedInventoryWrapper wrapper;
    private final List<String> availableItems;
    private int currentItem = 0;

    public RecipeView(ShowMeMyRecipes plugin, RecipeManager recipeManager, Player owner) {
        this.plugin = plugin;
        config = plugin.getPluginConfig();
        this.recipeManager = recipeManager;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        availableItems = new ArrayList<>();
        recipeManager.getRecipes().forEach((key, recipe) -> {
            if (owner.hasPermission(recipe.getPermission())) {
                availableItems.add(key);
            }
        });
        initInv();
        displayItem(currentItem);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() != inv) return;
        e.setCancelled(true);

        if (e.getSlot() == config.GUI_LEFT_ARROW_SLOT){
            currentItem--;
            if (currentItem < 0) currentItem = availableItems.size() - 1;
        } else if (e.getSlot() == config.GUI_RIGHT_ARROW_SLOT){
            currentItem++;
            if (currentItem >= availableItems.size()) currentItem = 0;
        } else if (e.getSlot() == config.GUI_CLOSE_SLOT){
            e.getWhoClicked().closeInventory();
            return;
        } else {
            return;
        }
        displayItem(currentItem);
    }

    public void openInventory(Player player) {
        wrapper.showInventory(player);
    }

    private void displayItem(int index) {
        if (index < 0 || index >= availableItems.size()) return;

        CustomRecipe recipe = recipeManager.getRecipes().get(availableItems.get(index));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ItemStack item = recipe.getRecipe()[i][j];
                if (item == null) inv.setItem((i * 9 + j + 10) + 9, null);
                inv.setItem((i * 9 + j + 10), item);
            }
        }
        ItemStack result = recipe.getResult();
        inv.setItem(25, result);
    }

    public void initInv(){
        wrapper = new TexturedInventoryWrapper(null,
                config.GUI_INV_SIZE,
                ChatColor.translateAlternateColorCodes('&', config.GUI_TITLE),
                new FontImageWrapper(config.GUI_NAMESPACE_ID),
                config.GUI_TITLE_OFFSET,
                config.GUI_TEXTURE_OFFSET
        );
        inv = wrapper.getInternal();

        inv.setItem(config.GUI_LEFT_ARROW_SLOT, CustomStack.getInstance(config.GUI_LEFT_ARROW).getItemStack());
        inv.setItem(config.GUI_RIGHT_ARROW_SLOT, CustomStack.getInstance(config.GUI_RIGHT_ARROW).getItemStack());
        inv.setItem(config.GUI_CLOSE_SLOT, CustomStack.getInstance(config.GUI_CLOSE_TEXTURE).getItemStack());
    }
}
