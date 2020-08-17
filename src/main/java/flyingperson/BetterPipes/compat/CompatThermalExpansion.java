package flyingperson.BetterPipes.compat;

import cofh.thermaldynamics.duct.ConnectionType;
import cofh.thermaldynamics.duct.tiles.TileGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class CompatThermalExpansion extends CompatBase {

    @Override
    public boolean isModLoaded() {
        return Loader.isModLoaded("thermaldynamics");
    }


    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (te != null) {
            BlockPos connectTo = te.getPos().offset(direction, 1);
            if (te.getWorld().getBlockState(connectTo).getBlock().hasTileEntity(te.getWorld().getBlockState(connectTo))) {
                TileEntity connectionTE = te.getWorld().getTileEntity(connectTo);
                return connectionTE instanceof TileGrid;
            }
        }
        return false;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player, float hitX, float hitY, float hitZ) {
        if (canConnect(te, direction)) {
            if (te instanceof TileGrid) {
                TileGrid grid = (TileGrid) te;

                grid.setConnectionType(direction.getIndex(), ConnectionType.NORMAL);

                grid.updateLighting();

                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);

                grid.markDirty();
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player, float hitX, float hitY, float hitZ) {
        if (canConnect(te, direction)) {
            if (te instanceof TileGrid) {
                TileGrid grid = (TileGrid) te;

                grid.setConnectionType(direction.getIndex(), ConnectionType.BLOCKED);

                grid.updateLighting();

                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);

                grid.markDirty();
            }
        }
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (te instanceof TileGrid) {
            TileGrid grid = (TileGrid) te;
            for (EnumFacing e : EnumFacing.VALUES) {
                if (te.getWorld().getTileEntity(te.getPos().offset(e)) instanceof TileGrid && grid.getConnectionType(e.getIndex()) == ConnectionType.NORMAL) {
                    connections.add(e);
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileGrid;
    }
}
