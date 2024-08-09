package me.wuzzyxy.showmemyrecipes;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import me.wuzzyxy.showmemyrecipes.recipes.CustomRecipe;
import me.wuzzyxy.showmemyrecipes.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;

import java.util.*;

public class RecipeManager {
    ShowMeMyRecipes plugin;
    HashMap<String, CustomRecipe> recipes = new LinkedHashMap<>();

    FileConfiguration recipeConfig;

    public RecipeManager(ShowMeMyRecipes plugin) {
        this.plugin = plugin;
        reloadConfig();

    }

    public void reloadConfig() {
        loadConfig();
        loadItems();
    }

    private void loadConfig(){
        File configFile = new File(plugin.getDataFolder(), "recipes.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("recipes.yml", false);
        }

        recipeConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadItems(){
        ConfigurationSection recipes = recipeConfig.getConfigurationSection("recipes.crafting_table");

        for (String key : recipes.getKeys(false)) {
            plugin.getLogger().severe(key);
            ConfigurationSection recipe = recipes.getConfigurationSection(key);
            String[][] pattern = new String[3][3];
            List<String> patternCfg = recipe.getStringList("pattern");
            plugin.getLogger().severe(patternCfg.toString());
            for (int i = 0; i < 3; i++) {
                pattern[i] = patternCfg.get(i).split("");
                // if is X then set to null
                for (int j = 0; j < 3; j++) {
                    if (pattern[i][j].equals("X")) {
                        pattern[i][j] = null;
                    }
                }
            }
            HashMap<String, ItemStack> ingredients = new HashMap<>();
            ConfigurationSection ingredientsSection = recipe.getConfigurationSection("ingredients");
            for (String ingredient : ingredientsSection.getKeys(false)) {
                String itemName = ingredientsSection.getString(ingredient);
                ingredients.put(ingredient, ItemUtils.getItemByName(itemName));
            }
            String resultItem = recipe.getString("result.item");
            int resultAmount = recipe.getInt("result.amount");
            String permission = recipe.getString("permission");
            ItemStack result = ItemUtils.getItemByName(resultItem, recipe.getString("result.name"), recipe.getStringList("result.lore"));
            result.setAmount(resultAmount);
            CustomRecipe customRecipe = new CustomRecipe(key, permission, pattern, ingredients, result);
            this.recipes.put(key, customRecipe);
            plugin.getLogger().info("Loaded recipe: " + key);
        }
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }
}
