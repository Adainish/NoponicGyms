package gg.oddysian.adenydd.noponicgyms.methods;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.cable.library.tasks.Task;
import com.cable.library.teleport.TeleportUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.StoragePosition;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.listener.BattleListener;
import gg.oddysian.adenydd.noponicgyms.storage.StoreMethods;
import gg.oddysian.adenydd.noponicgyms.storage.config.LanguageConfig;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.tasks.StorePokemonTask;
import gg.oddysian.adenydd.noponicgyms.ui.LeaderSelectPage;
import gg.oddysian.adenydd.noponicgyms.ui.SelectPartyPage;
import gg.oddysian.adenydd.noponicgyms.ui.UI;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GymMethods {

    public static void sendChallengeNotification(GymPlayer player, GymsRegistry.Gym gym){
        for (String s:gym.getGymLeaderList()) {
            EntityPlayerMP playerMP = ServerUtils.getPlayer(s);
            if (playerMP == null)
                continue;

            ServerUtils.send(playerMP, "&c%player% has challenged %gym%".replaceAll("%player%", player.getName()).replaceAll("%gym%", gym.getDisplay()));
        }
    }

    public static void giveGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {

        EntityPlayerMP playerMP = ServerUtils.getPlayer(gymPlayer.getName());

        if (playerMP == null) {
            NoponicGyms.log.error("A player returned null when trying to hand out a gym badge, ending function");
            return;
        }

        if (gymBadge.getGym() == null || gymBadge.getGym().isEmpty()) {
            NoponicGyms.log.error("A badge was attempted to be handy out, but the gym didn't exist while doing so! Ending function");
            return;
        }

        if (gymBadge.getBadgeName() == null || gymBadge.getBadgeName().isEmpty()) {
            NoponicGyms.log.error("A badge was attempted to be handed out, but the badge name didn't exist while doing so! Ending function");
            return;
        }

        String sendMSG = LanguageConfig.getConfig().get().getNode("Badge", "Received").getString();
        String broadCast = LanguageConfig.getConfig().get().getNode("Badge", "Broadcast").getString();
        ServerUtils.send(playerMP, sendMSG.replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        ServerUtils.doBroadcast(broadCast.replaceAll("%player%", gymPlayer.getName()).replaceAll("%gym%", gymBadge.getGym()).replaceAll("%badge%", gymBadge.getBadgeName()));
        StoreMethods.writeGymBadge(playerMP, gymBadge);

    }

    public static void takeGymBadge(GymPlayer gymPlayer, GymBadge gymBadge) {
        String takeMSG = LanguageConfig.getConfig().get().getNode("Badge", "Taken").getString();
        ServerUtils.send(ServerUtils.getPlayer(gymPlayer.getName()), takeMSG.replaceAll("%badge%", gymBadge.getBadgeName()));
        gymPlayer.removeBadge(gymBadge);
        StoreMethods.updateGymPlayerData(gymPlayer);
    }

    public static void takeChallenge(GymPlayer player, GymPlayer leader, GymsRegistry.Gym gym) {
        EntityPlayerMP challenger = ServerUtils.getPlayer(player.getName());
        EntityPlayerMP gymLeader = ServerUtils.getPlayer(leader.getName());

        BlockPos challengerPos = new BlockPos(gym.getChallengerPosX(), gym.getChallengerPosY(), gym.getChallengerPosZ());
        BlockPos leaderPos = new BlockPos(gym.getLeaderPosX(), gym.getLeaderPosY(), gym.getLeaderPosZ());

        TeleportUtils.teleport(challenger, gym.getChallengerWorldID(), challengerPos);
        TeleportUtils.teleport(gymLeader, gym.getLeaderWorldID(), leaderPos);

        SelectPartyPage partyPage = new SelectPartyPage(challenger, player);
        LeaderSelectPage selectPage = new LeaderSelectPage(gymLeader, gym, leader);
        UIManager.openUIPassively(gymLeader, selectPage, 20, TimeUnit.SECONDS);
        UIManager.openUIPassively(challenger, partyPage, 20, TimeUnit.SECONDS);
//        Task.builder().delay(20).execute(() -> {
//            startBattle(challenger, gymLeader);
//        }).build();

    }

    public static List<Pokemon> rentalPokemon(GymsRegistry.Gym gym) {
        List <Pokemon> rentalList = new ArrayList<>();
        for (Pokemon gymPokemon : gym.setGymPokemon(gym.getKey())) {
            if (gymPokemon != null)
                rentalList.add(gymPokemon);
        }
        return rentalList;
    }

    public void handleRentals(GymsRegistry.Gym gym, GymPlayer player) {
        Task.builder().execute(new StorePokemonTask(player.getUuid())).iterations(1).build();
        PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(player.getUuid());
        Task.builder().delay(5).execute(()-> {
            for (Pokemon gymPokemon : gym.setGymPokemon(gym.getKey())) {
                if (gymPokemon != null)
                    playerPartyStorage.add(gymPokemon);
            }
     }).iterations(1).build();
    }

    public static void startBattle(EntityPlayerMP challenger, EntityPlayerMP leader) {

        PlayerPartyStorage challengerStorage = Pixelmon.storageManager.getParty(challenger);
        PlayerPartyStorage leaderStorage = Pixelmon.storageManager.getParty(leader);

        PlayerParticipant challengerParticipant = new PlayerParticipant(challenger, challengerStorage.getAndSendOutFirstAblePokemon(challenger));
        PlayerParticipant challengedParticipant = new PlayerParticipant(leader, leaderStorage.getAndSendOutFirstAblePokemon(leader));
        BattleControllerBase bcb =
                new BattleControllerBase(
                        new BattleParticipant[]{challengedParticipant},
                        new BattleParticipant[]{challengerParticipant},
                        new BattleRules()
                );

        startBattle(bcb);
    }

    public static void startBattle(BattleControllerBase bcb) {
        BattleListener.activeBattles.add(bcb);
        BattleRegistry.registerBattle(bcb);
    }

    public static void updateChallengerParty(List<Pokemon> pokemonList, GymPlayer player) {
        Task.builder().execute(new StorePokemonTask(player.getUuid())).iterations(1).build();
        PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(player.getUuid());
        Task.builder().delay(1).execute(()-> {
            for (Pokemon pokemon : pokemonList) {
                pokemon.addSpecFlag("gympokemon");
                playerPartyStorage.add(pokemon);
            }
        }).iterations(1).build();
        ServerUtils.send(ServerUtils.getInstance().getPlayerList().getPlayerByUUID(player.getUuid()), "&eWe've updated your Party to fit your selected members!");
    }

    public static void sendRentals(List<Pokemon> pokemonList, GymPlayer player, GymsRegistry.Gym gym) {
        Task.builder().execute(new StorePokemonTask(player.getUuid())).iterations(1).build();
        PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(player.getUuid());
        Task.builder().delay(2).execute(()-> {
            for (Pokemon gymPokemon : pokemonList) {
                if (gymPokemon != null)
                    playerPartyStorage.add(gymPokemon);
            }
        }).iterations(1).build();
        ServerUtils.send(ServerUtils.getPlayer(player.getName()), "&eYou received the Gym Team for the %gym% Gym!".replaceAll("%gym%", gym.getDisplay()));
    }


    public static void updateGym(GymsRegistry.Gym gym, boolean open) {
        if (gym == null)
            return;

        if (open && !gym.isOpened()) {
            openGym(gym);
        }
        if (!open && gym.isOpened()) {
            closeGym(gym);
        }

    }

    public static void closeGym(GymsRegistry.Gym gym) {

        if (!gym.isOpened())
            return;

        String closeMSG = LanguageConfig.getConfig().get().getNode("Gym", "CloseGym").getString();
        ServerUtils.doBroadcast(closeMSG.replaceAll("%gym%", gym.getDisplay()));
        gym.setOpened(false);

    }

    public static void openGym(GymsRegistry.Gym gym) {

        if (gym.isOpened())
            return;

        String openMSG = LanguageConfig.getConfig().get().getNode("Gym", "OpenGym").getString();
        ServerUtils.doBroadcast(openMSG.replaceAll("%gym%", gym.getDisplay()));
        gym.setOpened(true);

    }
}
