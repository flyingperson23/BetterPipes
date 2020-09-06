package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IC2WrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.ic2wrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item){
        return item.getItem() instanceof ItemToolWrench;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player){
        if (item.getItem() instanceof IElectricItem){
            return ElectricItem.manager.canUse(item, 50);
        }
        return true;
    }

    @Override
    public void use(ItemStack item, EntityPlayer player){
        if (item.getItem() instanceof IElectricItem){
            ElectricItem.manager.use(item, 50, player);
        } else {
            item.damageItem(1, player);
        }
    }
}
