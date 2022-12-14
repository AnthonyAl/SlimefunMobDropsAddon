package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.Config;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.DropTable;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings({"unused"})
public final class ConfigHandler {

    private final Config config = new Config();
    private final HashMap<String, DropTable> dropTables = new HashMap<>();

    public ConfigHandler(SlimefunMobDrops plugin) {
        loadDropTables(plugin);

        loadBaseConfig(plugin);
    }

    private void loadBaseConfig(SlimefunMobDrops plugin) {
        FileConfiguration fileConfiguration = plugin.getConfig();

        config.setVanilla_enabled(fileConfiguration.getBoolean("vanilla.enabled"));
        config.setMythic_enabled(fileConfiguration.getBoolean("mythic.enabled"));
        config.setPlayer_only(fileConfiguration.getBoolean("player_only"));
        config.setItem_logging(fileConfiguration.getBoolean("item_logging"));

        if(config.isVanilla_enabled())
            for(EntityType type : EntityType.values()) {
                String path = "vanilla.";
                if(!fileConfiguration.contains(path += type.toString().toLowerCase())) continue;
                List<String> data;
                data = fileConfiguration.getStringList(path+".drops");
                config.put_to_vanilla(type, data);
            }

        if(config.isMythic_enabled()) {
            MythicBukkit mythicBukkit = (MythicBukkit) (getServer().getPluginManager().getPlugin("MythicMobs"));
            for (String type : Objects.requireNonNull(mythicBukkit).getMobManager().getMobNames()) {
                String path = "mythic.";
                if(!fileConfiguration.contains(path += type)) continue;
                List<String> data;
                data = fileConfiguration.getStringList(path+".drops");
                config.put_to_mythic(type, data);
            }
        }
    }

    private void loadDropTables(SlimefunMobDrops plugin) {
        FileConfiguration fileConfiguration = plugin.getDropsConfig();

        for (String s : Objects.requireNonNull(fileConfiguration.getConfigurationSection("")).getKeys(false)) {

            double noDrop = fileConfiguration.getDouble(s + ".noDrop");

            List<String> items = fileConfiguration.getStringList(s+ ".items");

            dropTables.put(s, new DropTable(s, noDrop, items));
        }
    }
    public boolean isItem_Logging() {
        return config.isItem_logging();
    }

    public boolean isPlayer_only() {
        return config.isPlayer_only();
    }

    public boolean isVanilla_enabled() {
        return config.isVanilla_enabled();
    }

    public boolean isMythic_enabled() {
        return config.isMythic_enabled();
    }

    public boolean contains_vanilla(EntityType key) {
        return config.contains_vanilla(key);
    }

    public boolean contains_mythic(String key) {
        return config.contains_mythic(key);
    }

    public List<String> get_drops(EntityType key) {
        return config.get_drops(key);
    }

    public List<String> get_drops(String key) {
        return config.get_drops(key);
    }

    public DropTable getTable(String name) {
        return dropTables.get(name);
    }

    public Set<String> getTableNames() {
        return dropTables.keySet();
    }

}
