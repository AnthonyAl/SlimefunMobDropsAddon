package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;


import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public final class EventsHandler implements Listener {

    private final SlimefunMobDrops plugin;

    public EventsHandler(SlimefunMobDrops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanillaPerish(EntityDeathEvent event) {

    }

    @EventHandler
    public void onMythicPerish(MythicMobDeathEvent event) {

    }
}
