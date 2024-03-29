package gg.oddysian.adenydd.noponicgyms.storage.registry;

import com.google.common.reflect.TypeToken;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.config.GymConfig;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.*;
import java.util.stream.Collectors;

public class ModeRegistry {
    public static List<Mode> gymModes = new ArrayList<>();

    public static List<GymsRegistry.Gym> gymsInMode(Mode mode) {
        List<GymsRegistry.Gym> list = new ArrayList<>();
            for (GymsRegistry.Gym g: GymsRegistry.getGyms()) {
                if (g.getMode().equalsIgnoreCase(mode.modeName())) {
                    list.add(g);
                }
        }
            list.sort(Comparator.comparing(GymsRegistry.Gym::getWeight));
        return list;
    }

    public static void loadGymModes() {
        gymModes.clear();
        CommentedConfigurationNode rootNode = GymConfig.getConfig().get().getNode("Modes");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                NoponicGyms.log.info(nodeObject + "MODE NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    NoponicGyms.log.info(node + "MODE NODE NULL");
                } else {
                    gymModes.add(new Mode(node));
                }
            }
        }

        gymModes.sort(Comparator.comparing(Mode::getWeight));

    }

    public static List<String> modeList() {
        return gymModes.stream().map(mode -> mode.key).collect(Collectors.toList());
    }

    public static boolean isMode(String name) {
        Iterator var1 = gymModes.iterator();

        Mode mode;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            mode = (Mode) var1.next();
        } while (!mode.key.equalsIgnoreCase(name));

        return true;
    }

    public static Mode getMode(String key) {
        Iterator var1 = gymModes.iterator();

        Mode mode;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            mode = (Mode) var1.next();
        } while (!mode.key.equalsIgnoreCase(key));

        return mode;
    }

    public static class Mode {
        private List<String> info;
        private final String key;
        private String itemString;
        private String display;
        private List<String> loreList;
        private boolean enableNPC;
        private boolean onlyNPC;
        private int weight;

        public Mode(String key) {
            this.key = key;
            this.setWeight(GymConfig.getConfig().get().getNode("Modes", key, "Weight").getInt());
            this.setDisplay(GymConfig.getConfig().get().getNode("Modes", key, "Display").getString());
            this.setItemString(GymConfig.getConfig().get().getNode("Modes", key, "ItemString").getString());
            try {
                this.setLoreList(GymConfig.getConfig().get().getNode("Modes", key, "Lore").getList(TypeToken.of(String.class)));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
            this.setOnlyNPC(GymConfig.getConfig().get().getNode("Modes", key, "NPCOnly").getBoolean());
            this.setNPC(GymConfig.getConfig().get().getNode("Modes", key, "EnableNPC").getBoolean());
            try {
                this.setInfo(GymConfig.getConfig().get().getNode("Modes", key, "Info").getList(TypeToken.of(String.class)));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        }
        public List<String> getFormattedText() {
            List<String> formattedInfo = new ArrayList<>();
            for (String s: this.info) {
                formattedInfo.add(s.replaceAll("&", "§"));
            }
            return formattedInfo;
        }

        public void setInfo(List <String> info) {
            this.info = info;
        }

        public boolean isNPC() {
            return enableNPC;
        }

        public void setNPC(boolean NPC) {
            enableNPC = NPC;
        }

        public boolean isOnlyNPC() {
            return onlyNPC;
        }

        public void setOnlyNPC(boolean onlyNPC) {
            this.onlyNPC = onlyNPC;
        }

        public String modeName() {
            return this.key;
        }

        public String getItemString() {
            return itemString;
        }

        public void setItemString(String itemString) {
            this.itemString = itemString;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public List<String> getLoreList() {
            return loreList;
        }

        public void setLoreList(List<String> loreList) {
            this.loreList = loreList;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
