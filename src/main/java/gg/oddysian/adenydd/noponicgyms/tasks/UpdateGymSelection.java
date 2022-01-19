package gg.oddysian.adenydd.noponicgyms.tasks;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.cable.library.tasks.Task;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymSelection;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class UpdateGymSelection implements Runnable{

    @Override
    public void run() {

        if (NoponicGyms.selectionList.isEmpty())
            return;

        List <GymSelection> outdated = new ArrayList <>();
        for (int i = 0; i < NoponicGyms.selectionList.size(); i++) {
            GymSelection selection = NoponicGyms.selectionList.get(i);
            GymPlayer leader = GymPlayerWrapper.gymPlayerHashMap.get(selection.getGymLeader().getUuid());
            GymPlayer challenger = GymPlayerWrapper.gymPlayerHashMap.get(selection.getChallenger().getUuid());
            if (leader == null) {
                outdated.add(selection);
                continue;
            }
            if (challenger == null) {
                outdated.add(selection);
                continue;
            }
            selection.setGymLeader(leader);
            selection.setChallenger(challenger);

            if (selection.isShouldBattle()) {
                EntityPlayerMP lead = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(leader.getUuid());
                EntityPlayerMP pl = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(challenger.getUuid());

                UIManager.closeUI(lead);
                UIManager.closeUI(pl);
                GymMethods.sendRentals(selection.getGymPokemon(), leader, selection.getGym());
                GymMethods.updateChallengerParty(selection.getChallengerPokemon(), challenger);
                Task.builder().delay(40).iterations(1).execute(() -> {GymMethods.startBattle(lead, pl);}).build();
                outdated.add(selection);
            }
        }
        NoponicGyms.selectionList.removeAll(outdated);
    }
}
