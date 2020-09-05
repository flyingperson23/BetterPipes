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
        @Config.Comment("Compat for vanilla?")
        public boolean vanilla = true;
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
        @Config.Comment("Compat for Mekanism?")
        public boolean mekanism = true;
        @Config.Comment("Compat for Cyclic?")
        public boolean cyclic = true;
        @Config.Comment("Compat for PneumaticCraft: Repressurized?")
        public boolean pneumaticcraft = true;
        @Config.Comment("Compat for ExU2?")
        public boolean exu2 = true;
    }

    @Config.Comment("Add other mods' wrenches?")
    public static WrenchCompat wrenchCompat = new WrenchCompat();
    public static class WrenchCompat {
        @Config.Comment("Compat for GTCE wrenches?")
        public boolean gtceWrench = true;
        @Config.Comment("Compat for AE2 wrenches?")
        public boolean ae2Wrench = true;
        @Config.Comment("Compat for BuildCraft wrench?")
        public boolean bcWrench = true;
        @Config.Comment("Compat for EnderIO yeta wrench?")
        public boolean enderIOWrench = true;
        @Config.Comment("Compat for Immersive Engineering engineer's hammer?")
        public boolean immersiveEngineeringWrench = true;
        @Config.Comment("Compat for Thermal Dynamics crescent hammer?")
        public boolean thermalWrench = true;
        @Config.Comment("Compat for Mekanism wrenches?")
        public boolean mekanismWrench = true;
        @Config.Comment("Compat for PneumaticCraft: Repressurized wrench?")
        public boolean pneumaticcraftwrench = true;
        @Config.Comment("Compat for ExU2 wrench?")
        public boolean exu2wrench = true;
        @Config.Comment("Should a wrench from this mod be added?")
        public boolean addWrench = true;
    }
}