package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;


import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.DropTable;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.Utils;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSpawnReason;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public final class EventsVanillaHandler implements Listener {

    private final SlimefunMobDrops plugin;

    public EventsVanillaHandler(SlimefunMobDrops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanillaPerish(EntityDeathEvent event) {
        if(!plugin.config.isVanilla_enabled()) return;
        if(plugin.config.isPlayer_only() && event.getEntity().getKiller() == null) return;
        if(!plugin.config.contains_vanilla(event.getEntityType())) return;
        if(plugin.config.isMythic_enabled())
            if(((MythicBukkit) (Objects.requireNonNull(getServer().getPluginManager().getPlugin("MythicMobs")))).getAPIHelper().isMythicMob(event.getEntity())) return;

        List<String> drops = plugin.config.get_drops(event.getEntityType());

        if(drops != null) for(String data : drops) {
            String[] amounts = data.split(" ")[1].split("-");
            int amount;
            if(amounts.length == 1) amount = Integer.parseInt(amounts[0]);
            else amount = Utils.getRandValue(Integer.parseInt(amounts[0]), Integer.parseInt(amounts[1]));

            switch (data.split(" ")[0].split(":")[0]) {
                case "minecraft":
                    if(Utils.calcDropChance(Double.parseDouble(data.split(" ")[2])))
                        dropMinecraftItem(event, data.split(" ")[0].split(":")[1], amount);
                    break;
                case "slimefun":
                    if(Utils.calcDropChance(Double.parseDouble(data.split(" ")[2])))
                        dropSlimefunItem(event, data.split(" ")[0].split(":")[1], amount);
                    break;
                case "droptable":
                    //DropTable calculations:
                    if(Utils.calcDropChance(Double.parseDouble(data.split(" ")[2]))) {
                        //The droptable is going to run an amount number of times.
                        for(int i = 0; i < amount; i++) {
                            DropTable dT = plugin.config.getTable(data.split(" ")[0].split(":")[1]);
                            //If dT is null, there has been a mistake in the config file setups.
                            if(dT == null) {
                                getLogger().severe("DataTable " + data.split(" ")[0].split(":")[1] + " was not found," +
                                        " please check your config.yml for typo mistakes.");
                                return;
                            }
                            DropTable.Drop drop = dT.getRandDrop();
                            //drop is only null when the dT has randomly picked to drop no items (dT.noDropWeight)
                            if(drop == null) continue;

                            if(drop.getType().equals("minecraft")) {
                                dropMinecraftItem(event, drop.getName(), Utils.getRandValue(drop.getMin(), drop.getMax()));
                            }
                            if(drop.getType().equals("slimefun")) {
                                dropSlimefunItem(event, drop.getName(), Utils.getRandValue(drop.getMin(), drop.getMax()));
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void dropSlimefunItem(EntityDeathEvent event, String name, int stack) {
        SlimefunItemStack itemStack = new SlimefunItemStack(name, Objects.requireNonNull(SlimefunItem.getById(name)).getItem());
        itemStack.setAmount(stack);
        if(plugin.config.isItem_Logging()) getLogger().info("Spawned slimefun item " + name + " x" + stack + " as a result of a Vanilla Mob Drop.");
        SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
    }

    private void dropMinecraftItem(EntityDeathEvent event, String name, int stack) {
        ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(name.toUpperCase())));
        itemStack.setAmount(stack);
        if(plugin.config.isItem_Logging()) getLogger().info("Spawned minecraft item " + name + " x" + stack + " as a result of a Vanilla Mob Drop.");
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
    }
}
