package me.gagman.LobbySystem.listeners;

import me.gagman.LobbySystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidTP implements Listener {

    FileConfiguration config = Main.plugin.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVoid(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getBlockY() > -5)
            return;
        String l = config.getString("spawn");
        if (l == null)
            return;
        String[] loc = l.split(";");
        Location location = p.getLocation();
        if (config.getStringList("disabledWorlds") != null && config.getStringList("disabledWorlds").contains(location.getWorld().getName()))
            return;
        location.setWorld(Bukkit.getWorld(loc[0]));
        location.setX(Double.parseDouble(loc[1]));
        location.setY(Double.parseDouble(loc[2]));
        location.setZ(Double.parseDouble(loc[3]));
        location.setYaw((float) Double.parseDouble(loc[4]));
        location.setPitch((float) Double.parseDouble(loc[5]));
        p.teleport(location);
        p.setFallDistance(0.0F);
    }
}
