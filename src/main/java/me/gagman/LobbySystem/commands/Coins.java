package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Coins implements CommandExecutor {

    FileConfiguration config = Main.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("coins")) {
            if (args.length == 0) {
                p.performCommand("token");
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("bal")) {
                    if (p.hasPermission("coins.others")) {
                        p.performCommand("token bal");
                        return false;
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                    }
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("bal")) {
                    if (p.hasPermission("coins.others")) {
                        p.performCommand("token bal " + args[1]);
                        return false;
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                    }
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("send")) {
                    p.performCommand("token send " + args[1] + " " + args[2]);
                    return false;
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("coins-usage")));
            }
        }
        return false;
    }
}
