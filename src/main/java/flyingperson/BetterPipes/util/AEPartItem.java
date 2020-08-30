package flyingperson.BetterPipes.util;

import appeng.api.parts.IPartItem;
import flyingperson.BetterPipes.BetterPipes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class AEPartItem extends Item implements IPartItem<AEBlockPart> {

    public AEPartItem() {
        this.setRegistryName("aepart");
        this.setUnlocalizedName(BetterPipes.MODID+".aepart");
    }

    @Nullable
    @Override
    public AEBlockPart createPartFromItemStack(ItemStack itemStack) {
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
            tooltip.add(I18n.format("betterpipes.tooltip.aepart1"));
            tooltip.add(I18n.format("betterpipes.tooltip.blocker2"));
        }
    }

}
