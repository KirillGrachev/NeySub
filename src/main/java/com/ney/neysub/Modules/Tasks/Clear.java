package com.ney.neysub.Modules.Tasks;

import com.ney.neysub.Modules.Data.SaveAndLoad;
import com.ney.neysub.Modules.Utils.MainUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Clear extends BukkitRunnable {

    private final SaveAndLoad saveAndLoad = new SaveAndLoad();
    private final MainUtil mainUtil = new MainUtil();

    @Override
    public void run() {

        if (!mainUtil.Enabled) return;

        Bukkit.getOnlinePlayers().forEach(player -> {

            if (saveAndLoad.getDaysSincePurchase(player.getName()) >= mainUtil.getInteger("Settings.time"))
                saveAndLoad.removePlayerPurchaseDate(player.getName(), player);

        });
    }
}