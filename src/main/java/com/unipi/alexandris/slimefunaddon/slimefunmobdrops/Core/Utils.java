package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Utils {

    public static boolean calcDropChance(double rarity) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        return r.nextDouble(100) < rarity;
    }

}
