package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExU2WrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.exu2wrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == Item.REGISTRY.getObject(new ResourceLocation("extrautils2", "wrench"));
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        return isAcceptable(item);
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {

    }
}
