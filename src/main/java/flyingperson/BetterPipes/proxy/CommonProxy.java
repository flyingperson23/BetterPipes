package flyingperson.BetterPipes.proxy;

import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.ModSounds;
import flyingperson.BetterPipes.item.ItemWrench;
import flyingperson.BetterPipes.network.MessageGetConnections;
import flyingperson.BetterPipes.network.MessagePlaySound;
import flyingperson.BetterPipes.network.MessageReturnConnections;
import flyingperson.BetterPipes.network.MessageSwingArm;
import flyingperson.BetterPipes.util.RegisterAEStuff;
import flyingperson.BetterPipes.util.RegisterBCStuff;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        if (Loader.isModLoaded("appliedenergistics2")) RegisterAEStuff.registerPartModel();
        if (Loader.isModLoaded("buildcrafttransport")) RegisterBCStuff.preInit(e);
    }

    public void init(FMLInitializationEvent e) {
        ConfigManager.sync(BetterPipes.MODID, Config.Type.INSTANCE);

        BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.registerMessage(MessageGetConnections.MessageHandler.class, MessageGetConnections.class, 0, Side.SERVER);
        BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.registerMessage(MessageReturnConnections.MessageHandler.class, MessageReturnConnections.class, 1, Side.CLIENT);
        BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.registerMessage(MessagePlaySound.MessageHandler.class, MessagePlaySound.class, 2, Side.CLIENT);
        BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.registerMessage(MessageSwingArm.MessageHandler.class, MessageSwingArm.class, 3, Side.CLIENT);
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        if (BPConfig.wrenchCompat.addWrench) event.getRegistry().register(new ItemWrench());
        if (Loader.isModLoaded("appliedenergistics2")) RegisterAEStuff.registerItems(event);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> sounds) {
        ModSounds.init(sounds.getRegistry());
    }
}