package com.rafael.winther.wintherutils.commands;

import com.rafael.winther.wintherutils.Winther;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class DmGladCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas in-game");
            return true;
        }

        val player = (Player) sender;
        if (!player.hasPermission("winther.master")) {
            player.sendMessage("§cVocê não tem permissão suficiente.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cUtilize: /dmglad <setar/iniciar>");
            return true;
        }

        if (args[0].equalsIgnoreCase("setar")) {
            val location = player.getLocation();
            val config = Winther.getInstance().getConfig().createSection("dmglad");
            config.set("world", location.getWorld().getName());
            config.set("x", location.getX());
            config.set("y", location.getY());
            config.set("z", location.getZ());
            config.set("yaw", location.getYaw());
            config.set("pitch", location.getPitch());
            Winther.getInstance().saveConfig();
            player.sendMessage("§aA dm-glad foi setada com sucesso.");
            return true;
        }

        if (args[0].equalsIgnoreCase("iniciar")) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§f* §6§lDM INICIADA!");
            Bukkit.broadcastMessage("§f* §6O pvp será reativado em §f30s§6.");
            Bukkit.broadcastMessage("");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg flag guerra pvp -w Gladiador deny");
            for (Player soldiers : Bukkit.getOnlinePlayers()) {
                if (soldiers.getWorld().getName().equalsIgnoreCase("gladiador")) {
                    val file = Winther.getInstance().getConfig();
                    val random = ThreadLocalRandom.current();
                    val world = Bukkit.getServer().getWorld(file.getString("dmglad.world"));
                    double x = file.getDouble("dmglad.x") + random.nextInt(5);
                    double y = file.getDouble("dmglad.y");
                    double z = file.getDouble("dmglad.z") + random.nextInt(5);
                    float yaw = Float.parseFloat(file.getString("dmglad.yaw"));
                    float pitch = Float.parseFloat(file.getString("dmglad.pitch"));
                    val location = new Location(world, x, y, z, yaw, pitch);
                    soldiers.teleport(location);
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("§f* §6§lPVP ATIVADO!");
                    Bukkit.broadcastMessage("");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg flag guerra pvp -w Gladiador allow");
                }
            }.runTaskLater(Winther.getPlugin(Winther.class), 30 * 20);
        }
        return false;
    }
}
