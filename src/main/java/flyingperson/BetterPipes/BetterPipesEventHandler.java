package flyingperson.BetterPipes;

import flyingperson.BetterPipes.client.Renderer;
import flyingperson.BetterPipes.compat.CompatBase;
import flyingperson.BetterPipes.network.ConnectionGrid;
import flyingperson.BetterPipes.network.MessageGetConnections;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BetterPipesEventHandler {
    @SubscribeEvent
    public void onEvent(BlockEvent.PlaceEvent event) {
        for (CompatBase i : BetterPipes.instance.COMPAT_LIST) {
            BlockPos pos = event.getPos();
            if (event.getWorld().getBlockState(pos).getBlock().hasTileEntity(event.getWorld().getBlockState(pos))) {
                if (i.isAcceptable(event.getWorld().getTileEntity(pos))) {
                    for (EnumFacing j : EnumFacing.VALUES) {

                        boolean lookingAt = false;

                        RayTraceResult rt1 = Utils.getBlockLookingat1(event.getPlayer(), pos);
                        RayTraceResult rt2 = Utils.getBlockLookingat2(event.getPlayer(), pos);


                        if (rt1 != null) {
                            if (Utils.arePosEqual(rt1.getBlockPos(), pos.offset(j, 1))) lookingAt = true;
                        }
                        if (rt2 != null) {
                            if (Utils.arePosEqual(rt2.getBlockPos(), pos.offset(j, 1))) lookingAt = true;
                        }

                        if (i.canConnect(event.getWorld().getTileEntity(pos), j)) {
                            if ((lookingAt & BPConfig.general.clicking_on_pipes_connects_them) | (event.getPlayer().isSneaking() & BPConfig.general.sneaking_makes_pipes_connect)) {
                                i.connect(event.getWorld().getTileEntity(pos), j, event.getPlayer());
                                i.connect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer());
                            } else {
                                i.disconnect(event.getWorld().getTileEntity(pos), j, event.getPlayer());
                                i.disconnect(event.getWorld().getTileEntity(pos.offset(j, 1)), j.getOpposite(), event.getPlayer());
                            }
                        }
                    }
                } else {
                    for (EnumFacing facing : EnumFacing.VALUES) {
                        TileEntity side = event.getWorld().getTileEntity(pos.offset(facing, 1));
                        if (side != null) {
                            if (i.isAcceptable(side)) {

                                boolean lookingAt = false;

                                RayTraceResult rt1 = Utils.getBlockLookingat1(event.getPlayer(), pos);
                                RayTraceResult rt2 = Utils.getBlockLookingat2(event.getPlayer(), pos);


                                if (rt1 != null) {
                                    if (Utils.arePosEqual(rt1.getBlockPos(), pos.offset(facing,1))) lookingAt = true;
                                }
                                if (rt2 != null) {
                                    if (Utils.arePosEqual(rt2.getBlockPos(), pos.offset(facing, 1))) lookingAt = true;
                                }

                                if ((lookingAt & BPConfig.general.clicking_on_pipes_connects_them) | (event.getPlayer().isSneaking() & BPConfig.general.sneaking_makes_pipes_connect)) {
                                    i.connect(side, facing.getOpposite(), event.getPlayer());
                                } else {
                                    i.disconnect(side, facing.getOpposite(), event.getPlayer());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer().getHeldItemMainhand().getItem() instanceof IBetterPipesWrench) {
            for (CompatBase compat : BetterPipes.instance.COMPAT_LIST) {
                if (compat.isAcceptable(event.getWorld().getTileEntity(event.getPos()))) {
                    ((IBetterPipesWrench) event.getPlayer().getHeldItemMainhand().getItem()).damage(event.getPlayer().getHeldItemMainhand(), event.getPlayer());
                    Utils.dropItems(compat.getDrops(event.getWorld().getTileEntity(event.getPos()), event.getState()), event.getPlayer());
                    event.getWorld().playSound(event.getPlayer(), event.getPos(), ModSounds.wrench_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    event.getWorld().setBlockToAir(event.getPos());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEvent(PlayerInteractEvent event) {
        if (event.getHand() == EnumHand.MAIN_HAND) {
            if (event instanceof PlayerInteractEvent.RightClickItem | event instanceof PlayerInteractEvent.RightClickBlock) {
                ItemStack held = event.getEntityPlayer().getHeldItemMainhand();
                if (held.getItem() instanceof IBetterPipesWrench) {
                    RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(event.getEntityPlayer());
                    if (lookingAt != null) {
                        for (CompatBase compat : BetterPipes.instance.COMPAT_LIST) {
                            if (compat.isAcceptable(event.getWorld().getTileEntity(lookingAt.getBlockPos()))) {
                                if (((IBetterPipesWrench) held.getItem()).canBeUsed(held, event.getEntityPlayer())) {
                                    ((IBetterPipesWrench) held.getItem()).damage(held, event.getEntityPlayer());
                                    Utils.wrenchUse(event);
                                    event.setCanceled(true);
                                    return;
                                }
                            }
                        }
                    }
                }
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
                for (CompatBase compat : BetterPipes.instance.COMPAT_LIST) {
                    if (compat.isAcceptable(player.world.getTileEntity(pos))) {
                        if (player.getHeldItemMainhand().getItem() instanceof IBetterPipesWrench) {
                            BetterPipes.INSTANCE.sendToServer(new MessageGetConnections(pos));
                            if (ConnectionGrid.instance() != null) {
                                if (ConnectionGrid.instance().get(pos) != null) {
                                    Renderer.renderOverlay(player, pos, lookingAt.sideHit, event.getPartialTicks(), ConnectionGrid.instance().get(pos).connections);
                                    EnumFacing directionHovered = Utils.getDirection(lookingAt.sideHit, lookingAt.hitVec);
                                    if (BPConfig.visual.highlight_wrench_hover) Renderer.renderSide(player, pos, directionHovered, event.getPartialTicks(),  0.5F);
                                    if (BPConfig.visual.block_outline && directionHovered != null) Renderer.drawOutline(player, pos.offset(directionHovered, 1), event.getPartialTicks());
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