package gg.oddysian.adenydd.noponicgyms.methods;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.StoreMethods;
import gg.oddysian.adenydd.noponicgyms.storage.config.LanguageConfig;
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

        String sendMSG = LanguageConfig.getConfig().get().getNode("Badge", "Received").getString();
        String broadCast = LanguageConfig.getConfig().get().getNode("Badge", "Broadcast").getString();
        ServerUtils.send(playerMP, sendMSG.replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        ServerUtils.doBroadcast(broadCast.replaceAll("%player%", gymPlayer.getName()).replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        StoreMethods.writeGymBadge(playerMP, gymBadge);
    }

    public static void takeGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {
        String takeMSG = LanguageConfig.getConfig().get().getNode("Badge", "Taken").getString();
        ServerUtils.send(ServerUtils.getPlayer(gymPlayer.getName()), takeMSG.replaceAll("%badge%", gymBadge.getBadgeName()));
        gymPlayer.removeBadge(gymBadge);
        StoreMethods.updateGymPlayerData(gymPlayer);
    }

    public static void takeChallenge() {

    }


    public static void updateGym(GymsRegistry.Gym gym, boolean open) {
        if (gym == null)
            return;

        if (open && !gym.isOpened()) {
            openGym(gym);
        }
        if (!open && gym.isOpened()) {
            closeGym(gym);
        }

    }

    public static void closeGym(GymsRegistry.Gym gym) {

        if (!gym.isOpened())
            return;

        String closeMSG = LanguageConfig.getConfig().get().getNode("Gym", "CloseGym").getString();
        ServerUtils.doBroadcast(closeMSG.replaceAll("%gym%", gym.getDisplay()));
        gym.setOpened(false);

    }

    public static void openGym(GymsRegistry.Gym gym) {

        if (gym.isOpened())
            return;

        String openMSG = LanguageConfig.getConfig().get().getNode("Gym", "OpenGym").getString();
        ServerUtils.doBroadcast(openMSG.replaceAll("%gym%", gym.getDisplay()));
        gym.setOpened(true);

    }
}
