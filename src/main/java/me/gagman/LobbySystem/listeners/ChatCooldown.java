package me.gagman.LobbySystem.listeners;

import me.gagman.LobbySystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChatCooldown implements Listener {

    FileConfiguration config = Main.plugin.getConfig();

    HashMap<UUID, Integer> cooldown = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        int timeInt = Math.toIntExact(System.currentTimeMillis() / 1000);
        if (!p.hasPermission("chatcd.master")) {
            if (cooldown.containsKey(p.getUniqueId()) && !p.hasPermission("chatcd.vip") && !p.hasPermission("chatcd.premium")) {
                if (Math.toIntExact(timeInt) - cooldown.get(p.getUniqueId()) <= config.getInt("cooldown.player")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("cooldown-message").replace("%seconds%", new StringBuilder().append(config.getInt("cooldown.player")).append("").toString())));
                    return;
                }
            }
            if (cooldown.containsKey(p.getUniqueId()) && p.hasPermission("chatcd.vip")) {
                if (Math.toIntExact(timeInt) - cooldown.get(p.getUniqueId()) <= config.getInt("cooldown.vip")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("cooldown-message").replace("%seconds%", new StringBuilder().append(config.getInt("cooldown.vip")).append("").toString())));
                    return;
                }
            }
            if (cooldown.containsKey(p.getUniqueId()) && p.hasPermission("chatcd.premium")) {
                if (Math.toIntExact(timeInt) - cooldown.get(p.getUniqueId()) <= config.getInt("cooldown.premium")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("cooldown-message").replace("%seconds%", new StringBuilder().append(config.getInt("cooldown.premium")).append("").toString())));
                    return;
                }
            }
            cooldown.put(p.getUniqueId(), Math.toIntExact(timeInt));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        cooldown.remove(e.getPlayer().getUniqueId());
    }
}
