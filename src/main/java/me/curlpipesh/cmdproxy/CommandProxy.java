package me.curlpipesh.cmdproxy;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author audrey
 * @since 12/29/15.
 */
public class CommandProxy extends JavaPlugin {
    private final Collection<String> proxiedCommands = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        proxiedCommands.addAll(getConfig().getStringList("proxied-commands").stream().map(String::toLowerCase).collect(Collectors.<String>toList()));
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            @SuppressWarnings("unused")
            public void onCommandPreprocessEvent(final PlayerCommandPreprocessEvent event) {
                final String message = event.getMessage();
                final String cmd = message.replaceFirst("/", "").split(" ")[0];
                if(proxiedCommands.contains(cmd.toLowerCase())) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Sorry, only the console can do that.");
                    event.setCancelled(true);
                }
            }
        }, this);
    }
}
