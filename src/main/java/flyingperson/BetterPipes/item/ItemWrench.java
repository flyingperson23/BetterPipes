package flyingperson.BetterPipes.item;

import flyingperson.BetterPipes.*;
import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    public static void wrenchUse(PlayerInteractEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        World worldIn = event.getWorld();
        event.getWorld().playSound(event.getEntityPlayer(), event.getPos(), ModSounds.wrench_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        for (CompatBase compat : BetterPipes.instance.COMPAT_LIST) {
            RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(player);
            if (lookingAt != null) {
                BlockPos pos = lookingAt.getBlockPos();
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null) {
                    if (compat.isAcceptable(te)) {
                        if (!player.isSneaking()) {
                            EnumFacing sideToggled = Utils.getDirection(lookingAt.sideHit, lookingAt.hitVec);
                            if (compat.getConnections(te).contains(sideToggled)) {
                                compat.disconnect(te, sideToggled, player);
                                compat.disconnect(worldIn.getTileEntity(pos.offset(sideToggled, 1)), sideToggled.getOpposite(), player);
                            } else {
                                compat.connect(te, sideToggled, player);
                                compat.connect(worldIn.getTileEntity(pos.offset(sideToggled, 1)), sideToggled.getOpposite(), player);
                            }
                            worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
                            te.markDirty();
                        }
                    }
                }
            }
        }
    }

}
