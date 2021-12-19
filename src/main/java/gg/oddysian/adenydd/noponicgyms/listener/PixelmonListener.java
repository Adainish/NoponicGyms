package gg.oddysian.adenydd.noponicgyms.listener;

import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.PixelmonTradeEvent;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PixelmonListener {

    @SubscribeEvent
    public static void onRanchAddEvent(BreedEvent.AddPokemon event) {
        if (event.pokemon.hasSpecFlag("gympokemon")) {
            event.setCanceled(true);
            ServerUtils.send(event.player, "&cYou're not allowed to Breed Gym Pokemon! This has been reported to an Administrator!");
        }
    }

    @SubscribeEvent
    public static void onTradeEvent(PixelmonTradeEvent event) {
        if (event.pokemon1.hasSpecFlag("gympokemon")) {
            event.setCanceled(true);
            ServerUtils.send(event.player1, "&cYou're not allowed to Trade Gym Pokemon! This has been reported to an Administrator!");
        }

        if (event.pokemon2.hasSpecFlag("gympokemon")) {
            event.setCanceled(true);
            ServerUtils.send(event.player2, "&cYou're not allowed to Trade Gym Pokemon! This has been reported to an Administrator!");
        }

    }
}
