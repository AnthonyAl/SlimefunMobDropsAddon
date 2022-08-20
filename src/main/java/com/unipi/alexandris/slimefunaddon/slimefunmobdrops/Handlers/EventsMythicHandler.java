package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.DropTable;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.Utils;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSpawnReason;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class EventsMythicHandler implements Listener {

    private final SlimefunMobDrops plugin;

    public EventsMythicHandler(SlimefunMobDrops plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onMythicPerish(MythicMobDeathEvent event) {
        if (!plugin.config.isMythic_enabled()) return;
        if (plugin.config.isPlayer_only() && event.getKiller() == null) return;
        if (!plugin.config.contains_mythic(event.getMobType().getInternalName())) return;

        List<String> drops = plugin.config.get_drops(event.getMobType().getInternalName());

        if (drops != null) for (String data : drops) {
            String[] amounts = data.split(" ")[1].split("-");
            int amount;
            if (amounts.length == 1) amount = Integer.parseInt(amounts[0]);
            else amount = Utils.getRandValue(Integer.parseInt(amounts[0]), Integer.parseInt(amounts[1]));

            switch (data.split(" ")[0].split(":")[0]) {
                case "minecraft":
                    if (Utils.calcDropChance(Double.parseDouble(data.split(" ")[2])))
                        dropMinecraftItem(event, data.split(" ")[0].split(":")[1], amount);
                    break;
                case "slimefun":
                    if (Utils.calcDropChance(Double.parseDouble(data.split(" ")[2])))
                        dropSlimefunItem(event, data.split(" ")[0].split(":")[1], amount);
                    break;
                case "droptable":
                    if (Utils.calcDropChance(Double.parseDouble(data.split(" ")[2]))) {
                        for (int i = 0; i < amount; i++) {
                            DropTable dT = plugin.config.getTable(data.split(" ")[0].split(":")[1]);
                            if(dT == null) {
                                getLogger().severe("DataTable " + data.split(" ")[0].split(":")[1] + " was not found," +
                                        " please check your config.yml for typo mistakes.");
                                return;
                            }
                            DropTable.Drop drop = dT.getRandDrop();

                            if (drop == null) continue;

                            if (drop.getType().equals("minecraft"))
                                dropMinecraftItem(event, drop.getName(), Utils.getRandValue(drop.getMin(), drop.getMax()));

                            if (drop.getType().equals("slimefun"))
                                dropSlimefunItem(event, drop.getName(), Utils.getRandValue(drop.getMin(), drop.getMax()));
                        }
                    }
                    break;

            }
        }
    }

    private void dropSlimefunItem(MythicMobDeathEvent event, String name, int stack) {
        SlimefunItemStack itemStack = new SlimefunItemStack(name, Objects.requireNonNull(SlimefunItem.getById(name)).getItem());
        itemStack.setAmount(stack);
        if(plugin.config.isItem_Logging()) getLogger().info("Spawned slimefun item " + name + " x" + stack + " as a result of a Mythic Mob Drop.");
        SlimefunUtils.spawnItem(event.getEntity().getLocation(), itemStack, ItemSpawnReason.MISC);
    }

    private void dropMinecraftItem(MythicMobDeathEvent event, String name, int stack) {
        ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(name.toUpperCase())));
        itemStack.setAmount(stack);
        if(plugin.config.isItem_Logging()) getLogger().info("Spawned minecraft item " + name + " x" + stack + " as a result of a Mythic Mob Drop.");
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
    }


}
