package flyingperson.BetterPipes.compat;

import cofh.thermaldynamics.block.BlockDuct;
import cofh.thermaldynamics.duct.Attachment;
import cofh.thermaldynamics.duct.ConnectionType;
import cofh.thermaldynamics.duct.attachments.cover.Cover;
import cofh.thermaldynamics.duct.tiles.*;
import cofh.thermaldynamics.init.TDBlocks;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatThermalDynamics extends CompatBase {

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
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (canConnect(te, direction)) {
            if (te instanceof TileGrid) {
                TileGrid grid = (TileGrid) te;

                grid.setConnectionType(direction.getIndex(), ConnectionType.NORMAL);

                grid.updateLighting();
            }
            if (te.getWorld() instanceof WorldServer) {
                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                te.markDirty();
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (canConnect(te, direction)) {
            if (te instanceof TileGrid) {
                TileGrid grid = (TileGrid) te;

                boolean flag = true;
                if (grid.attachmentData != null) {
                    if (grid.attachmentData.attachments != null) {
                        for (Attachment a : grid.attachmentData.attachments) {
                            if (a != null) {
                                if (Utils.fromIndex(a.side) == direction) {
                                    flag = false;
                                }
                            }
                        }
                    }
                    if (grid.attachmentData.covers != null) {
                        for (Cover c : grid.attachmentData.covers) {
                            if (c != null) {
                                if (Utils.fromIndex(c.side) == direction) {
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                if (flag) grid.setConnectionType(direction.getIndex(), ConnectionType.BLOCKED);

                grid.updateLighting();
            }
            if (te.getWorld() instanceof WorldServer) {
                te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
                te.markDirty();
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
        if (te instanceof TileGrid) {
            TileGrid grid = (TileGrid) te;
            for (EnumFacing e : EnumFacing.VALUES) {
                if (canConnect(te, e) && grid.getConnectionType(e.getIndex()) == ConnectionType.NORMAL) {
                    connections.add(e);
                }
            }
            if (grid.attachmentData != null) {
                if (grid.attachmentData.attachments != null) {
                    for (Attachment a : grid.attachmentData.attachments) {
                        if (a != null) {
                            if (Utils.fromIndex(a.side) != null) connections.add(Utils.fromIndex(a.side));
                        }
                    }
                }
                if (grid.attachmentData.covers != null) {
                    for (Cover c : grid.attachmentData.covers) {
                        if (c != null) {
                            if (Utils.fromIndex(c.side) != null) connections.add(Utils.fromIndex(c.side));
                        }
                    }
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileGrid;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        for (BlockDuct duct : TDBlocks.blockDuct) {
            accepted.add(duct.getBlockState().getBlock());
        }
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 20f;
    }
}
