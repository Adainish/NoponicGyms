package gg.oddysian.adenydd.noponicgyms.methods;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.StoreMethods;
import gg.oddysian.adenydd.noponicgyms.storage.config.Config;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.entity.player.EntityPlayerMP;

public class GymMethods {

    public static void sendChallengeNotification(){

    }

    public static void giveGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {

        EntityPlayerMP playerMP = ServerUtils.getPlayer(gymPlayer.getName());

        if (playerMP == null) {
            NoponicGyms.log.error("A player returned null when trying to hand out a gym badge, ending function");
            return;
        }

        if (gymBadge.getGym() == null || gymBadge.getGym().isEmpty()) {
            NoponicGyms.log.error("A badge was attempted to be handy out, but the gym didn't exist while doing so! Ending function");
            return;
        }

        if (gymBadge.getBadgeName() == null || gymBadge.getBadgeName().isEmpty()) {
            NoponicGyms.log.error("A badge was attempted to be handed out, but the badge name didn't exist while doing so! Ending function");
            return;
        }

        ServerUtils.send(playerMP, "&eCongrats on beating the %gym%&e and obtaining the %badge%".replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        ServerUtils.doBroadcast(Config.getConfig().get().getNode("ServerPrefix").getString() + " " + "&c%player% has defeated the %gym% and obtained the %badge% badge".replaceAll("%player%", gymPlayer.getName()).replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        StoreMethods.writeGymBadge(playerMP, gymBadge);
    }

    public static void takeGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {
        ServerUtils.send(ServerUtils.getPlayer(gymPlayer.getName()), "&cYour %badge% &cwas taken away by a Gym Leader!".replaceAll("%badge%", gymBadge.getBadgeName()));
        gymPlayer.removeBadge(gymBadge);
        StoreMethods.updateGymPlayerData(gymPlayer);
    }

    public static void takeChallenge() {

    }


    public static void updateGym(GymsRegistry.Gym gym, boolean open) {
        if (gym == null)
            return;

        if (open && !gym.opened) {
            openGym(gym);
        }

        if (!open && gym.opened) {

            closeGym(gym);
        }

    }

    public static void closeGym(GymsRegistry.Gym gym) {

        if (!gym.opened)
            return;

        ServerUtils.doBroadcast("&c&lthe %gym% Gym is now closed!".replaceAll("%gym%", gym.display));
        gym.opened = false;

    }

    public static void openGym(GymsRegistry.Gym gym) {

        if (gym.opened)
            return;

        ServerUtils.doBroadcast("&a&lthe %gym% Gym is now open!".replaceAll("%gym%", gym.display));
        gym.opened = true;

    }
}
