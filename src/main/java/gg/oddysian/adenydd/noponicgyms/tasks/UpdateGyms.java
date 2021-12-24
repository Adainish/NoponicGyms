package gg.oddysian.adenydd.noponicgyms.tasks;

import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UpdateGyms implements Runnable{

    @Override
    public void run() {

            for (GymsRegistry.Gym gym: GymsRegistry.gyms) {

                List <String> toRemove = new ArrayList <>();
                List<String> toUpdate = new ArrayList <>();

                if (gym == null)
                    continue;

                if (gym.getGymLeaderList().isEmpty())
                    GymMethods.updateGym(gym, false);


                for (EntityPlayerMP player:ServerUtils.getInstance().getPlayerList().getPlayers()) {
                    if (player == null) {
                        continue;
                    }

                    if (gym.getPermission() == null || gym.getPermission().isEmpty() )
                        continue;

                    if (!PermissionUtils.canUse(gym.getPermission(), player)) {

                        toRemove.add(player.getName());

                    } else {
                        if (!gym.getGymLeaderList().contains(player.getName())) {
                            toUpdate.add(player.getName());
                        }
                    }
                }

                for (String s:gym.getGymLeaderList()) {
                    if (toRemove.contains(s) || toUpdate.contains(s))
                        continue;

                    EntityPlayerMP player = ServerUtils.getPlayer(s);
                    if (player == null) {
                        toRemove.add(s);
                        continue;
                    }

                    if (gym.getPermission() == null || gym.getPermission().isEmpty())
                        continue;

                    if (!PermissionUtils.canUse(gym.getPermission(), player)) {
                        toRemove.add(s);
                    } else if (!gym.getGymLeaderList().isEmpty() && !gym.getGymLeaderList().contains(s)) {
                        toUpdate.add(s);
                    }
                }
                gym.getGymLeaderList().removeAll(toRemove);
                gym.getGymLeaderList().addAll(toUpdate);
                GymMethods.updateGym(gym, !gym.getGymLeaderList().isEmpty());
            }
    }
}
