package gg.oddysian.adenydd.noponicgyms.commands;

import gg.oddysian.adenydd.noponicgyms.config.Config;
import gg.oddysian.adenydd.noponicgyms.config.GymConfig;
import gg.oddysian.adenydd.noponicgyms.obj.GymObj;
import gg.oddysian.adenydd.noponicgyms.obj.ModeObj;
import gg.oddysian.adenydd.noponicgyms.util.PermissionManager;
import gg.oddysian.adenydd.noponicgyms.util.PermissionUtils;
import gg.oddysian.adenydd.noponicgyms.util.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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

            if (PermissionUtils.canUse(PermissionManager.ADMIN_RELOAD, sender)) {
                Config.getConfig().load();
                GymConfig.getConfig().load();
                GymObj.loadGyms();
                ModeObj.loadGymModes();
                ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");
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
