package com.ney.neysub;

import com.ney.neysub.Modules.Commands.Commands;
import com.ney.neysub.Modules.Commands.TabComplete;
import com.ney.neysub.Modules.Data.SaveAndLoad;
import com.ney.neysub.Modules.Placeholders.PlaceholderRegistrator;
import com.ney.neysub.Modules.Tasks.Clear;
import com.ney.neysub.Modules.Utils.MainUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ney.neysub.Modules.Utils.MainUtil.*;

public final class NeySub extends JavaPlugin {

    private static NeySub instance;
    public final static Map<String, Date> playerPurchaseDates = new HashMap<>();
    public LuckPerms luckPerms;


    @Override
    public void onEnable() {

        instance = this;
        luckPerms = LuckPermsProvider.get();

        saveDefaultConfig();

        Clear clear = new Clear();
        SaveAndLoad saveAndLoad = new SaveAndLoad();
        MainUtil mainUtil = new MainUtil();

        clear.runTaskTimerAsynchronously(NeySub.getInstance(), 0, 60);
        saveAndLoad.initConfig();
        saveAndLoad.loadPlayerPurchaseDates();

        new PlaceholderRegistrator(saveAndLoad, mainUtil).register();

        Commands commands = new Commands();
        String command = "neysub";

        getCommand(command).setExecutor(commands);
        getCommand(command).setTabCompleter(new TabComplete());

    }

    @Override
    public void onDisable() { bossBars.forEach(BossBar::removeAll); }

    public static NeySub getInstance() { return NeySub.instance; }

    public void addPermissions(Player player, List<String> permissions) {

        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        for (String permission : permissions) {

            Node permissionNode = Node.builder(permission).build();
            user.data().add(permissionNode);

        }

        luckPerms.getUserManager().saveUser(user);
    }

    public void removePermissions(Player player, List<String> permissions) {

        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        for (String permission : permissions) {

            Node permissionNode = Node.builder(permission).build();
            user.data().remove(permissionNode);

        }

        luckPerms.getUserManager().saveUser(user);
    }

    public void addCommand(Player player, List<String> commands) {

        for (String command : commands) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("\\{player_name\\}", player.getName()));

        }
    }
}