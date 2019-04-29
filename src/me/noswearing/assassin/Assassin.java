package me.noswearing.assassin;

import org.bukkit.plugin.java.JavaPlugin;

public class Assassin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting Assassin v1.0");
        getCommand("astart").setExecutor(new StartCommand());
        getCommand("aconfig").setExecutor(new ConfigCommand());
        getServer().getPluginManager().registerEvents(new KillHandler(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling Assassin v1.0");
    }
}
