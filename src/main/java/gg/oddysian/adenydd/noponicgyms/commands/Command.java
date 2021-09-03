package gg.oddysian.adenydd.noponicgyms.commands;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.battles.handling.GymQueue;
import gg.oddysian.adenydd.noponicgyms.storage.config.Config;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.ui.UI;
import gg.oddysian.adenydd.noponicgyms.util.PermissionManager;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
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

                if (arguments[0].contains("reload") && PermissionUtils.hasPermission(PermissionManager.ADMIN_RELOAD, (EntityPlayerMP) sender)) {
                    NoponicGyms.INSTANCE.loadConfig();
                    NoponicGyms.INSTANCE.initOBJ();
                    ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");

                } else {
                    ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this!");
                }


                if (arguments[0].contains("badges") && PermissionUtils.hasPermission("noponicgyms.user.checkbadges", (EntityPlayerMP) sender)) {
                    EntityPlayerMP playerMP = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                    GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(playerMP.getUniqueID());
                    UI.gymBadges(gymPlayer, playerMP, false).openPage(playerMP);
                } else {
                    ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");

                }


                if (arguments[0].contains("list") && PermissionUtils.hasPermission("noponicgyms.user.listgyms", (EntityPlayerMP) sender)) {
                    EntityPlayerMP playerMP = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                    GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(playerMP.getUniqueID());
                    UI.gyms(gymPlayer).openPage(gymPlayer.getPlayer());

                } else {
                    ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
                }
            }


            if (arguments.length == 2) {

                if (arguments[0].contains("checkbadges") && PermissionUtils.hasPermission("noponicgyms.leader.checkbadges", (EntityPlayerMP) sender)) {

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

                    UI.gymBadges(GymPlayer.gymPlayerHashMap.get(target.getUniqueID()), opener, true).openPage(opener);
                    ServerUtils.send(sender, "Checking %targets% gym badges!".replaceAll("%targets%", target.getName()));

                }

                if (arguments[0].contains("challenge")) {
                    if (!GymObj.isGym(arguments[1])) {
                        ServerUtils.send(sender, "&eThat's not a valid gym!");
                        return;
                    }
                    GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(((EntityPlayerMP) sender).getUniqueID());
                    GymObj.Gym challengingGym = GymObj.getGym(arguments[1]);

                    if (gymPlayer.isQueuedFor(gymPlayer, challengingGym)) {
                        ServerUtils.send(sender, "&eYou're already in the queue for this Gym!");
                        return;
                    }
                    if (gymPlayer.isQueued()) {
                        ServerUtils.send(sender, "&eYou're already in the queue for another Gym!");
                        return;
                    }
                    gymPlayer.setQueued(true);
                    gymPlayer.setQueuedFor(gymPlayer, challengingGym, true);
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
            possibleArgs.add("givebadge");
            if (sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
            possibleArgs.add("takebadge");
//            possibleArgs.addAll(Arrays.asList(server.getPlayerList().getOnlinePlayerNames()));0
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("givebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            if (args[0].equalsIgnoreCase("takebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("givebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_GIVE, (EntityPlayerMP) sender))
            {
                GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(server.getPlayerList().getPlayerByUsername(args[1]));
                if (gymPlayer == null) {
                    possibleArgs.add("player does not exist");
                } else
                possibleArgs.addAll(gymPlayer.badgeList());
            }
            if (args[0].equalsIgnoreCase("takebadge") && sender instanceof EntityPlayerMP && PermissionUtils.hasPermission(PermissionManager.LEADER_TAKE, (EntityPlayerMP) sender))
            {
                GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(server.getPlayerList().getPlayerByUsername(args[1]));
                if (gymPlayer == null) {
                    possibleArgs.add("player does not exist");
                } else
                    possibleArgs.addAll(gymPlayer.badgeList());
            }
        }
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}