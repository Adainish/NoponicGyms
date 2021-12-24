package gg.oddysian.adenydd.noponicgyms.tasks;

import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;
import java.util.function.Consumer;

public class StorePokemonTask implements Consumer<Task> {

    UUID uuid;

    public StorePokemonTask(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void accept(Task task) {

        //Define the storage of the players PC
        PCStorage pc = Pixelmon.storageManager.getPCForPlayer(uuid);
        //Define the Storage of the players party
        PlayerPartyStorage playerParty = Pixelmon.storageManager.getParty(uuid);

        if(playerParty.countPokemon() == 0)
            return;

        for (Pokemon poke : playerParty.getAll()) {
            if(poke != null) {
                poke.retrieve(); //First retrieve the Pokemon so it doesn't glitch out
                pc.add(poke); //Add Poke to PC
                playerParty.set(playerParty.getPosition(poke), null); //Delete the Pokemon
            }
        }
    }
}
