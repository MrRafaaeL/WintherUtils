package com.rafael.winther.wintherutils.manager;

import com.rafael.winther.wintherutils.Winther;
import com.rafael.winther.wintherutils.listeners.CombatListener;
import com.rafael.winther.wintherutils.listeners.LunarListener;
import com.rafael.winther.wintherutils.listeners.PotionListener;
import lombok.Data;
import org.bukkit.Bukkit;

@Data(staticConstructor = "of")
public class ListenerRegistry {

    private final Winther winther;

    public void registry() {
        try {
            Bukkit.getPluginManager().registerEvents(new PotionListener(), winther);
            Bukkit.getPluginManager().registerEvents(new LunarListener(), winther);
            new CombatListener();
            Bukkit.getConsoleSender().sendMessage("§6・ §aTodas as listeners foram iniciadas;");
        }catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§6・ §cErro ao carregar as listeners.");
        }
    }
}
