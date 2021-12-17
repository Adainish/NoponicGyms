package gg.oddysian.adenydd.noponicgyms.commands;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.methods.GymMethods;
import gg.oddysian.adenydd.noponicgyms.storage.config.Config;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import gg.oddysian.adenydd.noponicgyms.ui.UI;
import gg.oddysian.adenydd.noponicgyms.util.PermissionManager;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command extends CommandBase {
    @Override
    public String getName() {
        return "nopongyms";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return Config.getConfig().get().getNode("ServerPrefix").getString() + TextFormatting.RED + "Use: /" + this.getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) throws CommandException {
        if (sender instanceof EntityPlayerMP) {

            if (!PermissionUtils.hasPermission("noponicgyms.command.nopongyms", (EntityPlayerMP) sender)) {
                ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
                return;
            }
            if (arguments.length == 0)
                getUsage(sender);


            if (arguments.length == 1) {

                if (arguments[0].contains("reload")) {
                    if (PermissionUtils.hasPermission(PermissionManager.ADMIN_RELOAD, (EntityPlayerMP) sender)) {
                        NoponicGyms.INSTANCE.loadConfig();
                        NoponicGyms.INSTANCE.initOBJ();
                        ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");
                    } else {
                        ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this!");
                    }
                }

                if (arguments[0].contains("queue")) {
                    EntityPlayerMP player = ServerUtils.getPlayer(sender.getName());
                    GymPlayer gymPlayer = GymPlayerWrapper.gymPlayerHashMap.get(player.getUniqueID());

//                    if (!gymPlayer.isQueued()) {
//                        ServerUtils.send(player, "&cOi, You're not currently queue'd, try challenging a gym using /gym challenge <gymname>");
//                        return;
//                    }
//                    List<String> msg = new ArrayList<>();
//                    if (GymObj.getGym(gymPlayer.getQueuedForGym()) == null || gymPlayer.getQueuedForGym().isEmpty()) {
//                        ServerUtils.send(player, "&eIt seems the Queue was empty or the Gym currently does not exist");
//                        return;
//                    }
//                    GymObj.getGym(gymPlayerWrapper.getQueuedForGym()).gymQueue.forEach((uuid, queuePlayer) -> {
//                        msg.add(queuePlayer.getPlayer().getName());
//                    });

//                    for (String s : msg) {
//                        ServerUtils.send(player, s);
//                    }
                }


                if (arguments[0].contains("badges")) {
                    if (PermissionUtils.hasPermission("noponicgyms.user.checkbadges", (EntityPlayerMP) sender)) {
                        EntityPlayerMP playerMP = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                        GymPlayer gymPlayer = GymPlayerWrapper.gymPlayerHashMap.get(playerMP.getUniqueID());
                        UI.gymBadges(gymPlayer, false).openPage(playerMP);
                    } else {
                        ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");

                    }
                }


                if (arguments[0].contains("list")) {
                    if (PermissionUtils.hasPermission("noponicgyms.user.listgyms", (EntityPlayerMP) sender)) {
                        EntityPlayerMP playerMP = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                        UI.gyms().openPage(ServerUtils.getPlayer(playerMP.getName()));

                    } else {
                        ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
                    }
                }
            }


            if (arguments.length == 2) {

                if (arguments[0].contains("checkbadges")) {
                    if (PermissionUtils.hasPermission("noponicgyms.leader.checkbadges", (EntityPlayerMP) sender)) {

                        EntityPlayerMP opener = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                        String arg = arguments[1];

                        if (arg == null) {
                            ServerUtils.send(sender, "&cThe Target Player can't be null!");
                            return;
                        }

                        EntityPlayerMP target = ServerUtils.getInstance().getPlayerList().getPlayerByUsername(arg);

                        if (target == null) {
                            ServerUtils.send(sender, "&cThe target player isn't online or doesn't exist!");
                            return;
                        }

                        UI.gymBadges(GymPlayerWrapper.gymPlayerHashMap.get(target.getUniqueID()), true).openPage(opener);
                        ServerUtils.send(sender, "Checking %targets% gym badges!".replaceAll("%targets%", target.getName()));

                    } else {
                        ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
                    }
                }

                if (arguments[0].contains("challenge")) {
                    if (!GymObj.isGym(arguments[1])) {
                        ServerUtils.send(sender, "&eThat's not a valid gym!");
                        return;
                    }
                    GymPlayer gymPlayerWrapper = GymPlayerWrapper.gymPlayerHashMap.get(((EntityPlayerMP) sender).getUniqueID());
                    if (gymPlayerWrapper == null) {
                        ServerUtils.send(sender, "&cIt seems there was an issue loading your Gym Player Data");
                        return;
                    }

                    GymObj.Gym challengingGym = GymObj.getGym(arguments[1]);

                    if (challengingGym == null) {
                        ServerUtils.send(sender, "&cIt seems there was an issue loading the Gym");
                        return;
                    }
//                    if (gymPlayerWrapper.isQueuedFor(gymPlayerWrapper, challengingGym)) {
//                        ServerUtils.send(sender, "&eYou're already in the queue for this Gym!");
//                        return;
//                    }
//                    if (gymPlayerWrapper.isQueued()) {
//                        ServerUtils.send(sender, "&eYou're already in the queue for another Gym!");
//                        return;
//                    }
//                    gymPlayerWrapper.setQueued(true);
//                    gymPlayerWrapper.setQueuedForGym(arguments[1]);
//                    gymPlayerWrapper.setQueuedFor(gymPlayerWrapper, challengingGym, true);
                    ServerUtils.send(sender, "&eYou entered the Gym queue for the %gym% Gym!".replaceAll("%gym%", arguments[1]));
                }
            }

            if (arguments.length == 3) {
                if (arguments[0].equalsIgnoreCase("givebadge")) {
                    EntityPlayerMP playerMP = ServerUtils.getPlayer(arguments[1]);

                    if (playerMP == null) {
                        ServerUtils.send(sender, "&c%Player% doesn't exist!".replaceAll("%Player%", arguments[1]));
                        return;
                    }

                    GymPlayer gymPlayerWrapper = GymPlayerWrapper.gymPlayerHashMap.get(playerMP.getUniqueID());

                    GymObj.Gym gym;

                    if (gymPlayerWrapper == null) {
                        ServerUtils.send(sender, "&cIt seems there was an issue loading your Gym Player Data");
                        return;
                    }

                    GymBadge gymBadge = new GymBadge();

                    if (!GymObj.isGym(arguments[2])) {
                        ServerUtils.send(sender, "&4That's not a valid Gym to give a Badge for!");
                        return;
                    }

                    gym = GymObj.getGym(arguments[2]);

                    if (gymPlayerWrapper.hasSpecificBadge(arguments[2]))
                    {
                        ServerUtils.send(sender, "&cYou can't give a badge twice!");
                    }

                    long date = System.currentTimeMillis();
                    String convertedDate = DateFormat.getDateTimeInstance().format(date);
                    gymBadge.setDate(convertedDate);
                    gymBadge.setItemstring(gym.badgeitemstring);
                    gymBadge.setBadgedisplay(gym.display);
                    EntityPlayerMP leader = ServerUtils.getPlayer(sender.getName());
                    PlayerPartyStorage pps = Pixelmon.storageManager.getParty(playerMP.getUniqueID());
                    for (Pokemon pokemon:pps.getAll()) {
                        if (pokemon == null)
                            continue;
                        if (pokemon.isEgg())
                            continue;
                        gymBadge.addPokemon(pokemon.getSpecies().name);
                    }

                    if (leader == null) {
                        ServerUtils.send(sender, "&cThe leader returned empty!");
                        return;
                    }
                    gymBadge.setLeader(leader.getName());
                    gymBadge.setBadgeName(gym.key);
                    gymBadge.setGym(gym.key);
                    gymBadge.setObtained(true);
                    GymMethods.giveGymBadge(gymPlayerWrapper, gymBadge);

                }
                if (arguments[0].equalsIgnoreCase("takebadge")) {
                    GymPlayer gymPlayerWrapper = GymPlayerWrapper.gymPlayerHashMap.get(ServerUtils.getPlayer(arguments[1]).getUniqueID());

                    GymBadge gymBadge = gymPlayerWrapper.returnSpecificBadge(arguments[2]);
                    if (gymBadge == null) {
                        ServerUtils.send(sender, "&4That's not a valid Gym Badge!");
                        return;
                    }
                    GymMethods.takeGymBadge(gymPlayerWrapper, gymBadge);
                }
            }
        } else {
            if (arguments.length == 1) {
                if (arguments[0].contains("reload")) {
                        NoponicGyms.INSTANCE.loadConfig();
                        NoponicGyms.INSTANCE.initOBJ();
                        ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");
                    } else {
                        ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this!");
                    }
                }

            }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        if (args.length == 1) {
            possibleArgs.add("reload");
            possibleArgs.add("badges");
            possibleArgs.add("list");
            possibleArgs.add("challenge");
            possibleArgs.add("queue");
            if (sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
            possibleArgs.add("checkbadges");
            if (sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
            possibleArgs.add("givebadge");
            if (sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
            possibleArgs.add("takebadge");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("givebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            if (args[0].equalsIgnoreCase("takebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            if (args[0].equalsIgnoreCase("challenge"))
                possibleArgs.addAll(GymObj.gymList());
            if (args[0].equalsIgnoreCase("checkbadges"))
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("givebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
            {
                GymPlayer gymPlayer = GymPlayerWrapper.gymPlayerHashMap.get(server.getPlayerList().getPlayerByUsername(args[1]).getUniqueID());
                if (gymPlayer == null) {
                    possibleArgs.add("player does not exist");
                } else
                    possibleArgs.addAll(GymObj.gymList());
            }
            if (args[0].equalsIgnoreCase("takebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
            {
                GymPlayer gymPlayer = GymPlayerWrapper.gymPlayerHashMap.get(server.getPlayerList().getPlayerByUsername(args[1]).getUniqueID());
                if (gymPlayer == null) {
                    possibleArgs.add("player does not exist");
                } else
                    gymPlayer.getBadges().forEach((badge -> {possibleArgs.add(badge.getGym());}));
            }
        }
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}