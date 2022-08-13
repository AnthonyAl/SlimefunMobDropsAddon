package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;


import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public final class EventsHandler implements Listener {

    private final SlimefunMobDrops plugin;

    public EventsHandler(SlimefunMobDrops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanillaPerish(EntityDeathEvent event) {
        if(!plugin.config.isVanilla_enabled()) return;
        if(plugin.config.isMythic_enabled())
            if(((MythicBukkit) (Objects.requireNonNull(getServer().getPluginManager().getPlugin("MythicMobs")))).getAPIHelper().isMythicMob(event.getEntity())) return;

        List<String> commons = plugin.config.get_items(event.getEntityType(), 0);
        List<String> rares = plugin.config.get_items(event.getEntityType(), 1);


    }

    @EventHandler
    public void onMythicPerish(MythicMobDeathEvent event) {
        if(!plugin.config.isMythic_enabled()) return;

    }
}
