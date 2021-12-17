package gg.oddysian.adenydd.noponicgyms.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import net.minecraft.entity.player.EntityPlayer;

import java.io.*;

public class StoreMethods {

    public static void writeGymPlayer(EntityPlayer player) {
        File playerFile = new File(NoponicGyms.dataDir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUniqueID())));
        if (playerFile.exists()) {
            NoponicGyms.log.error("There was an issue generating the GymPlayerWrapper, Data already exists? Ending function");
            return;
        }
        GymPlayer gymPlayer = new GymPlayer(player.getUniqueID());

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson(gymPlayer);

        try {
            playerFile.createNewFile();
            FileWriter writer = new FileWriter(playerFile);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateGymPlayerData(GymPlayer player) {
        File playerFile = new File(NoponicGyms.dataDir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUuid())));
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson(player);
        try {
            playerFile.createNewFile();
            FileWriter writer = new FileWriter(playerFile);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeGymBadge(EntityPlayer player, GymBadge badge) {

        GymPlayer gymPlayer = getGymPlayer(player);

        if (gymPlayer == null)
            return;

        if (gymPlayer.hasSpecificBadge(badge.getBadgeName()))
            gymPlayer.removeBadge(badge);

        gymPlayer.addBadge(badge);
        updateGymPlayerData(gymPlayer);
    }

    public static GymPlayer getGymPlayer(EntityPlayer player) {

        File playerFile = new File(NoponicGyms.dataDir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUniqueID())));

        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(playerFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            NoponicGyms.log.error("Something went wrong attempting to read the GymPlayer Data");
            return null;
        }

        return gson.fromJson(reader, GymPlayer.class);
    }
}
