package flyingperson.BetterPipes.network;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class ConnectionBlock {
    public BlockPos pos;
    public ArrayList<EnumFacing> connections;
    public ConnectionBlock(BlockPos pos, ArrayList<EnumFacing> connections) {
        this.pos = pos;
        this.connections = connections;
    }
}
