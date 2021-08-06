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

public class GymObj {
    public static List<Gym> gyms = new ArrayList<>();

    public static void loadGyms() {
        gyms.clear();
        CommentedConfigurationNode rootNode = GymConfig.getConfig().get().getNode("Gyms");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                NoponicGyms.log.info(nodeObject + "GYM NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    NoponicGyms.log.info(node + "GYM NODE NULL");
                } else {
                    NoponicGyms.log.info(node + "NEW GYM ADDED");
                    gyms.add(new Gym(node));
                }
            }
        }

    }

    public static List<String> gymList() {
        return gyms.stream().map(gym -> gym.key).collect(Collectors.toList());
    }

    public static boolean isGym(String name) {
        Iterator var1 = gyms.iterator();

        Gym gym;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            gym = (Gym) var1.next();
        } while (!gym.key.equalsIgnoreCase(name));

        return true;
    }

    public static Gym getGym(String key) {
        Iterator var1 = gyms.iterator();

        Gym gym;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            gym = (Gym) var1.next();
        } while (!gym.key.equalsIgnoreCase(key));

        return gym;
    }

    public static class Gym {
        public String key;
        public String permission = "DEFAULT IMAGE";
        public String tier;
        public String leadermessage;
        public int levelcap;
        public String display;
        public List<String> lore;

        public Gym(String key) {

            this.key = key;
            this.display = GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Display").getString();
            this.permission = GymConfig.getConfig().get().getNode("Gyms", key, "Permission").getString();
            this.tier = GymConfig.getConfig().get().getNode("Gyms", key, "Tier").getString();
            this.leadermessage = GymConfig.getConfig().get().getNode("Gyms", key, "LeaderMessage").getString();
            this.levelcap = GymConfig.getConfig().get().getNode("Gyms", key, "LevelCap").getInt();
            try {
                this.lore = GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Lore").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        }
    }
}
