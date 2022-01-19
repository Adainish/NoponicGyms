package gg.oddysian.adenydd.noponicgyms.listener;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class BattleListener {
    public static ArrayList<BattleControllerBase> activeBattles = new ArrayList<>();

    @SubscribeEvent
    public static void onGymBattleEnd(BattleEndEvent event) {
        if (!activeBattles.contains(event.bc))
            return;
    }
}
