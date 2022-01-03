package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KreditStore implements CommandExecutor {

    FileConfiguration config = Main.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("kreditstore")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("kreditstore")));
        }
        return false;
    }
}
