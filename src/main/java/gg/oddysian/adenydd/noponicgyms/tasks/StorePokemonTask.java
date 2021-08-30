package gg.oddysian.adenydd.noponicgyms.tasks;

import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import java.util.UUID;
import java.util.function.Consumer;

public class StorePokemonTask implements Consumer<Task> {

    UUID uuid;

    public StorePokemonTask(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void accept(Task task) {

        PlayerPartyStorage pps = Pixelmon.storageManager.getParty(uuid); //Declare player party storage
        PCStorage pc = Pixelmon.storageManager.getPCForPlayer(uuid); //Declare player computer

        for (Pokemon pokemon: pps.getAll()) { //loop through all entries in the party storage
            pc.add(pokemon); //add to computer to ensure entity not null
            pps.set(pokemon.getPosition(), null); //null the pokemon in party
        }



    }
}
