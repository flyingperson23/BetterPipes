package flyingperson.BetterPipes.compat;

import com.lothrazar.cyclicmagic.block.cable.TileEntityCableBase;
import com.lothrazar.cyclicmagic.block.cable.energy.TileEntityCablePower;
import com.lothrazar.cyclicmagic.block.cable.fluid.TileEntityFluidCable;
import com.lothrazar.cyclicmagic.block.cable.item.TileEntityItemCable;
import com.lothrazar.cyclicmagic.block.cable.multi.TileEntityCableBundle;
import com.lothrazar.cyclicmagic.block.cablepump.TileEntityBasePump;
import com.lothrazar.cyclicmagic.block.cablepump.energy.TileEntityEnergyPump;
import com.lothrazar.cyclicmagic.block.cablepump.fluid.TileEntityFluidPump;
import com.lothrazar.cyclicmagic.block.cablepump.item.TileEntityItemPump;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatCyclic extends CompatBaseTE {

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (te instanceof TileEntityCableBase) {
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (connectTo != null) {
                if ((te instanceof TileEntityCablePower | te instanceof TileEntityCableBundle) && connectTo.hasCapability(CapabilityEnergy.ENERGY, direction.getOpposite())) return true;
                if ((te instanceof TileEntityItemCable | te instanceof TileEntityCableBundle) && connectTo.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite())) return true;
                if ((te instanceof TileEntityFluidCable | te instanceof TileEntityCableBundle) && connectTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())) return true;
                if (te instanceof TileEntityCablePower && (connectTo instanceof TileEntityCablePower | connectTo instanceof TileEntityCableBundle)) return true;
                if (te instanceof TileEntityFluidCable && (connectTo instanceof TileEntityFluidCable | connectTo instanceof TileEntityCableBundle)) return true;
                if (te instanceof TileEntityItemCable && (connectTo instanceof TileEntityItemCable | connectTo instanceof TileEntityCableBundle)) return true;
                if (te instanceof TileEntityCableBundle && connectTo instanceof TileEntityCableBase) return true;
            }
        }
        if (te instanceof TileEntityBasePump) {
            TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
            if (connectTo != null) {
                if (te instanceof TileEntityEnergyPump && connectTo.hasCapability(CapabilityEnergy.ENERGY, direction.getOpposite())) return true;
                if (te instanceof TileEntityEnergyPump && connectTo.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite())) return true;
                if (te instanceof TileEntityEnergyPump && connectTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())) return true;
                if (te instanceof TileEntityEnergyPump && (connectTo instanceof TileEntityCablePower | connectTo instanceof TileEntityCableBundle)) return true;
                if (te instanceof TileEntityFluidPump && (connectTo instanceof TileEntityFluidCable | connectTo instanceof TileEntityCableBundle)) return true;
                if (te instanceof TileEntityItemPump && (connectTo instanceof TileEntityItemCable | connectTo instanceof TileEntityCableBundle)) return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (te instanceof TileEntityCableBase) {
            TileEntityCableBase tile = (TileEntityCableBase) te;
            for (EnumFacing e : EnumFacing.values()) {
                if (!tile.getBlacklist(e) && canConnect(te, e)) connections.add(e);
            }
        }
        if (te instanceof TileEntityBasePump) {
            TileEntityBasePump tile = (TileEntityBasePump) te;
            for (EnumFacing e : EnumFacing.values()) {
                if (!tile.getBlacklist(e) && canConnect(te, e)) connections.add(e);
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityCableBase | te instanceof TileEntityBasePump;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (te != null) {
            te.getWorld();
            if (te instanceof TileEntityCableBase) {
                TileEntityCableBase tile = (TileEntityCableBase) te;
                if (!getConnections(te).contains(direction)) tile.toggleBlacklist(direction);
                Utils.update(te);
                te.getWorld().notifyNeighborsOfStateChange(tile.getPos(), tile.getBlockType(), true);
            }
            if (te instanceof TileEntityBasePump) {
                TileEntityBasePump tile = (TileEntityBasePump) te;
                if (!getConnections(te).contains(direction)) tile.toggleBlacklist(direction);
                Utils.update(te);
                te.getWorld().notifyNeighborsOfStateChange(tile.getPos(), tile.getBlockType(), true);
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (te != null) {
            if (te instanceof TileEntityCableBase) {
                TileEntityCableBase tile = (TileEntityCableBase) te;
                if (getConnections(te).contains(direction)) tile.toggleBlacklist(direction);
                Utils.update(te);
                te.getWorld().notifyNeighborsOfStateChange(tile.getPos(), tile.getBlockType(), true);
            }
            if (te instanceof TileEntityBasePump) {
                TileEntityBasePump tile = (TileEntityBasePump) te;
                if (getConnections(te).contains(direction)) tile.toggleBlacklist(direction);
                Utils.update(te);
                te.getWorld().notifyNeighborsOfStateChange(tile.getPos(), tile.getBlockType(), true);
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        Collection<ItemStack> drops = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityCableBase tile = (TileEntityCableBase) te;
            drops.addAll(tile.getBlockType().getDrops(tile.getWorld(), tile.getPos(), te.getWorld().getBlockState(te.getPos()), 0));
        }
        return drops;
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("cyclicmagic", "item_pipe")));
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("cyclicmagic", "energy_pipe")));
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("cyclicmagic", "fluid_pipe")));
        blocks.add(Block.REGISTRY.getObject(new ResourceLocation("cyclicmagic", "bundled_pipe")));
        return blocks;
    }

    @Override
    public float getBreakSpeed() {
        return 30f;
    }
}
