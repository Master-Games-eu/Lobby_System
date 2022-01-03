package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Rank implements CommandExecutor {

    FileConfiguration config = Main.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rank")) {
            Player p = (Player)sender;
            if (args.length == 0) {
                if (p.hasPermission("mg.rank.helper") || p.hasPermission("mg.rank.builder")) {
                    p.sendMessage("§b§lRank §8» §7Usage: §b/rank (nick) (rank)");
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                }
            }
            if (args.length == 1) {
                if (p.hasPermission("mg.rank.helper")) {
                    p.sendMessage("§b§lRank §8» §7Available ranks: §bel.helper, helper, zk.helper, player");
                }
                if (p.hasPermission("mg.rank.builder")) {
                    p.sendMessage("§b§lRank §8» §7Available ranks: §bel.builder, builder, zk.builder, player");
                }
            }
            if (args.length == 2) {
                if (p.hasPermission("mg.rank.helper")) {
                    if (args[1].equalsIgnoreCase("el.helper") || args[1].equalsIgnoreCase("helper") || args[1].equalsIgnoreCase("zk.helper")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set " + args[1]);
                    } else if (args[1].equalsIgnoreCase("player")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set default");
                    } else {
                        p.sendMessage("§b§lRank §8» §7Available ranks: §bel.helper, helper, zk.helper, player");
                    }
                }
                if (p.hasPermission("mg.rank.builder")) {
                    if (args[1].equalsIgnoreCase("el.builder") || args[1].equalsIgnoreCase("builder") || args[1].equalsIgnoreCase("zk.builder")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set " + args[1]);
                    } else if (args[1].equalsIgnoreCase("player")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set default");
                    } else {
                        p.sendMessage("§b§lRank §8» §7Available ranks: §bel.builder, builder, zk.builder, player");
                    }
                }
            }
        }
        return false;
    }
}
