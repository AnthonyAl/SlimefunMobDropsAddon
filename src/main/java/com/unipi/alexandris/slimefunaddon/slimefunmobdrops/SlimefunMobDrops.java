package com.unipi.alexandris.slimefunaddon.slimefunmobdrops;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.CommandsHandler;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.ConfigHandler;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers.EventsHandler;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SlimefunMobDrops extends JavaPlugin implements SlimefunAddon {

    public ConfigHandler config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        config = new ConfigHandler(this);
        Objects.requireNonNull(getCommand("sfmobdrops")).setExecutor(new CommandsHandler(this));
        getServer().getPluginManager().registerEvents(new EventsHandler(this), this);
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

}
