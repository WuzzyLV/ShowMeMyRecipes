package me.wuzzyxy.showmemyrecipes;

public class PluginConfig {

    public PluginConfig(ShowMeMyRecipes plugin) {
        plugin.saveDefaultConfig();

        GUI_NAMESPACE_ID = plugin.getConfig().getString("gui.namespace_id", "_iainternal:blank_menu");
        GUI_TEXTURE_OFFSET = plugin.getConfig().getInt("gui.texture_offset", -8);
        GUI_LEFT_ARROW = plugin.getConfig().getString("gui.left_arrow.texture", "_iainternal:icon_left_blue");
        GUI_LEFT_ARROW_SLOT = plugin.getConfig().getInt("gui.left_arrow.slot", 0);
        GUI_RIGHT_ARROW = plugin.getConfig().getString("gui.right_arrow.texture", "_iainternal:icon_right_blue");
        GUI_RIGHT_ARROW_SLOT = plugin.getConfig().getInt("gui.right_arrow.slot", 8);
        GUI_INV_SIZE = plugin.getConfig().getInt("gui.inv_size", 54);
        GUI_TITLE = plugin.getConfig().getString("gui.title", "&8Recipe book");
        GUI_TITLE_OFFSET = plugin.getConfig().getInt("gui.title_offset", 16);
    }

    public final String GUI_NAMESPACE_ID;
    public final int GUI_TEXTURE_OFFSET;
    public final String GUI_LEFT_ARROW;
    public final int GUI_LEFT_ARROW_SLOT;
    public final String GUI_RIGHT_ARROW;
    public final int GUI_RIGHT_ARROW_SLOT;
    public final int GUI_INV_SIZE;
    public final String GUI_TITLE;
    public final int GUI_TITLE_OFFSET;
}
