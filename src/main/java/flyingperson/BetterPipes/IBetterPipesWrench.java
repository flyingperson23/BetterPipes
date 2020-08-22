package flyingperson.BetterPipes;

import net.minecraft.item.ItemStack;

public interface IBetterPipesWrench {
    boolean canBeUsed(ItemStack stack);
    void damage(ItemStack stack);
}
