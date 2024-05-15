package me.wuzzyxy.showmemyrecipes.recipes;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CustomRecipe {
    private final String name;
    private final ItemStack[][] recipe;
    private final ItemStack result;

    public CustomRecipe(String name, String[][] shape, HashMap<String, ItemStack> ingredients, ItemStack result) {
        this.name = name;
        if (shape.length != 3) throw new IllegalArgumentException("Shape must be 3x3");
        if (shape[0].length != 3 || shape[1].length != 3 || shape[2].length != 3) throw new IllegalArgumentException("Shape must be 3x3");

        this.recipe = new ItemStack[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ingredient = shape[i][j];
                if (ingredient == null) continue;
                if (!ingredients.containsKey(ingredient)) throw new IllegalArgumentException("Ingredient " + ingredient + " not found");
                this.recipe[i][j] = ingredients.get(ingredient);
            }
        }
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public ItemStack[][] getRecipe() {
        return recipe;
    }

    public ItemStack getResult() {
        return result;
    }
}
