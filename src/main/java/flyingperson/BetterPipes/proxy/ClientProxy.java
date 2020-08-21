package flyingperson.BetterPipes.proxy;

import flyingperson.BetterPipes.ModItems;
import flyingperson.BetterPipes.util.RegisterAEStuff;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItems.initModels();
        if (Loader.isModLoaded("appliedenergistics2")) RegisterAEStuff.registerItemModel();
    }
}
