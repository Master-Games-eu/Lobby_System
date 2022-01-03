package me.gagman.LobbySystem.listeners;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DoubleJumpEvent extends Event implements Cancellable {

    private final Player player;

    private boolean isCancelled;

    private int cooldownTime;

    private Sound sound;

    private float volume;

    private float pitch;

    public DoubleJumpEvent(Player player, int cooldownTime, Sound sound, float volume, float pitch) {
        this.player = player;
        this.cooldownTime = cooldownTime;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public int getCooldownTime(){
        return this.cooldownTime;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
