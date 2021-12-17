package gg.oddysian.adenydd.noponicgyms.methods;

import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.entity.player.EntityPlayerMP;

public class GymMethods {

    public static void sendChallengeNotification(){

    }

    public static void giveGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {

        EntityPlayerMP playerMP = ServerUtils.getPlayer(gymPlayer.getName());
        ServerUtils.send(playerMP, "&eCongrats on beating the %gym%&e and obtaining the %badge%".replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        ServerUtils.doBroadcast("&c%player% has defeated the %gym% and obtained the %badge% badge".replaceAll("%player%", gymPlayer.getName()).replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
    }

    public static void takeGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {
        ServerUtils.send(ServerUtils.getPlayer(gymPlayer.getName()), "&cYour %badge% &cwas taken away by a Gym Leader!".replaceAll("%badge%", gymBadge.getBadgeName()));
    }

    public static void takeChallenge() {

    }


    public static void updateGym(GymObj.Gym gym, boolean open) {
        if (gym == null)
            return;

        if (open && !gym.opened) {
            openGym(gym);
        }

        if (!open && gym.opened) {

            closeGym(gym);
        }

    }

    public static void closeGym(GymObj.Gym gym) {

        if (!gym.opened)
            return;

        ServerUtils.doBroadcast("&c&lthe %gym% Gym is now closed!".replaceAll("%gym%", gym.display));
        gym.opened = false;

    }

    public static void openGym(GymObj.Gym gym) {

        if (gym.opened)
            return;

        ServerUtils.doBroadcast("&a&lthe %gym% Gym is now open!".replaceAll("%gym%", gym.display));
        gym.opened = true;

    }
}
