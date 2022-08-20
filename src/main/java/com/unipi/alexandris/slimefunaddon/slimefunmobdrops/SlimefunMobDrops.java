package com.unipi.alexandris.slimefunaddon.slimefunmobdrops;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.CommandsHandler;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.ConfigHandler;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.EventsMythicHandler;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.EventsVanillaHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class SlimefunMobDrops extends JavaPlugin implements SlimefunAddon {

    public ConfigHandler config;
    private FileConfiguration dropTablesConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        createDropsConfig();


        config = new ConfigHandler(this);
        Objects.requireNonNull(getCommand("sfmobdrops")).setExecutor(new CommandsHandler(this));
        getServer().getPluginManager().registerEvents(new EventsVanillaHandler(this), this);
        getServer().getPluginManager().registerEvents(new EventsMythicHandler(this), this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

    public FileConfiguration getDropsConfig() {
        return this.dropTablesConfig;
    }

    private void createDropsConfig() {
        File dropTablesConfigFile = new File(getDataFolder(), "DropTables.yml");
        if (!dropTablesConfigFile.exists()) {
            dropTablesConfigFile.getParentFile().mkdirs();
            saveResource("DropTables.yml", false);
        }

        dropTablesConfig = new YamlConfiguration();
        YamlConfiguration.loadConfiguration(dropTablesConfigFile);
    }

}
