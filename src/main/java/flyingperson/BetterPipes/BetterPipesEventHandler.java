package flyingperson.BetterPipes;

import flyingperson.BetterPipes.client.GridRenderer;
import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.compat.CompatEnderIO;
import flyingperson.BetterPipes.item.ItemWrench;
import flyingperson.BetterPipes.network.ConnectionGrid;
import flyingperson.BetterPipes.network.MessageGetConnections;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
                    if (i.isAcceptable(event.getWorld().getTileEntity(pos))) {
                        for (EnumFacing j : EnumFacing.VALUES) {

                            i.disconnect(event.getWorld().getTileEntity(pos), j, event.getPlayer());
                            i.disconnect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer());

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
                                i.connect(event.getWorld().getTileEntity(pos), j, event.getPlayer());
                                i.connect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer());
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
    public void onEvent(PlayerInteractEvent event) {
        if (event instanceof PlayerInteractEvent.RightClickItem | event instanceof  PlayerInteractEvent.RightClickBlock) {
            if (event.getEntityPlayer().getHeldItemMainhand().getItem() == ModItems.itemWrench && !event.getEntityPlayer().isSneaking()) {
                ItemWrench.wrenchUse(event);
                event.setCanceled(true);
            }
        }
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