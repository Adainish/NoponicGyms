package gg.oddysian.adenydd.noponicgyms.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.InventoryAPI;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.storage.obj.ModeObj;
import gg.oddysian.adenydd.noponicgyms.tasks.TeleportTask;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<Button> gyms(List<GymObj.Gym> gyms) {
        List<Button> buttonList = new ArrayList<>();

        for (GymObj.Gym gym: gyms) {
            List<String> lore = new ArrayList<>();

            for (String s:gym.lore) {
                lore.add(s.replaceAll("%gym%", gym.display).replaceAll("%availableleaders%", String.valueOf(gym.gymLeaderList)).replaceAll("%gymlevel%", String.valueOf(gym.levelcap)));
            }
            Button button = Button.builder()
                    .item(gym.gymBadge)
                    .lore(getFormattedList(lore))
                    .onClick(buttonAction -> {
                        InventoryAPI.getInstance().closePlayerInventory(buttonAction.getPlayer());
                        Task.builder().iterations(1).execute(new TeleportTask(buttonAction.getPlayer(), gym.worldID, gym.posX, gym.posY, gym.posZ)).build();
                    })
                    .displayName(getFormattedDisplayName(gym.display))
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

                Button button = Button.builder().item(stack).lore(getFormattedList(lore)).displayName(getFormattedDisplayName(display)).build();
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
                Button button = Button.builder().item(stack).lore(getFormattedList(lore)).displayName(getFormattedDisplayName(display)).build();
                buttonList.add(button);
            }
        }

        return buttonList;
    }

    public static Page gyms() {
        Template.Builder template = Template.builder(5);
        template.fill(filler());
        return Page.builder().template(template.build()).title("Gyms").dynamicContentArea(1, 1, 3, 7).dynamicContents(gyms(GymObj.gyms)).build();
    }

    public static Page tiers(GymPlayer gymPlayerWrapper) {
        Template.Builder template = Template.builder(5);

        int i = 0;
        for (String s : ModeObj.modeList()) {
            i++;
            Button button = Button.builder()
                    .displayName(s)
                    .onClick(buttonAction -> {
                        gyms().forceOpenPage(ServerUtils.getPlayer(gymPlayerWrapper.getName()));
                    })
                    .item(new ItemStack(Items.DIAMOND))
                    .build();
            template.set(3, i, button);
        }

        template.fill(filler());
        return Page.builder().template(template.build()).title("Gyms").build();
    }

    public static Page gymBadges(GymPlayer gymPlayer, boolean isSpying) {
        Template.Builder template = Template.builder(5);
        template.fill(filler());
        return Page.builder().template(template.build()).dynamicContentArea(1, 1, 3, 7).dynamicContents(playerBadges(gymPlayer, isSpying)).title("%player%'s Badges".replaceAll("%player%", gymPlayer.getName())).build();
    }

}
