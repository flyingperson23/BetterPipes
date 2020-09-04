package flyingperson.BetterPipes.util;

import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.api.transport.pluggable.PluggableDefinition;
import buildcraft.api.transport.pluggable.PluggableModelKey;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

public class BCBlockPart extends PipePluggable {

    public BCBlockPart(PluggableDefinition definition, IPipeHolder holder, EnumFacing side) {
        super(definition, holder, side);
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(8/16.0, 8/16.0, 8/16.0, 8/16.0, 8/16.0, 8/16.0);
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public ItemStack getPickStack() {
        return new ItemStack(RegisterBCStuff.blockPart, 0);
    }

    @Override
    public PluggableModelKey getModelRenderKey(BlockRenderLayer layer) {
        if (layer == BlockRenderLayer.CUTOUT) return new BCModelKey(side);
        return null;
    }

    @Override
    public void addDrops(NonNullList<ItemStack> toDrop, int fortune) {
        super.addDrops(toDrop, fortune);
        toDrop.removeIf(stack -> stack.getItem() == RegisterBCStuff.blockPart);
    }
}