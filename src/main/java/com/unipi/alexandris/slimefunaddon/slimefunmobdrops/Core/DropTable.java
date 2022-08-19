package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Core;


import java.util.ArrayList;
import java.util.List;

public class DropTable {

    private static class Drop {
        private final int min;
        private final int max;
        private final String type;
        private final String name;
        private final double weight;

        public Drop(int min, int max, String type, String name, double weight) {
            this.min = min;
            this.max = max;
            this.type = type;
            this.name = name;
            this.weight = weight;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public double getWeight() {
            return weight;
        }
    }

    private final String tableName;
    double noDropWeight = 0;
    private final List<Drop> drops = new ArrayList<>();

    public DropTable(String tableName, double noDropWeight, List<String> items) {
        this.tableName = tableName;
        this.noDropWeight = noDropWeight;
        double total = noDropWeight;

        for(String item : items) {
            String[] data = item.split(" ");
            String type = data[0].split(":")[0];
            String name = data[0].split(":")[1];
            String[] range = data[1].split("-");
            int min = Integer.parseInt(range[0]);
            int max;
            if(range.length > 1) max = Integer.parseInt(range[1]);
            else max = min;
            total += Double.parseDouble(data[2]);
            drops.add(new Drop(min, max, type, name, total));
        }
    }

    public String getTableName() {
        return tableName;
    }

    public double getNoDropWeight() {
        return noDropWeight;
    }

    public Drop getDrop(int index) {
        return drops.get(index);
    }
}
