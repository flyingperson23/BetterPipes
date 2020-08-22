package flyingperson.BetterPipes.item;

import flyingperson.BetterPipes.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWrench extends Item implements IBetterPipesWrench {
    public ItemWrench() {
        setRegistryName("wrench");
        setUnlocalizedName(BetterPipes.MODID+".wrench");
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public void damage(ItemStack stack) {

    }
}
