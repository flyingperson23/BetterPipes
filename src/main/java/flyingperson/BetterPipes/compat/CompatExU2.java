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
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CompatExU2 implements ICompatBase {

    @Override
    public boolean canConnect(BlockWrapper block, EnumFacing direction) {
        if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, block.offset(direction), direction.getOpposite())) return true;
        if (Utils.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, block.offset(direction), direction.getOpposite())) return true;
        if (Utils.hasCapability(CapabilityEnergy.ENERGY, block.offset(direction), direction.getOpposite())) return true;
        if (Utils.getBlockOffset(block, direction) instanceof BlockTransferPipe) return true;
        return Utils.getBlockOffset(block, direction) instanceof BlockTransferHolder;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(BlockWrapper block) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(block)) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (Utils.getBlockOffset(block, facing) instanceof BlockTransferPipe) {
                    if (BlockTransferPipe.isUnblocked(block.state, facing) && BlockTransferPipe.isUnblocked(block.offset(facing).state, facing.getOpposite())) connections.add(facing);
                } else if (Utils.getBlockOffset(block, facing) instanceof BlockTransferHolder) {
                    if (BlockTransferPipe.isUnblocked(block.state, facing) &&
                            BlockTransferPipe.isUnblocked(Objects.requireNonNull(TileTransferHolder.getCenterPipeState(((TileTransferHolder) Objects.requireNonNull(block.world.getTileEntity(block.pos.offset(facing, 1)))).getCenterPipeIndex())), facing.getOpposite())) connections.add(facing);
                } else {
                    if (BlockTransferPipe.isUnblocked(block.state, facing)) {
                        if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, block.offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                        if (Utils.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, block.offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                        if (Utils.hasCapability(CapabilityEnergy.ENERGY, block.offset(facing), facing.getOpposite())) {
                            connections.add(facing);
                        }
                    }
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        return block.state.getBlock() instanceof BlockTransferPipe;
    }

    @Override
    public void connect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        connect2(block, direction);
        if (block.world.getTileEntity(block.pos.offset(direction, 1)) instanceof TileTransferHolder) {
            new CompatExU2TE().connect2(block.world.getTileEntity(block.pos.offset(direction, 1)), direction.getOpposite());
        }
    }

    @Override
    public void disconnect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        disconnect2(block, direction);
        if (block.world.getTileEntity(block.pos.offset(direction, 1)) instanceof TileTransferHolder) {
            new CompatExU2TE().disconnect2(block.world.getTileEntity(block.pos.offset(direction, 1)), direction.getOpposite());
        }
    }

    public void connect2(BlockWrapper block, EnumFacing direction) {
        if (isAcceptable(block) && canConnect(block, direction)) {
            block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), false));
        }
        Utils.update(block, direction);
    }

    public void disconnect2(BlockWrapper block, EnumFacing direction) {
        if (isAcceptable(block) && canConnect(block, direction)) {
            block.world.setBlockState(block.pos, block.world.getBlockState(block.pos).withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), true));
        }
        Utils.update(block, direction);
    }

    @Override
    public Collection<ItemStack> getDrops(BlockWrapper block, IBlockState blockState) {
        return block.state.getBlock().getDrops(block.world, block.pos, block.state, 0);
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        return new ArrayList<>(XU2Entries.pipe.value);
    }

    @Override
    public float getBreakSpeed() {
        return 10f;
    }

}
