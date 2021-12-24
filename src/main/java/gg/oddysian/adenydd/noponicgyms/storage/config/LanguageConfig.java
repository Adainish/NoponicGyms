package gg.oddysian.adenydd.noponicgyms.storage.config;

import lombok.SneakyThrows;

public class LanguageConfig extends Configurable {
    private static LanguageConfig config;

    public static LanguageConfig getConfig() {
        if (config == null) {
            config = new LanguageConfig();
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
        this.get().getNode("Gym", "OpenGym").setValue("&a&lthe %gym% Gym is now open!").setComment("Sent when a Gym Opens");
        this.get().getNode("Gym", "CloseGym").setValue("&c&lthe %gym% Gym is now closed!").setComment("Sent when a Gym Closes");
        this.get().getNode("Badge", "Broadcast").setValue("&c%player% has defeated the %gym% and obtained the %badge% badge").setComment("Broadcasted to all players when a player receives a badge");
        this.get().getNode("Badge", "Received").setValue("&eCongrats on beating the %gym%&e and obtaining the %badge%").setComment("Sent when a player receives a badge");
        this.get().getNode("Badge", "Taken").setValue("&cYour %badge% &cwas taken away by a Gym Leader!").setComment("Sent when a badge has been taken from a player");
        this.get().getNode("").setValue("").setComment("");

    }

    public String getConfigName() {
        return "LanguageConfig.conf";
    }

    public LanguageConfig() {}
}
