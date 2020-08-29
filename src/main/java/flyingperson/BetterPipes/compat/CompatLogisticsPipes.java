package flyingperson.BetterPipes.compat;

import logisticspipes.LPBlocks;
import logisticspipes.network.guis.upgrade.DisconnectionUpgradeConfigGuiProvider;
import logisticspipes.network.packets.upgrade.ToogleDisconnectionUpgradeSidePacket;
import logisticspipes.pipes.basic.CoreUnroutedPipe;
import logisticspipes.pipes.basic.LogisticsTileGenericPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatLogisticsPipes extends CompatBase {
    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            LogisticsTileGenericPipe tile = (LogisticsTileGenericPipe) te;
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (connectTo != null) return tile.pipe.transport.canPipeConnect(connectTo, direction);
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            LogisticsTileGenericPipe tile = (LogisticsTileGenericPipe) te;
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (tile.isPipeConnectedCached(facing)) connections.add(facing);
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof LogisticsTileGenericPipe;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            LogisticsTileGenericPipe tile = (LogisticsTileGenericPipe) te;
            if (tile.isRoutingPipe()) {
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {

    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (isAcceptable(te)) {
            LogisticsTileGenericPipe tile = (LogisticsTileGenericPipe) te;
            //drops.addAll(tile.getBlock().getDrops(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()), 0));
        }
        return drops;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        accepted.add(LPBlocks.pipe);
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 20f;
    }
}
