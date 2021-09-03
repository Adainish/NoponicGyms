package gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IGymBadge extends INBTSerializable<NBTTagCompound> {

    void setObtained(boolean obtained);

    List<String> getBadgelore();

    void setBadgelore(List<String> badgelore);

    String getBadgedisplay();

    void setBadgedisplay(String badgedisplay);

    String getItemstring();

    void setItemstring(String itemstring);

    String getTier();

    void setTier(String tier);

    String getLeader();

    String getGym();

    void setGym(String gym);

    void setPokemon(List<String> pokemonNames);

    long getDate();

    void setLeader(String leader);

    void setBadgeName(String badgeName);

    Boolean getObtained();

    public void setDate(long date);

    public void addPokemon(String pokemon);
}
