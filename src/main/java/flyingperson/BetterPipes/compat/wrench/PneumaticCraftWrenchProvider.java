package flyingperson.BetterPipes.compat.wrench;

import flyingperson.BetterPipes.BPConfig;
import me.desht.pneumaticcraft.common.item.ItemPneumaticWrench;
import me.desht.pneumaticcraft.lib.PneumaticValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PneumaticCraftWrenchProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.pneumaticcraftwrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item) {
        return item.getItem() == Item.REGISTRY.getObject(new ResourceLocation("pneumaticcraft", "pneumatic_wrench"));
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player) {
        ItemPneumaticWrench wrench = (ItemPneumaticWrench) Item.REGISTRY.getObject(new ResourceLocation("pneumaticcraft", "pneumatic_wrench"));
        if (wrench != null) return wrench.getPressure(item) > 0.1f;
        return false;
    }

    @Override
    public void use(ItemStack item, EntityPlayer player) {
        if (isAcceptable(item) && canBeUsed(item, player)) {
            ItemPneumaticWrench wrench = (ItemPneumaticWrench) Item.REGISTRY.getObject(new ResourceLocation("pneumaticcraft", "pneumatic_wrench"));
            if (wrench != null) wrench.addAir(item, -1*PneumaticValues.USAGE_PNEUMATIC_WRENCH);
        }
    }
}
