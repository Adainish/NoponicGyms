package gg.oddysian.adenydd.noponicgyms.storage.registry;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.config.GymConfig;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class GymsRegistry {
    public static List<Gym> gyms = new ArrayList<>();

    public static List <Gym> getGyms() {
        return gyms;
    }

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
                    gyms.add(new Gym(node));
                }
            }
        }
        gyms.sort(Comparator.comparing(Gym::getWeight)); //Sort the gyms in ascending order
    }

    public static List<String> gymList() {
        return gyms.stream().map(Gym::getKey).collect(Collectors.toList());
    }

    public static boolean isGym(String name) {
        Iterator var1 = gyms.iterator();

        Gym gym;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            gym = (Gym) var1.next();
        } while (!gym.getKey().equalsIgnoreCase(name));

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
        } while (!gym.getKey().equalsIgnoreCase(key));

        return gym;
    }

    public static class Gym {
        private List<String> gymLeaderList = new ArrayList <>();
        private HashMap<UUID, GymPlayer> gymQueue = new HashMap <>();
        private List<GymPlayer> queue = new ArrayList<>();
        private ItemStack gymBadge = new ItemStack(Items.DIAMOND);
        private String key = "";
        private String permission = "";
        private String tier = "";
        private String leadermessage = "";
        private String badgeitemstring = "";
        private List<String> rewards = new ArrayList <>();
        private boolean opened = false;
        private int weight = 0;
        private int levelcap = 10;
        private int worldID = 0;
        private double posX = 0.0;
        private double posY = 0.0;
        private double posZ = 0.0;

        private String mode = "";
        private int leaderWorldID = 0;
        private double leaderPosX= 0.0;
        private double leaderPosY = 0.0;
        private double leaderPosZ = 0.0;

        private int challengerWorldID = 0;
        private double challengerPosX = 0.0;
        private double challengerPosY = 0.0;
        private double challengerPosZ = 0.0;

        private String display = "";
        private List<String> lore = new ArrayList<>();
        private List<Pokemon> gymPokemon = new ArrayList<>();
        
        private boolean gymFee = true;
        private boolean payLeader = true;
        private double feeCost = 100.00;

        public Gym(String key) {
            this.setOpened(false);
            this.setGymFee(GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "EnableFee").getBoolean());
            this.setPayLeader(GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "PayLeader").getBoolean());
            this.setFeeCost(GymConfig.getConfig().get().getNode("Gyms", key, "Fee", "Fee").getDouble());
            this.setWorldID(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "WorldID").getInt());
            this.setPosX(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "X").getDouble());
            this.setPosY(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "Y").getDouble());
            this.setPosZ(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Entry", "Z").getDouble());

            this.setLeaderWorldID(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "WorldID").getInt());
            this.setLeaderPosX(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "X").getDouble());
            this.setLeaderPosY(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "Y").getDouble());
            this.setLeaderPosZ(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "GymLeader", "Z").getDouble());

            this.setChallengerWorldID(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "WorldID").getInt());
            this.setChallengerPosX(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "X").getDouble());
            this.setChallengerPosY(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "Y").getDouble());
            this.setChallengerPosZ(GymConfig.getConfig().get().getNode("Gyms", key, "Warp", "Challenger", "Z").getDouble());

            this.setMode(GymConfig.getConfig().get().getNode("Gyms", key, "Mode").getString());
            this.setBadgeitemstring(GymConfig.getConfig().get().getNode("Gyms", key, "Badge").getString());
            this.setKey(key);
            this.setWeight(GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Weight").getInt());
            this.setDisplay(GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Display").getString());
            this.setPermission(GymConfig.getConfig().get().getNode("Gyms", key, "Permission").getString());
            this.setTier(GymConfig.getConfig().get().getNode("Gyms", key, "Tier").getString());
            this.setLeadermessage(GymConfig.getConfig().get().getNode("Gyms", key, "LeaderMessage").getString());
            this.setLevelcap(GymConfig.getConfig().get().getNode("Gyms", key, "LevelCap").getInt());
            try {
                this.setRewards(GymConfig.getConfig().get().getNode("Gyms", key, "Rewards").getList(TypeToken.of(String.class)));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
            try {
                this.setLore(GymConfig.getConfig().get().getNode("Gyms", key, "UI", "Lore").getList(TypeToken.of(String.class)));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
            String[] elements = getBadgeitemstring().split(" ");
            this.setGymBadge(setDefaultIcon(elements));
        }

        
        public List<Pokemon> setGymPokemon(String gymKey) {
            List<Pokemon> gymPokemon = new ArrayList<>();
            CommentedConfigurationNode node = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon");
            Map nodemap = node.getChildrenMap();
            for (Object obj: nodemap.keySet()) {
                if (obj == null) {
                    NoponicGyms.log.error(gymKey);
                    NoponicGyms.log.error("OBJ Null while generating gym Pokemon");
                    continue;
                }
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

        public Pokemon generateGymPokemon(String gymKey, String node) {

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
            List<String> moves = new ArrayList <>();
            try {
                moves = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "MoveSet").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }

            List<String> specFlags = new ArrayList <>();
            try {
                specFlags = GymConfig.getConfig().get().getNode("Gyms", gymKey, "GymPokemon", node, "SpecFlags").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
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
            else {
                pokemon.setNature(EnumNature.getRandomNature());
                NoponicGyms.log.info("There was an issue generating the nature for %pokemon%, please check your config for any errors");
            }
            pokemon.getMoveset().clear();
            for (String s:moves) {
                Attack atk = new Attack(s);
                if (AttackBase.getAttackBase(s).isPresent()) {
                    pokemon.getMoveset().add(atk);
                } else {
                    NoponicGyms.log.info("The %move% for %pokemon% doesn't exist! skipping move!");
                }
            }
            if (AbilityBase.getAbility(ability).isPresent())
            pokemon.setAbility(ability);
            else NoponicGyms.log.info("There was an issue generating the ability for %pokemon%, please check your config for any errors");
            pokemon.setDynamaxLevel(dynamaxLevel);
            pokemon.setDoesLevel(false);
            pokemon.addSpecFlag("gympokemon");
            if (specFlags != null && !specFlags.isEmpty()) {
                for (String s : specFlags) {
                    pokemon.addSpecFlag(s);
                }
            }
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

        public List <String> getGymLeaderList() {
            return gymLeaderList;
        }

        public void setGymLeaderList(List <String> gymLeaderList) {
            this.gymLeaderList = gymLeaderList;
        }

        public HashMap <UUID, GymPlayer> getGymQueue() {
            return gymQueue;
        }

        public void setGymQueue(HashMap <UUID, GymPlayer> gymQueue) {
            this.gymQueue = gymQueue;
        }

        public ItemStack getGymBadge() {
            return gymBadge;
        }

        public void setGymBadge(ItemStack gymBadge) {
            this.gymBadge = gymBadge;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getTier() {
            return tier;
        }

        public void setTier(String tier) {
            this.tier = tier;
        }

        public String getLeadermessage() {
            return leadermessage;
        }

        public void setLeadermessage(String leadermessage) {
            this.leadermessage = leadermessage;
        }

        public String getBadgeitemstring() {
            return badgeitemstring;
        }

        public void setBadgeitemstring(String badgeitemstring) {
            this.badgeitemstring = badgeitemstring;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getLevelcap() {
            return levelcap;
        }

        public void setLevelcap(int levelcap) {
            this.levelcap = levelcap;
        }

        public int getWorldID() {
            return worldID;
        }

        public void setWorldID(int worldID) {
            this.worldID = worldID;
        }

        public double getPosX() {
            return posX;
        }

        public void setPosX(double posX) {
            this.posX = posX;
        }

        public double getPosY() {
            return posY;
        }

        public void setPosY(double posY) {
            this.posY = posY;
        }

        public double getPosZ() {
            return posZ;
        }

        public void setPosZ(double posZ) {
            this.posZ = posZ;
        }

        public int getLeaderWorldID() {
            return leaderWorldID;
        }

        public void setLeaderWorldID(int leaderWorldID) {
            this.leaderWorldID = leaderWorldID;
        }

        public double getLeaderPosX() {
            return leaderPosX;
        }

        public void setLeaderPosX(double leaderPosX) {
            this.leaderPosX = leaderPosX;
        }

        public double getLeaderPosY() {
            return leaderPosY;
        }

        public void setLeaderPosY(double leaderPosY) {
            this.leaderPosY = leaderPosY;
        }

        public double getLeaderPosZ() {
            return leaderPosZ;
        }

        public void setLeaderPosZ(double leaderPosZ) {
            this.leaderPosZ = leaderPosZ;
        }

        public int getChallengerWorldID() {
            return challengerWorldID;
        }

        public void setChallengerWorldID(int challengerWorldID) {
            this.challengerWorldID = challengerWorldID;
        }

        public double getChallengerPosX() {
            return challengerPosX;
        }

        public void setChallengerPosX(double challengerPosX) {
            this.challengerPosX = challengerPosX;
        }

        public double getChallengerPosY() {
            return challengerPosY;
        }

        public void setChallengerPosY(double challengerPosY) {
            this.challengerPosY = challengerPosY;
        }

        public double getChallengerPosZ() {
            return challengerPosZ;
        }

        public void setChallengerPosZ(double challengerPosZ) {
            this.challengerPosZ = challengerPosZ;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public List <String> getLore() {
            return lore;
        }

        public void setLore(List <String> lore) {
            this.lore = lore;
        }

        public List <Pokemon> getGymPokemon() {
            return gymPokemon;
        }

        public void setGymPokemon(List <Pokemon> gymPokemon) {
            this.gymPokemon = gymPokemon;
        }

        public boolean isGymFee() {
            return gymFee;
        }

        public void setGymFee(boolean gymFee) {
            this.gymFee = gymFee;
        }

        public boolean isPayLeader() {
            return payLeader;
        }

        public void setPayLeader(boolean payLeader) {
            this.payLeader = payLeader;
        }

        public double getFeeCost() {
            return feeCost;
        }

        public void setFeeCost(double feeCost) {
            this.feeCost = feeCost;
        }

        public List <String> getRewards() {
            return rewards;
        }

        public void setRewards(List <String> rewards) {
            this.rewards = rewards;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public boolean isQueued(UUID uuid) {
            for (GymPlayer player:queue) {
                if (player.getUuid().equals(uuid))
                    return true;
            }
            return false;
        }

        public boolean isLeader(String s) {
            for (String l:gymLeaderList) {
                if (l.equalsIgnoreCase(s))
                    return true;
            }
            return false;
        }

        public List<GymPlayer> getQueue() {
            return queue;
        }

        public void setQueue(List<GymPlayer> queue) {
            this.queue = queue;
        }

        public void removeFromQueue(GymPlayer player) {
            this.queue.remove(player);
            player.setQueuePos(0);
            updateQueue();
        }

        public void updateQueuePos(GymPlayer player, int pos, boolean increase) {
            if (increase)
                player.increaseQueuePos(pos);
            else player.decreaseQueuePos(pos);
        }

        public void addToQueue(GymPlayer player) {
            if (queue.isEmpty()) {
                player.setQueuePos(0);
                queue.add(player);
            } else {
                player.setQueuePos(queue.size() + 1);
                this.queue.add(player);
                updateQueue();
            }
        }

        public void updateQueue() {
            for (int i = 0; i < queue.size(); i++) {
                GymPlayer p = queue.get(i);
                updateQueuePos(p, i -1, false);
            }
            queue.sort(Comparator.comparing(GymPlayer::getQueuePos));
        }
    }
}
