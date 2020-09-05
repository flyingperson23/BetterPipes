package flyingperson.BetterPipes.compat.wrench;

import cofh.thermalfoundation.init.TFItems;
import flyingperson.BetterPipes.BPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ThermalDynamicsWrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.thermalWrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == TFItems.itemWrench;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return isAcceptable(item);
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {

    }
}
