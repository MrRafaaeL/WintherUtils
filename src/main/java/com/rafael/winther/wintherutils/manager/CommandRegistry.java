package com.rafael.winther.wintherutils.manager;

import com.rafael.winther.wintherutils.Winther;
import com.rafael.winther.wintherutils.commands.DmGladCommand;
import lombok.Data;
import org.bukkit.Bukkit;

@Data(staticConstructor = "of")
public class CommandRegistry {

    private final Winther winther;

    public void registry() {
        try {
            winther.getCommand("dmglad").setExecutor(new DmGladCommand());
            Bukkit.getConsoleSender().sendMessage("§6・ §aTodas os comandos foram iniciadas;");
        }catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§6・ §cErro ao carregar os comandos.");
        }
    }
}
