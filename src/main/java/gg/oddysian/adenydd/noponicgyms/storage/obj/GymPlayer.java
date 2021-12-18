package gg.oddysian.adenydd.noponicgyms.storage.obj;

import gg.oddysian.adenydd.noponicgyms.battles.handling.Queue;
import net.minecraftforge.common.UsernameCache;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GymPlayer {

    private UUID uuid;
    private String name;
    private List<GymBadge> badges = new ArrayList<>();

    public GymPlayer(UUID uuid) {
        setUuid(uuid);
        setName(UsernameCache.getLastKnownUsername(uuid));
    }

    public void addBadge(GymBadge badge) {
        if (!this.badges.contains(badge)) {
            badges.add(badge);
        }
    }


    public List<GymBadge> getBadges() {
        return badges;
    }

    public void removeBadge(GymBadge badge) {
        this.badges.remove(badge);
    }

    public boolean hasSpecificBadge(String gymName) {
        for (GymBadge badge:badges) {
            if (badge.getBadgeName().equalsIgnoreCase(gymName))
                return true;
        }
        return false;
    }

    public boolean hasSpecificBadge(GymBadge parsedBadge) {
        for (GymBadge badge:badges) {
            if (badge.equals(parsedBadge))
                return true;
        }
        return false;
    }

    public GymBadge returnSpecificBadge(String gymName) {
        for (GymBadge badge:badges) {
            if (badge.getBadgeName().equalsIgnoreCase(gymName))
                return badge;
        }
        return null;
    }

    public void setBadges(List<GymBadge> badges) {
        this.badges = badges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
