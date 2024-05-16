package me.wuzzyxy.showmemyrecipes;

import me.wuzzyxy.showmemyrecipes.commands.AllRecipeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShowMeMyRecipes extends JavaPlugin {

    @Override
    public void onEnable() {
        new AllRecipeCommand(this, new RecipeManager(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
