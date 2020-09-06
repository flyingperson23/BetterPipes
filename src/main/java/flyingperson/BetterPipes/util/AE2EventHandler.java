package flyingperson.BetterPipes.util;

import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.util.AEPartLocation;
import appeng.tile.networking.TileCableBus;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AE2EventHandler {
    @SubscribeEvent
    public void onEvent(PlayerInteractEvent event) {
        if (!(event instanceof PlayerInteractEvent.LeftClickEmpty | event instanceof PlayerInteractEvent.LeftClickBlock)) {
            if (event.getItemStack().getItem() instanceof IPartItem<?>) {
                RayTraceResult lookingAt = Utils.getBlockLookingat1(event.getEntityPlayer());
                if (lookingAt != null) {
                    BlockPos pos = lookingAt.getBlockPos();
                    if (event.getWorld().getTileEntity(pos) instanceof TileCableBus) {
                        TileCableBus tile = (TileCableBus) event.getWorld().getTileEntity(pos);
                        if (tile != null) {
                            IPart part = tile.getPart(lookingAt.sideHit);
                            if (part == null || part instanceof AEBlockPart) {
                                tile.removePart(AEPartLocation.fromFacing(lookingAt.sideHit), false);
                            }
                        }
                    }
                }
            }
        }
    }
}
