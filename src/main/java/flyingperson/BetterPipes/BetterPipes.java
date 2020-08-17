package flyingperson.BetterPipes;

import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.compat.CompatImmersiveEngineering;
import flyingperson.BetterPipes.compat.CompatThermalExpansion;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEEnergy;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEFluid;
import flyingperson.BetterPipes.compat.gtce.CompatGTCEItem;
import flyingperson.BetterPipes.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(modid = BetterPipes.MODID, name = BetterPipes.NAME, version = BetterPipes.VERSION, dependencies = "required:codechickenlib")
public class BetterPipes
{
    public static final String MODID = "betterpipes";
    public static final String NAME = "Better Pipes";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @Mod.Instance
    public static BetterPipes instance;

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("betterpipes");

    public int counter = 0;

    @SidedProxy(clientSide = "flyingperson.BetterPipes.proxy.ClientProxy", serverSide = "flyingperson.BetterPipes.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new BetterPipesEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public static final CompatBase[] COMPAT_LIST = {
            new CompatThermalExpansion(),
            new CompatImmersiveEngineering(),
            new CompatGTCEEnergy(),
            new CompatGTCEFluid(),
            new CompatGTCEItem()
    };

}
