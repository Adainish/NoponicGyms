package gg.oddysian.adenydd.noponicgyms.tasks;

import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PokemonStorage;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import java.util.UUID;
import java.util.function.Consumer;

public class CleanRentalsTask implements Consumer<Task> {

    UUID uuid;

    public CleanRentalsTask(UUID player) {
        this.uuid = player;
    }

    @Override
    public void accept(Task task) {

        PlayerPartyStorage pps = Pixelmon.storageManager.getParty(uuid);
        PokemonStorage pokemonStorage = Pixelmon.storageManager.getPCForPlayer(uuid);
        for (Pokemon pokemon: pps.getAll()) {

            if (pokemon == null)
                continue;

            if (pokemon.isEgg())
                continue;

            if (pokemon.hasSpecFlag("gympokemon")) {
                pps.set(pokemon.getPosition(), null);
            }
            if (pps.countPokemon() < 1 ) {
                pps.add(Pixelmon.pokemonFactory.create(EnumSpecies.Magikarp));
            }
        }

        for (Pokemon pokemon: pokemonStorage.getAll()) {

            if (pokemon == null)
                continue;

            if (pokemon.isEgg())
                continue;

            if (pokemon.hasSpecFlag("gympokemon")) {
                pokemonStorage.set(pokemon.getPosition(), null);
            }
            if (pokemonStorage.countPokemon() < 1 ) {
                pokemonStorage.add(Pixelmon.pokemonFactory.create(EnumSpecies.Magikarp));
            }
        }

    }
}
