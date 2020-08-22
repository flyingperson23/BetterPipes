package flyingperson.BetterPipes;

import net.minecraft.item.ItemStack;

public interface IBetterPipesWrench {
    boolean canBeUsed();
    void damage(ItemStack stack);
}
