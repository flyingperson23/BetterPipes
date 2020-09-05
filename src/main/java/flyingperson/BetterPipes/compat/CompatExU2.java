package flyingperson.BetterPipes.compat;

import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.transfernodes.BlockTransferHolder;
import com.rwtema.extrautils2.transfernodes.BlockTransferPipe;
import com.rwtema.extrautils2.transfernodes.IPipe;
import com.rwtema.extrautils2.transfernodes.TileTransferHolder;
import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatExU2 extends CompatBaseNoTE {

    public CompatExU2() {
        BetterPipes.instance.WRENCH_LIST.add(Item.REGISTRY.getObject(new ResourceLocation("extrautils2", "wrench")));
    }

    @Override
    public boolean canConnect(BlockWrapper block, EnumFacing direction) {
        if (block.state.getBlock() instanceof IPipe) {
            if (Utils.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, block, direction) || block.offset(direction).state.getBlock() instanceof BlockTransferPipe || block.offset(direction).state.getBlock() instanceof BlockTransferHolder) return true;
            if (block.world.getTileEntity(block.pos.offset(direction, 1)) instanceof TileTransferHolder) return true;
            return BlockTransferPipe.shouldConnectTile(block.world, block.pos, direction, (IPipe) block.state.getBlock()) | BlockTransferPipe.shouldConnectPipe(block.world, block.pos, direction, (IPipe) block.state.getBlock());
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(BlockWrapper block) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(block)) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (BlockTransferPipe.isUnblocked(block.state, facing) && canConnect(block, facing)) {
                    connections.add(facing);
                }
                if (BlockTransferPipe.shouldConnectPipe(block.world, block.pos, facing, (IPipe) block.state.getBlock()) | BlockTransferPipe.shouldConnectTile(block.world, block.pos, facing, (IPipe) block.state.getBlock())) {
                    connections.add(facing);
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
            block.world.setBlockState(block.pos, block.state.withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), false));
        }
    }

    public void disconnect2(BlockWrapper block, EnumFacing direction) {
        if (isAcceptable(block) && canConnect(block, direction)) {
            block.world.setBlockState(block.pos, block.state.withProperty(BlockTransferPipe.SIDE_BLOCKED.get(direction), true));
        }
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
