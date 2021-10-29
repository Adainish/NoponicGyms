package gg.oddysian.adenydd.noponicgyms.storage.obj;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.config.GymConfig;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayer;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class GymObj {
    public static List<Gym> gyms = new ArrayList<>();

    public static void loadGyms() {
        gyms.clear();
        CommentedConfigurationNode rootNode = GymConfig.getConfig().get().getNode("Gyms");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                NoponicGyms.log.info(nodeObject + "GYM NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    NoponicGyms.log.info(node + "GYM NODE NULL");
                } else {
                    NoponicGyms.log.info(node + "NEW GYM ADDED");
                    gyms.add(new Gym(node));
                }
            }
        }

    }

    public static List<String> gymList() {
        return gyms.stream().map(gym -> gym.key).collect(Collectors.toList());
    }

    public static boolean isGym(String name) {
        Iterator var1 = gyms.iterator();

        Gym gym;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            gym = (Gym) var1.next();
        } while (!gym.key.equalsIgnoreCase(name));

        return true;
    }

    public static Gym getGym(String key) {
        Iterator var1 = gyms.iterator();

        Gym gym;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            gym = (Gym) var1.next();
        } while (!gym.key.equalsIgnoreCase(key));

        return gym;
    }

    public static class Gym {
        public List<String> gymLeaderList = new ArrayList<>();
        public HashMap<UUID, GymPlayer> gymQueue = new HashMap<>();
        public ItemStack gymBadge;
        public String key;
        public String permission = "DEFAULT IMAGE";
        public String tier = "";
        public String leadermessage = "";
        public String badgeitemstring = "minecraft:paper";
        public boolean opened = false;
        public int levelcap = 10;
        public int worldID = 0;
        public double posX = 0.0;
        public double posY = 0.0;
        public double posZ = 0.0;

        public int leaderWorldID = 0;
        public double leaderPosX= 0.0;
        public double leaderPosY = 0.0;
        public double leaderPosZ = 0.0;

        public int challengerWorldID = 0;
        public double challengerPosX = 0.0;
        public double challengerPosY = 0.0;
        public double challengerPosZ = 0.0;

        public String display = "";
        public List<String> lore = new ArrayList<>();
        public List<Pokemon> gymPokemon = new ArrayList<>();
        
        public boolean gymFee = true;
        public boolean payLeader = true;
        public double feeCost = 100.00;

        public Gym(String key) {
            this.opened = false;
            this.gymFee = GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "EnableFee").getBoolean();
            this.payLeader = GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "PayLeader").getBoolean();
            this.feeCost = GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "Fee").getDouble();
            this.worldID = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "WorldID").getInt();
            this.posX = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "X").getDouble();
            this.posY = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "Y").getDouble();
            this.posZ = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "Z").getDouble();

            this.leaderWorldID = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "WorldID").getInt();
            this.leaderPosX = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "X").getDouble();
            this.leaderPosY = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "Y").getDouble();
            this.leaderPosZ = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "Z").getDouble();

            this.challengerWorldID = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "WorldID").getInt();
            this.challengerPosX = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "X").getDouble();
            this.challengerPosY = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "Y").getDouble();
            this.challengerPosZ = GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "Z").getDouble();


            this.badgeitemstring = GymConfig.getConfig().get().getNode("Gyms", key, "Badge").getString();
            this.key = key;
            this.display = GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Display").getString();
            this.permission = GymConfig.getConfig().get().getNode("Gyms", key, "Permission").getString();
            this.tier = GymConfig.getConfig().get().getNode("Gyms", key, "Tier").getString();
            this.leadermessage = GymConfig.getConfig().get().getNode("Gyms", key, "LeaderMessage").getString();
            this.levelcap = GymConfig.getConfig().get().getNode("Gyms", key, "LevelCap").getInt();
            try {
                this.lore = GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Lore").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
            String[] elements = badgeitemstring.split(" ");
            this.gymBadge = setDefaultIcon(elements);
            this.gymPokemon = setGymPokemon(key, GymConfig.getConfig().get().getNode("Gyms", key, "GymPokemon"));
        }

        
        private List<Pokemon> setGymPokemon(String gymKey, CommentedConfigurationNode node) {
            List<Pokemon> gymPokemon = new ArrayList<>();
            Map nodemap = node.getChildrenMap();
            for (Object obj: nodemap.keySet()) {
                if (obj == null)
                    continue;
                String nodestring = obj.toString();
                if (generateGymPokemon(gymKey, nodestring) == null) {
                    NoponicGyms.log.error("There was an error generating the Pokemon for " + gymKey + " " + nodestring);
                    continue;
                }
                Pokemon pokemon = generateGymPokemon(gymKey, nodestring);
                gymPokemon.add(pokemon);

            }
            return gymPokemon;
        }

        private Pokemon generateGymPokemon(String gymKey, String node) {

            String pokemonname = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "PokemonName").getString();
            int form = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Form").getInt();
            int level = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Level").getInt();
            String nickname = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "NickName").getString();
            boolean shiny = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Shiny").getBoolean();
            String texture = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Texture").getString();
            String nature = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "Nature").getString();
            String ability = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "Ability").getString();
            int dynamaxLevel = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Dynamax").getInt();
            String heldItem = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "HeldItem").getString();

            //Evs

            int evsHP = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "HP").getInt();
            int evsATK = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "ATK").getInt();
            int evsSPA = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "SPA").getInt();
            int evsDEF = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "DEF").getInt();
            int evsSPDEF = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "SPDEF").getInt();
            int evsSPD = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "EVS", "SPD").getInt();
            //IVS
            int ivsHP = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "HP").getInt();
            int ivsATK = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "ATK").getInt();
            int ivsSPA = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "SPA").getInt();
            int ivsDEF = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "DEF").getInt();
            int ivsSPDEF = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "SPDEF").getInt();
            int ivsSPD = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "Stats", "IVS", "SPD").getInt();

            if (pokemonname == null) {
                NoponicGyms.log.info("The PokemonName doesn't exist, please check your config!");
                return null;
            }
            if (EnumSpecies.getFromNameAnyCase(pokemonname) == null) {
                NoponicGyms.log.info("The Pokemon Species %mon% doesn't exist!".replaceAll("%mon%", pokemonname));
                return null;
            }
            Pokemon pokemon = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(pokemonname));
            pokemon.setLevel(level);

            pokemon.getIVs().setStat(StatsType.HP, ivsHP);
            pokemon.getIVs().setStat(StatsType.Attack, ivsATK);
            pokemon.getIVs().setStat(StatsType.SpecialAttack, ivsSPA);
            pokemon.getIVs().setStat(StatsType.Defence, ivsDEF);
            pokemon.getIVs().setStat(StatsType.SpecialDefence, ivsSPDEF);
            pokemon.getIVs().setStat(StatsType.Speed, ivsSPD);


            pokemon.getEVs().setStat(StatsType.HP, evsHP);
            pokemon.getEVs().setStat(StatsType.Attack, evsATK);
            pokemon.getEVs().setStat(StatsType.SpecialAttack, evsSPA);
            pokemon.getEVs().setStat(StatsType.Defence, evsDEF);
            pokemon.getEVs().setStat(StatsType.SpecialDefence, evsSPDEF);
            pokemon.getEVs().setStat(StatsType.Speed, evsSPD);

            if (nickname != null) {
                if (!nickname.isEmpty())
                    pokemon.setNickname(nickname);
            }

            if (heldItem != null)
                if (!heldItem.isEmpty()) {
                    ItemStack itemStack = new ItemStack(Item.getByNameOrId(heldItem));
                    if (itemStack != null) //Ignore, this can still be null due to the String being editable by administrators
                    pokemon.setHeldItem(itemStack);
                    else NoponicGyms.log.error("The ItemStack couldn't be created for rental pokemon %pokemon%".replaceAll("%pokemon%", pokemonname));
                }

            if (form != 0)
                pokemon.setForm(form);

            pokemon.setShiny(shiny);

            if (texture != null) {
                if (!texture.isEmpty())
                    pokemon.setCustomTexture(texture);
            }

            if (EnumNature.natureFromString(nature) != null)
            pokemon.setNature(EnumNature.natureFromString(nature));
            else NoponicGyms.log.info("There was an issue generating the nature for %pokemon%, please check your config for any errors");
            if (AbilityBase.getAbility(ability).isPresent())
            pokemon.setAbility(ability); else NoponicGyms.log.info("There was an issue generating the ability for %pokemon%, please check your config for any errors");
            pokemon.setDynamaxLevel(dynamaxLevel);
            pokemon.setDoesLevel(false);

            pokemon.addSpecFlag("gympokemon");

            return pokemon;
        }

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

                        ITechnicalMove technicalMove = ITechnicalMove.getMoveFor(mv, Integer.parseInt(elements[1]));

                        if (technicalMove == null)
                            NoponicGyms.log.info("There was an issue generating the gym badge item, please check your console for any errors");
                        return  PixelmonItemsTMs.createStackFor(technicalMove);

                    }

                }
            }

            ItemStack itemType;
            if (op != null) {
                itemType = new ItemStack(op);
                if (applyMeta) {
                    itemType.setItemDamage(Integer.parseInt(elements[1]));
                }
                if (itemType == null)
                    NoponicGyms.log.info("There was an issue generating the gym badge item, please check your console for any errors");
                return itemType;
            }

            itemType = new ItemStack(op);
            if (itemType == null)
                NoponicGyms.log.info("There was an issue generating the gym badge item, please check your console for any errors");
            return itemType;
        }
    }
}
