package flyingperson.BetterPipes.util;

import buildcraft.api.transport.IItemPluggable;
import buildcraft.transport.tile.TilePipeHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BCEventHandler {
    @SubscribeEvent
    public void onEvent(PlayerInteractEvent event) {
        if (!(event instanceof PlayerInteractEvent.LeftClickEmpty | event instanceof PlayerInteractEvent.LeftClickBlock)) {
            if (event.getItemStack().getItem() instanceof IItemPluggable) {
                RayTraceResult lookingAt = Utils.getBlockLookingat1(event.getEntityPlayer());
                if (lookingAt != null) {
                    BlockPos pos = lookingAt.getBlockPos();
                    if (event.getWorld().getTileEntity(pos) instanceof TilePipeHolder) {
                        TilePipeHolder tile = (TilePipeHolder) event.getWorld().getTileEntity(pos);
                        if (tile != null) {
                            tile.replacePluggable(lookingAt.sideHit, null);
                        }
                    }
                }
            }
        }
    }
}
