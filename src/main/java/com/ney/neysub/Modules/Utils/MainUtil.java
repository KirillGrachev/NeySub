package com.ney.neysub.Modules.Utils;

import com.ney.neysub.Modules.Data.SaveAndLoad;
import com.ney.neysub.NeySub;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.ney.neysub.Modules.Utils.Hex.color;

public class MainUtil {

    public boolean MessagesHelp = getBoolean("Messages.help.enabled");
    public boolean MessagesInfo = getBoolean("Messages.info.enabled");
    public boolean MessagesBuy = getBoolean("Messages.buy.enabled");
    public boolean MessagesTake = getBoolean("Messages.take.enabled");
    public boolean MessagesGive = getBoolean("Messages.give.enabled");
    public boolean MessagesDefault = getBoolean("Messages.default.enabled");
    public boolean MessageCooldown = getBoolean("Settings.cooldown.enabled");
    public boolean Enabled = getBoolean("Settings.enabled");
    public boolean Permissions = getBoolean("Permissions.enabled");
    public boolean Commands = getBoolean("Commands.enabled");

    public static List<BossBar> bossBars = new ArrayList<>();

    public double getDouble(String message) {
        return NeySub.getInstance().getConfig().getDouble(message);
    }

    public File getDataFolder () {
        return NeySub.getInstance().getDataFolder();
    }

    public String getString(String message) { return color(NeySub.getInstance().getConfig().getString(message)); }
   
    public String getStringWithoutColor(String message) { return NeySub.getInstance().getConfig().getString(message); }
   
    public int getInteger(String message) {
        return NeySub.getInstance().getConfig().getInt(message);
    }

    public Sound getSound(String message) {

        String soundName = NeySub.getInstance().getConfig().getString(message);
        if (soundName != null) try { return Sound.valueOf(soundName); } catch (IllegalArgumentException e) {e.printStackTrace(); }

        return null;

    }

    public boolean getBoolean(String message) {
        return NeySub.getInstance().getConfig().getBoolean(message);
    }

    public List<String> getStringList(String message) {

        return NeySub.getInstance().getConfig().getStringList(message)
                .stream()
                .map(Hex::color)
                .collect(Collectors.toList());

    }

    public List<String> getStringListWithoutColor(String message) { return NeySub.getInstance().getConfig().getStringList(message); }

    public void sendMessage(String messageText, Player player, Sound soundType, float volume, float pitch, boolean soundEnabled, String title, String subtitle, boolean titleEnable, int fadeIn, int stay, int fadeOut, String actionbarText, boolean actionbar, String bossbarText, BarColor barColor, BarStyle barStyle, int bossBarDuration, boolean bossBarEnable, int actionbarTime, SaveAndLoad saveAndLoad) {

        getStringList(messageText).stream()

                .map(message ->
                        message.replaceAll("\\{player_name\\}", player.getName())
                                .replaceAll("\\{time\\}", String.valueOf(getInteger("Settings.time") - saveAndLoad.getDaysSincePurchase(player.getName())))
                ).forEach(player::sendMessage);

        if (titleEnable) player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        if (actionbar) sendActionBar(player, actionbarText, actionbarTime);
        if (soundEnabled) player.playSound(player.getLocation(), soundType, volume, pitch);
        if (bossBarEnable) sendBossBar(player, bossbarText, barColor, barStyle, bossBarDuration);

    }

    private void sendActionBar(Player player, String message, int durationSeconds) {

        BukkitTask actionBarTask = new BukkitRunnable() {

            @Override
            public void run() { player.sendActionBar(message); }

        }.runTaskTimer(NeySub.getInstance(), 1L, 1L);

        new BukkitRunnable() {

            @Override
            public void run() { actionBarTask.cancel(); }

        }.runTaskLater(NeySub.getInstance(), durationSeconds * 20L);
    }

    private void sendBossBar(Player player, String bossbarText, BarColor barColor, BarStyle barStyle, int bossBarDuration) {

        BossBar bossBar = Bukkit.createBossBar(bossbarText, barColor, barStyle);
        bossBar.addPlayer(player);
        bossBars.add(bossBar);

        AtomicInteger bossBarTickCount = new AtomicInteger(bossBarDuration);
        double progressIncrement = 1.0 / bossBarDuration;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(NeySub.getInstance(), () -> {

            double progress = 1.0 - progressIncrement * bossBarTickCount.getAndDecrement();
            bossBar.setProgress(Math.max(0.0, Math.min(1.0, progress)));

            if (bossBarTickCount.get() < 0) {

                bossBar.removeAll();
                bossBars.remove(bossBar);

            }
        }, 0, 20);
    }
}