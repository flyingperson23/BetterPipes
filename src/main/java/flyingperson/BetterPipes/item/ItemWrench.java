package flyingperson.BetterPipes.item;

import flyingperson.BetterPipes.*;
import flyingperson.BetterPipes.compat.ICompatBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

public class ItemWrench extends ItemTool {

    public static final ToolMaterial MATERIAL = EnumHelper.addToolMaterial("BetterPipesWrench", 3, -1, 0f, 1.0f, 0);


    public ItemWrench() {
        super(MATERIAL, getAcceptedBlocks());
        setRegistryName("wrench");
        setUnlocalizedName(BetterPipes.MODID+".wrench");
        setCreativeTab(CreativeTabs.TOOLS);
    }

    private static Set<Block> getAcceptedBlocks() {
        HashSet<Block> set = new HashSet<>();
        for (ICompatBase compat : BetterPipes.instance.COMPAT_LIST) {
            set.addAll(compat.getAcceptedBlocks());
        }
        return set;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) worldIn.playSound((EntityPlayer) entityLiving, pos, ModSounds.wrench_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        for (ICompatBase compat : BetterPipes.instance.COMPAT_LIST) {
            if (compat.getAcceptedBlocks().contains(state.getBlock())) {
                return compat.getBreakSpeed();
            }
        }
        return 0f;
    }
}
