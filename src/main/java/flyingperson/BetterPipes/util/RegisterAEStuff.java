package flyingperson.BetterPipes.util;

import appeng.api.AEApi;
import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class RegisterAEStuff {
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(aepart);
    }

    public static AEPartItem aepart = new AEPartItem();

    public static void registerPartModel() {
        AEApi.instance().registries().partModels().registerModels(new ResourceLocation(BetterPipes.MODID, "ae2part/blockpart" ));
    }

    public static void registerItemModel() {
        aepart.initModel();
    }
}
