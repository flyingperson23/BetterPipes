package flyingperson.BetterPipes;

import net.minecraftforge.common.config.Config;

@Config(modid = BetterPipes.MODID, name = "betterpipes", category = "")
public class BPConfig {
    @Config.Comment("General configuration")
    public static General general = new General();
    public static class General {
        @Config.Comment("Should pressing shift make pipes connect?")
        public boolean sneaking_makes_pipes_connect = false;
        @Config.Comment("Should pipes connect when placed on each other?")
        public boolean clicking_on_pipes_connects_them = true;
    }

    @Config.Comment("Visual settings")
    public static Visual visual = new Visual();
    public static class Visual {
        @Config.Comment("Highlight wrenched side on wrenched block? (Not recommended)")
        public boolean highlight_wrench_hover = false;
        @Config.Comment("Highlight entire wrenched block? (Recommended)")
        public boolean block_outline = true;
    }

    @Config.Comment("Enable/disable mod compat")
    public static Compat compat = new Compat();
    @Config.RequiresMcRestart
    public static class Compat {
        @Config.Comment("Compat for GTCE?")
        public boolean gtce = true;
        @Config.Comment("Compat for AE2?")
        public boolean ae2 = true;
        @Config.Comment("Compat for BuildCraft?")
        public boolean bc = true;
        @Config.Comment("Compat for EnderIO?")
        public boolean enderIO = true;
        @Config.Comment("Compat for Immersive Engineering? (WIP)")
        public boolean immersiveEngineering = false;
        @Config.Comment("Compat for Logistics Pipes? (WIP)")
        public boolean lp = false;
        @Config.Comment("Compat for Thermal Dynamics?")
        public boolean thermal = true;
        @Config.Comment("Compat for Mekanism")
        public boolean mekanism = true;
    }
}