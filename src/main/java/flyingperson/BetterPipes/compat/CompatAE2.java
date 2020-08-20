package flyingperson.BetterPipes.compat;

import appeng.api.networking.IGridHost;
import appeng.api.util.AEPartLocation;
import appeng.tile.networking.TileCableBus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class CompatAE2 extends CompatBase {
    @Override
    public boolean isModLoaded() {
        return Loader.isModLoaded("appliedenergistics2");
    }

    @Override
    public boolean canConnect(TileEntity te, EnumFacing direction) {
        return false;
    }

    @Override
    public ArrayList<EnumFacing> getConnections(TileEntity te) {
        ArrayList<EnumFacing> connections = new ArrayList<>();
        if (isAcceptable(te)) {
            TileCableBus tile = (TileCableBus) te;
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (tile.getCableBus().getCableConnectionType(AEPartLocation.fromFacing(facing)).isValid() && te.getWorld().getTileEntity(te.getPos().offset(facing, 1)) instanceof IGridHost) connections.add(facing);
            }
        }
        return connections;
    }

    @Override
    public boolean isAcceptable(TileEntity te) {
        return te instanceof TileCableBus;
    }

    @Override
    public void connect(TileEntity te, EnumFacing direction, EntityPlayer player) {

    }

    @Override
    public void disconnect(TileEntity te, EnumFacing direction, EntityPlayer player) {
        if (isAcceptable(te)) {
            TileCableBus tile = (TileCableBus) te;
            tile.addPart(new ItemStack(Blocks.DIRT), AEPartLocation.fromFacing(direction), player, player.getActiveHand());
        }
    }
}
