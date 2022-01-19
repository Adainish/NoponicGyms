package gg.oddysian.adenydd.noponicgyms.wrapper;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GymSelection {
    private GymPlayer gymLeader;
    private GymPlayer challenger;
    private GymsRegistry.Gym gym;
    private List<Pokemon> challengerPokemon = new ArrayList <>();
    private List <Pokemon> gymPokemon = new ArrayList <>();

    private boolean shouldBattle;

    public GymSelection(GymsRegistry.Gym gym, GymPlayer gymLeader, GymPlayer challenger) {
        setChallenger(challenger);
        setGymLeader(gymLeader);
        setGym(gym);
    }

    public GymPlayer getGymLeader() {
        return gymLeader;
    }

    public void setGymLeader(GymPlayer gymLeader) {
        this.gymLeader = gymLeader;
    }

    public GymPlayer getChallenger() {
        return challenger;
    }

    public void setChallenger(GymPlayer challenger) {
        this.challenger = challenger;
    }

    public GymsRegistry.Gym getGym() {
        return gym;
    }

    public void setGym(GymsRegistry.Gym gym) {
        this.gym = gym;
    }

    public boolean isShouldBattle() {
        return(gymLeader.isReadyForBattle() && challenger.isReadyForBattle());
    }

    public void updateEntity(GymPlayer player, boolean ready) {
        if (gymLeader.getUuid().equals(player.getUuid())) {
            gymLeader.setReadyForBattle(ready);
        }
        if (challenger.getUuid().equals(player.getUuid()))
            challenger.setReadyForBattle(ready);
    }


    public static GymSelection getSelection(UUID uuid) {
        for (GymSelection s:NoponicGyms.selectionList) {
            if (s.getChallenger().getUuid().equals(uuid))
                return s;
            if (s.getGymLeader().getUuid().equals(uuid))
                return s;
        }
        return null;
    }
    public static GymSelection getGymSelection(GymPlayer player) {
        for (int i = 0; i < NoponicGyms.selectionList.size(); i++) {
            GymSelection selection = NoponicGyms.selectionList.get(i);
            if (selection.getGymLeader().getUuid().equals(player.getUuid()) || selection.getChallenger().getUuid().equals(player.getUuid()))
                return selection;
        }
        return null;
    }

    public List <Pokemon> getGymPokemon() {
        return gymPokemon;
    }

    public void setGymPokemon(List <Pokemon> gymPokemon) {
        this.gymPokemon = gymPokemon;
    }

    public List <Pokemon> getChallengerPokemon() {
        return challengerPokemon;
    }

    public void setChallengerPokemon(List <Pokemon> challengerPokemon) {
        this.challengerPokemon = challengerPokemon;
    }
}
