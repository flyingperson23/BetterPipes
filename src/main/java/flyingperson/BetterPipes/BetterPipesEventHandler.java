package flyingperson.BetterPipes;

import flyingperson.BetterPipes.client.Renderer;
import flyingperson.BetterPipes.compat.CompatBaseRotation;
import flyingperson.BetterPipes.compat.ICompatBase;
import flyingperson.BetterPipes.compat.wrench.IWrenchProvider;
import flyingperson.BetterPipes.network.ConnectionGrid;
import flyingperson.BetterPipes.network.MessageGetConnections;
import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
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
        if (!event.getWorld().isRemote) {
            for (int j = 0; j < BetterPipes.instance.COMPAT_LIST.size(); j++) {
                ICompatBase i = BetterPipes.instance.COMPAT_LIST.get(j);
                if (!(i instanceof CompatBaseRotation)) {

                    BlockPos pos = event.getPos();
                    BlockWrapper block = new BlockWrapper(event);
                    if (i.isAcceptable(block)) {
                        for (EnumFacing facing : EnumFacing.VALUES) {

                            boolean lookingAt = false;

                            RayTraceResult rt1 = Utils.getBlockLookingat1(event.getPlayer());
                            RayTraceResult rt2 = Utils.getBlockLookingat2(event.getPlayer(), pos);


                            if (rt1 != null) {
                                if (Utils.arePosEqual(rt1.getBlockPos(), pos.offset(facing, 1))) lookingAt = true;
                            }
                            if (rt2 != null) {
                                if (Utils.arePosEqual(rt2.getBlockPos(), pos.offset(facing, 1))) lookingAt = true;
                            }

                            if (i.canConnect(block, facing) && ((lookingAt & BPConfig.general.clicking_on_pipes_connects_them) | (event.getPlayer().isSneaking() & BPConfig.general.sneaking_makes_pipes_connect))) {
                                i.connect(block, facing, event.getPlayer());
                                i.connect(block.offset(facing), facing.getOpposite(), event.getPlayer());
                            } else {
                                i.disconnect(block, facing, event.getPlayer());
                                i.disconnect(block.offset(facing), facing.getOpposite(), event.getPlayer());
                            }
                            BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.sendToServer(new MessageGetConnections(pos, j));
                            BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.sendToServer(new MessageGetConnections(pos.offset(facing, 1), j));
                            Utils.update(block, facing);
                        }
                    } else {
                        for (EnumFacing facing : EnumFacing.VALUES) {
                            BlockWrapper side = block.offset(facing);
                            if (side != null) {
                                if (i.isAcceptable(side)) {

                                    boolean lookingAt = false;

                                    RayTraceResult rt1 = Utils.getBlockLookingat1(event.getPlayer());
                                    RayTraceResult rt2 = Utils.getBlockLookingat2(event.getPlayer(), pos);


                                    if (rt1 != null) {
                                        if (Utils.arePosEqual(rt1.getBlockPos(), pos.offset(facing, 1)))
                                            lookingAt = true;
                                    }
                                    if (rt2 != null) {
                                        if (Utils.arePosEqual(rt2.getBlockPos(), pos.offset(facing, 1)))
                                            lookingAt = true;
                                    }

                                    if (i.canConnect(side, facing.getOpposite()) && (lookingAt & BPConfig.general.clicking_on_pipes_connects_them) | (event.getPlayer().isSneaking() & BPConfig.general.sneaking_makes_pipes_connect)) {
                                        i.connect(side, facing.getOpposite(), event.getPlayer());
                                    } else {
                                        i.disconnect(side, facing.getOpposite(), event.getPlayer());
                                    }
                                    BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.sendToServer(new MessageGetConnections(side.pos, j));
                                }
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
        if (BetterPipes.instance.counter % 3 == 0) {
            BetterPipes.instance.wrenchMap.clear();
        }
    }

    @SubscribeEvent
    public void onEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer().getHeldItemMainhand().getItem() == ModItems.itemWrench) {
            for (ICompatBase compat : BetterPipes.instance.COMPAT_LIST) {
                if (compat.isAcceptable(new BlockWrapper(event.getPos(), event.getPlayer()))) {
                    BlockWrapper block = new BlockWrapper(event.getPos(), event.getPlayer());
                    Utils.dropItems(compat.getDrops(block, block.state), event.getPlayer());
                    event.getWorld().playSound(event.getPlayer(), event.getPos(), ModSounds.wrench_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    event.getWorld().setBlockToAir(event.getPos());
                    event.getWorld().notifyBlockUpdate(event.getPos(), event.getState(), event.getState(), 3);
                    event.setCanceled(true);
                }
            }
        }
    }



    @SubscribeEvent
    public void onEvent(PlayerInteractEvent event) {
        if (!(event instanceof PlayerInteractEvent.LeftClickEmpty | event instanceof  PlayerInteractEvent.LeftClickBlock)) {
            RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(event.getEntityPlayer());
            for (int i = 0; i < BetterPipes.instance.COMPAT_LIST.size(); i++) {
                ICompatBase compat = BetterPipes.instance.COMPAT_LIST.get(i);
                if (lookingAt != null) {
                    if (compat.isAcceptable(new BlockWrapper(lookingAt.getBlockPos(), event.getEntityPlayer()))) {
                        if ((compat instanceof CompatBaseRotation && event.getEntityPlayer().isSneaking())  | (!(compat instanceof CompatBaseRotation ) &&  !event.getEntityPlayer().isSneaking())) {
                            for (IWrenchProvider c : BetterPipes.instance.WRENCH_PROVIDERS) {
                                if (c.enable() && c.canBeUsed(event.getEntityPlayer().getHeldItemMainhand(), event.getEntityPlayer()) && c.isAcceptable(event.getEntityPlayer().getHeldItemMainhand())) {
                                    if (event.isCancelable()) event.setCanceled(true);
                                    if (!BetterPipes.instance.wrenchMap.contains(lookingAt.getBlockPos())) {
                                        BetterPipes.instance.wrenchMap.add(lookingAt.getBlockPos());
                                        if (Utils.wrenchUse(event, i)) {
                                            c.use(event.getEntityPlayer().getHeldItemMainhand(), event.getEntityPlayer());
                                            return;
                                        }
                                    }
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
            if (Utils.isValidWrench(player.getHeldItemMainhand().getItem())) {
                RayTraceResult lookingAt = Utils.getBlockLookingAtIgnoreBB(player);
                if (lookingAt != null) {
                    BlockPos pos = lookingAt.getBlockPos();
                    for (int i = 0; i < BetterPipes.instance.COMPAT_LIST.size(); i++) {
                        ICompatBase compat = BetterPipes.instance.COMPAT_LIST.get(i);
                        if (compat.isAcceptable(new BlockWrapper(pos, player))) {
                            if ((compat instanceof CompatBaseRotation && Minecraft.getMinecraft().player.isSneaking()) | (!(compat instanceof CompatBaseRotation ) &&  !Minecraft.getMinecraft().player.isSneaking())) {
                                BetterPipes.BETTER_PIPES_NETWORK_WRAPPER.sendToServer(new MessageGetConnections(pos, i));
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
        }
        if (BetterPipes.instance.counter % 100 == 0) ConnectionGrid.instance().clear();
    }
}