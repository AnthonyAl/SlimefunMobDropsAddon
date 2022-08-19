package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;

public final class Config {

    private boolean item_logging;

    private boolean player_only;

    private boolean vanilla_enabled;

    private boolean mythic_enabled;

    private final HashMap<EntityType, List<String>> vanilla = new HashMap<>();

    private final HashMap<String, List<String>> mythic = new HashMap<>();


    public boolean isItem_logging() {
        return item_logging;
    }

    public void setItem_logging(boolean item_logging) {
        this.item_logging = item_logging;
    }

    public boolean isPlayer_only() {
        return player_only;
    }

    public void setPlayer_only(boolean player_only) {
        this.player_only = player_only;
    }

    public boolean isVanilla_enabled() {
        return vanilla_enabled;
    }

    public void setVanilla_enabled(boolean vanilla_enabled) {
        this.vanilla_enabled = vanilla_enabled;
    }

    public boolean isMythic_enabled() {
        return mythic_enabled;
    }

    public void setMythic_enabled(boolean mythic_enabled) {
        this.mythic_enabled = mythic_enabled;
    }

    public void put_to_vanilla(EntityType key, List<String> value) {
        vanilla.put(key, value);
    }

    public void put_to_mythic(String key, List<String> value) {
        mythic.put(key, value);
    }

    public boolean contains_vanilla(EntityType key) {
        return vanilla.containsKey(key);
    }

    public boolean contains_mythic(String key) {
        return mythic.containsKey(key);
    }

    public List<String> get_drops(EntityType key) {
        return vanilla.getOrDefault(key, null);
    }

    public List<String> get_drops(String key) {
        return mythic.getOrDefault(key, null);
    }

}
