package flyingperson.BetterPipes.util;

import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pluggable.PluggableDefinition;
import buildcraft.lib.item.ItemPluggableSimple;
import buildcraft.lib.registry.RegistrationHelper;
import buildcraft.lib.registry.RegistryConfig;
import buildcraft.lib.registry.TagManager.EnumTagType;
import buildcraft.lib.registry.TagManager;
import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import java.io.File;
import java.util.function.Consumer;

public class RegisterBCStuff {
    private static final RegistrationHelper HELPER = new RegistrationHelper();

    public static ItemPluggableSimple blockPart;

    public static PluggableDefinition pluggableDefinition;

    public static Configuration objConfig;

    public static void preInit(FMLPreInitializationEvent e) {
        objConfig = RegistryConfig.setRegistryConfig(BetterPipes.MODID, new File(e.getModConfigurationDirectory().toURI().getRawPath()+"/unused"));
        pluggableDefinition = register(new PluggableDefinition(new ResourceLocation(BetterPipes.MODID, "BCBlocker"), BCBlockPart::new));
        blockPart = HELPER.addItem(new ItemPluggable("betterpipes.blocker", pluggableDefinition));
    }

    private static PluggableDefinition register(PluggableDefinition def) {
        PipeApi.pluggableRegistry.register(def);
        return def;
    }



    static {
        startBatch();

        registerTag("betterpipes.blocker").reg("blocker").locale("Blocker").model("blocker").tab("buildcraft.plugs");

        endBatch(TagManager.prependTags("betterpipes:", EnumTagType.REGISTRY_NAME, EnumTagType.MODEL_LOCATION).andThen(TagManager.setTab("buildcraft.main")));
    }

    private static TagManager.TagEntry registerTag(String id) {
        return TagManager.registerTag(id);
    }

    private static void startBatch() {
        TagManager.startBatch();
    }

    private static void endBatch(Consumer<TagManager.TagEntry> consumer) {
        TagManager.endBatch(consumer);
    }
}
