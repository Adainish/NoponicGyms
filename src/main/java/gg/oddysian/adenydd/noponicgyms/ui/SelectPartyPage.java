package gg.oddysian.adenydd.noponicgyms.ui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymSelection;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gg.oddysian.adenydd.noponicgyms.ui.UI.getFormattedDisplayName;
import static gg.oddysian.adenydd.noponicgyms.ui.UI.getPhoto;

public class SelectPartyPage extends UpdateEmitter<Page> implements Page {

    private GymPlayer player;
    private LinkedPage selectParty;
    private List<Pokemon> selectedPokemon = new ArrayList <>();
    private ChestTemplate.Builder chestTemplate = ChestTemplate.builder(7);

    public GooeyButton readyUp() {
        ItemStack stack;
        if (player.isReadyForBattle()) {
            stack = new ItemStack(Items.DYE, 1, 5);
        } else stack = new ItemStack(Items.DYE, 1, 4);

        return GooeyButton.builder().onClick(b -> {
            GymSelection s = GymSelection.getSelection(player.getUuid());
            GymSelection newSelection = s;
            if (player.isReadyForBattle()) {
                player.setReadyForBattle(false);
                if (newSelection != null) {
                    newSelection.updateEntity(player, false);
                    NoponicGyms.selectionList.remove(s);
                    NoponicGyms.selectionList.add(newSelection);

                }
            } else {
                player.setReadyForBattle(true);
                if (newSelection != null) {
                    newSelection.updateEntity(player, true);
                    NoponicGyms.selectionList.remove(s);
                    NoponicGyms.selectionList.add(newSelection);

                }
            }
            newSelection.setChallengerPokemon(selectedPokemon);
            GymPlayerWrapper.gymPlayerHashMap.put(player.getUuid(), player);
            updatePage();
        }).title("Ready Up").display(stack).build();
    }
    LinkedPageButton previous = LinkedPageButton.builder()
            .display(new ItemStack(PixelmonItems.LtradeHolderLeft))
            .title("Previous Page")
            .linkType(LinkType.Previous)
            .build();

    LinkedPageButton next = LinkedPageButton.builder()
            .display(new ItemStack(PixelmonItems.tradeHolderRight))
            .title("Next Page")
            .linkType(LinkType.Next)
            .build();

    List<Button> playerPC(EntityPlayerMP p) {
        List<Button> buttonList = new ArrayList <>();
        PCStorage storage = Pixelmon.storageManager.getPCForPlayer(p);
        for (Pokemon pm:storage.getAll()) {
            ItemStack stack;
            String title = "";
            if (pm != null) {
                stack = getPhoto(pm);
                title = pm.getDisplayName();
            }
            else stack = new ItemStack(Items.AIR);
            GooeyButton button = GooeyButton.builder().display(stack).title(getFormattedDisplayName(title))
                    .onClick(() -> {
                        if (selectedPokemon.contains(pm)) {
                            ServerUtils.send(p, "&cYou can't add a Pokemon that has already been added to the selection!");
                            return;
                        } else if (selectedPokemon.size() < 6) {
                            selectedPokemon.add(pm);
                        }
                        updatePage();
                    })
                    .build();
            buttonList.add(button);
        }
        return buttonList;
    }

     GooeyButton filler() {
        return GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.GRAY.getMetadata()))
                .title("")
                .build();
    }

    public SelectPartyPage(EntityPlayerMP playerMP, GymPlayer player) {

        PlaceholderButton placeholderButton = new PlaceholderButton();
        Template template = null;

        setPlayer(player);
        for (int i = 0; i < Pixelmon.storageManager.getParty(playerMP).getAll().length; i++) {
            Pokemon pokemon = Pixelmon.storageManager.getParty(playerMP).get(i);
            ItemStack stack = new ItemStack(Items.AIR);
            String title = "";
            if (pokemon != null) {
                stack = getPhoto(pokemon);
                title = pokemon.getDisplayName();
            }
            GooeyButton button = GooeyButton.builder().display(stack)
                    .title(title)
                    .onClick(b -> {
                        if (selectedPokemon.contains(pokemon)) {
                            ServerUtils.send(playerMP, "&cYou've already selected this Pokemon for the Battle!");
                        } else if (selectedPokemon.size() < 6)
                            selectedPokemon.add(pokemon);
                        updatePage();
                    })
                    .build();
            chestTemplate.set(0, 1+(i), button);
        }
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon;
            GooeyButton button = GooeyButton.builder().display(new ItemStack(Items.GLASS_BOTTLE)).title("Empty Spot").build();
            if (!selectedPokemon.isEmpty()) {
                pokemon = selectedPokemon.get(i);
                button = GooeyButton.builder().display(getPhoto(pokemon)).title(pokemon.getDisplayName()).build();
            }
            chestTemplate.set(8 + (9 * i), button);
        }

        if (playerPC(playerMP).size() >31) {
            template = chestTemplate
                    .fill(filler())
                    .set(0, 0 , previous)
                    .set(1, 0, next)
                    .set(2, 0, readyUp())
                    .rectangle(1, 1, 5, 7, placeholderButton)
                    .build();
        } else {
            template = chestTemplate
                    .fill(filler())
                    .set(2, 0, readyUp())
                    .rectangle(1, 2, 5, 7, placeholderButton)
                    .build();
        }
        setSelectParty(PaginationHelper.createPagesFromPlaceholders(template, playerPC(playerMP), LinkedPage.builder().title("Gym Battle Party").template(template)));
    }


    public void updatePage() {
        for (int i = 0; i < 6; i++) {
            GooeyButton button = null;
            Pokemon pokemon;
            if (i < selectedPokemon.size()) {
                pokemon = selectedPokemon.get(i);
                if (pokemon != null) {
                    int finalI = i;
                    button = GooeyButton.builder()
                            .display(getPhoto(pokemon))
                            .title(pokemon.getDisplayName())
                            .onClick(a -> {
                                selectedPokemon.remove(finalI);
                                update();
                            })
                            .build();
                }
            } else button = GooeyButton.builder().display(new ItemStack(Items.GLASS_BOTTLE)).title("Empty Spot").build();

            getTemplate().getSlot( 8 + (9 * i)).setButton(button);
        }
        getSelectParty().update();
    }


    @Override
    public Template getTemplate() {
        return selectParty.getTemplate();
    }

    @Override
    public String getTitle() {
        return selectParty.getTitle();
    }


    public LinkedPage getSelectParty() {
        return selectParty;
    }

    public void setSelectParty(LinkedPage selectParty) {
        this.selectParty = selectParty;
    }

    public GymPlayer getPlayer() {
        return player;
    }

    public void setPlayer(GymPlayer player) {
        this.player = player;
    }
}
