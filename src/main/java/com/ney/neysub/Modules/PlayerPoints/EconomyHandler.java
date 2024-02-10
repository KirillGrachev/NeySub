package com.ney.neysub.Modules.PlayerPoints;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.entity.Player;

public class EconomyHandler {

    public boolean hasEnoughPoints(Player player, int amount) { return PlayerPoints.getInstance().getAPI().look(player.getUniqueId()) > amount; }

    public boolean withdrawPoints(Player player, int amount) {

        if (hasEnoughPoints(player, amount)) {

            PlayerPoints.getInstance().getAPI().take(player.getUniqueId(), amount);
            return true;

        }

        return false;
    }
}