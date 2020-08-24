package flyingperson.BetterPipes.compat;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityFluidPipe;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatImmersiveEngineering extends CompatBase {

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
        if (te.getWorld().isRemote) {
            if (canConnect(te, direction)) {
                if (te instanceof TileEntityFluidPipe) {
                    TileEntityFluidPipe pipe = (TileEntityFluidPipe) te;


                    //getInfo(pipe);
                    System.out.println("connect" + te.getWorld().isRemote);
                    pipe.sideConfig[direction.getIndex()] = 0;

                    if (!getConnections(te).contains(direction)) toggleSide(pipe, direction.getIndex());


                    te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                    te.markDirty();


                    pipe.markContainingBlockForUpdate(null);
                }
            }
        }
    }


    public void getInfo(TileEntityFluidPipe te) {
        for (EnumFacing e : EnumFacing.VALUES) {
            System.out.println(te.getPos());
            System.out.println(e);
            System.out.println(te.sideConfig[e.getIndex()]);
            System.out.println(te.getAvailableConnectionByte());
            System.out.println(te.hasOutputConnection(e));
            System.out.println();
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (te.getWorld().isRemote) {
            if (canConnect(te, direction)) {
                if (te instanceof TileEntityFluidPipe) {
                    TileEntityFluidPipe pipe = (TileEntityFluidPipe) te;

                    System.out.println("disconnect" + te.getWorld().isRemote);

                    //getInfo(pipe);

                    pipe.sideConfig[direction.getIndex()] = -1;
                    if (getConnections(te).contains(direction)) toggleSide(pipe, direction.getIndex());


                    te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                    te.markDirty();

                    pipe.markContainingBlockForUpdate(null);

                }
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        return te.getBlockType().getDrops(te.getWorld(), te.getPos(), blockState, 0);
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (te instanceof TileEntityFluidPipe) {
            TileEntityFluidPipe grid = (TileEntityFluidPipe) te;
            for (EnumFacing e : EnumFacing.VALUES) {
                if (grid.getConnectionStyle(e.getIndex()) != 0) {
                    connections.add(e);
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityFluidPipe;
    }


    public void toggleSide(TileEntityFluidPipe pipe, int side)
    {
        //pipe.sideConfig[side]++;
        //if(pipe.sideConfig[side] > 0)
        //    pipe.sideConfig[side] = -1;
        pipe.markDirty();

        //EnumFacing fd = Utils.fromIndex(side);
        //TileEntity connected = pipe.getWorld().getTileEntity(pipe.getPos().offset(fd));
        //if(connected instanceof TileEntityFluidPipe) {
        //    ((TileEntityFluidPipe)connected).sideConfig[fd.getOpposite().ordinal()] = pipe.sideConfig[side];
        //    connected.markDirty();
        //    pipe.getWorld().addBlockEvent(pipe.getPos().offset(fd), pipe.getBlockType(), 0, 0);
        //}
        pipe.updateConnectionByte(Utils.fromIndex(side));
        pipe.getWorld().addBlockEvent(pipe.getPos(), pipe.getBlockType(), 0, 0);
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        accepted.add(IEContent.blockMetalDevice1);
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 30f;
    }
}
