package gg.oddysian.adenydd.noponicgyms.listener;

import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.StoreMethods;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {
    public PlayerListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }



    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        GymPlayer gymPlayer = StoreMethods.getGymPlayer(event.player);
        if (gymPlayer == null) {
            StoreMethods.writeGymPlayer(event.player);
            GymPlayer newPlayer = StoreMethods.getGymPlayer(event.player);
            GymPlayerWrapper.gymPlayerHashMap.put(event.player.getUniqueID(), newPlayer);
        } else GymPlayerWrapper.gymPlayerHashMap.put(event.player.getUniqueID(), gymPlayer);

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
