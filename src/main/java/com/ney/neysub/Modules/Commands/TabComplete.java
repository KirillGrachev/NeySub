package com.ney.neysub.Modules.Commands;

import com.ney.neysub.Modules.Utils.MainUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {

    private MainUtil mainUtil = new MainUtil();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        if (!mainUtil.Enabled) return completions;
        else if (!sender.hasPermission("neysub.default")) return completions;

        if (args.length == 1) {

            return Arrays.asList("help", "buy", "info", "take", "give");

        } else if (args.length == 2 && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("give"))) {

            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            return completions;

        }

        return completions;
    }
}