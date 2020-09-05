package flyingperson.BetterPipes;

import flyingperson.BetterPipes.compat.*;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEEnergy;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEFluid;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEItem;
import flyingperson.BetterPipes.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = BetterPipes.MODID, name = BetterPipes.NAME, version = BetterPipes.VERSION, dependencies = "after:appliedenergistics2; after:buildcrafttransport")
public class BetterPipes
{
    public static final String MODID = "betterpipes";
    public static final String NAME = "Better Pipes";
    public static final String VERSION = "0.13";

    public static Logger logger;

    @Mod.Instance
    public static BetterPipes instance;

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("betterpipes");

    public int counter = 0;

    @SidedProxy(clientSide = "flyingperson.BetterPipes.proxy.ClientProxy", serverSide = "flyingperson.BetterPipes.proxy.ServerProxy")
    public static CommonProxy proxy;

    public ArrayList<BlockPos> wrenchMap = new ArrayList<>();
    public ArrayList<BlockPos> wrenchMapNoTE = new ArrayList<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BetterPipesEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        if (BPConfig.compat.thermal && Loader.isModLoaded("thermaldynamics")) COMPAT_LIST.add(new CompatThermalDynamics());
        if (BPConfig.compat.enderIO && Loader.isModLoaded("enderio")) COMPAT_LIST.add(new CompatEnderIO());
        if (BPConfig.compat.ae2 && Loader.isModLoaded("appliedenergistics2")) COMPAT_LIST.add(new CompatAE2());
        if (BPConfig.compat.bc && Loader.isModLoaded("buildcrafttransport")) COMPAT_LIST.add(new CompatBC());
        if (BPConfig.compat.lp && Loader.isModLoaded("logisticspipes")) COMPAT_LIST.add(new CompatLogisticsPipes());
        if (BPConfig.compat.immersiveEngineering && Loader.isModLoaded("immersiveengineering")) COMPAT_LIST.add(new CompatImmersiveEngineering());
        if (BPConfig.compat.mekanism && Loader.isModLoaded("mekanism")) COMPAT_LIST.add(new CompatMekanism());
        if (BPConfig.compat.cyclic && Loader.isModLoaded("cyclicmagic")) COMPAT_LIST.add(new CompatCyclic());
        if (BPConfig.compat.pneumaticcraft && Loader.isModLoaded("pneumaticcraft")) COMPAT_LIST.add(new CompatPneumaticCraft());
        if (BPConfig.compat.gtce && Loader.isModLoaded("gregtech")) {
            COMPAT_LIST.add(new CompatGTCEItem());
            COMPAT_LIST.add(new CompatGTCEFluid());
            COMPAT_LIST.add(new CompatGTCEEnergy());
        }


        if (BPConfig.compat.exu2 && Loader.isModLoaded("extrautils2")) {
            COMPAT_LIST_NO_TE.add(new CompatExU2());
            COMPAT_LIST.add(new CompatExU2TE());
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    public ArrayList<CompatBase> COMPAT_LIST = new ArrayList<>();
    public ArrayList<CompatBaseNoTE> COMPAT_LIST_NO_TE = new ArrayList<>();

    public ArrayList<Item> WRENCH_LIST = new ArrayList<>();

}
