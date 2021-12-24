package gg.oddysian.adenydd.noponicgyms.listener;

import com.cable.library.tasks.Task;
import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.StoreMethods;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.tasks.CleanRentalsTask;
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

        for (GymsRegistry.Gym gym: GymsRegistry.gyms) {
            if (gym == null)
                continue;

            if (ServerUtils.isPlayerOnline(event.player.getUniqueID())) {
                playerMP = ServerUtils.getPlayer(event.player.getName());

                if (gym.getPermission().isEmpty() || gym.getPermission() == null)
                    continue;

                if (PermissionUtils.canUse(gym.getPermission(), playerMP)) {
                    if (!gym.getGymLeaderList().contains(playerMP.getName()))
                        gym.getGymLeaderList().add(playerMP.getName());
                    GymMethods.updateGym(gym, true);
                }
            }
        }
        Task.builder().execute(new CleanRentalsTask(event.player.getUniqueID())).delay(5).iterations(1).build();
    }

    @SubscribeEvent
    public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayerMP playerMP;

        for (GymsRegistry.Gym gym: GymsRegistry.gyms) {

            if (gym == null)
                continue;

            playerMP = (EntityPlayerMP) event.player;

            if (playerMP == null) {
                if (gym.getGymLeaderList().isEmpty())
                    GymMethods.updateGym(gym, false);
                continue;
            }

            if (gym.getPermission().isEmpty() || gym.getPermission() == null)
                continue;

                if (PermissionUtils.canUse(gym.getPermission(), playerMP)) {

                    gym.getGymLeaderList().remove(playerMP.getName());

                    if (gym.getGymLeaderList().isEmpty())
                    GymMethods.updateGym(gym, false);
                }
        }
    }
}
