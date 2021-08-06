package gg.oddysian.adenydd.noponicgyms;

import gg.oddysian.adenydd.noponicgyms.config.Config;
import gg.oddysian.adenydd.noponicgyms.config.GymConfig;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Mod(
        modid = NoponicGyms.MOD_ID,
        name = NoponicGyms.MOD_NAME,
        version = NoponicGyms.VERSION,
        acceptableRemoteVersions = "*"
)
public class NoponicGyms {

    public static final String MOD_ID = "noponicgyms";
    public static final String MOD_NAME = "NoponicGyms";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final List<String> AUTHORS = Arrays.asList("Adenydd");
    public static Logger log = LogManager.getLogger(MOD_NAME);
    public static File configDir;
    @Mod.Instance(MOD_ID)
    public static NoponicGyms INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        log.info("Meh Meh! Tora booting up " + MOD_NAME + " by Miss DevPon " + AUTHORS + " " + VERSION + " - 2021" + "to make Server better than any ServerPon!\n Poppi at your service!");

        configDir = new File(event.getModConfigurationDirectory() + "/");
        configDir.mkdir();

        Config.getConfig().setup();
        GymConfig.getConfig().setup();

        Config.getConfig().load();
        GymConfig.getConfig().load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }


    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
