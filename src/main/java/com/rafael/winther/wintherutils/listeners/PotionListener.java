package com.rafael.winther.wintherutils.listeners;

import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionListener implements Listener {

    @EventHandler
    public void joinWorld(PlayerTeleportEvent event) {
        val player = event.getPlayer();
        val world = event.getTo().getWorld();

        if (world.getName().equalsIgnoreCase("gladiador")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
        }


    }


}
