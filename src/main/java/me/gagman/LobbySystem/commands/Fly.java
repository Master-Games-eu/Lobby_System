package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class Fly implements CommandExecutor, Listener {

    FileConfiguration config = Main.plugin.getConfig();

    public static List<String> FlyingP = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                if (p.hasPermission("mg.fly")) {
                    if (FlyingP.contains(p.getUniqueId().toString())) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("fly-off")));
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        FlyingP.remove(p.getUniqueId().toString());
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("fly-on")));
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        FlyingP.add(p.getUniqueId().toString());
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms")));
                }
                return true;
            }
        }
        return true;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (FlyingP.contains(e.getPlayer().getUniqueId().toString())) {
            FlyingP.remove(e.getPlayer().getUniqueId().toString());
        }
    }
}
