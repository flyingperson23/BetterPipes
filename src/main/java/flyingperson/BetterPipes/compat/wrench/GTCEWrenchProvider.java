package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.common.items.MetaItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GTCEWrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.gtceWrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == MetaItems.WRENCH.getMetaItem() || item.getItem() == MetaItems.WRENCH_LV.getMetaItem() || item.getItem() == MetaItems.WRENCH_MV.getMetaItem() || item.getItem() == MetaItems.WRENCH_HV.getMetaItem();
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        if (item.hasCapability(GregtechCapabilities.CAPABILITY_WRENCH, null)) {
            return item.getCapability(GregtechCapabilities.CAPABILITY_WRENCH, null).damageItem(1, true);
        }
        return false;
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {
        item.getCapability(GregtechCapabilities.CAPABILITY_WRENCH, null).damageItem(1, false);
    }
}
