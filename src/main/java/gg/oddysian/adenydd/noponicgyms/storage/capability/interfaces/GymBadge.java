package gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class GymBadge implements IGymBadge{

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



    @Override
    public void setPokemon(List<String> pokemonNames) {
        this.pokemon = pokemonNames;
    }

    @Override
    public String getDate() {
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
    public String getBadgeName() {
        return badgeName;
    }

    @Override
    public Boolean getObtained() {
        return this.obtained;
    }

    @Override
    public void setDate(String date) {
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

    public NBTTagList nbtTagList(List<String> list) {
        NBTTagList nbtTagList = new NBTTagList();
        for (String s:list) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("pokemon", s);
            nbtTagList.appendTag(nbt);
        }

        return nbtTagList;
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (badgeName != null && gym != null)
        nbt.setString(gym, badgeName);
        if (badgeName != null)
        nbt.setBoolean(badgeName, obtained);
        if (badgeName != null)
        nbt.setString(badgeName, date);
        if (obtained && !pokemon.isEmpty())
        nbt.setTag("pokemon", nbtTagList(pokemon));
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        nbt.getString(gym);
        nbt.getBoolean(badgeName);
        if (nbt.getBoolean(badgeName))
        nbt.getLong(badgeName);
        if (nbt.getBoolean(badgeName))
        nbt.getTag("pokemon");
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
