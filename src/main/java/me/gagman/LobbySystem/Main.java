package me.gagman.LobbySystem;

import me.gagman.LobbySystem.commands.*;
import me.gagman.LobbySystem.listeners.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;

    public void onEnable() {
        plugin = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Others(), (Plugin)this);
        pm.registerEvents(new ChatCooldown(), (Plugin)this);
        pm.registerEvents(new Join_Leave(), (Plugin)this);
        pm.registerEvents(new VoidTP(), (Plugin)this);
        pm.registerEvents(new Fly(), (Plugin)this);
        pm.registerEvents(new DoubleJump(), (Plugin)this);
        getCommand("fly").setExecutor(new Fly());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("setspawn").setExecutor(new Spawn());
        getCommand("kreditstore").setExecutor(new KreditStore());
        getCommand("help").setExecutor(new Help());
        getCommand("coins").setExecutor(new Coins());
        getCommand("rank").setExecutor(new Rank());
        saveDefaultConfig();
    }

    public void onDisable() {}
}
