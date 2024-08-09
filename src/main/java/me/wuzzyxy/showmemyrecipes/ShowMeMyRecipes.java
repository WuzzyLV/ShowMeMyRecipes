package me.wuzzyxy.showmemyrecipes;

import me.wuzzyxy.showmemyrecipes.commands.AllRecipeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShowMeMyRecipes extends JavaPlugin {

    private PluginConfig config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new PluginConfig(this);
        new AllRecipeCommand(this, new RecipeManager(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public PluginConfig getPluginConfig() {
        return config;
    }

    public void reloadPluginConfig() {
        config = new PluginConfig(this);
    }
}
