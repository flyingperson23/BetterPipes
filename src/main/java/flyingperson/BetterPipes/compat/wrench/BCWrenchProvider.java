package flyingperson.BetterPipes.compat.wrench;

import buildcraft.api.BCItems;
import flyingperson.BetterPipes.BPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BCWrenchProvider implements IWrenchProvider {

    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.bcWrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == BCItems.Core.WRENCH;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return isAcceptable(item);
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {

    }
}
