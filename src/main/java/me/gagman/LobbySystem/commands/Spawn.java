package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    FileConfiguration config = Main.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("setspawn")) {
            if (sender instanceof Player) {
                if (p.hasPermission("mg.setspawn")) {
                    if (args.length == 0) {
                        Location loc = p.getLocation();
                        config.set("spawn", String.valueOf(loc.getWorld().getName()) + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch());
                        Main.plugin.saveConfig();
                        p.sendMessage("§b§lServer §8» §aSpawn has been set to your position");
                        return false;
                    }
                    p.sendMessage("§b§lServer §8» §7Usage: §b/setspawn");
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                return true;
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                if (p.hasPermission("mg.spawn")) {
                    if (args.length == 0) {
                        String l = config.getString("spawn");
                        if (l == null) {
                            p.sendMessage("§b§lServer §8» §cSpawn is not set");
                            return false;
                        }
                        String[] loc = l.split(";");
                        Location location = p.getLocation();
                        location.setWorld(Bukkit.getWorld(loc[0]));
                        location.setX(Double.parseDouble(loc[1]));
                        location.setY(Double.parseDouble(loc[2]));
                        location.setZ(Double.parseDouble(loc[3]));
                        location.setYaw((float) Double.parseDouble(loc[4]));
                        location.setPitch((float) Double.parseDouble(loc[5]));
                        p.teleport(location);
                        return false;
                    }
                    p.sendMessage("§b§lServer §8» §7Usage: §b/spawn");
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                return true;
            }
            return true;
        }
        return true;
    }
}
