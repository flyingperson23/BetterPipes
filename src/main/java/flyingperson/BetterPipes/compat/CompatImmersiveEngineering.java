package flyingperson.BetterPipes.compat;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityFluidPipe;
import cofh.thermaldynamics.duct.tiles.TileGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class CompatImmersiveEngineering extends CompatBase {

    @Override
    public boolean isModLoaded() {
        return Loader.isModLoaded("immersiveengineering");
    }


    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (te instanceof TileEntityFluidPipe) {
            TileEntity te2 = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (te2 != null) return te2.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
        }
        return false;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (canConnect(te, direction)) {
            if (te instanceof TileEntityFluidPipe) {
                TileEntityFluidPipe pipe = (TileEntityFluidPipe) te;



                getInfo(pipe);




                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                te.markDirty();
            }
        }
    }


    public void getInfo(TileEntityFluidPipe te) {
        for (EnumFacing e : EnumFacing.VALUES) {
            System.out.println(e);
            System.out.println(te.sideConfig[e.getIndex()]);
            System.out.println(te.getConnectionStyle(e.getIndex()));
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (canConnect(te, direction)) {
            if (te instanceof TileEntityFluidPipe) {
                TileEntityFluidPipe pipe = (TileEntityFluidPipe) te;

                getInfo(pipe);



                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                te.markDirty();
            }
        }
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (te instanceof TileEntityFluidPipe) {
            TileEntityFluidPipe grid = (TileEntityFluidPipe) te;
            for (EnumFacing e : EnumFacing.VALUES) {
                if (grid.getConnectionStyle(e.getIndex()) != 0) {
                    connections.add(e);
                    System.out.println(e);
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityFluidPipe;
    }
}
