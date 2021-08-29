package gg.oddysian.adenydd.noponicgyms.commands;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.config.Config;
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
import net.minecraft.util.text.TextFormatting;

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


        if (arguments.length == 1) {
            if (arguments[0].contains("reload") && PermissionUtils.hasPermission(PermissionManager.ADMIN_RELOAD, (EntityPlayerMP) sender)) {
                NoponicGyms.INSTANCE.loadConfig();
                NoponicGyms.INSTANCE.initOBJ();
                ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");
            } else {
                ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
            }

            if (arguments[0].contains("badges") && PermissionUtils.hasPermission("noponicgyms.user.checkbadges", (EntityPlayerMP) sender)) {
                EntityPlayerMP playerMP = ServerUtils.getInstance().getPlayerList().getPlayerByUUID(sender.getCommandSenderEntity().getUniqueID());
                GymPlayer gymPlayer = GymPlayer.gymPlayerHashMap.get(playerMP.getUniqueID());
                UI.gymBadges(gymPlayer).openPage(playerMP);
            } else {
                ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
            }

            if (arguments[0].contains("list") && PermissionUtils.canUse("noponicgyms.user.listgyms", sender)) {

            }
            else {
                ServerUtils.send(sender, "&b(&e!&b) &eYou're not allowed to use this command");
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

}
