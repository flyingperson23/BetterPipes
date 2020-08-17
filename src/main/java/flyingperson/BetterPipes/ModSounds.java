package flyingperson.BetterPipes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds {
    public static final SoundEvent wrench_sound = new SoundEvent(new ResourceLocation("betterpipes", "wrench")).setRegistryName(new ResourceLocation(BetterPipes.MODID, "wrench"));;

    public static void init(IForgeRegistry<SoundEvent> registry) {
        registry.register(wrench_sound);
    }
}
