package me.gagman.LobbySystem.listeners;

import me.gagman.LobbySystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Join_Leave implements Listener {

    FileConfiguration config = Main.plugin.getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999999, 3));
        giveItems(p);
        final List<String> message = config.getStringList("join-motd");
        (new BukkitRunnable() {
            public void run() {
                for (String msg : message) {
                    msg = msg.replace('&', '§');
                    p.sendMessage(msg);
                }
            }
        }).runTaskLater((Plugin)Main.plugin, 20L);
        if (p.hasPermission("lobby.master")) {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString("master-join").replace("%player_name%", p.getName())));
        } else {
            e.setJoinMessage(null);
        }
        String l = config.getString("spawn");
        String[] loc = l.split(";");
        Location location = p.getLocation();
        location.setWorld(Bukkit.getWorld(loc[0]));
        location.setX(Double.parseDouble(loc[1]));
        location.setY(Double.parseDouble(loc[2]));
        location.setZ(Double.parseDouble(loc[3]));
        location.setYaw((float) Double.parseDouble(loc[4]));
        location.setPitch((float) Double.parseDouble(loc[5]));
        p.teleport(location);
    }

    public void giveItems(Player player) {
        ItemStack ServerS = new ItemStack(Material.COMPASS);
        ItemMeta ServerSmeta = ServerS.getItemMeta();
        ServerSmeta.setDisplayName("§bServer Selector");
        ServerS.setItemMeta(ServerSmeta);
        ItemStack LobbyS = new ItemStack(Material.BOOK);
        ItemMeta LobbySmeta = LobbyS.getItemMeta();
        LobbySmeta.setDisplayName("§bLobby Selector");
        LobbyS.setItemMeta(LobbySmeta);
        ItemStack Ranks = new ItemStack(Material.GOLD_INGOT);
        ItemMeta Ranksmeta = Ranks.getItemMeta();
        Ranksmeta.setDisplayName("§eRanks");
        Ranks.setItemMeta(Ranksmeta);
        player.getInventory().setItem(0, ServerS);
        player.getInventory().setItem(1, LobbyS);
        player.getInventory().setItem(4, Ranks);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getItemInHand().getType() == Material.COMPASS) {
            p.performCommand("servers");
        } else if (p.getItemInHand().getType() == Material.BOOK) {
            p.performCommand("lobbys");
        } else if (p.getItemInHand().getType() == Material.GOLD_INGOT) {
            p.performCommand("ranks");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
