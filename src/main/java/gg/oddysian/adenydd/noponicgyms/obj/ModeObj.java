package gg.oddysian.adenydd.noponicgyms.obj;

import com.google.common.reflect.TypeToken;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.config.GymConfig;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModeObj {
    public static List<Mode> gymModes = new ArrayList<>();

    public static void loadGymModes() {
        gymModes.clear();
        CommentedConfigurationNode rootNode = GymConfig.getConfig().get().getNode("Gyms");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                NoponicGyms.log.info(nodeObject + "MODE NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    NoponicGyms.log.info(node + "MODE NODE NULL");
                } else {
                    NoponicGyms.log.info(node + "NEW MODE ADDED");
                    gymModes.add(new Mode(node));
                }
            }
        }

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
        public List<String> info;
        public String key;
        public String permission = "DEFAULT IMAGE";
        public Mode(String key) {
            this.key = key;

            try {
                this.info = GymConfig.getConfig().get().getNode("Book", key, "Text").getList(TypeToken.of(String.class));
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
    }
}
