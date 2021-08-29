package gg.oddysian.adenydd.noponicgyms.listener;

import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {
    public PlayerListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(event.player.getUniqueID());
        if (gymPlayer == null) {
            GymPlayer.gymPlayerHashMap.put(event.player.getUniqueID(), new GymPlayer(event.player.getUniqueID()));
        } else GymPlayer.gymPlayerHashMap.put(event.player.getUniqueID(), gymPlayer);

    }
}
