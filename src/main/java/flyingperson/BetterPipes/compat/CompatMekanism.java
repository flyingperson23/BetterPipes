package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.util.Utils;
import mekanism.common.Mekanism;
import mekanism.common.MekanismBlocks;
import mekanism.common.MekanismItems;
import mekanism.common.tile.prefab.TileEntityBasicBlock;
import mekanism.common.tile.transmitter.TileEntitySidedPipe;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
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

public class CompatMekanism extends CompatBase {

    public CompatMekanism() {
        if (BPConfig.compat.mekanismWrench) BetterPipes.instance.WRENCH_LIST.add(MekanismItems.Configurator);
    }

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileEntityTransmitter tile = (TileEntityTransmitter) te;
            return tile.canConnect(direction);
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityTransmitter tile = (TileEntityTransmitter) te;
            connections.addAll(tile.getConnections(TileEntitySidedPipe.ConnectionType.NORMAL));
            connections.addAll(tile.getConnections(TileEntitySidedPipe.ConnectionType.PULL));
            connections.addAll(tile.getConnections(TileEntitySidedPipe.ConnectionType.PUSH));
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityTransmitter;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityTransmitter tile = (TileEntityTransmitter) te;
            tile.connectionTypes[direction.ordinal()] = TileEntitySidedPipe.ConnectionType.NORMAL;
            tile.refreshConnections();
            tile.refreshConnections(direction);
            tile.notifyTileChange();
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
            if (te instanceof TileEntityBasicBlock) {
                Mekanism.packetHandler.sendUpdatePacket((TileEntityBasicBlock) te);
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityTransmitter tile = (TileEntityTransmitter) te;
            tile.connectionTypes[direction.ordinal()] = TileEntitySidedPipe.ConnectionType.NONE;
            tile.refreshConnections();
            tile.refreshConnections(direction);
            tile.notifyTileChange();
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
            if (te instanceof TileEntityBasicBlock) {
                Mekanism.packetHandler.sendUpdatePacket((TileEntityBasicBlock) te);
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        ArrayList<ItemStack> items = new ArrayList<>();
        if (isAcceptable(te)) {
            items.addAll(te.getBlockType().getDrops(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()), 0));
        }
        return items;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(MekanismBlocks.Transmitter);
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return 10f;
    }
}
