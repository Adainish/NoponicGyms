package gg.oddysian.adenydd.noponicgyms.wrapper;

import gg.oddysian.adenydd.noponicgyms.capability.GymBadgeCapability;
import gg.oddysian.adenydd.noponicgyms.capability.interfaces.GymBadge;
import gg.oddysian.adenydd.noponicgyms.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class GymPlayer {
    public static HashMap<UUID, GymPlayer> gymPlayerHashMap = new HashMap<>();

    private EntityPlayerMP player;
    private UUID uuid;
    private long lastUpdated;

    private List<GymObj.Gym> gymList = GymObj.gyms;
    private List<GymBadge> badges = new ArrayList<>();


    public GymPlayer(UUID uuid) {
        this.uuid = uuid;
        this.player = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(uuid);
        this.lastUpdated = System.currentTimeMillis();
        loadBadges();
    }



    public List<String> badgeList() {
        return this.badges.stream().map(GymBadge::getGym).collect(Collectors.toList());
    }

    public boolean isBadge(String name) {
        Iterator var1 = this.badges.iterator();

        GymBadge badge;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            badge = (GymBadge) var1.next();
        } while (!badge.getGym().equalsIgnoreCase(name));

        return true;
    }

    public GymBadge getBadge(String key) {
        Iterator var1 = badges.iterator();

        GymBadge badge;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            badge = (GymBadge) var1.next();
        } while (!badge.getGym().equalsIgnoreCase(key));

        return badge;
    }

    public EntityPlayerMP getPlayer() {
        return player;
    }
    public List<GymBadge> getBadges() {return badges;}
    public long getLastUpdated() {
        return lastUpdated;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setBadges(List<GymBadge> badges) {
        this.badges = badges;
    }
    public void setPlayer(EntityPlayerMP player) {
        this.player = player;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void updateBadges(GymBadge badge) {
        this.badges.add(badge);
    }

    public void loadBadges() {
        for (GymObj.Gym gym:gymList) {
            GymBadge gymBadge = new GymBadge();
            gymBadge.setGym(gym.key);
            gymBadge.setBadgeName(gym.display);
            gymBadge.setItemstring(gym.badgeitemstring);
            gymBadge.setBadgelore(gym.lore);
            gymBadge.setBadgedisplay(gym.display);
            badges.add(gymBadge);
            badgeList().add(gym.key);
        }
    }
}
