package com.rafael.winther.wintherutils.listeners;

import com.rafael.winther.wintherutils.Winther;
import com.rafael.winther.wintherutils.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CombatListener implements Listener {

    public static HashMap<String, Integer> combat = new HashMap<>();

    public CombatListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Winther.getPlugin(Winther.class));

        new BukkitRunnable() {
            public void run() {

                Iterator<Entry<String, Integer>> iterator = CombatListener.combat.entrySet().iterator();

                while (iterator.hasNext()) {
                    Entry<String, Integer> data = iterator.next();

                    Integer time = data.getValue();

                    if (time > 0) {
                        data.setValue(time - 1);

                        if (Bukkit.getPlayer(data.getKey()) != null) {
                            ActionBar.sendActionBarMessage(Bukkit.getPlayer(data.getKey()), "§cVocê está em combate por " + time + "s");
                        }
                    }

                    if (time == 0) {
                        iterator.remove();
                        ActionBar.sendActionBarMessage(Bukkit.getPlayer(data.getKey()), "§a§lOPA! §aVocê§l não§r§a está mais em combate.");
                    }

                }
            }
        }.runTaskTimerAsynchronously(Winther.getPlugin(Winther.class), 0L, 20L);
    }

    @EventHandler
    public void onQuit(PlayerDeathEvent e) {
        if (combat.containsKey(e.getEntity().getName())) {
            Player p = e.getEntity();
            p.sendMessage("§aVocê§l não§r§a está mais em combate.");
            combat.remove(e.getEntity().getName());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (combat.containsKey(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            combat.remove(e.getPlayer().getName());
            p.damage(999.0D);
            Bukkit.broadcastMessage("§c" + p.getName() + " desconectou em combate! Peleco sendo peleco.");
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            if (e.getDamager().getType() == EntityType.PLAYER && e.getEntity().getType() == EntityType.PLAYER) {
                Player damager = (Player) e.getDamager();
                Player entity = (Player) e.getEntity();
                if (entity.hasPermission("winther.staff")) {
                    return;
                }
                if (damager.getAllowFlight()) {
                    damager.setAllowFlight(false);
                }

                if (entity.getAllowFlight()) {
                    entity.setAllowFlight(false);
                }

                if (entity.isFlying()) {
                    entity.setFlying(false);
                }

                if (damager.isFlying()) {
                    damager.setFlying(false);
                }

                if (combat.containsKey(damager.getName())) {
                    combat.replace(damager.getName(), 15);
                } else {
                    damager.sendMessage("§cVocê entrou em combate com " + entity.getName() + ", aguarde 15 segundos para deslogar.");
                    entity.sendMessage("§cVocê entrou em combate com " + damager.getName() + ", aguarde 15 segundos para deslogar.");

                    damager.setFlying(false);
                    damager.setAllowFlight(false);

                    entity.setFlying(false);
                    entity.setAllowFlight(false);

                    combat.put(damager.getName(), 15);
                }

                if (combat.containsKey(entity.getName())) {
                    combat.replace(entity.getName(), 15);
                }

                if (!(e.getDamage() <= 0.0D)) {
                    combat.put(entity.getName(), 15);
                }

            }

        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (combat.containsKey(p.getName())) {
            e.setCancelled(true);
            p.sendMessage("§cVocê está em combate! Aguarde " + combat.get(p.getName()) + "s para executar comandos.");
        }
    }
}
