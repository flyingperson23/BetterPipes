package flyingperson.BetterPipes.compat.gtce;

import flyingperson.BetterPipes.compat.CompatBaseTE;
import flyingperson.BetterPipes.util.Utils;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.pipenet.tile.AttachmentType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompatGTCEEnergy extends CompatBaseTE {

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        TileEntity te2 = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
        if (te2 != null) {
            return te2.hasCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, direction.getOpposite()) | te2 instanceof TileEntityCable;
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityCable cable = (TileEntityCable) te;
            int mask = cable.getPipeBlock().getActualConnections(cable.getPipeBlock().getPipeTileEntity(te), te.getWorld());
            connections = Utils.fromGTCEBitmask(mask);
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityCable;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityCable cable = (TileEntityCable) te;
            cable.setConnectionBlocked(AttachmentType.PIPE, direction, false);

            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
            cable.notifyBlockUpdate();
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityCable cable = (TileEntityCable) te;
            cable.setConnectionBlocked(AttachmentType.PIPE, direction, true);

            if (te.getWorld() instanceof WorldServer) {
                Utils.update(te);
            }
            cable.notifyBlockUpdate();
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        return te.getBlockType().getDrops(te.getWorld(), te.getPos(), blockState, 0);
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        accepted.add(MetaBlocks.CABLE);
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 80f;
    }
}
