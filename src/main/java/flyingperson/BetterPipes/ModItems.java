package flyingperson.BetterPipes;

import flyingperson.BetterPipes.item.ItemWrench;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("betterpipes:wrench")
    public static ItemWrench itemWrench;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        if (BPConfig.wrenchCompat.addWrench) itemWrench.initModel();
    }
}