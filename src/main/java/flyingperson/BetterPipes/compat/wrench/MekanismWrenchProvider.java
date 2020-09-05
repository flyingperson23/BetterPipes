package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import mekanism.common.MekanismItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MekanismWrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.mekanismWrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == MekanismItems.Configurator;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return MekanismItems.Configurator.getEnergy(item) >= MekanismItems.Configurator.ENERGY_PER_CONFIGURE;
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {
        MekanismItems.Configurator.setEnergy(item, MekanismItems.Configurator.getEnergy(item) - MekanismItems.Configurator.ENERGY_PER_CONFIGURE);
    }
}
