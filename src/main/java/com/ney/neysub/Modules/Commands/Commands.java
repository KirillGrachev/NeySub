package com.ney.neysub.Modules.Commands;

import com.ney.neysub.Modules.Cooldown.CooldownManager;
import com.ney.neysub.Modules.Data.SaveAndLoad;
import com.ney.neysub.Modules.PlayerPoints.EconomyHandler;
import com.ney.neysub.Modules.Utils.MainUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {

    private final MainUtil mainUtil = new MainUtil();
    private final SaveAndLoad saveAndLoad = new SaveAndLoad();
    private final EconomyHandler economyHandler = new EconomyHandler();
    private final CooldownManager cooldownManager = new CooldownManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("neysub")) {

            if (!mainUtil.Enabled) return true;

            String key = "neysub";
            int time = mainUtil.getInteger("Settings.cooldown.time");

            if (sender instanceof Player && mainUtil.MessageCooldown) {

                Player player = (Player) sender;
                Sound soundType = mainUtil.getSound("Settings.cooldown.sound.type");
                float volume = (float) mainUtil.getDouble("Settings.cooldown.sound.volume");
                float pitch = (float) mainUtil.getDouble("Settings.cooldown.sound.pitch");

                if (cooldownManager.isOnCooldown(player, key)) {

                    long remainingTime = cooldownManager.getCooldownTime(player, key) / 1000L;
                    mainUtil.getStringList("Settings.cooldown.text").stream()
                            .map(message -> message.replace("{time}", String.valueOf(remainingTime)))
                            .forEach(sender::sendMessage);

                    player.playSound(player.getLocation(), soundType, volume, pitch);
                    return true;

                } else cooldownManager.setCooldown(player, "neysub", time);
            }

            if (args.length == 0) {

                handleDefaultMessage(sender);
                return true;

            }

            switch (args[0].toLowerCase()) {

                    case "help" -> {

                        if (mainUtil.MessagesHelp) {

                            if (sender instanceof ConsoleCommandSender) {

                                sender.sendMessage(mainUtil.getString("Messages.no_console.text"));
                                return true;

                            }

                            Player player = (Player) sender;
                            Sound soundType = mainUtil.getSound("Messages.help.sound.type");
                            float volume = (float) mainUtil.getDouble("Messages.help.sound.volume");
                            float pitch = (float) mainUtil.getDouble("Messages.help.sound.pitch");

                            if (!player.hasPermission("neysub.help")) {

                                player.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                                return true;

                            } else if (args.length != 1) {

                                mainUtil.sendMessage("Messages.help.valid.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.help.sound.enabled"), mainUtil.getString("Messages.help.valid.other.title.title"), mainUtil.getString("Messages.help.valid.other.title.subtitle"), mainUtil.getBoolean("Messages.help.valid.other.title.enabled"), mainUtil.getInteger("Messages.help.valid.other.title.fadeIn"), mainUtil.getInteger("Messages.help.valid.other.title.stay"), mainUtil.getInteger("Messages.help.valid.other.title.fadeOut"), mainUtil.getString("Messages.help.valid.other.actionbar.text"), mainUtil.getBoolean("Messages.help.valid.other.actionbar.enabled"), mainUtil.getString("Messages.help.valid.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.help.valid.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.help.valid.other.bossbar.style")), mainUtil.getInteger("Messages.help.valid.other.bossbar.time"), mainUtil.getBoolean("Messages.help.valid.other.bossbar.enabled"), mainUtil.getInteger("Messages.help.valid.other.actionbar.time"), saveAndLoad);
                                return true;

                            }

                            mainUtil.sendMessage("Messages.help.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.help.sound.enabled"), mainUtil.getString("Messages.help.other.title.title"), mainUtil.getString("Messages.help.other.title.subtitle"), mainUtil.getBoolean("Messages.help.other.title.enabled"), mainUtil.getInteger("Messages.help.other.title.fadeIn"), mainUtil.getInteger("Messages.help.other.title.stay"), mainUtil.getInteger("Messages.help.other.title.fadeOut"), mainUtil.getString("Messages.help.other.actionbar.text"), mainUtil.getBoolean("Messages.help.other.actionbar.enabled"), mainUtil.getString("Messages.help.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.help.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.help.other.bossbar.style")), mainUtil.getInteger("Messages.help.other.bossbar.time"), mainUtil.getBoolean("Messages.help.other.bossbar.enabled"), mainUtil.getInteger("Messages.help.other.actionbar.time"), saveAndLoad);

                        }
                    }

                case "buy" -> {

                    if (mainUtil.MessagesBuy) {

                        if (sender instanceof ConsoleCommandSender) {

                            sender.sendMessage(mainUtil.getString("Messages.no_console.text"));
                            return true;

                        }

                        Player player = (Player) sender;
                        Sound soundType = mainUtil.getSound("Messages.buy.sound.type");
                        float volume = (float) mainUtil.getDouble("Messages.buy.sound.volume");
                        float pitch = (float) mainUtil.getDouble("Messages.buy.sound.pitch");

                        if (!player.hasPermission("neysub.buy")) {

                            player.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                            return true;

                        } else if (args.length != 1) {

                            mainUtil.sendMessage("Messages.buy.valid.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.buy.sound.enabled"), mainUtil.getString("Messages.buy.valid.other.title.title"), mainUtil.getString("Messages.buy.valid.other.title.subtitle"), mainUtil.getBoolean("Messages.buy.valid.other.title.enabled"), mainUtil.getInteger("Messages.buy.valid.other.title.fadeIn"), mainUtil.getInteger("Messages.buy.valid.other.title.stay"), mainUtil.getInteger("Messages.buy.valid.other.title.fadeOut"), mainUtil.getString("Messages.buy.valid.other.actionbar.text"), mainUtil.getBoolean("Messages.buy.valid.other.actionbar.enabled"), mainUtil.getString("Messages.buy.valid.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.buy.valid.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.buy.valid.other.bossbar.style")), mainUtil.getInteger("Messages.buy.valid.other.bossbar.time"), mainUtil.getBoolean("Messages.buy.valid.other.bossbar.enabled"), mainUtil.getInteger("Messages.buy.valid.other.actionbar.time"), saveAndLoad);
                            return true;

                        } else if (saveAndLoad.getDaysSincePurchase(player.getName()) != -1) {

                            mainUtil.sendMessage("Messages.buy.already_purchased.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.buy.sound.enabled"), mainUtil.getString("Messages.buy.already_purchased.other.title.title"), mainUtil.getString("Messages.buy.already_purchased.other.title.subtitle"), mainUtil.getBoolean("Messages.buy.already_purchased.other.title.enabled"), mainUtil.getInteger("Messages.buy.already_purchased.other.title.fadeIn"), mainUtil.getInteger("Messages.buy.already_purchased.other.title.stay"), mainUtil.getInteger("Messages.buy.already_purchased.other.title.fadeOut"), mainUtil.getString("Messages.buy.already_purchased.other.actionbar.text"), mainUtil.getBoolean("Messages.buy.already_purchased.other.actionbar.enabled"), mainUtil.getString("Messages.buy.already_purchased.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.buy.already_purchased.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.buy.already_purchased.other.bossbar.style")), mainUtil.getInteger("Messages.buy.already_purchased.other.bossbar.time"), mainUtil.getBoolean("Messages.buy.already_purchased.other.bossbar.enabled"), mainUtil.getInteger("Messages.buy.already_purchased.other.actionbar.time"), saveAndLoad);
                            return true;

                        } else if (!economyHandler.hasEnoughPoints(player, mainUtil.getInteger("Settings.price"))) {

                            mainUtil.sendMessage("Messages.buy.no_money.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.buy.sound.enabled"), mainUtil.getString("Messages.buy.no_money.other.title.title"), mainUtil.getString("Messages.buy.no_money.other.title.subtitle"), mainUtil.getBoolean("Messages.buy.no_money.other.title.enabled"), mainUtil.getInteger("Messages.buy.no_money.other.title.fadeIn"), mainUtil.getInteger("Messages.buy.no_money.other.title.stay"), mainUtil.getInteger("Messages.buy.no_money.other.title.fadeOut"), mainUtil.getString("Messages.buy.no_money.other.actionbar.text"), mainUtil.getBoolean("Messages.buy.no_money.other.actionbar.enabled"), mainUtil.getString("Messages.buy.no_money.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.buy.no_money.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.buy.no_money.other.bossbar.style")), mainUtil.getInteger("Messages.buy.no_money.other.bossbar.time"), mainUtil.getBoolean("Messages.buy.no_money.other.bossbar.enabled"), mainUtil.getInteger("Messages.buy.no_money.other.actionbar.time"), saveAndLoad);
                            return true;

                        }

                        saveAndLoad.savePlayerPurchaseDate(player.getName(), player);
                        saveAndLoad.loadPlayerPurchaseDates();
                        economyHandler.withdrawPoints(player, mainUtil.getInteger("Settings.price"));

                        mainUtil.sendMessage("Messages.buy.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.buy.sound.enabled"), mainUtil.getString("Messages.buy.other.title.title"), mainUtil.getString("Messages.buy.other.title.subtitle"), mainUtil.getBoolean("Messages.buy.other.title.enabled"), mainUtil.getInteger("Messages.buy.other.title.fadeIn"), mainUtil.getInteger("Messages.buy.other.title.stay"), mainUtil.getInteger("Messages.buy.other.title.fadeOut"), mainUtil.getString("Messages.buy.other.actionbar.text"), mainUtil.getBoolean("Messages.buy.other.actionbar.enabled"), mainUtil.getString("Messages.buy.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.buy.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.buy.other.bossbar.style")), mainUtil.getInteger("Messages.buy.other.bossbar.time"), mainUtil.getBoolean("Messages.buy.other.bossbar.enabled"), mainUtil.getInteger("Messages.buy.other.actionbar.time"), saveAndLoad);

                    }
                }

                case "info" -> {

                    if (mainUtil.MessagesInfo) {

                        if (!sender.hasPermission("neysub.info")) {

                            sender.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                            return true;

                        } else if (args.length != 2) {

                            mainUtil.getStringList("Messages.info.valid.text").forEach(sender::sendMessage);
                            return true;

                        }

                        String targetPlayerName = args[1];
                        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                        if (targetPlayer != null && targetPlayer.isOnline()) {

                            mainUtil.getStringList("Messages.info.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName).replace("{sub}", saveAndLoad.getDaysSincePurchase(targetPlayer.getName()) != -1 ? mainUtil.getString("Placeholders.yes_") : mainUtil.getString("Placeholders.no_")))
                                    .forEach(sender::sendMessage);

                        } else {

                            mainUtil.getStringList("Messages.info.not_online.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                        }
                    }
                }

                case "take" -> {

                    if (mainUtil.MessagesTake) {

                        if (!sender.hasPermission("neysub.take")) {

                            sender.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                            return true;

                        } else if (args.length != 2) {

                            mainUtil.getStringList("Messages.take.valid.text").forEach(sender::sendMessage);
                            return true;

                        }

                        String targetPlayerName = args[1];
                        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                        if (targetPlayer != null && targetPlayer.isOnline() && saveAndLoad.getDaysSincePurchase(targetPlayerName) != -1) {

                            mainUtil.getStringList("Messages.take.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                            saveAndLoad.removePlayerPurchaseDate(targetPlayerName, targetPlayer);

                        } else if (targetPlayer == null && !targetPlayer.isOnline()) {

                            mainUtil.getStringList("Messages.take.not_online.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                        } else {

                            mainUtil.getStringList("Messages.take.not_sub.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                        }
                    }
                }

                case "give" -> {

                    if (mainUtil.MessagesGive) {

                        if (!sender.hasPermission("neysub.give")) {

                            sender.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                            return true;

                        } else if (args.length != 2) {

                            mainUtil.getStringList("Messages.give.valid.text").forEach(sender::sendMessage);
                            return true;

                        }

                        String targetPlayerName = args[1];
                        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                        if (targetPlayer != null && targetPlayer.isOnline()) {

                            mainUtil.getStringList("Messages.give.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                            saveAndLoad.savePlayerPurchaseDate(targetPlayerName, targetPlayer);
                            saveAndLoad.loadPlayerPurchaseDates();

                        } else {

                            mainUtil.getStringList("Messages.give.not_online.text").stream()
                                    .map(message -> message.replace("{player_name}", targetPlayerName))
                                    .forEach(sender::sendMessage);

                        }
                    }
                }

                default -> handleDefaultMessage(sender);

            }

            return true;
        }

        return false;
    }

    public void handleDefaultMessage(CommandSender sender) {

        if (mainUtil.MessagesDefault) {

            if (sender instanceof ConsoleCommandSender) {

                mainUtil.getStringList("Messages.default.text").forEach(sender::sendMessage);
                return;

            } else if (!sender.hasPermission("neysub.default")) {

                sender.sendMessage(mainUtil.getString("Messages.no_permission.text"));
                return;

            }

            Player player = (Player) sender;
            Sound soundType = mainUtil.getSound("Messages.default.sound.type");
            float volume = (float) mainUtil.getDouble("Messages.default.sound.volume");
            float pitch = (float) mainUtil.getDouble("Messages.default.sound.pitch");

            mainUtil.sendMessage("Messages.default.text", player, soundType, volume, pitch, mainUtil.getBoolean("Messages.default.sound.enabled"), mainUtil.getString("Messages.default.other.title.title"), mainUtil.getString("Messages.default.other.title.subtitle"), mainUtil.getBoolean("Messages.default.other.title.enabled"), mainUtil.getInteger("Messages.default.other.title.fadeIn"), mainUtil.getInteger("Messages.default.other.title.stay"), mainUtil.getInteger("Messages.default.other.title.fadeOut"), mainUtil.getString("Messages.default.other.actionbar.text"), mainUtil.getBoolean("Messages.default.other.actionbar.enabled"), mainUtil.getString("Messages.default.other.bossbar.text"), BarColor.valueOf(mainUtil.getString("Messages.default.other.bossbar.color")), BarStyle.valueOf(mainUtil.getString("Messages.default.other.bossbar.style")), mainUtil.getInteger("Messages.default.other.bossbar.time"), mainUtil.getBoolean("Messages.default.other.bossbar.enabled"), mainUtil.getInteger("Messages.default.other.actionbar.time"), saveAndLoad);

        }
    }
}