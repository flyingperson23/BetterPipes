package flyingperson.BetterPipes.compat;

import buildcraft.transport.BCTransportBlocks;
import buildcraft.transport.tile.TilePipeHolder;
import flyingperson.BetterPipes.util.BCBlockPart;
import flyingperson.BetterPipes.util.RegisterBCStuff;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatBC extends CompatBaseTE {

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TilePipeHolder tile = (TilePipeHolder) te;
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (connectTo != null) return tile.getPipe().behaviour.canConnect(direction, connectTo);
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TilePipeHolder tile = (TilePipeHolder) te;
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (tile.getPipe().isConnected(facing)) connections.add(facing);
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TilePipeHolder;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TilePipeHolder tile = (TilePipeHolder) te;
            if (tile.getPluggable(direction) instanceof  BCBlockPart) tile.replacePluggable(direction, null);
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TilePipeHolder tile = (TilePipeHolder) te;
            if (tile.getPluggable(direction) == null) tile.replacePluggable(direction, new BCBlockPart(RegisterBCStuff.pluggableDefinition, tile.getPipe().holder, direction));
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        Collection<ItemStack> drops = new ArrayList<>();
        if (isAcceptable(te)) {
            TilePipeHolder tile = (TilePipeHolder) te;
            drops.addAll(tile.getBlockType().getDrops(tile.getWorld(), tile.getPos(), tile.getCurrentState(), 0));
        }
        return drops;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        List<Block> acceptedBlocks = new ArrayList<>();
        acceptedBlocks.add(BCTransportBlocks.pipeHolder);
        return acceptedBlocks;
    }

    @Override
    public float getBreakSpeed() {
        return 20f;
    }

}
