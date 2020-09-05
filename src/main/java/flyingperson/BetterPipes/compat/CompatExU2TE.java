package flyingperson.BetterPipes.compat;

import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.transfernodes.BlockTransferHolder;
import com.rwtema.extrautils2.transfernodes.BlockTransferPipe;
import com.rwtema.extrautils2.transfernodes.IPipe;
import com.rwtema.extrautils2.transfernodes.TileTransferHolder;
import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatExU2TE extends CompatBase{
    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, te, direction) || te.getWorld().getBlockState(te.getPos().offset(direction, 1)).getBlock() instanceof BlockTransferPipe || te.getWorld().getBlockState(te.getPos().offset(direction, 1)).getBlock() instanceof BlockTransferHolder) return true;
            if (te.getWorld().getTileEntity(te.getPos().offset(direction, 1)) instanceof TileTransferHolder) return true;
            return BlockTransferPipe.shouldConnectTile(te.getWorld(), te.getPos(), direction, (IPipe) te) | BlockTransferPipe.shouldConnectPipe(te.getWorld(), te.getPos(), direction, (IPipe) te);
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            for (EnumFacing facing : EnumFacing.values()) {
                IBlockState state = TileTransferHolder.getCenterPipeState(((TileTransferHolder) te).getCenterPipeIndex());
                if (state != null) {
                    if (BlockTransferPipe.isUnblocked(state, facing) && canConnect(te, facing)) {
                        connections.add(facing);
                    }
                }
                if (BlockTransferPipe.shouldConnectPipe(te.getWorld(), te.getPos(), facing, (IPipe) te) | BlockTransferPipe.shouldConnectTile(te.getWorld(), te.getPos(), facing, (IPipe) te)) {
                    connections.add(facing);
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileTransferHolder;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            connect2(te, direction);
            IBlockState state = te.getWorld().getBlockState(te.getPos().offset(direction, 1));
            if (state.getBlock() instanceof BlockTransferPipe) {
                new CompatExU2().connect2(new BlockWrapper(te.getPos().offset(direction, 1), state, te.getWorld()), direction.getOpposite());
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            disconnect2(te, direction);
            IBlockState state = te.getWorld().getBlockState(te.getPos().offset(direction, 1));
            if (state.getBlock() instanceof BlockTransferPipe) {
                new CompatExU2().disconnect2(new BlockWrapper(te.getPos().offset(direction, 1), state, te.getWorld()), direction.getOpposite());
            }
        }
    }

    public void connect2(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileTransferHolder tile = (TileTransferHolder) te;
            if (TileTransferHolder.getCenterPipeState(tile.getCenterPipeIndex()) != null) {
                IBlockState state = TileTransferHolder.getCenterPipeState(tile.getCenterPipeIndex()).withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), false);
                byte b = (byte) BlockTransferPipe.stateBuilder.states2meta.get(state);
                NBTTagCompound nbt = tile.writeToNBT(new NBTTagCompound());
                nbt.setByte("CenterPipeState", b);
                tile.readFromNBT(nbt);
            }
        }
    }

    public void disconnect2(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileTransferHolder tile = (TileTransferHolder) te;
            if (TileTransferHolder.getCenterPipeState(tile.getCenterPipeIndex()) != null) {
                IBlockState state = TileTransferHolder.getCenterPipeState(tile.getCenterPipeIndex()).withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), true);
                byte b = (byte) BlockTransferPipe.stateBuilder.states2meta.get(state);
                NBTTagCompound nbt = tile.writeToNBT(new NBTTagCompound());
                nbt.setByte("CenterPipeState", b);
                tile.readFromNBT(nbt);
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        return new ArrayList<>(te.getBlockType().getDrops(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()), 0));
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(XU2Entries.holder.value);
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return new CompatExU2().getBreakSpeed();
    }
}
