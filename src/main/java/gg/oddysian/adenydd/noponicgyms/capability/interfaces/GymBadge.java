package gg.oddysian.adenydd.noponicgyms.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class GymBadge implements IGymBadge{

    private String gym;
    private String badgeName;
    private String tier;
    private boolean obtained;
    private long date;
    private List<String> pokemon;
    private String leader;



    @Override
    public void setPokemon(List<String> pokemonNames) {
        this.pokemon = pokemonNames;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Override
    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    @Override
    public Boolean getObtained() {
        return this.obtained;
    }

    @Override
    public void setDate(long date) {
        this.date = date;
    }

    @Override
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

    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(gym, badgeName);
        nbt.setBoolean(badgeName, obtained);
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        nbt.getString(gym);
        nbt.getBoolean(badgeName);
    }
}
