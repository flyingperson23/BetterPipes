package flyingperson.BetterPipes.compat.gtce;

import flyingperson.BetterPipes.Utils;
import flyingperson.BetterPipes.compat.CompatBase;
import gregtech.api.pipenet.tile.AttachmentType;
import gregtech.common.pipelike.inventory.tile.TileEntityInventoryPipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;

public class CompatGTCEItem extends CompatBase {
    @Override
    public boolean isModLoaded() {
        return Loader.isModLoaded("gregtech");
    }

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        TileEntity te2 = te.getWorld().getTileEntity(te.getPos().offset(direction, 1));
        if (te2 != null) {
            return te2.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite()) | te2 instanceof TileEntityInventoryPipe;
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileEntityInventoryPipe cable = (TileEntityInventoryPipe) te;
            int mask = cable.getPipeBlock().getActualConnections(cable.getPipeBlock().getPipeTileEntity(te), te.getWorld());
            connections = Utils.fromGTCEBitmask(mask);
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileEntityInventoryPipe;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityInventoryPipe cable = (TileEntityInventoryPipe) te;
            cable.setConnectionBlocked(AttachmentType.PIPE, direction, false);

            te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
            te.markDirty();
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileEntityInventoryPipe cable = (TileEntityInventoryPipe) te;
            cable.setConnectionBlocked(AttachmentType.PIPE, direction, true);

            te.getWorld().notifyBlockUpdate(te.getPos(), te.getWorld().getBlockState(te.getPos()), te.getWorld().getBlockState(te.getPos()), 3);
            te.markDirty();
        }
    }

}
