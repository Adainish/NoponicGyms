package gg.oddysian.adenydd.noponicgyms.storage.obj;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
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
        public HashMap<UUID, GymPlayer> gymQueue = new HashMap<>();
        public ItemStack gymBadge;
        public String key;
        public String permission = "DEFAULT IMAGE";
        public String tier;
        public String leadermessage;
        public String badgeitemstring;
        public int levelcap;
        public int worldID;
        public double posX;
        public double posY;
        public double posZ;

        public int leaderWorldID;
        public double leaderPosX;
        public double leaderPosY;
        public double leaderPosZ;

        public int challengerWorldID;
        public double challengerPosX;
        public double challengerPosY;
        public double challengerPosZ;

        public String display;
        public List<String> lore;
        public List<Pokemon> gymPokemon;
        
        public boolean gymFee;
        public boolean payLeader;
        public double feeCost;

        public Gym(String key) {
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
                gymPokemon.add(generateGymPokemon(gymKey, nodestring));

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

            if (form != 0)
                pokemon.setForm(form);

            pokemon.setShiny(shiny);

            if (texture != null) {
                if (!nickname.isEmpty())
                    pokemon.setCustomTexture(texture);
            }

            pokemon.setNature(EnumNature.natureFromString(nature));
            pokemon.setAbility(ability);
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
    }
}
