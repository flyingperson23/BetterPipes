package flyingperson.BetterPipes;

import flyingperson.BetterPipes.compat.*;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEEnergy;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEFluid;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEItem;
import flyingperson.BetterPipes.compat.wrench.*;
import flyingperson.BetterPipes.proxy.CommonProxy;
import flyingperson.BetterPipes.util.AE2EventHandler;
import flyingperson.BetterPipes.util.BCEventHandler;
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
    public static final String VERSION = "0.14";

    public static Logger logger;

    @Mod.Instance
    public static BetterPipes instance;

    public static final SimpleNetworkWrapper BETTER_PIPES_NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("betterpipes");

    public int counter = 0;

    @SidedProxy(clientSide = "flyingperson.BetterPipes.proxy.ClientProxy", serverSide = "flyingperson.BetterPipes.proxy.ServerProxy")
    public static CommonProxy proxy;

    public ArrayList<BlockPos> wrenchMap = new ArrayList<>();
	
	

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BetterPipesEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
        if (Loader.isModLoaded("buildcrafttransport")) MinecraftForge.EVENT_BUS.register(new BCEventHandler());
        if (Loader.isModLoaded("appliedenergistics2")) MinecraftForge.EVENT_BUS.register(new AE2EventHandler());
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        if (BPConfig.wrenchCompat.addWrench) {
            WRENCH_PROVIDERS.add(new DefaultWrenchProvider());
        }
        if (BPConfig.compat.vanilla) {
            COMPAT_LIST.add(new CompatVanilla());
        }
        if (BPConfig.compat.thermal && Loader.isModLoaded("thermaldynamics")) {
            COMPAT_LIST.add(new CompatThermalDynamics());
            WRENCH_PROVIDERS.add(new ThermalDynamicsWrenchProvider());
        }
        if (BPConfig.compat.enderIO && Loader.isModLoaded("enderio")) {
            COMPAT_LIST.add(new CompatEnderIO());
            WRENCH_PROVIDERS.add(new EnderIOWrenchProvider());
        }
        if (BPConfig.compat.ae2 && Loader.isModLoaded("appliedenergistics2")) {
            COMPAT_LIST.add(new CompatAE2());
            WRENCH_PROVIDERS.add(new AE2WrenchProvider());
        }
        if (BPConfig.compat.bc && Loader.isModLoaded("buildcrafttransport")) {
            COMPAT_LIST.add(new CompatBC());
            WRENCH_PROVIDERS.add(new BCWrenchProvider());
        }
        if (BPConfig.compat.lp && Loader.isModLoaded("logisticspipes")) {
            COMPAT_LIST.add(new CompatLogisticsPipes());
        }
        if (BPConfig.compat.immersiveEngineering && Loader.isModLoaded("immersiveengineering")) {
            COMPAT_LIST.add(new CompatImmersiveEngineering());
        }
        if (BPConfig.compat.mekanism && Loader.isModLoaded("mekanism")) {
            COMPAT_LIST.add(new CompatMekanism());
            WRENCH_PROVIDERS.add(new MekanismWrenchProvider());
        }
        if (BPConfig.compat.cyclic && Loader.isModLoaded("cyclicmagic")) {
            COMPAT_LIST.add(new CompatCyclic());
        }
        if (BPConfig.compat.pneumaticcraft && Loader.isModLoaded("pneumaticcraft")) {
            COMPAT_LIST.add(new CompatPneumaticCraft());
            WRENCH_PROVIDERS.add(new PneumaticCraftWrenchProvider());
        }
        if (BPConfig.compat.gtce && Loader.isModLoaded("gregtech")) {
            COMPAT_LIST.add(new CompatGTCEItem());
            COMPAT_LIST.add(new CompatGTCEFluid());
            COMPAT_LIST.add(new CompatGTCEEnergy());
            WRENCH_PROVIDERS.add(new GTCEWrenchProvider());
        }
        if (BPConfig.compat.exu2 && Loader.isModLoaded("extrautils2")) {
            COMPAT_LIST.add(new CompatExU2());
            COMPAT_LIST.add(new CompatExU2TE());
            WRENCH_PROVIDERS.add(new ExU2WrenchProvider());
        }
        if (Loader.isModLoaded("ic2")) {
            WRENCH_PROVIDERS.add(new IC2WrenchProvider());
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    public ArrayList<ICompatBase> COMPAT_LIST = new ArrayList<>();
    public ArrayList<IWrenchProvider> WRENCH_PROVIDERS = new ArrayList<>();

}
