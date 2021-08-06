package gg.oddysian.adenydd.noponicgyms.config;

import lombok.SneakyThrows;

import java.util.Arrays;

public class GymConfig extends Configurable {
    private static GymConfig config;

    public static GymConfig getConfig() {
        if (config == null) {
            config = new GymConfig();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }

    @SneakyThrows
    public void populate() {
        this.get().getNode("Modes", "Tiers").setValue(Arrays.asList("Tier1", "Tier2", "NPCGym"));

        this.get().getNode("Gyms", "Example", "Tier").setValue("Tier1");
        this.get().getNode("Gyms", "Example", "LeaderMessage").setValue("&c%leader% of the %gym% has joined the server!");
        this.get().getNode("Gyms", "Example", "Permission").setValue("gyms.examplegym.leader");
        this.get().getNode("Gyms", "Example", "LevelCap").setValue(10);
        this.get().getNode("Gyms", "Example", "UI", "Display").setValue("&b&lExample Gym");
        this.get().getNode("Gyms", "Example", "UI", "Lore").setValue(Arrays.asList("&cThis is the %gym% Gym", "The following leaders are online: %availableleaders%", "Gym Level: %gymlevel%"));
        this.get().getNode("Gyms", "Example", "Badge").setValue("minecraft:paper");
    }

    public String getConfigName() {
        return "Gyms.conf";
    }

    public GymConfig() {}
}
