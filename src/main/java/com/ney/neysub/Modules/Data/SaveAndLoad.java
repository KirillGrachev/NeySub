package com.ney.neysub.Modules.Data;

import com.ney.neysub.Modules.Utils.MainUtil;
import com.ney.neysub.NeySub;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ney.neysub.NeySub.playerPurchaseDates;
import static org.bukkit.Bukkit.getLogger;

public class SaveAndLoad {

    private final MainUtil mainUtil = new MainUtil();
    private final PrefixUpdater prefixUpdater = new PrefixUpdater();
    private final File dataFile = new File(mainUtil.getDataFolder(), "data.yml");
    private final FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    private final long currentTime = System.currentTimeMillis();

    public void initConfig() {

        if (!dataFile.exists()) {

            getLogger().warning("data.yml not found, creating...");

            try {

                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();

                try { dataConfig.save(dataFile);
                } catch (IOException e) { e.printStackTrace(); }

            } catch (IOException e) { e.printStackTrace(); }

        } else {

            loadPlayerPurchaseDates();

        }
    }

    public void savePlayerPurchaseDate(String playerName, Player player) {

        dataConfig.set("players." + playerName + ".date", getCurrentDate());
        if (mainUtil.Permissions) NeySub.getInstance().addPermissions(player, mainUtil.getStringListWithoutColor("Permissions.list"));
        if (mainUtil.Commands) NeySub.getInstance().addCommand(player, mainUtil.getStringListWithoutColor("Commands.list"));

        UserManager userManager = NeySub.getInstance().luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());
        prefixUpdater.updatePrefix(user);

        try { dataConfig.save(dataFile);
        } catch (IOException e) { e.printStackTrace(); }

    }

    public void removePlayerPurchaseDate(String playerName, Player player) {

        if (mainUtil.Permissions) NeySub.getInstance().removePermissions(player, mainUtil.getStringListWithoutColor("Permissions.list"));

        ConfigurationSection configurationSection = dataConfig.getConfigurationSection("players");
        if (configurationSection == null) return;

        if (configurationSection.contains(playerName)) {

            if (configurationSection.getKeys(false).size() > 1) dataConfig.set("players." + playerName, null);
            else dataConfig.set("players", null);

            playerPurchaseDates.remove(playerName);

            try { dataConfig.save(dataFile);
            } catch (IOException e) { e.printStackTrace(); }

        }
    }

    public String getPlayerPurchaseDate(String playerName) {

        Date purchaseDate = playerPurchaseDates.get(playerName);

        if (purchaseDate != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy, HH:mm");
            return sdf.format(purchaseDate);

        }

        return null;
    }

    public long getDaysSincePurchase(String playerName) {

        Date purchaseDate = playerPurchaseDates.get(playerName);

        if (purchaseDate != null) {

            long purchaseTime = purchaseDate.getTime();
            long timeDifference = currentTime - purchaseTime;

            return timeDifference / (24 * 60 * 60 * 1000);
        }

        return -1;
    }

    public void loadPlayerPurchaseDates() {

        if (dataConfig.isConfigurationSection("players")) {

            for (String playerName : dataConfig.getConfigurationSection("players").getKeys(false)) {

                String dateString = dataConfig.getString("players." + playerName + ".date");
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy, HH:mm");

                try {

                    Date purchaseDate = sdf.parse(dateString);
                    playerPurchaseDates.put(playerName, purchaseDate);

                } catch (ParseException e) { e.printStackTrace(); }
            }
        }
    }

    private String getCurrentDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy, HH:mm");
        return sdf.format(new Date());

    }

    private void saveConfig() {

        try { dataConfig.save(dataFile);
        } catch (IOException e) { e.printStackTrace(); }

    }
}