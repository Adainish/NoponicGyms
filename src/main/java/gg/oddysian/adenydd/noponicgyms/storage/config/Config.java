package gg.oddysian.adenydd.noponicgyms.storage.config;

import lombok.SneakyThrows;

public class Config extends Configurable {
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
        this.get().getNode("ServerPrefix").setValue("&b&lPoppi").setComment("Plugin prefix for messages");
    }

    public String getConfigName() {
        return "Config.conf";
    }

    public Config() {}
}
