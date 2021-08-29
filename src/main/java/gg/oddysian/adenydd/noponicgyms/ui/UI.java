package gg.oddysian.adenydd.noponicgyms.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UI {

    private static Button filler() {
        return Button.builder()
                .item(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.GRAY.getMetadata()))
                .displayName("")
                .build();
    }

    public static String getFormattedDisplayName(String s) {
        return s.replaceAll("&", "§");
    }

    public List<String> getFormattedList(List<String> lore) {
        List<String> formattedInfo = new ArrayList<>();
        for (String s: lore) {
            formattedInfo.add(s.replaceAll("&", "§"));
        }
        return formattedInfo;
    }
}
