package gg.oddysian.adenydd.noponicgyms.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import gg.oddysian.adenydd.noponicgyms.capability.interfaces.GymBadge;
import gg.oddysian.adenydd.noponicgyms.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UI {

    private static ItemStack setDefaultIcon(String[] elements) {


        boolean applyMeta = false;

        String itemTypeString;

        try {
            itemTypeString = elements[0];
        } catch (Exception var5) {
            itemTypeString = "minecraft:paper";
        }

        Item op = Item.getByNameOrId("" + itemTypeString);

        if (elements.length > 1 && elements[1] != null && !elements[1].isEmpty()) {
            if (op != null) {
                op.setHasSubtypes(true);
                applyMeta = true;

                if (itemTypeString.contains("pixelmon:tm_gen")) {

                    String mv = elements[0].replaceAll("pixelmon:", "");

                    ITechnicalMove technicalMove = ITechnicalMove.getMoveFor(mv, Integer.valueOf(elements[1]));

                    return  PixelmonItemsTMs.createStackFor(technicalMove);

                }

            }
        }

        ItemStack itemType;
        if (op != null) {
            itemType = new ItemStack(op);
            if (applyMeta) {
                itemType.setItemDamage(Integer.valueOf(elements[1]));
            }
            return itemType;
        }

        itemType = new ItemStack(op);
        return itemType;
    }

    private static Button filler() {
        return Button.builder()
                .item(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.GRAY.getMetadata()))
                .displayName("")
                .build();
    }

    public static String getFormattedDisplayName(String s) {
        return s.replaceAll("&", "§");
    }

    public static List<String> getFormattedList(List<String> lore) {
        List<String> formattedInfo = new ArrayList<>();
        for (String s: lore) {
            formattedInfo.add(s.replaceAll("&", "§"));
        }
        return formattedInfo;
    }


    public static List<Button> playerBadges(GymPlayer player) {
        List<Button> buttonList = new ArrayList<>();

        for (GymBadge b: player.getBadges()) {
            String[] elements = b.getItemstring().split(" ");
            System.out.println(b.getItemstring());
            System.out.println(b.getBadgelore());
            System.out.println(b.getBadgedisplay());
            Button button = Button.builder().item(setDefaultIcon(elements)).lore(getFormattedList(b.getBadgelore())).displayName(getFormattedDisplayName(b.getBadgedisplay())).build();
            buttonList.add(button);
        }

        return buttonList;
    }


    public static Page gymBadges(GymPlayer gymPlayer) {
        Template.Builder template = Template.builder(5);
        template.fill(filler());
        return Page.builder().template(template.build()).dynamicContentArea(1, 1, 3, 7).dynamicContents(playerBadges(gymPlayer)).title("%player% Badges".replaceAll("%player%", gymPlayer.getPlayer().getName())).build();
    }

}
