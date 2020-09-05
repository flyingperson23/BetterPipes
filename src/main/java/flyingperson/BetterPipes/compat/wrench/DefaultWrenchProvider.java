package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DefaultWrenchProvider implements IWrenchProvider{
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.addWrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == ModItems.itemWrench;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return isAcceptable(item);
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {

    }
}
