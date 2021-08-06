package gg.oddysian.adenydd.noponicgyms.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class ServerUtils {
    private static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static boolean isPlayerOnline(EntityPlayerMP player) {
        return isPlayerOnline(player.getUniqueID());
    }

    public static boolean isPlayerOnline(UUID uuid) {
        EntityPlayerMP player = SERVER.getPlayerList().getPlayerByUUID(uuid);
        // IJ says it's always true ignore
        return player != null;
    }

    public static void doBroadcast(String message) {
        SERVER.getPlayerList().sendMessage(new TextComponentString(message.replaceAll("&([0-9a-fk-or])","\u00a7$1")));
    }

    public static void runCommand(String cmd) {
        SERVER.getCommandManager().executeCommand(SERVER, cmd);
    }

    public static MinecraftServer getInstance() {
        return SERVER;
    }
}
