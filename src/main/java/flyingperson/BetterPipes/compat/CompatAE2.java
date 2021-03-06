package flyingperson.BetterPipes.compat;

import appeng.api.AEApi;
import appeng.api.networking.IGridHost;
import appeng.api.parts.IPart;
import appeng.api.util.AEPartLocation;
import appeng.parts.misc.PartCableAnchor;
import appeng.parts.networking.PartCable;
import appeng.parts.networking.PartDenseCable;
import appeng.tile.networking.TileCableBus;
import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.compat.wrench.IWrenchProvider;
import flyingperson.BetterPipes.util.AEBlockPart;
import flyingperson.BetterPipes.util.RegisterAEStuff;
import flyingperson.BetterPipes.util.Utils;
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

public class CompatAE2 extends CompatBaseTE {

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        if (isAcceptable(te)) {
            TileCableBus tile = (TileCableBus) te;
            TileEntity connectTo = tile.getWorld().getTileEntity(tile.getPos().offset(direction, 1));
            if (tile.getCableBus().getCableConnectionType(AEPartLocation.fromFacing(direction)).isValid() && te.getWorld().getTileEntity(te.getPos().offset(direction, 1)) instanceof IGridHost && (!tile.isBlocked(direction))) {
                if (connectTo != null) {
                    if (isAcceptable(connectTo)) {
                        IPart part = tile.getPart(AEPartLocation.fromFacing(direction));
                        IPart part2 = ((TileCableBus) connectTo).getPart(AEPartLocation.fromFacing(direction.getOpposite()));
                        return (part == null | part instanceof AEBlockPart) && (part2 == null | part2 instanceof AEBlockPart);
                    } else if (tile.getCableBus().getCableConnectionType(AEPartLocation.fromFacing(direction)).isValid() && connectTo instanceof IGridHost && (!tile.isBlocked(direction))) {
                        IPart part = tile.getPart(AEPartLocation.fromFacing(direction));
                        return part == null | part instanceof AEBlockPart;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileCableBus tile = (TileCableBus) te;
            for (EnumFacing facing : EnumFacing.VALUES) {
                IPart part = tile.getPart(AEPartLocation.fromFacing(facing));
                if (!(part instanceof AEBlockPart || part instanceof PartCableAnchor || part == null)) {
                    connections.add(facing);
                }
                if (tile.getCableBus().getCableConnectionType(AEPartLocation.fromFacing(facing)).isValid() && te.getWorld().getTileEntity(te.getPos().offset(facing, 1)) instanceof IGridHost && (!tile.isBlocked(facing))) {
                    TileEntity connectTo = te.getWorld().getTileEntity(te.getPos().offset(facing, 1));
                    if (connectTo instanceof TileCableBus) {
                        if (part == null && ((TileCableBus) connectTo).getPart(facing.getOpposite()) == null) connections.add(facing);
                    } else {
                        if (part == null) connections.add(facing);
                    }
                }
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        if (te instanceof TileCableBus) {
            TileCableBus tile = (TileCableBus) te;
            return tile.getPart(AEPartLocation.INTERNAL) instanceof PartCable;
        }
        return false;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te) && canConnect(te, direction) && (!getConnections(te).contains(direction))) {
            TileCableBus tile = (TileCableBus) te;
            if (tile.getPart(AEPartLocation.fromFacing(direction)) instanceof AEBlockPart) {
                tile.removePart(AEPartLocation.fromFacing(direction), false);
                tile.partChanged();
                tile.markForSave();
                tile.markForUpdate();
                if (te.getWorld() instanceof WorldServer) {
                    Utils.update(te);
                }
            }
        }
    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te) && canConnect(te, direction) && getConnections(te).contains(direction)) {
            TileCableBus tile = (TileCableBus) te;
            if (tile.canAddPart(new ItemStack(RegisterAEStuff.aepart, 1), AEPartLocation.fromFacing(direction))) {
                tile.addPart(new ItemStack(RegisterAEStuff.aepart, 1), AEPartLocation.fromFacing(direction), player, player.getActiveHand());
                tile.partChanged();
                tile.markForSave();
                tile.markForUpdate();
                if (te.getWorld() instanceof WorldServer) {
                    Utils.update(te);
                }
            }
        }
    }

    @Override
    public Collection<ItemStack> getDrops(TileEntity te, IBlockState blockState) {
        if (isAcceptable(te)) {
            return ((TileCableBus) te).getCableBus().getDrops(new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Block> getAcceptedBlocks() {
        ArrayList<Block> accepted = new ArrayList<>();
        if (AEApi.instance().definitions().blocks().multiPart().maybeBlock().isPresent()) accepted.add(AEApi.instance().definitions().blocks().multiPart().maybeBlock().get());
        return accepted;
    }

    @Override
    public float getBreakSpeed() {
        return 60f;
    }


}
