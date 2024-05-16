package me.wuzzyxy.showmemyrecipes;

import dev.lone.itemsadder.api.CustomStack;
import me.wuzzyxy.showmemyrecipes.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RecipeManager {
    ShowMeMyRecipes plugin;
    HashMap<String, CustomRecipe> recipes = new HashMap<>();

    FileConfiguration recipeConfig;

    public RecipeManager(ShowMeMyRecipes plugin) {
        this.plugin = plugin;
        loadConfig();
        loadItems();
//        String tempName = "daggers:wooden_dagger";
//        CustomStack stack = CustomStack.getInstance(tempName);
//
//        CustomRecipe recipe = new CustomRecipe(
//                tempName,
//                new String[][]{
//                        {null, null, null},
//                        {null, "W", null},
//                        {null, "S", null}
//                },
//                new HashMap<>() {{
//                    put("W", new ItemStack(Material.OAK_PLANKS));
//                    put("S", new ItemStack(Material.STICK));
//                }},
//                stack.getItemStack()
//        );
//
//        recipes.put(tempName, recipe);


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
                ItemStack item;
                if (itemName.contains(":")) {
                    item = CustomStack.getInstance(itemName).getItemStack();
                } else {
                    item = new ItemStack(
                            Material.getMaterial(itemName)
                    );
                }
                ingredients.put(ingredient, new ItemStack(item));
            }
            String resultItem = recipe.getString("result.item");
            int resultAmount = recipe.getInt("result.amount");
            ItemStack result;
            if (resultItem.contains(":")) {
                CustomStack stack = CustomStack.getInstance(resultItem);
                result = stack.getItemStack();
            } else {
                result = new ItemStack(
                        Objects.requireNonNull(
                                Material.getMaterial(resultItem.toUpperCase())
                        ),
                        resultAmount
                );
            }
            CustomRecipe customRecipe = new CustomRecipe(key, pattern, ingredients, result);
            this.recipes.put(key, customRecipe);
            plugin.getLogger().info("Loaded recipe: " + key);
        }
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }
}
