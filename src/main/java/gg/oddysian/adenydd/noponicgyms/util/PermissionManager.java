package gg.oddysian.adenydd.noponicgyms.util;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class PermissionManager {
    // Base
    private static final String BASE_USER = NoponicGyms.MOD_ID + ".user.";
    private static final String BASE_LEADER = NoponicGyms.MOD_ID + ".leader.";
    private static final String BASE_ADMIN = NoponicGyms.MOD_ID + ".admin.";

    // Commands
    public static final String ADMIN_RELOAD = BASE_ADMIN + "reload";
    public static final String LEADER_GIVE = BASE_LEADER + "givebadge";
    public static final String LEADER_TAKE = BASE_LEADER + "takebadge";
    public static final String ADMIN_GIVE = BASE_ADMIN + "giveleader";
    public static final String ADMIN_TAKE = BASE_ADMIN + "takeleader";

    public static void giveAllGymPerms(EntityPlayerMP player) {
        List<String> permissions = Arrays.asList(LEADER_GIVE, LEADER_TAKE);
        for (String perm : permissions) {
            ServerUtils.runCommand("".replace("@pl", player.getName()).replace("%perm%", perm));
        }
    }

    public static void removeAllGymPerms(EntityPlayerMP player) {
        List<String> permissions = Arrays.asList(LEADER_GIVE, LEADER_TAKE);
        for (String perm : permissions) {
            ServerUtils.runCommand("".replace("@pl", player.getName()).replace("%perm%", perm));
        }
    }
}
