package flyingperson.BetterPipes.compat;

import flyingperson.BetterPipes.util.BlockWrapper;
import flyingperson.BetterPipes.util.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CompatBaseTE implements ICompatBase {
    /** @param block block to check for connectability
     *  @param direction the direction in which to check connectability
     *  @return is there a connectable block in that direction? */
    public abstract boolean canConnect(TileEntity block, EnumFacing direction);
    /** @param block block to get connections from
     *  @return ArrayList of active connections on block block */
    public abstract ArrayList<EnumFacing> getConnections(TileEntity block);
    /** @param block block to test
     *  @return can block te connect to a block of given compat type*/
    public abstract boolean isAcceptable(TileEntity block);
    /** @param block block to connect
     *  @param direction direction in which to connect
     *  @param player the player connecting/disconnecting the pipe*/
    public abstract void connect(TileEntity block, EnumFacing direction, EntityPlayer player);
    /** @param block block to disconnect
     *  @param direction the direction of the the block to disconnect from block
     *  @param player the player connecting/disconnecting the pipe*/
    public abstract void disconnect(TileEntity block, EnumFacing direction, EntityPlayer player);
    /**@param block block to get drops from
     * @param blockState state to get drops from
     * @return list of drops block should drop*/
    public abstract Collection<ItemStack> getDrops(TileEntity block, IBlockState blockState);

    @Override
    public boolean canConnect(BlockWrapper block, EnumFacing direction) {
        return canConnect(Utils.getTE(block), direction);
    }

    @Override
    public ArrayList<EnumFacing> getConnections(BlockWrapper block) {
        return getConnections(Utils.getTE(block));
    }

    @Override
    public boolean isAcceptable(BlockWrapper block) {
        return isAcceptable(Utils.getTE(block));
    }

    @Override
    public void connect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        connect(Utils.getTE(block), direction, player);
    }

    @Override
    public void disconnect(BlockWrapper block, EnumFacing direction, EntityPlayer player) {
        disconnect(Utils.getTE(block), direction, player);
    }

    @Override
    public Collection<ItemStack> getDrops(BlockWrapper block, IBlockState blockState) {
        return getDrops(Utils.getTE(block), blockState);
    }
}
