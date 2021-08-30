package gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IGymBadge extends INBTSerializable<NBTTagCompound> {

    public void setObtained(boolean obtained);

    public List<String> getBadgelore();

    public void setBadgelore(List<String> badgelore);

    public String getBadgedisplay();

    public void setBadgedisplay(String badgedisplay);

    public String getItemstring();

    public void setItemstring(String itemstring);

    public String getTier();

    public void setTier(String tier);

    public String getLeader();

    public String getGym();

    public void setGym(String gym);

    public void setPokemon(List<String> pokemonNames);

    public long getDate();

    public void setLeader(String leader);

    public void setBadgeName(String badgeName);

    public Boolean getObtained();

    public void setDate(long date);

    public void addPokemon(String pokemon);
}
