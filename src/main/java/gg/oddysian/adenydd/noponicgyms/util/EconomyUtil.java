package gg.oddysian.adenydd.noponicgyms.util;

import com.pixelmonmod.pixelmon.Pixelmon;

import java.util.UUID;

public class EconomyUtil {
    public static int getPlayerBalance(UUID uuid) {
        return Pixelmon.moneyManager.getBankAccount(uuid).get().getMoney();
    }

    public static void setBalance(UUID uuid, int newBalance) {
        Pixelmon.moneyManager.getBankAccount(uuid).get().setMoney(newBalance);
    }

    public static void giveBalance(UUID uuid, int amount) {
        setBalance(uuid, (getPlayerBalance(uuid) + amount));
    }

    public static void takeBalance(UUID uuid, int amount) {
        setBalance(uuid, (getPlayerBalance(uuid) - amount));
    }

    public static void resetBalance(UUID uuid) {
        Pixelmon.moneyManager.getBankAccount(uuid).get().setMoney(0);
    }

    public static boolean canAfford(UUID uuid, int amount) {
        return (getPlayerBalance(uuid) - amount) >= 0;
    }
}
