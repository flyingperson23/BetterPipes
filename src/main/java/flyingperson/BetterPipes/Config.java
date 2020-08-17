package flyingperson.BetterPipes;

import flyingperson.BetterPipes.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_GENERAL = "general";

    public static boolean sneaking_makes_pipes_connect = false;
    public static boolean clicking_on_pipes_connects_them = true;


    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            BetterPipes.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        cfg.setCategoryRequiresMcRestart(CATEGORY_GENERAL, false);
        cfg.setCategoryRequiresWorldRestart(CATEGORY_GENERAL, false);
        sneaking_makes_pipes_connect = cfg.getBoolean("sneaking_makes_pipes_connect", CATEGORY_GENERAL, sneaking_makes_pipes_connect, "Should pipes auto-connect when sneaking?");
        clicking_on_pipes_connects_them = cfg.getBoolean("clicking_on_pipes_connects_them", CATEGORY_GENERAL, clicking_on_pipes_connects_them, "Should one pipe autoconnect to another if you place the first looking at the second (see gt6 or gt:nh)?");
    }
}