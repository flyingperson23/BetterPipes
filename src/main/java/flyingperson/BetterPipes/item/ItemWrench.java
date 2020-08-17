package flyingperson.BetterPipes.item;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityFluidPipe;
import flyingperson.BetterPipes.*;
import flyingperson.BetterPipes.compat.CompatBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos1, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        for (CompatBase compat : BetterPipes.COMPAT_LIST) {
            RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(player);
            if (lookingAt != null) {
                BlockPos pos = lookingAt.getBlockPos();
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null) {
                    if (compat.isAcceptable(te)) {
                        if (!player.isSneaking()) {
                            EnumFacing sideToggled = Utils.getDirection(lookingAt.sideHit, lookingAt.hitVec);
                            worldIn.playSound(player, pos1, ModSounds.wrench_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            player.swingArm(EnumHand.MAIN_HAND);

                            Vec3d vec3d = Utils.getVecHitFromPos(pos, sideToggled);
                            Vec3d vec3d1 = Utils.getVecHitFromPos(pos, sideToggled.getOpposite());

                            if (compat.getConnections(te).contains(sideToggled)) {
                                compat.disconnect(te, sideToggled, player, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
                                compat.disconnect(worldIn.getTileEntity(pos.offset(sideToggled, 1)), sideToggled.getOpposite(), player, (float) vec3d1.x, (float) vec3d1.y, (float) vec3d1.z);
                            } else {
                                compat.connect(te, sideToggled, player, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
                                compat.connect(worldIn.getTileEntity(pos.offset(sideToggled, 1)), sideToggled.getOpposite(), player, (float) vec3d1.x, (float) vec3d1.y, (float) vec3d1.z);
                            }
                            worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
                            te.markDirty();
                        }
                    }
                }

                if (te instanceof TileEntityFluidPipe) {
                    TileEntityFluidPipe pipe = (TileEntityFluidPipe) te;
                    for (int i = 0; i < pipe.sideConfig.length; i++) System.out.println(i+" "+pipe.sideConfig[i]);
                    System.out.println(pipe.getConnectionStyle(0));
                }
            }
        }
        return EnumActionResult.PASS;
    }

}
