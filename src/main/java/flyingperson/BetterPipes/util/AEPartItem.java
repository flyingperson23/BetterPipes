package flyingperson.BetterPipes.util;

import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class AEPartItem extends Item implements IPartItem {
    public AEPartItem() {
        setRegistryName("aepart");
        setUnlocalizedName(BetterPipes.MODID+".aepart");
    }

    @Nullable
    @Override
    public IPart createPartFromItemStack(ItemStack itemStack) {
        return new AEBlockPart();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
