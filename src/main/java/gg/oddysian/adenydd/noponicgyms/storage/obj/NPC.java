package gg.oddysian.adenydd.noponicgyms.storage.obj;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NPC {

    private UUID uuid;
    private String name;
    private GymsRegistry.Gym gym;
    private int level;
    private List<Pokemon> pokemonList = new ArrayList<>();
    private HashMap<UUID, GymPlayer> gymPlayerHashMap = new HashMap<>();
    private int coolDownTime;
    private boolean beingBattled;
    private boolean checkForPlayerLeaders;


    public NPC() {

    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GymsRegistry.Gym getGym() {
        return gym;
    }

    public void setGym(GymsRegistry.Gym gym) {
        this.gym = gym;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public HashMap<UUID, GymPlayer> getGymPlayerHashMap() {
        return gymPlayerHashMap;
    }

    public void setGymPlayerHashMap(HashMap<UUID, GymPlayer> gymPlayerHashMap) {
        this.gymPlayerHashMap = gymPlayerHashMap;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public boolean isBeingBattled() {
        return beingBattled;
    }

    public void setBeingBattled(boolean beingBattled) {
        this.beingBattled = beingBattled;
    }

    public boolean isCheckForPlayerLeaders() {
        return checkForPlayerLeaders;
    }

    public void setCheckForPlayerLeaders(boolean checkForPlayerLeaders) {
        this.checkForPlayerLeaders = checkForPlayerLeaders;
    }
}
