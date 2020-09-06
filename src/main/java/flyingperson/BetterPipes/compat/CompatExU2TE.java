package flyingperson.BetterPipes.compat;

import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.transfernodes.BlockTransferHolder;
import com.rwtema.extrautils2.transfernodes.BlockTransferPipe;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CompatExU2TE extends CompatBaseTE {
    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Utils.fromTE(te).offset(direction), direction.getOpposite())) return true;
            if (Utils.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Utils.fromTE(te).offset(direction), direction.getOpposite())) return true;
            if (Utils.hasCapability(CapabilityEnergy.ENERGY, Utils.fromTE(te).offset(direction), direction.getOpposite())) return true;

            if (Utils.getBlockOffset(te, direction) instanceof BlockTransferPipe) return true;
            return Utils.getBlockOffset(te, direction) instanceof BlockTransferHolder;
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (Utils.getBlockOffset(te, facing) instanceof BlockTransferHolder) {
                    if (BlockTransferPipe.isUnblocked(Objects.requireNonNull(TileTransferHolder.getCenterPipeState(((TileTransferHolder) te).getCenterPipeIndex())), facing) &&
                            BlockTransferPipe.isUnblocked(Objects.requireNonNull(TileTransferHolder.getCenterPipeState(((TileTransferHolder) Objects.requireNonNull(Utils.fromTE(te).world.getTileEntity(Utils.fromTE(te).pos.offset(facing, 1)))).getCenterPipeIndex())), facing.getOpposite())) connections.add(facing);
                } else if (Utils.getBlockOffset(te, facing) instanceof BlockTransferPipe) {
                    if (BlockTransferPipe.isUnblocked(Objects.requireNonNull(TileTransferHolder.getCenterPipeState(((TileTransferHolder) te).getCenterPipeIndex())), facing) &&
                            BlockTransferPipe.isUnblocked(Utils.fromTE(te).offset(facing).state, facing.getOpposite())) connections.add(facing);
                } else {
                    if (BlockTransferPipe.isUnblocked(Objects.requireNonNull(TileTransferHolder.getCenterPipeState(((TileTransferHolder) te).getCenterPipeIndex())), facing)) {
                        if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Utils.fromTE(te).offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                        if (Utils.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Utils.fromTE(te).offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                        if (Utils.hasCapability(CapabilityEnergy.ENERGY, Utils.fromTE(te).offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                    }
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
        if (te != null) {
            connect2(te, direction);
            IBlockState state = te.getWorld().getBlockState(te.getPos().offset(direction, 1));
            if (state.getBlock() instanceof BlockTransferPipe) {
                new CompatExU2().connect2(new BlockWrapper(te.getPos().offset(direction, 1), state, te.getWorld()), direction.getOpposite());
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (te != null) {
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
            Utils.update(Utils.fromTE(te), direction);
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
            Utils.update(Utils.fromTE(te), direction);
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
