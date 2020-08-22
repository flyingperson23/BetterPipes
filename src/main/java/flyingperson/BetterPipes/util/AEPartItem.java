package flyingperson.BetterPipes.util;

import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getItem() instanceof AEPartItem) {
            tooltip.add("This item serves no purpose other than to block off AE2 cable connections");
            tooltip.add("If you got this in survival mode, please report to mod author");
        }
    }

}
