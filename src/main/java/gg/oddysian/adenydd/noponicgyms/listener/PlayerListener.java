package gg.oddysian.adenydd.noponicgyms.listener;

import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.capability.BadgeCapability;
import gg.oddysian.adenydd.noponicgyms.storage.capability.GymBadgeCapability;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.IBadges;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.IGymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {
    public PlayerListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onPlayerCloned(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {

        IBadges badges = event.getOriginal().getCapability(BadgeCapability.I_GYM_BADGES_CAPABILITY, null);
        IBadges newBadges = event.getEntityLiving().getCapability(BadgeCapability.I_GYM_BADGES_CAPABILITY, null);

        newBadges.setBadges(badges.getBadges());

        IGymBadge gymBadges = event.getOriginal().getCapability(GymBadgeCapability.I_GYM_BADGE_CAPABILITY, null);
        IGymBadge currentGymBadges = event.getEntityLiving().getCapability(GymBadgeCapability.I_GYM_BADGE_CAPABILITY, null);

    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(event.player.getUniqueID());
        if (gymPlayer == null) {
            GymPlayer.gymPlayerHashMap.put(event.player.getUniqueID(), new GymPlayer(event.player.getUniqueID()));
        } else GymPlayer.gymPlayerHashMap.put(event.player.getUniqueID(), gymPlayer);

        EntityPlayerMP playerMP;

        for (GymObj.Gym gym:GymObj.gyms) {
            if (ServerUtils.isPlayerOnline(event.player.getUniqueID())) {
                playerMP = ServerUtils.getPlayer(event.player.getName());

                if (!gym.gymLeaderList.contains(playerMP.getName()))
                gym.gymLeaderList.add(playerMP.getName());

                if (PermissionUtils.hasPermission(gym.permission, playerMP)) {
                    GymMethods.updateGym(gym, true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayerMP playerMP;

        for (GymObj.Gym gym:GymObj.gyms) {

            playerMP = ServerUtils.getPlayer(event.player.getName());

                if (PermissionUtils.hasPermission(gym.permission, playerMP)) {

                    gym.gymLeaderList.remove(playerMP.getName());

                    if (gym.gymLeaderList.isEmpty())
                    GymMethods.updateGym(gym, false);
                }
        }
    }
}
