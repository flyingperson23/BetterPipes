package flyingperson.BetterPipes.compat;

import cofh.thermaldynamics.duct.ConnectionType;
import cofh.thermaldynamics.duct.tiles.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;

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
                if (connectionTE != null) {
                    if (connectionTE instanceof TileGrid) return true;
                    if (connectionTE.hasCapability(CapabilityEnergy.ENERGY, direction.getOpposite()))
                        return true;
                    if (connectionTE.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite()))
                        return true;
                    if (connectionTE.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite()))
                        return true;
                }
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
            }
            te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
            te.markDirty();
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player, float hitX, float hitY, float hitZ) {
        if (canConnect(te, direction)) {
            if (te instanceof TileGrid) {
                TileGrid grid = (TileGrid) te;

                grid.setConnectionType(direction.getIndex(), ConnectionType.BLOCKED);

                grid.updateLighting();
            }
            te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
            te.markDirty();
        }
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (te instanceof TileGrid) {
            TileGrid grid = (TileGrid) te;
            for (EnumFacing e : EnumFacing.VALUES) {
                if (canConnect(te, e) && grid.getConnectionType(e.getIndex()) == ConnectionType.NORMAL) {
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
