package flyingperson.BetterPipes.compat.wrench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchProvider {
    /** @return should this wrench provider be enabled? Add your providers to the list even if you don't necessarily
     * want it to be enabled so that you can change it without requiring an MC restart. Handle enabling/disabling
     * the module here. */
    boolean enable();
    /** @param item The item to check
     * @return Does this item count as one of your wrenches? */
    boolean isAcceptable(ItemStack item);
    /** @param item The item to check
     * @param player The player using the item
     * @return Can the item be used? I.e. does it have enough power/durability? */
    boolean canBeUsed(ItemStack item, EntityPlayer player);
    /**@param item the item being used
     * @param player the player using the item
     * This is called when a wrench from your module is actually used. Use this method to damage your wrench, remove power, etc. */
    void use(ItemStack item, EntityPlayer player);
}
