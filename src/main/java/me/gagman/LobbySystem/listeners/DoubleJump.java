package me.gagman.LobbySystem.listeners;

import me.gagman.LobbySystem.Main;
import me.gagman.LobbySystem.commands.Fly;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DoubleJump implements Listener {

    private static final HashMap<Player, Integer> cooldown = new HashMap<>();

    HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();

    public static Integer getCooldown(Player p) {
        return cooldown.get(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.isFlying())
            return;
        if (p.getGameMode() == GameMode.SPECTATOR)
            return;
        if (p.getGameMode() == GameMode.CREATIVE)
            return;
        if (cooldown.containsKey(p))
            return;
        if (Fly.FlyingP.contains(p.getUniqueId().toString()))
            return;
        p.setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerToggleFlyEvent(PlayerToggleFlightEvent e) {
        final Player p = e.getPlayer();
        if (Fly.FlyingP.contains(p.getUniqueId().toString()))
            return;
        if (cooldown.containsKey(p))
            return;
        if (p.getGameMode() == GameMode.SPECTATOR)
            return;
        if (p.getGameMode() == GameMode.CREATIVE)
            return;
        int cooldownTime = 2;
        Sound sound = Sound.BAT_TAKEOFF;
        float volume = (float)1.0;
        float pitch = (float)1.0;
        DoubleJumpEvent doubleJumpEvent = new DoubleJumpEvent(p, cooldownTime, sound, volume, pitch);
        Bukkit.getPluginManager().callEvent((Event)doubleJumpEvent);
        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        if (doubleJumpEvent.isCancelled())
            return;
        cooldown.put(p, Integer.valueOf(doubleJumpEvent.getCooldownTime()));
        this.cooldownTask.put(p, new BukkitRunnable() {
            public void run() {
                DoubleJump.cooldown.put(p, Integer.valueOf(((Integer)DoubleJump.cooldown.get(p)).intValue() - 1));
                if (((Integer)DoubleJump.cooldown.get(p)).intValue() == 0) {
                    DoubleJump.cooldown.remove(p);
                    DoubleJump.this.cooldownTask.remove(p);
                    cancel();
                }
            }
        });
        ((BukkitRunnable)this.cooldownTask.get(p)).runTaskTimer((Plugin)Main.getPlugin(Main.class), 20L, 20L);
        p.setVelocity(p.getLocation().getDirection().multiply(1.6).setY(1));
        p.playSound(p.getLocation(), doubleJumpEvent.getSound(), doubleJumpEvent.getVolume(), doubleJumpEvent.getPitch());
    }
}
