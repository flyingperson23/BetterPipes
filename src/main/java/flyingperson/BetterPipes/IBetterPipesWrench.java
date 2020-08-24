package flyingperson.BetterPipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBetterPipesWrench {
    boolean canBeUsed(ItemStack stack, EntityPlayer player);
    void damage(ItemStack stack, EntityPlayer player);
}
