package flyingperson.BetterPipes.compat.wrench;

import appeng.api.AEApi;
import flyingperson.BetterPipes.BPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class AE2WrenchProvider implements IWrenchProvider{
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.ae2Wrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        if (AEApi.instance().definitions().items().netherQuartzWrench().maybeItem().isPresent()) {
            if (item.getItem() == AEApi.instance().definitions().items().netherQuartzWrench().maybeItem().get()) return true;
        }
        if (AEApi.instance().definitions().items().certusQuartzWrench().maybeItem().isPresent()) {
            if (item.getItem() == AEApi.instance().definitions().items().certusQuartzWrench().maybeItem().get()) return true;
        }
        return false;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return isAcceptable(item) && item.getItemDamage()<item.getMaxDamage();
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {
        item.damageItem(1, player);
    }
}
