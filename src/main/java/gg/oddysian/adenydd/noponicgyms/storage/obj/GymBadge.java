package gg.oddysian.adenydd.noponicgyms.storage.obj;

import java.util.ArrayList;
import java.util.List;

public class GymBadge {

    private String gym;
    private String badgeName;
    private String tier;
    private String itemstring;
    private String badgedisplay;
    private List<String> badgelore = new ArrayList<>();
    private boolean obtained;
    private String date;
    private List<String> pokemon = new ArrayList<>();
    private String leader;



    public void setPokemon(List<String> pokemonNames) {
        this.pokemon = pokemonNames;
    }

    public List<String> getPokemon() {
        return this.pokemon;
    }

    public String getDate() {
        return date;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public Boolean getObtained() {
        return this.obtained;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addPokemon(String pokemon) {
        this.pokemon.add(pokemon);
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getLeader() {
        return leader;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    public List<String> getBadgelore() {
        return badgelore;
    }

    public void setBadgelore(List<String> badgelore) {
        this.badgelore = badgelore;
    }

    public String getBadgedisplay() {
        return badgedisplay;
    }

    public void setBadgedisplay(String badgedisplay) {
        this.badgedisplay = badgedisplay;
    }

    public String getItemstring() {
        return itemstring;
    }

    public void setItemstring(String itemstring) {
        this.itemstring = itemstring;
    }
}
