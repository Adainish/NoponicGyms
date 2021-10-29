package gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IBadges extends INBTSerializable<NBTTagCompound> {

    List<String> getBadges();

    void setBadges(List<String> param);

}
