package flyingperson.BetterPipes;

import flyingperson.BetterPipes.client.GridRenderer;
import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.network.ConnectionGrid;
import flyingperson.BetterPipes.network.MessageGetConnections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BetterPipesEventHandler {
    @SubscribeEvent
    public void onEvent(BlockEvent.PlaceEvent event) {


        for (CompatBase i : BetterPipes.COMPAT_LIST) {
            if (i.isModLoaded()) {
                BlockPos pos = event.getPos();
                if (event.getWorld().getBlockState(pos).getBlock().hasTileEntity(event.getWorld().getBlockState(pos))) {
                    for (EnumFacing j : EnumFacing.VALUES) {
                        if (i.canConnect(event.getWorld().getTileEntity(pos), j)) {
                            Vec3d vec3d = Utils.getVecHitFromPos(pos, j);
                            Vec3d vec3d1 = Utils.getVecHitFromPos(pos, j.getOpposite());

                            i.disconnect(event.getWorld().getTileEntity(pos), j, event.getPlayer(), (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
                            i.disconnect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer(), (float) vec3d1.x, (float) vec3d1.y, (float) vec3d1.z);

                            boolean lookingAt = false;

                            RayTraceResult rt1 = Utils.getBlockLookingat1(event.getPlayer(), pos);
                            RayTraceResult rt2 = Utils.getBlockLookingat2(event.getPlayer(), pos);


                            if (rt1 != null) {
                                if (Utils.arePosEqual(rt1.getBlockPos(), pos.offset(j, 1))) lookingAt = true;
                            }
                            if (rt2 != null) {
                                if (Utils.arePosEqual(rt2.getBlockPos(), pos.offset(j, 1))) lookingAt = true;
                            }


                            if ((lookingAt & Config.clicking_on_pipes_connects_them) | (event.getPlayer().isSneaking() & Config.sneaking_makes_pipes_connect)) {
                                i.connect(event.getWorld().getTileEntity(pos), j, event.getPlayer(), (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
                                i.connect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer(), (float) vec3d1.x, (float) vec3d1.y, (float) vec3d1.z);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEvent(TickEvent event) {
        BetterPipes.instance.counter++;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null) {
            RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(player);
            if (lookingAt != null) {
                BlockPos pos = lookingAt.getBlockPos();
                for (CompatBase compat : BetterPipes.COMPAT_LIST) {
                    if (compat.isAcceptable(player.world.getTileEntity(pos))) {
                        if (player.getHeldItemMainhand().getItem() == ModItems.itemWrench) {
                            BetterPipes.INSTANCE.sendToServer(new MessageGetConnections(pos));
                            if (ConnectionGrid.instance() != null) {
                                if (ConnectionGrid.instance().get(pos) != null) {
                                    GridRenderer.render(player, pos, lookingAt.sideHit, event.getPartialTicks(), ConnectionGrid.instance().get(pos).connections);
                                    EnumFacing directionHovered = Utils.getDirection(lookingAt.sideHit, lookingAt.hitVec);
                                    GridRenderer.renderSide(player, pos, directionHovered, event.getPartialTicks());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (BetterPipes.instance.counter % 100 == 0) ConnectionGrid.instance().clear();
    }
}