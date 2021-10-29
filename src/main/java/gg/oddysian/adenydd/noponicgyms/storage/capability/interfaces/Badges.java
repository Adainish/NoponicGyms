package gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class Badges implements IBadges{
    private List<String> badges = new ArrayList<>();

    public NBTTagList nbtTagList(List<String> list) {
        NBTTagList nbtTagList = new NBTTagList();
        for (String s:list) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("badges", s);
            nbtTagList.appendTag(nbt);
        }

        return nbtTagList;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setTag("badges", nbtTagList(badges));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        nbt.getTag("badges");
    }

    @Override
    public List<String> getBadges() {
        return badges;
    }

    @Override
    public void setBadges(List<String> param) {
        this.badges = param;
    }


}
