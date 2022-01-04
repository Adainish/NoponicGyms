package gg.oddysian.adenydd.noponicgyms.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.storage.registry.ModeRegistry;
import gg.oddysian.adenydd.noponicgyms.tasks.TeleportTask;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class UI {

    private static ItemStack setDefaultIcon(String[] elements, boolean enchanted) {

        NBTTagCompound noEnchants = new NBTTagCompound();
        noEnchants.setInteger("Unbreakable", 1);
        noEnchants.setInteger("HideFlags", 4);
        noEnchants.setString("tooltip", "");

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
            if (enchanted) {
                itemType.setTagCompound(noEnchants);
                itemType.addEnchantment(Enchantment.getEnchantmentByID(-1), 1);
            }
            return itemType;
        }

        itemType = new ItemStack(op);
        return itemType;
    }

    private static GooeyButton filler() {
        return GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.GRAY.getMetadata()))
                .title("")
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

    public static List<Button> gyms(List<GymsRegistry.Gym> gyms) {
        List<Button> buttonList = new ArrayList<>();

        for (GymsRegistry.Gym gym: gyms) {
            List<String> lore = new ArrayList<>();

            for (String s: gym.getLore()) {
                String openStatus = "&aOpen";
                if (!gym.isOpened())
                    openStatus = "&4Closed";
                lore.add(s.replaceAll("%gym%", gym.getDisplay()).replaceAll("%availableleaders%", String.valueOf(gym.getGymLeaderList())).replaceAll("%gymlevel%", String.valueOf(gym.getLevelcap())).replaceAll("%open%", openStatus));
            }
            Button button = GooeyButton.builder()
                    .display(gym.getGymBadge())
                    .lore(getFormattedList(lore))
                    .onClick(buttonAction -> {
                        UIManager.closeUI(buttonAction.getPlayer());
                        Task.builder().iterations(1).execute(new TeleportTask(buttonAction.getPlayer(), gym.getWorldID(), gym.getPosX(), gym.getPosY(), gym.getPosZ())).build();
                    })
                    .title(getFormattedDisplayName(gym.getDisplay()))
                    .build();
            buttonList.add(button);

        }
        return buttonList;
    }

    public static List<Button> playerBadges(GymPlayer player, boolean isSpying) {
        List<Button> buttonList = new ArrayList<>();

        if (!isSpying) {
            for (GymBadge b : player.getBadges()) {
                String[] elements = b.getItemstring().split(" ");
                String display = "";

                if (b.getBadgedisplay() == null || b.getBadgedisplay().isEmpty()) {
                    display = "BADGE DISPLAY ERROR";
                } else display = b.getBadgedisplay();


                List<String> lore = new ArrayList<>();
                ItemStack stack = setDefaultIcon(elements, b.getObtained());

                if (stack == null) {
                    NoponicGyms.log.error("The ItemStack for the Badge %badge% could not generate in the GUI, Skipping display!".replaceAll("%badge%", b.getBadgeName()));
                    continue;
                }

                if (b.getObtained()) {
                 lore.add("&eYou've defeated the &b%gym% Gym &eon &b%date%".replaceAll("%gym%", b.getBadgeName()).replaceAll("%date%", b.getDate()));
                 lore.add("&cPokemon used:");
                    lore.addAll(b.getPokemon());
                }

                GooeyButton button = GooeyButton.builder().display(stack).lore(getFormattedList(lore)).title(getFormattedDisplayName(display)).build();
                buttonList.add(button);
            }
        } else {
            for (GymBadge b : player.getBadges()) {
                if (!player.hasSpecificBadge(b))
                    continue;
                String[] elements = b.getItemstring().split(" ");
                String display = b.getBadgedisplay();
                List<String> lore = new ArrayList<>();
                ItemStack stack = setDefaultIcon(elements, b.getObtained());
                if (b.getObtained()) {
                    display = b.getBadgedisplay();
                    lore = Arrays.asList("&e%player% defeated the %gym%".replaceAll("%gym%", b.getBadgeName()).replaceAll("%player%", player.getName()));
                }
                GooeyButton button = GooeyButton.builder().display(stack).lore(getFormattedList(lore)).title(getFormattedDisplayName(display)).build();
                buttonList.add(button);
            }
        }

        return buttonList;
    }

    public static LinkedPage gyms() {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        Template template = ChestTemplate.builder(5).rectangle(1, 1, 3,  7, placeHolderButton).fill(filler()).build();

        return PaginationHelper.createPagesFromPlaceholders(template, gyms(GymsRegistry.gyms), LinkedPage.builder().title("Gyms").template(template));
    }


    public static LinkedPage gymBadges(GymPlayer gymPlayer, boolean isSpying) {
        PlaceholderButton placeholderButton = new PlaceholderButton();
        Template template = ChestTemplate.builder(5).fill(filler()).rectangle(1, 1, 3, 7, placeholderButton).build();
        return  PaginationHelper.createPagesFromPlaceholders(template, playerBadges(gymPlayer, isSpying), LinkedPage.builder().title("%player%'s Badges".replaceAll("%player%", gymPlayer.getName())).template(template));
    }

}
