package gg.oddysian.adenydd.noponicgyms.config;

import lombok.SneakyThrows;

import java.util.Arrays;

public class GymConfig extends Configurable {
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
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
        this.get().getNode(new Object[]{"Modes", "Tier1", "PlayerGym"}).setValue(true).setComment("Is this a Player Gym or a NPC Gym?");
        this.get().getNode(new Object[]{"Modes", "Tier2", "PlayerGym"}).setValue(true).setComment("Is this a Player Gym or a NPC Gym?");
        this.get().getNode(new Object[]{"Modes", "NPCGym", "PlayerGym"}).setValue(true).setComment("Is this a Player Gym or a NPC Gym?");

        this.get().getNode(new Object[]{"Gyms", "Example", "Tier"}).setValue("Tier1");
        this.get().getNode(new Object[]{"Gyms", "Example", "LeaderMessage"}).setValue("&c%leader% of the %gym% has joined the server!");
        this.get().getNode(new Object[]{"Gyms", "Example", "Permission"}).setValue("gyms.examplegym.leader");
        this.get().getNode(new Object[]{"Gyms", "Example", "LevelCap"}).setValue(10);
        this.get().getNode(new Object[]{"Gyms", "Example", "UI", "Display"}).setValue("&b&lExample Gym");
        this.get().getNode(new Object[]{"Gyms", "Example", "UI", "Lore"}).setValue(Arrays.asList("&cThis is the %gym% Gym", "The following leaders are online: %availableleaders%", "Gym Level: %gymlevel%"));
        this.get().getNode(new Object[]{"Gyms", "Example", "Badge"}).setValue("minecraft:paper");
    }

    public String getConfigName() {
        return "Gyms.conf";
    }

    public GymConfig() {}
}
