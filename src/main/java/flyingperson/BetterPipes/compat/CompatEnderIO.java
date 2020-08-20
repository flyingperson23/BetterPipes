package flyingperson.BetterPipes.compat;

import crazypants.enderio.base.conduit.IConduit;
import crazypants.enderio.base.conduit.IServerConduit;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class CompatEnderIO extends CompatBase {
    @Override
    public boolean isModLoaded() {
        return Loader.isModLoaded("enderio");
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
                        if (connectToConduit.getConduit(conduit.getClass()) != null) {
                            if (conduit.canConnectToConduit(direction, connectToConduit.getConduit(conduit.getClass()))) return true;
                        }
                    }
                } else {
                    if (conduit.canConnectToExternal(direction, true)) return true;
                }
            }
        }
        return false;
    }

    private boolean canConnectExternally(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
                if (!isAcceptable(connectTo)) {
                    return conduit.canConnectToExternal(direction, true);
                }
            }
        }
        return false;
    }

    private boolean canConnectToConduit(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
                if (isAcceptable(connectTo)) {
                    TileConduitBundle connectToConduit = (TileConduitBundle) connectTo;
                    if (connectToConduit != null) {
                        if (connectToConduit.getConduit(conduit.getClass()) != null) {
                            return conduit.canConnectToConduit(direction, connectToConduit.getConduit(conduit.getClass()));
                        }
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
                    if (!connections.contains(e)) connections.add(e);
                }
                for (EnumFacing e : conduit.getExternalConnections()) {
                    if (!connections.contains(e)) connections.add(e);
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
                if (canConnectToConduit(te, direction)) conduit.conduitConnectionAdded(direction);
                else if (canConnectExternally(te, direction)) conduit.externalConnectionAdded(direction);
                conduit.connectionsChanged();
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            System.out.println("Disconnected "+te.getPos()+direction);
            TileConduitBundle tile = (TileConduitBundle) te;
            for (IServerConduit conduit : tile.getServerConduits()) {
                if (conduit.getConduitConnections().contains(direction)) conduit.conduitConnectionRemoved(direction);
                if (conduit.getExternalConnections().contains(direction)) conduit.externalConnectionRemoved(direction);
                conduit.connectionsChanged();
            }
        }
    }
}
