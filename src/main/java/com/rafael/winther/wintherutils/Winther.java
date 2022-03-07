package com.rafael.winther.wintherutils;

import com.rafael.winther.wintherutils.manager.CommandRegistry;
import com.rafael.winther.wintherutils.manager.ListenerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Winther extends JavaPlugin {

    public static Winther instance;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§f[§a⚡§f]§a " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + "§f ativo!");
        Bukkit.getConsoleSender().sendMessage("");
        instance = this;

        ListenerRegistry.of(this).registry();
        CommandRegistry.of(this).registry();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§f[§c⚡§f]§c " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + "§f desativado!");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public static Winther getInstance() {
       return instance;
    }
}
