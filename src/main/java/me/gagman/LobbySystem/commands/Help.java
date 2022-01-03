package me.gagman.LobbySystem.commands;

import me.gagman.LobbySystem.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Help implements CommandExecutor {

    FileConfiguration config = Main.plugin.getConfig();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("help")) {
            final List<String> message = config.getStringList("help-cmd");
            (new BukkitRunnable() {
                public void run() {
                    for (String msg : message) {
                        msg = msg.replace('&', '§');
                        p.sendMessage(msg);
                    }
                }
            }).runTaskLater((Plugin) Main.plugin, 20L);
        }
        return false;
    }
}
