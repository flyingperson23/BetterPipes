package flyingperson.BetterPipes;

import net.minecraftforge.common.config.Config;

@Config(modid = BetterPipes.MODID, name = "betterpipes", category = "")
public class BPConfig {
    @Config.Comment("General configuration")
    public static General general = new General();
    public static class General{
        @Config.Comment("Should pressing shift make pipes connect?")
        public boolean sneaking_makes_pipes_connect = false;
        @Config.Comment("Should pipes connect when placed on each other?")
        public boolean clicking_on_pipes_connects_them = true;
    }
}