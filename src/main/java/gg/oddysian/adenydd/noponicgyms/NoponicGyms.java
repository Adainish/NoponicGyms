package gg.oddysian.adenydd.noponicgyms;

import com.pixelmonmod.pixelmon.Pixelmon;
import gg.oddysian.adenydd.noponicgyms.commands.Command;
import gg.oddysian.adenydd.noponicgyms.listener.PixelmonListener;
import gg.oddysian.adenydd.noponicgyms.storage.config.Config;
import gg.oddysian.adenydd.noponicgyms.storage.config.GymConfig;
import gg.oddysian.adenydd.noponicgyms.listener.PlayerListener;
import gg.oddysian.adenydd.noponicgyms.storage.config.LanguageConfig;
import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.registry.ModeRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
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
    public static File dataDir;

    @Mod.Instance(MOD_ID)
    public static NoponicGyms INSTANCE;


    public static PlayerListener playerListener;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        log.info("Meh Meh! Tora booting up " + MOD_NAME + " by Miss DevPon " + AUTHORS + " " + VERSION + " - 2021" + "to make Server better than any ServerPon!\n Poppi at your service!");
        configDir = new File(event.getModConfigurationDirectory() + "/");
        configDir.mkdir();

        dataDir = new File(event.getModConfigurationDirectory() + "/NoponicGyms/data");
        dataDir.mkdir();

        initConfig(); //Init all Configs
        loadConfig(); //Load all configs

        playerListener = new PlayerListener();
        Pixelmon.EVENT_BUS.register(PixelmonListener.class);
    }

    @Mod.EventHandler
    public void starting(FMLServerStartingEvent event) {

        initOBJ();
        event.registerServerCommand(new Command());
    }

    private void initConfig() {
        Config.getConfig().setup();
        GymConfig.getConfig().setup();
        LanguageConfig.getConfig().setup();
    }

    public void initOBJ() {
        GymsRegistry.loadGyms();
        ModeRegistry.loadGymModes();
    }

    public void loadConfig() {
        Config.getConfig().load();
        LanguageConfig.getConfig().load();
        GymConfig.getConfig().load();
    }

}
