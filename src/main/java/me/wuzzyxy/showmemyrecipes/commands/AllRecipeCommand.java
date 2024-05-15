package me.wuzzyxy.showmemyrecipes.commands;

import me.wuzzyxy.showmemyrecipes.RecipeManager;
import me.wuzzyxy.showmemyrecipes.ShowMeMyRecipes;
import me.wuzzyxy.showmemyrecipes.inventory.RecipeView;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;



public class AllRecipeCommand implements CommandExecutor {
    private final ShowMeMyRecipes plugin;
    private final RecipeManager recipeManager;

    public AllRecipeCommand(ShowMeMyRecipes plugin, RecipeManager recipeManager) {
        this.plugin = plugin;
        this.recipeManager = recipeManager;
        plugin.getCommand("recipes").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) commandSender;

        new RecipeView(plugin, recipeManager).openInventory(player);

        return false;
    }


}
