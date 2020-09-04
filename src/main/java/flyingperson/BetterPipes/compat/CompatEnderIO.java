package flyingperson.BetterPipes.compat;

import crazypants.enderio.base.conduit.ConnectionMode;
import crazypants.enderio.base.conduit.IConduit;
import crazypants.enderio.base.conduit.IServerConduit;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import crazypants.enderio.conduits.init.ConduitObject;
import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatEnderIO extends CompatBase {

    public CompatEnderIO() {
        if (BPConfig.compat.enderIOWrench) BetterPipes.instance.WRENCH_LIST.add(Item.REGISTRY.getObject(new ResourceLocation("enderio", "item_yeta_wrench")));
    }

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
                if (isAcceptable(connectTo)) {
                    TileConduitBundle connectToConduit = (TileConduitBundle) connectTo;
                    if (connectToConduit != null) {
                        for (IServerConduit conduit2 : connectToConduit.getServerConduits()) {
                            if (conduit.getBaseConduitType() == conduit2.getBaseConduitType()) return true;
                        }
                    }
                } else {
                    if (conduit.canConnectToExternal(direction, true)) return true;
                }
            }
        }
        return false;
    }

    private boolean canConnectExternally(IServerConduit conduit, TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (!isAcceptable(connectTo)) {
                return conduit.canConnectToExternal(direction, true);
            }
        }
        return false;
    }

    private boolean canConnectToConduit(IServerConduit conduit, TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (isAcceptable(connectTo)) {
                TileConduitBundle connectToConduit = (TileConduitBundle) connectTo;
                if (connectToConduit != null) {
                    for (IServerConduit conduit2 : connectToConduit.getServerConduits()) {
                        if (conduit.getBaseConduitType() == conduit2.getBaseConduitType()) return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IConduit conduit : tile.getConduits()) {
                for (EnumFacing e : conduit.getConduitConnections()) {
                    if (!connections.contains(e) && conduit.getConnectionMode(e)!=ConnectionMode.DISABLED) connections.add(e);
                }
                for (EnumFacing e : conduit.getExternalConnections()) {
                    if (!connections.contains(e) && conduit.getConnectionMode(e)!=ConnectionMode.DISABLED) connections.add(e);
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileConduitBundle;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                if (canConnectToConduit(conduit, te, direction)) {
                    conduit.setConnectionMode(direction, ConnectionMode.NOT_SET);
                    conduit.conduitConnectionAdded(direction);
                }
                else if (canConnectExternally(conduit, te, direction)) {
                    conduit.externalConnectionAdded(direction);
                    if (conduit.supportsConnectionMode(ConnectionMode.IN_OUT)) conduit.setConnectionMode(direction, ConnectionMode.IN_OUT);
                    else conduit.setConnectionMode(direction, ConnectionMode.OUTPUT);
                }
                conduit.connectionsChanged();
            }
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                if (!canConnectToConduit(conduit, te, direction)) {
                    conduit.setConnectionMode(direction, ConnectionMode.DISABLED);
                    conduit.externalConnectionRemoved(direction);
                } else {
                    conduit.conduitConnectionRemoved(direction);
                }
                conduit.connectionsChanged();
            }
            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                drops.addAll(conduit.getDrops());
            }
        }
        return drops;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        accepted.add(ConduitObject.block_conduit_bundle.getBlock());
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 30f;
    }
}
