package flyingperson.BetterPipes.util;

import buildcraft.api.transport.pluggable.PluggableModelKey;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

public class BCModelKey extends PluggableModelKey {
    public BCModelKey(EnumFacing side) {
        super(BlockRenderLayer.CUTOUT, side);
    }
}
