package com.ney.neysub.Modules.Placeholders;

import com.ney.neysub.Modules.Data.SaveAndLoad;
import com.ney.neysub.Modules.Utils.MainUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderRegistrator extends PlaceholderExpansion {

    private final SaveAndLoad saveAndLoad;
    private final MainUtil mainUtil;

    public PlaceholderRegistrator(SaveAndLoad saveAndLoad, MainUtil mainUtil) {

        this.saveAndLoad = saveAndLoad;
        this.mainUtil = mainUtil;

    }

    @Override
    public String getIdentifier() { return "neysub"; }

    @Override
    public String getAuthor() { return "Ney"; }

    @Override
    public String getVersion() { return "1.0.0-BETA"; }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (!mainUtil.Enabled) return "";
        if (player == null) return "";

        if (identifier.equals("has"))
            return saveAndLoad.getDaysSincePurchase(player.getName()) != -1 ? mainUtil.getString("Placeholders.no_colored.yes_") : mainUtil.getString("Placeholders.no_colored.no_");

        else if (identifier.equals("has_colored"))
            return saveAndLoad.getDaysSincePurchase(player.getName()) != -1 ? mainUtil.getString("Placeholders.yes_") : mainUtil.getString("Placeholders.no_");

        return null;

    }
}