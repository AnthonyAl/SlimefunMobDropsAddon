package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;


import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.Utils;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSpawnReason;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public final class EventsHandler implements Listener {

    private final SlimefunMobDrops plugin;

    public EventsHandler(SlimefunMobDrops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanillaPerish(EntityDeathEvent event) {
        if(!plugin.config.isVanilla_enabled()) return;
        if(plugin.config.isPlayer_only() && event.getEntity().getKiller() == null) return;
        if(!plugin.config.contains_vanilla(event.getEntityType())) return;
        if(plugin.config.isMythic_enabled())
            if(((MythicBukkit) (Objects.requireNonNull(getServer().getPluginManager().getPlugin("MythicMobs")))).getAPIHelper().isMythicMob(event.getEntity())) return;

        List<String> commons = plugin.config.get_items(event.getEntityType(), 0);
        List<String> rares = plugin.config.get_items(event.getEntityType(), 1);

        //For the common items (all of them can drop at one time)
        if(commons != null) for(String data : commons) {
            String slimefun_item = data.split(" ")[0];
            double drop_rate = Double.parseDouble(data.split(" ")[1]);

            ItemStack itemStack = Objects.requireNonNull(SlimefunItem.getById(slimefun_item)).getItem();

            if(Utils.calcDropChance(drop_rate)) {
                getLogger().info("Spawned slimefun item " + slimefun_item + " as a result of a Vanilla Common Mob Drop.");
                SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
            }
        }

        //For the rare items (only one can drop at a time)
        if(rares != null) for(String data : rares) {
            String slimefun_item = data.split(" ")[0];
            double drop_rate = Double.parseDouble(data.split(" ")[1]);

            ItemStack itemStack = Objects.requireNonNull(SlimefunItem.getById(slimefun_item)).getItem();

            if(Utils.calcDropChance(drop_rate)) {
                getLogger().info("Spawned slimefun item " + slimefun_item + " as a result of a Vanilla Rare Mob Drop.");
                SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
                break;
            }
        }
    }

    @EventHandler
    public void onMythicPerish(MythicMobDeathEvent event) {
        if(!plugin.config.isMythic_enabled()) return;
        if(plugin.config.isPlayer_only() && event.getKiller() == null) return;
        if(!plugin.config.contains_mythic(event.getMobType().getInternalName())) return;

        List<String> commons = plugin.config.get_items(event.getMobType().getInternalName(), 0);
        List<String> rares = plugin.config.get_items(event.getMobType().getInternalName(), 1);

        //For the common items (all of them can drop at one time)
        if(commons != null) for(String data : commons) {
            String slimefun_item = data.split(" ")[0];
            double drop_rate = Double.parseDouble(data.split(" ")[1]);

            ItemStack itemStack = Objects.requireNonNull(SlimefunItem.getById(slimefun_item)).getItem();

            if(Utils.calcDropChance(drop_rate)) {
                getLogger().info("Spawned slimefun item " + slimefun_item + " as a result of a Mythic Mob Common Mob Drop.");
                SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
            }
        }

        //For the rare items (only one can drop at a time)
        if(rares != null) for(String data : rares) {
            String slimefun_item = data.split(" ")[0];
            double drop_rate = Double.parseDouble(data.split(" ")[1]);

            ItemStack itemStack = Objects.requireNonNull(SlimefunItem.getById(slimefun_item)).getItem();

            if(Utils.calcDropChance(drop_rate)) {
                getLogger().info("Spawned slimefun item " + slimefun_item + " as a result of a Mythic Mob Rare Mob Drop.");
                SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
                break;
            }
        }
    }
}
