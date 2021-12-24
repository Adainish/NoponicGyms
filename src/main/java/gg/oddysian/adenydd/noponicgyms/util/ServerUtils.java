package gg.oddysian.adenydd.noponicgyms.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.oddysian.adenydd.noponicgyms.storage.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.util.UUID;

public class ServerUtils {
    private static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static boolean isPlayerOnline(EntityPlayerMP player) {
        return isPlayerOnline(player.getUniqueID());
    }

    public String getUserName(String uuid){
        String url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "")+"/names";
        try {
            String json = IOUtils.toString(new URL(url));
            JsonElement element = new JsonParser().parse(json);
            JsonArray nameArray = element.getAsJsonArray();
            JsonObject nameElement = nameArray.get(nameArray.size()-1).getAsJsonObject();
            nameElement.get("name").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static EntityPlayerMP getPlayer(String player) {
        return getInstance().getPlayerList().getPlayerByUsername(player);
    }
    public static boolean isPlayerOnline(UUID uuid) {
        EntityPlayerMP player = SERVER.getPlayerList().getPlayerByUUID(uuid);
        // IJ says it's always true ignore
        return player != null;
    }
    public static void send(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString((Config.getConfig().get().getNode("ServerPrefix").getString() + " " + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }
    public static void doBroadcast(String message) {
        SERVER.getPlayerList().sendMessage(new TextComponentString(Config.getConfig().get().getNode("ServerPrefix").getString().replaceAll("&([0-9a-fk-or])","\u00a7$1") + " " + message.replaceAll("&([0-9a-fk-or])","\u00a7$1")));
    }

    public static void runCommand(String cmd) {
        SERVER.getCommandManager().executeCommand(SERVER, cmd);
    }

    public static MinecraftServer getInstance() {
        return SERVER;
    }
}
