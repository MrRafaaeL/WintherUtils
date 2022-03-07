package com.rafael.winther.wintherutils.listeners;

import com.rafael.winther.wintherutils.utils.TabList;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LunarListener implements Listener {

    @EventHandler
    public void joinLunar(PlayerJoinEvent event) {
        val player = event.getPlayer();
        TabList.sendHeaderAndFooter(player, "\n§9§lREDE WINTHER\n\n§fVocê está conectado ao §9§lP4FREE§f.\n", "\n§9Discord: §fdiscord.gg/redewinther\n");
    }
}
