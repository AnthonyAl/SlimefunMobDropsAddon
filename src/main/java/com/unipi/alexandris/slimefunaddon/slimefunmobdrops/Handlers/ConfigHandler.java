package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core.Config;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings("unused")
public final class ConfigHandler {

    private final Config config = new Config();

    public ConfigHandler(SlimefunMobDrops plugin) {
        FileConfiguration fileConfiguration = plugin.getConfig();

        config.setVanilla_enabled(fileConfiguration.getBoolean("vanilla.enabled"));
        config.setMythic_enabled(fileConfiguration.getBoolean("mythic.enabled"));
        config.setPlayer_only(fileConfiguration.getBoolean("player_only"));

        if(config.isVanilla_enabled())
            for(EntityType type : EntityType.values()) {
                String path = "vanilla.";
                if(!fileConfiguration.contains(path += type.toString().toLowerCase())) continue;
                List<String>[] mobs = new List[2];
                mobs[0] = (List<String>) fileConfiguration.getList(path+".common_items");
                mobs[1] = (List<String>) fileConfiguration.getList(path+".rare_items");
                config.put_to_vanilla(type, mobs);
            }

        if(config.isMythic_enabled()) {
            MythicBukkit mythicBukkit = (MythicBukkit) (getServer().getPluginManager().getPlugin("MythicMobs"));
            for (String type : Objects.requireNonNull(mythicBukkit).getMobManager().getMobNames()) {
                String path = "mythic.";
                if(!fileConfiguration.contains(path += type.toLowerCase())) continue;
                List<String>[] mobs = new List[2];
                mobs[0] = (List<String>) fileConfiguration.getList(path+".common_items");
                mobs[1] = (List<String>) fileConfiguration.getList(path+".rare_items");
                config.put_to_mythic(type, mobs);
            }
        }
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

    public List<String> get_items(EntityType key, int rarity) {
        return config.get_items(key, rarity);
    }

    public List<String> get_items(String key, int rarity) {
        return config.get_items(key, rarity);
    }

}
