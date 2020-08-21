package flyingperson.BetterPipes.proxy;

import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.Config;
import flyingperson.BetterPipes.ModSounds;
import flyingperson.BetterPipes.item.ItemWrench;
import flyingperson.BetterPipes.network.MessageGetConnections;
import flyingperson.BetterPipes.network.MessageReturnConnections;
import flyingperson.BetterPipes.util.RegisterAEStuff;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    // Config instance
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "betterpipes.cfg"));
        Config.readConfig();

    }

    public void init(FMLInitializationEvent e) {
        BetterPipes.INSTANCE.registerMessage(MessageGetConnections.MessageHandler.class, MessageGetConnections.class, 0, Side.SERVER);
        BetterPipes.INSTANCE.registerMessage(MessageReturnConnections.MessageHandler.class, MessageReturnConnections.class, 1, Side.CLIENT);
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemWrench());
        if (Loader.isModLoaded("appliedenergistics2")) RegisterAEStuff.registerItems(event);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> sounds) {
        ModSounds.init(sounds.getRegistry());
    }
}