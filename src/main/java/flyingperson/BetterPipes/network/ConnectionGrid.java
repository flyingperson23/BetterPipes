package flyingperson.BetterPipes.network;

import flyingperson.BetterPipes.util.Utils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class ConnectionGrid {
    private static final ConnectionGrid CLIENT_INSTANCE = new ConnectionGrid();

    private Set<ConnectionBlock> connectionGrid = Collections.newSetFromMap(new WeakHashMap<>());


    public static ConnectionGrid instance() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient() ? CLIENT_INSTANCE : null;
    }

    public void clear() {
        connectionGrid = Collections.newSetFromMap(new WeakHashMap<>());
    }

    public void add(ConnectionBlock connection) {
        connectionGrid.removeIf(connectionBlock -> Utils.arePosEqual(connection.pos, connectionBlock.pos));
        connectionGrid.add(connection);
    }

    public ConnectionBlock get(BlockPos pos) {
        for (ConnectionBlock b : connectionGrid) {
            if (Utils.arePosEqual(b.pos, pos)) return b;
        }
        return null;
    }

}
