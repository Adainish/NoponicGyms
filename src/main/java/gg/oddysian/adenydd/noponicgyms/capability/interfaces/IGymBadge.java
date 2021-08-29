package gg.oddysian.adenydd.noponicgyms.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IGymBadge extends INBTSerializable<NBTTagCompound> {

    public String getTier();

    public void setTier(String tier);

    public String getLeader();

    public String getGym();

    public void setGym(String gym);

    void setPokemon(List<String> pokemonNames);

    long getDate();

    void setLeader(String leader);

    void setBadgeName(String badgeName);

    Boolean getObtained();

    void setDate(long date);

    void addPokemon(String pokemon);
}
