package com.ney.neysub.Modules.Cooldown;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {

    private final Map<String, Long> cooldowns;

    public CooldownManager() { this.cooldowns = new HashMap<>(); }

    public boolean isOnCooldown(Player player, String command) {

        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(getCooldownKey(player, command))) {

            long cooldownTime = cooldowns.get(getCooldownKey(player, command));
            return currentTime < cooldownTime;

        }

        return false;
    }

    public void setCooldown(Player player, String command, int seconds) {

        long cooldownTime = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(getCooldownKey(player, command), cooldownTime);

    }

    public long getCooldownTime(Player player, String command) {

        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(getCooldownKey(player, command))) {

            long cooldownTime = cooldowns.get(getCooldownKey(player, command));
            return Math.max(0, cooldownTime - currentTime);

        }

        return 0;
    }

    private String getCooldownKey(Player player, String command) { return player.getName() + "-" + command; }

}