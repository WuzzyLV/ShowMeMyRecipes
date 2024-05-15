package me.wuzzyxy.showmemyrecipes;

import dev.lone.itemsadder.api.CustomStack;
import me.wuzzyxy.showmemyrecipes.commands.AllRecipeCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class ShowMeMyRecipes extends JavaPlugin {

    @Override
    public void onEnable() {
        new AllRecipeCommand(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
